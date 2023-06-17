package firesea.testserver.controller.api;

import firesea.testserver.domain.CommentDetailDto;
import firesea.testserver.domain.basic.DefaultRes;
import firesea.testserver.domain.basic.PageCustomDto;
import firesea.testserver.domain.entity.Comment;
import firesea.testserver.service.CommentService;
import io.swagger.annotations.*;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
@RestController
@RequiredArgsConstructor
@Api(value="댓글 관리 API")
public class CommentController {

    private final CommentService commentService;

    /**
     * 프론트엔드로부터 comment 를 전송받아 DB에 저장하는 데 사용된다.
     */

    @ApiOperation(value = "댓글 저장하기")
    @PostMapping("/api/user/comment/send")
    public DefaultRes sendComment(HttpServletRequest request, @RequestBody CommentSendingDto dto) {
        String username = (String) request.getAttribute("username");
        commentService.save(username, dto.getId(), dto.getCommentBody());
        return DefaultRes.res(20021, "댓글 작성 완료");
    }

    @Getter
    @ApiModel(value = "댓글 정보")
    private static class CommentSendingDto {
        @ApiModelProperty(value = "본문(textMessage)의 아이디", required = true)
        int id;
        @ApiModelProperty(value = "댓글(comment)의 내용", required = true)
        String commentBody;
    }


    @ApiOperation(value = "댓글 목록 가져오기")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value="본문(textMessage)의 아이디", paramType = "query"),
            @ApiImplicitParam(name = "page", value="page 번호 (0부터 시작)", paramType = "query")
    })
    @GetMapping("/api/comment/list")
    public DefaultRes<PageCustomDto<CommentDetailDto>> commentList(@RequestParam int id, @PageableDefault(size = 20) Pageable pageable) {

        PageCustomDto<CommentDetailDto> list = commentService.getList(id, pageable);
        return DefaultRes.res(20023, id + "번 글의 댓글 리스트" ,list);
    }

    @ApiOperation(value = "댓글 내용 수정하기")
    @PatchMapping("/api/user/comment/update")
    public DefaultRes updateComment(HttpServletRequest request, @RequestBody UpdatedComment dto) {
        String username = (String) request.getAttribute("username");
        commentService.updateComment(dto.getCommentId(), dto.getCommentBody());
        return DefaultRes.res(20026, "번 글의 댓글을 수정하였습니다");
    }
    @ApiOperation(value = "댓글 삭제하기")
    @DeleteMapping("/api/user/comment/delete")
    public DefaultRes deleteComment(HttpServletRequest request, HttpServletRequest httpServletRequest, @RequestBody UpdatedComment dto) {
        String username = (String) request.getAttribute("username");
        commentService.deleteComment(dto.getCommentId());
        return DefaultRes.res(20027, "번 글의 댓글을 삭제하였습니다");
    }




    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    @ApiModel
    static class UpdatedComment{
        @ApiModelProperty(value = "댓글의 아이디", required = true )
        int commentId;
        @ApiModelProperty(value = "수정할 댓글의 내용" )
        String commentBody;
    }
}

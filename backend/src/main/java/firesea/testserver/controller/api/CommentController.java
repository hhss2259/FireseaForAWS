package firesea.testserver.controller.api;

import firesea.testserver.domain.CommentDetailDto;
import firesea.testserver.domain.basic.DefaultRes;
import firesea.testserver.domain.basic.PageCustomDto;
import firesea.testserver.domain.entity.Comment;
import firesea.testserver.service.CommentService;
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
public class CommentController {

    private final CommentService commentService;

    /**
     * 프론트엔드로부터 comment 를 전송받아 DB에 저장하는 데 사용된다.
     */
    @PostMapping("/api/user/comment/send")
    public DefaultRes sendComment(HttpServletRequest request, @RequestBody CommentSendingDto dto) {
        String username = (String) request.getAttribute("username");
        log.info("username = {}", username);
        log.info("id = {}", dto.getId());
        log.info("commentBody = {}", dto.getCommentBody());
        commentService.save(username, dto.getId(), dto.getCommentBody());
        return DefaultRes.res(20021, "댓글 작성 완료");
    }

    @Getter
    private static class CommentSendingDto {
        int id;
        String commentBody;
    }


    @GetMapping("/api/comment/list")
    public DefaultRes<PageCustomDto<CommentDetailDto>> commentList(@RequestParam int id, @PageableDefault(size = 20) Pageable pageable) {

        PageCustomDto<CommentDetailDto> list = commentService.getList(id, pageable);
        return DefaultRes.res(20023, id + "번 글의 댓글 리스트" ,list);
    }

    @PatchMapping("/api/user/comment/update")
    public DefaultRes updateComment(HttpServletRequest request, @RequestBody UpdatedComment dto) {
        String username = (String) request.getAttribute("username");
        commentService.updateComment(dto.getCommentId(), dto.getCommentBody());
        return DefaultRes.res(20026, "번 글의 댓글을 수정하였습니다");
    }

    @DeleteMapping("/api/user/comment/delete")
    public DefaultRes deleteComment(HttpServletRequest request, HttpServletRequest httpServletRequest, @RequestBody UpdatedComment dto) {
        String username = (String) request.getAttribute("username");

        log.info("content type = {}", httpServletRequest.getContentType());
        log.info("strign = {}" , dto.getCommentId());
        commentService.deleteComment(dto.getCommentId());
        return DefaultRes.res(20027, "번 글의 댓글을 삭제하였습니다");
    }




    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    static class UpdatedComment{
        int commentId;
        String commentBody;
    }
}

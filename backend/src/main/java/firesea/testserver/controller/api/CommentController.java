package firesea.testserver.controller.api;

import firesea.testserver.domain.CommentDetailDto;
import firesea.testserver.domain.basic.DefaultRes;
import firesea.testserver.domain.basic.PageCustomDto;
import firesea.testserver.domain.entity.Comment;
import firesea.testserver.service.CommentService;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@RestController
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

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
    static class CommentSendingDto {
        int id;
        String commentBody;
    }


    @GetMapping("/api/comment/list")
    public DefaultRes<PageCustomDto<CommentDetailDto>> commentList(@RequestParam int id, @PageableDefault(size = 20) Pageable pageable) {

        PageCustomDto<CommentDetailDto> list = commentService.getList(id, pageable);
        return DefaultRes.res(20023, id + "번 글의 댓글 리스트" ,list);
    }

    @PatchMapping("/api/user/comment/update")
    public DefaultRes<PageCustomDto<CommentDetailDto>> updateComment(HttpServletRequest request, @RequestBody UpdatedComment dto) {
        String username = (String) request.getAttribute("username");
        PageCustomDto<CommentDetailDto> list = commentService.updateComment(dto.getCommentId(), username, dto.getCommentBody());
        return DefaultRes.res(20023, 1 + "번 글의 댓글 리스트" ,list);
    }

    @RequiredArgsConstructor
    @NoArgsConstructor
    @Getter
    static class UpdatedComment{
        int commentId;
        int textMessageId;
        String commentBody;
    }
}

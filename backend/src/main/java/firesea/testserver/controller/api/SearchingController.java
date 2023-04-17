package firesea.testserver.controller.api;

import firesea.testserver.domain.TextMessageTitleDto;
import firesea.testserver.domain.basic.PageCustomDto;
import firesea.testserver.service.SearchingService;
import firesea.testserver.service.SearchingService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
public class SearchingController {

    private final SearchingService searchingService;

    @PostMapping("/api/search")
    public PageCustomDto<TextMessageTitleDto> search(@RequestBody SearchingDto searchingDto, @PageableDefault(size = 20) Pageable pageable) {
        PageCustomDto<TextMessageTitleDto> search = searchingService.search(searchingDto.getOption(), searchingDto.getContent(), pageable);
        return search;
    }

    @Data
    static class SearchingDto{
        String option;
        String content;
    }

}

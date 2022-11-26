package pl.jhonylemon.memewebsite.controller.guest;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.jhonylemon.memewebsite.controller.routes.ApiPaths;

import pl.jhonylemon.memewebsite.dto.poststatistic.PostStatisticGetDto;
import pl.jhonylemon.memewebsite.mapper.PostStatisticMapper;
import pl.jhonylemon.memewebsite.service.poststatistic.guest.GuestPostStatisticService;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = ApiPaths.Guest.GUEST_PATH+ApiPaths.PostStatistic.POST_STATISTIC_PATH)
@CrossOrigin
public class GuestPostStatisticController {

    private final GuestPostStatisticService postStatisticService;
    private final PostStatisticMapper postStatisticMapper;

    @GetMapping(path = ApiPaths.PostStatistic.POST_STATISTIC_GET)
    public ResponseEntity<PostStatisticGetDto> getStatistic(@PathVariable Long id){
        return ResponseEntity.ok().body(postStatisticService.getStatistic(id));
    }

}

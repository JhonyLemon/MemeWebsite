package pl.jhonylemon.memewebsite.service.poststatistic.guest;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.jhonylemon.memewebsite.dto.poststatistic.PostStatisticGetDto;
import pl.jhonylemon.memewebsite.entity.PostStatistic;
import pl.jhonylemon.memewebsite.exception.poststatistic.PostStatisticInvalidParamException;
import pl.jhonylemon.memewebsite.mapper.PostStatisticMapper;
import pl.jhonylemon.memewebsite.repository.PostStatisticRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GuestPostStatisticService {

    private final PostStatisticMapper postStatisticMapper;
    private final PostStatisticRepository postStatisticRepository;

    public PostStatisticGetDto getStatistic(Long id){
        if(id==null || id<1){
            throw new PostStatisticInvalidParamException();
        }
        List<PostStatistic> statisticList = postStatisticRepository.findByPost_Id(id);
        return postStatisticMapper.postStatisticToGetDto(statisticList);
    }

}

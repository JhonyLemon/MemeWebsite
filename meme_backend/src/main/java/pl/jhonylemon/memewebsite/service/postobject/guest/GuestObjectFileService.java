package pl.jhonylemon.memewebsite.service.postobject.guest;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.jhonylemon.memewebsite.dto.postobject.*;
import pl.jhonylemon.memewebsite.exception.postobject.PostObjectInvalidParamException;
import pl.jhonylemon.memewebsite.exception.postobject.PostObjectNotFoundException;
import pl.jhonylemon.memewebsite.mapper.PostObjectMapper;
import pl.jhonylemon.memewebsite.repository.PostObjectRepository;

@Service
@RequiredArgsConstructor
public class GuestObjectFileService {

    private final PostObjectMapper postObjectMapper;
    private final PostObjectRepository postObjectRepository;


    public PostObjectFullGetDto getFullPostFileById(Long id){
        if(id == null || id < 1 ){
            throw new PostObjectInvalidParamException();
        }

        return postObjectMapper.postFileToFullGetDto(postObjectRepository.findById(id).orElseThrow(()->{
            throw new PostObjectNotFoundException();
        }));
    }
}

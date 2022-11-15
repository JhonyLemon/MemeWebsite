package pl.jhonylemon.memewebsite.service.postfile.guest;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.jhonylemon.memewebsite.dto.postfile.*;
import pl.jhonylemon.memewebsite.exception.postfile.PostFileInvalidParamException;
import pl.jhonylemon.memewebsite.exception.postfile.PostFileNotFoundException;
import pl.jhonylemon.memewebsite.mapper.PostFileMapper;
import pl.jhonylemon.memewebsite.repository.PostFileRepository;

@Service
@RequiredArgsConstructor
public class GuestPostFileService {

    private final PostFileMapper postFileMapper;
    private final PostFileRepository postFileRepository;


    public PostFileFullGetDto getFullPostFileById(Long id){
        if(id == null || id < 1 ){
            throw new PostFileInvalidParamException();
        }
        return postFileMapper.postFileToFullGetDto(postFileRepository.findById(id).orElseThrow(()->{
            throw new PostFileNotFoundException();
        }));
    }

    public PostFileShortGetDto getShortPostFileById(Long id){
        if(id == null || id < 1 ){
            throw new PostFileInvalidParamException();
        }
        return postFileMapper.postFileToShortGetDto(postFileRepository.findById(id).orElseThrow(()->{
            throw new PostFileNotFoundException();
        }));
    }

    public byte[] getPostFileFileById(Long id){
        if(id == null || id < 1 ){
            throw new PostFileInvalidParamException();
        }
       return postFileRepository.findById(id).orElseThrow(()->{
            throw new PostFileNotFoundException();
        }).getFile();
    }

}

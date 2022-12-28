package pl.jhonylemon.memewebsite.service.poststatistic;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.jhonylemon.memewebsite.dto.poststatistic.PostStatisticGetDto;
import pl.jhonylemon.memewebsite.entity.Account;
import pl.jhonylemon.memewebsite.entity.Post;
import pl.jhonylemon.memewebsite.entity.PostStatistic;
import pl.jhonylemon.memewebsite.exception.account.AccountNotFoundException;
import pl.jhonylemon.memewebsite.exception.post.PostNotFoundException;
import pl.jhonylemon.memewebsite.exception.poststatistic.PostStatisticInvalidParamException;
import pl.jhonylemon.memewebsite.mapper.PostStatisticMapper;
import pl.jhonylemon.memewebsite.repository.AccountRepository;
import pl.jhonylemon.memewebsite.repository.PostRepository;
import pl.jhonylemon.memewebsite.repository.PostStatisticRepository;
import pl.jhonylemon.memewebsite.security.service.CustomUserDetailsService;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PostStatisticService {

    private final PostStatisticMapper postStatisticMapper;
    private final PostStatisticRepository postStatisticRepository;
    private final PostRepository postRepository;
    private final AccountRepository accountRepository;

    private final CustomUserDetailsService userDetailsService;

    @Transactional
    public PostStatisticGetDto setVoteStatistic(Long postId, Boolean vote){
        if(postId==null || postId<1){
            throw new PostStatisticInvalidParamException();
        }

        Account account = userDetailsService.currentUser();

        Optional<PostStatistic> postStatistic = postStatisticRepository.findByPost_IdAndAccount_Id(postId,account.getId());

        if(postStatistic.isPresent()) {
            postStatistic.get().setUpVote(vote != null && (vote));
            postStatistic.get().setDownVote(vote != null && (!vote));
        }else {
            Post post = postRepository.findById(postId).orElseThrow(()->{
                throw new PostNotFoundException();
            });
            PostStatistic postStat = PostStatistic.builder()
                    .post(post)
                    .account(account)
                    .seen(true)
                    .favorite(false)
                    .upVote(vote != null && (vote))
                    .downVote(vote != null && (!vote))
                    .build();
            postStatisticRepository.save(postStat);
        }
        List<PostStatistic> statisticList = postStatisticRepository.findByPost_Id(postId);
        PostStatisticGetDto postStatisticGetDto = postStatisticMapper.postStatisticToGetDto(statisticList);
        if(account!=null){
            postStatisticGetDto.expandData(
                    postStatisticRepository.findByPost_IdAndAccount_Id(
                            postId,
                            account.getId()
                    )
            );
        }
        return postStatisticGetDto;
    }

    @Transactional
    public PostStatisticGetDto setSeenStatistic(Long userId,Long postId){
        if(userId==null || userId<1){
            throw new PostStatisticInvalidParamException();
        }
        if(postId==null || postId<1){
            throw new PostStatisticInvalidParamException();
        }
        Optional<PostStatistic> postStatistic = postStatisticRepository.findByPost_IdAndAccount_Id(postId,userId);

        Account account = accountRepository.findById(userId).orElseThrow(()->{
            throw new AccountNotFoundException();
        });

        if(postStatistic.isPresent()) {
            postStatistic.get().setSeen(true);
        }else {
            Post post = postRepository.findById(postId).orElseThrow(()->{
                throw new PostNotFoundException();
            });
            PostStatistic postStat = PostStatistic.builder()
                    .post(post)
                    .account(account)
                    .seen(true)
                    .favorite(false)
                    .upVote(false)
                    .downVote(false)
                    .build();
            postStatisticRepository.save(postStat);
        }
        List<PostStatistic> statisticList = postStatisticRepository.findByPost_Id(postId);
        PostStatisticGetDto postStatisticGetDto = postStatisticMapper.postStatisticToGetDto(statisticList);
        if(account!=null){
            postStatisticGetDto.expandData(
                    postStatisticRepository.findByPost_IdAndAccount_Id(
                            postId,
                            account.getId()
                    )
            );
        }
        return postStatisticGetDto;
    }

    @Transactional
    public PostStatisticGetDto setFavoriteStatistic(Long postId, Boolean favorite) {
        if(postId==null || postId<1){
            throw new PostStatisticInvalidParamException();
        }
        if(favorite==null){
            favorite = false;
        }

        Account account = userDetailsService.currentUser();

        Optional<PostStatistic> postStatistic = postStatisticRepository.findByPost_IdAndAccount_Id(postId,account.getId());

        if(postStatistic.isPresent()) {
            postStatistic.get().setFavorite(favorite);
        }else {
            Post post = postRepository.findById(postId).orElseThrow(()->{
                throw new PostNotFoundException();
            });
            PostStatistic postStat = PostStatistic.builder()
                    .post(post)
                    .account(account)
                    .seen(true)
                    .favorite(favorite)
                    .upVote(false)
                    .downVote(false)
                    .build();
            postStatisticRepository.save(postStat);
        }
        List<PostStatistic> statisticList = postStatisticRepository.findByPost_Id(postId);
        PostStatisticGetDto postStatisticGetDto = postStatisticMapper.postStatisticToGetDto(statisticList);
        if(account!=null){
            postStatisticGetDto.expandData(
                    postStatisticRepository.findByPost_IdAndAccount_Id(
                            postId,
                            account.getId()
                    )
            );
        }
        return postStatisticGetDto;
    }

    public PostStatisticGetDto getPostStatistic(Long id) {
        if(id==null || id<1){
            throw new PostStatisticInvalidParamException();
        }
        Account account = userDetailsService.currentUser();

        List<PostStatistic> statisticList = postStatisticRepository.findByPost_Id(id);
        PostStatisticGetDto postStatisticGetDto = postStatisticMapper.postStatisticToGetDto(statisticList);
        if(account!=null){
            postStatisticGetDto.expandData(
                    postStatisticRepository.findByPost_IdAndAccount_Id(
                            id,
                            account.getId()
                    )
            );
        }
        return postStatisticGetDto;
    }


}

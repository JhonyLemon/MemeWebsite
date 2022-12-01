package pl.jhonylemon.memewebsite.component;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import org.springframework.stereotype.Component;

@Component
@Getter
@Setter
@ConfigurationProperties(prefix = "comment")
@RequiredArgsConstructor
public class CommentProperties {
    private Long maxCommentSize;
}
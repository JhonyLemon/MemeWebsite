package pl.jhonylemon.memewebsite.component;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Getter
@Setter
@ConfigurationProperties(prefix = "post")
@RequiredArgsConstructor
public class PostProperties {

    private Long maxObjects;
    private Long maxTextObject;
    private Long maxFileObject;
    private Long maxUnPublishedPosts;
    private List<String> allowedTypes;

}


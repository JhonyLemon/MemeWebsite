package pl.jhonylemon.memewebsite.dto.profilepicture;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ProfilePictureGetDto {
    private Long id;
    private byte[] file;
    private String mimeType;
}

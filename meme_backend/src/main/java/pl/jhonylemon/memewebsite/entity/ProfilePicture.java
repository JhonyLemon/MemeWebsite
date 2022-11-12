package pl.jhonylemon.memewebsite.entity;

import lombok.*;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "PROFILE_PICTURE")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProfilePicture {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "PICTURE")
    @Lob
    @Type(type="org.hibernate.type.BinaryType")
    private byte[] file;

    @Column(name = "MIME_TYPE")
    private String mimeType;

    @Column(name = "DEFAULT_PROFILE")
    private Boolean defaultProfile;

    @OneToMany(mappedBy = "profilePicture")
    private List<Account> accounts;
}

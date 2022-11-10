package pl.jhonylemon.memewebsite.entity;

import lombok.*;
import org.hibernate.annotations.Type;

import javax.persistence.*;

@Entity
@Table(name = "POST_FILE")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PostFile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @JoinColumn(name = "POST_ID")
    @ManyToOne()
    private Post post;

    @Column(name = "FILE_NAME")
    private String fileName;

    @Column(name = "FILE")
    @Lob
    @Type(type="org.hibernate.type.BinaryType")
    private byte[] file;

    @Column(name = "MIME_TYPE")
    private String mimeType;

    @Column(name = "DESCRIPTION")
    private String description;

}

package pl.jhonylemon.memewebsite.entity;

import lombok.*;
import org.hibernate.annotations.Type;

import javax.persistence.*;

@Entity
@Table(name = "POST_OBJECT")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PostObject {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @JoinColumn(name = "POST_ID",nullable = false)
    @ManyToOne()
    private Post post;

    @Column(name = "FILE_NAME")
    private String fileName;

    @Column(name = "CONTENT",nullable = false)
    @Lob
    @Type(type="org.hibernate.type.BinaryType")
    private byte[] content;

    @Column(name = "MIME_TYPE",nullable = false)
    private String mimeType;

    @Column(name = "DESCRIPTION")
    private String description;

    @Column(name = "ORDER_OF_OBJECT")
    private Long order;


}

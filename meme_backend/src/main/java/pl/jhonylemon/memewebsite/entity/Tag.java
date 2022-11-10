package pl.jhonylemon.memewebsite.entity;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "POST_FILE")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Tag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "TAG")
    private String tag;

    @ManyToMany(mappedBy = "tags", cascade = CascadeType.DETACH)
    private List<Post> posts;

}

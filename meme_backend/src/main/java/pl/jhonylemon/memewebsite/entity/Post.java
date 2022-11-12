package pl.jhonylemon.memewebsite.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "POST")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "TITLE")
    private String title;

    @Column(name = "CREATION_TIME")
    private LocalDate creationDate;

    @Column(name = "VISIBLE")
    private Boolean visible;

    @OneToMany(mappedBy = "post")
    private List<PostFile> files;

    @ManyToMany(
            fetch = FetchType.LAZY,
            cascade = {
                    CascadeType.DETACH,
                    CascadeType.PERSIST
            }
    )
    @JoinTable(
            name = "POST_TAG",
            joinColumns = @JoinColumn(name = "POST_ID"),
            inverseJoinColumns = @JoinColumn(name = "TAG_ID"))
    private List<Tag> tags;

    @OneToMany(mappedBy = "post")
    private List<Comment> comments;

    @JoinColumn(name = "ACCOUNT_ID")
    @ManyToOne()
    private Account account;

    @OneToMany(mappedBy = "post")
    private List<PostStatistic> postStatistics;
}

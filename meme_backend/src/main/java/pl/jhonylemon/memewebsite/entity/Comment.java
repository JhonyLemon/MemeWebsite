package pl.jhonylemon.memewebsite.entity;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "COMMENT")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @JoinColumn(name = "POST_ID")
    @ManyToOne()
    private Post post;

    @JoinColumn(name = "ACCOUNT_ID")
    @ManyToOne()
    private Account account;

    @JoinColumn(name = "ANSWER_TO_ID", referencedColumnName = "ID")
    @ManyToOne()
    private Comment replyTo;

    @Column(name = "COMMENT")
    private String comment;

    @OneToMany(mappedBy = "replyTo")
    private List<Comment> childComments;

    @OneToMany(mappedBy = "comment")
    private List<CommentStatistic> commentStatistics;
}

package pl.jhonylemon.memewebsite.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "COMMENT_STATISTIC")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CommentStatistic {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @JoinColumn(name = "COMMENT_ID")
    @ManyToOne()
    private Comment comment;

    @JoinColumn(name = "ACCOUNT_ID")
    @ManyToOne()
    private Account account;

    @Column(name = "VOTE")
    private Boolean vote;
}

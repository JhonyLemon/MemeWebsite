package pl.jhonylemon.memewebsite.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "POST_STATISTIC")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PostStatistic {
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

    @Column(name = "SEEN")
    private Boolean seen;

    @Column(name = "VOTE")
    private Boolean vote;

    @Column(name = "FAVORITE")
    private Boolean favorite;
}

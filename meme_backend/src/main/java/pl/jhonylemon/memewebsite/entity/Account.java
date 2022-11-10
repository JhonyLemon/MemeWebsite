package pl.jhonylemon.memewebsite.entity;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "ACCOUNT")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "ACCOUNT_NAME")
    private String name;

    @Column(name = "EMAIL")
    private String email;

    @Column(name = "PASSWORD")
    private String password;

    @Column(name = "ENABLED")
    private Boolean enabled;

    @Column(name = "BANNED")
    private Boolean banned;

    @JoinColumn(name = "ACCOUNT_ROLE_ID")
    @ManyToOne()
    private AccountRole role;

    @OneToMany(mappedBy = "account")
    private List<Comment> comments;

    @OneToMany(mappedBy = "account")
    private List<CommentStatistic> commentStatistics;

    @OneToMany(mappedBy = "account")
    private List<PostStatistic> postStatistics;
}

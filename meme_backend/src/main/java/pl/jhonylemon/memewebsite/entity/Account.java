package pl.jhonylemon.memewebsite.entity;

import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "ACCOUNT")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Account implements UserDetails {

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

    @Column(name = "CREATION_TIME")
    private LocalDate creationDate;

    @Column(name = "ENABLED")
    private Boolean enabled;

    @Column(name = "BANNED")
    private Boolean banned;

    @ManyToMany(
            fetch = FetchType.EAGER,
            cascade = {
                    CascadeType.DETACH,
                    CascadeType.PERSIST
            }
    )
    @JoinTable(
            name = "ACCOUNT_ACCOUNT_PERMISSION",
            joinColumns = @JoinColumn(name = "ACCOUNT_ID"),
            inverseJoinColumns = @JoinColumn(name = "PERMISSION_ID"))
    private List<AccountPermission> permissions;

    @OneToMany(mappedBy = "account")
    private List<Comment> comments;

    @OneToMany(mappedBy = "account")
    private List<CommentStatistic> commentStatistics;

    @OneToMany(mappedBy = "account")
    private List<PostStatistic> postStatistics;

    @OneToMany(mappedBy = "account",cascade = CascadeType.ALL)
    private List<Post> posts;

    @ManyToOne()
    @JoinColumn(name = "PROFILE_PICTURE_ID")
    private ProfilePicture profilePicture;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return permissions;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return !banned;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }
}

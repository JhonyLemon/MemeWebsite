package pl.jhonylemon.memewebsite.entity;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "ACCOUNT_ROLE")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AccountRole {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "ROLE")
    private String role;

    @ManyToMany(
            fetch = FetchType.LAZY,
            cascade = {
                    CascadeType.DETACH,
                    CascadeType.PERSIST
            }
    )
    @JoinTable(
            name = "ACCOUNT_ROLE_PERMISSION",
            joinColumns = @JoinColumn(name = "ACCOUNT_ROLE_ID"),
            inverseJoinColumns = @JoinColumn(name = "PERMISSION_ID"))
    private List<AccountPermission> permissions;

    @OneToMany(mappedBy = "role")
    private List<Account> accounts;
}

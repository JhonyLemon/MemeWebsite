package pl.jhonylemon.memewebsite.entity;

import lombok.*;
import lombok.experimental.Accessors;

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

    @Column(name = "DEFAULT_ROLE")
    @Accessors(fluent = true)
    private Boolean isDefaultRole;

    @OneToMany(mappedBy = "accountRole")
    private List<Account> accounts;

    @ManyToMany(
            fetch = FetchType.EAGER,
            cascade = {
                    CascadeType.DETACH,
                    CascadeType.PERSIST
            }
    )
    @JoinTable(
            name = "ACCOUNT_ROLE_PERMISSION",
            joinColumns = @JoinColumn(name = "ROLE_ID"),
            inverseJoinColumns = @JoinColumn(name = "PERMISSION_ID"))
    private List<AccountPermission> permissions;

}

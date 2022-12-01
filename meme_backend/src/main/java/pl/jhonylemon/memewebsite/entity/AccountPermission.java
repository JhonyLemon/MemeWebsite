package pl.jhonylemon.memewebsite.entity;

import lombok.*;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "ACCOUNT_PERMISSION")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AccountPermission implements GrantedAuthority {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "PERMISSION")
    private String permission;

    @ManyToMany(mappedBy = "permissions", cascade = CascadeType.DETACH)
    private List<AccountRole> accountRoles;

    @Override
    public String getAuthority() {
        return permission;
    }
}

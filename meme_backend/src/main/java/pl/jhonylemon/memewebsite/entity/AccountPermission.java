package pl.jhonylemon.memewebsite.entity;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "ACCOUNT_PERMISSION")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AccountPermission {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "PERMISSION")
    private String permission;

    @ManyToMany(mappedBy = "permissions", cascade = CascadeType.DETACH)
    private List<AccountRole> roles;
}

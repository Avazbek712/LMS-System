package uz.imv.lmssystem.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;
import org.hibernate.envers.Audited;
import org.hibernate.envers.RelationTargetAuditMode;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import uz.imv.lmssystem.entity.template.AbsLongEntity;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;


@Entity(name = "users")
@Audited
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@SQLRestriction(value = "deleted=false")
@SQLDelete(sql = "UPDATE users SET deleted = true WHERE id = ?")
public class User extends AbsLongEntity implements UserDetails {

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String surname;

    @Column(nullable = false)
    private String phoneNumber;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    @ManyToOne(fetch = FetchType.EAGER)
    @Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
    private Role role;

    @Column(length = 1024)
    private String refreshToken;

    @OneToMany(
            mappedBy = "teacher",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<TeacherInfo> teacherInfos;

    private String photoUrl;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (Objects.isNull(role) || Objects.isNull(role.getPermissions())) {
            return Collections.emptySet();
        }
        // 1. Взять разрешения из ОДНОЙ роли (this.role.getPermissions())
        // 2. Превратить каждое разрешение (Enum) в объект SimpleGrantedAuthority
        // 3. Собрать все в коллекцию (Set)
        return role.getPermissions().stream()
                .map(permissionEnum -> new SimpleGrantedAuthority(permissionEnum.name()))
                .collect(Collectors.toSet());
    }

}

package uz.imv.lmssystem.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;
import lombok.experimental.FieldNameConstants;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import uz.imv.lmssystem.entity.template.AbsLongEntity;
import uz.imv.lmssystem.enums.RoleEnum;

import java.util.Collection;
import java.util.List;
import java.util.Objects;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity(name = "users")
@FieldNameConstants
@Builder
@SQLDelete(sql = "update users set deleted=true where id=?")
@SQLRestriction(value = "deleted=false")
public class User extends AbsLongEntity implements UserDetails {


    @Column(nullable = false, name = "username")
    private String username;//(email) written as username because of spring security (UserDetails)

    private String password;

    @Enumerated(EnumType.STRING)
    private RoleEnum role;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (Objects.isNull(role))
            return List.of();
        return List.of(new SimpleGrantedAuthority("ROLE_" + this.role.name()));
    }
}

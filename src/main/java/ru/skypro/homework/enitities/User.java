package ru.skypro.homework.enitities;


import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import ru.skypro.homework.dto.Role;

import javax.persistence.*;
import java.util.Arrays;
import java.util.Collection;


/**
 * Класс сущность пользователь
 */
@Entity
@Table(name = "users", uniqueConstraints = @UniqueConstraint(columnNames={"email"}))
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(columnDefinition = "varchar(150)")
    private String email;
    private String password;

    @Column(columnDefinition = "varchar(100)")
    private String firstName;

    @Column(columnDefinition = "varchar(100)")
    private String lastName;

    @Column(columnDefinition = "varchar(50)")
    private String phone;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "varchar(20)")
    private Role role;

    public String getUsername() {
        return email;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Arrays.asList(
                new SimpleGrantedAuthority("ROLE_USER"),
                new SimpleGrantedAuthority("ROLE_ADMIN")
                );
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @OneToOne(fetch = FetchType.LAZY,
            cascade = CascadeType.ALL,
            mappedBy = "user")
    private UserAvatar avatar;
}

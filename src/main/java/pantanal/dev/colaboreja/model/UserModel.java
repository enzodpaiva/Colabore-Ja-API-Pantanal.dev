package pantanal.dev.colaboreja.model;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pantanal.dev.colaboreja.enumerable.RoleEnum;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "\"user\"")
public class UserModel implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String firstname;
    private String lastname;
    private String email;
    private String password;

    @Enumerated(EnumType.STRING)
    private RoleEnum role;

    @OneToMany(mappedBy = "user")
    private List<TokenModel> tokens;

    @Basic
    @Column
    @Temporal(TemporalType.TIMESTAMP) // Specify the date and time
    @CreationTimestamp
    private Date createdAt;

    @Basic
    @Column
    @Temporal(TemporalType.TIMESTAMP) // Specify the date and time
    @UpdateTimestamp
    private Date updatedAt;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return role.getAuthorities();
    }

    @Override
    public String getPassword() {
        return password;
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

    @Override
    public String toString() {
        return "UserModel{" +
                "id=" + id +
                ", firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
//                ", role='" + role + '\'' +
//                ", tokens='" + tokens + '\'' +
//                ", socialActionCategoryId='" + socialActionCategoryId + '\'' +
//                ", author='" + author + '\'' +
                // Add other fields you want to include
                '}';
    }
}
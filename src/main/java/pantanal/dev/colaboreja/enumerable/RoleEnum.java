package pantanal.dev.colaboreja.enumerable;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


import static pantanal.dev.colaboreja.enumerable.PermissionEnum.ADMIN_CREATE;
import static pantanal.dev.colaboreja.enumerable.PermissionEnum.ADMIN_DELETE;
import static pantanal.dev.colaboreja.enumerable.PermissionEnum.ADMIN_READ;
import static pantanal.dev.colaboreja.enumerable.PermissionEnum.ADMIN_UPDATE;


@RequiredArgsConstructor
public enum RoleEnum {



    SUPER_ADMIN(Collections.emptySet()),
    ADMIN(
            Set.of(
                    ADMIN_READ,
                    ADMIN_UPDATE,
                    ADMIN_DELETE,
                    ADMIN_CREATE

            )
    ),
    EDITOR(Collections.emptySet()),
    AUTHOR(Collections.emptySet()),
    COLABORATOR(Collections.emptySet()),

    ;

    @Getter
    private final Set<PermissionEnum> permissions;

    public List<SimpleGrantedAuthority> getAuthorities() {
        var authorities = getPermissions()
                .stream()
                .map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
                .collect(Collectors.toList());
        authorities.add(new SimpleGrantedAuthority("ROLE_" + this.name()));
        return authorities;
    }
}
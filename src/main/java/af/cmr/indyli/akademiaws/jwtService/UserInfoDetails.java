package af.cmr.indyli.akademiaws.jwtService;

import af.cmr.indyli.akademia.business.dao.PrivilegeRepository;
import af.cmr.indyli.akademia.business.entity.User;
import af.cmr.indyli.akademia.business.utils.ConstsValues;
import jakarta.annotation.Resource;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class UserInfoDetails implements UserDetails {
    private String email;
    private String password;
    private List<GrantedAuthority> authorities;

    @Resource(name = ConstsValues.ConstsDAO.PRIVILEGE_DAO_KEY)
    private PrivilegeRepository privilegeRepository;

    public UserInfoDetails(User user) {
        email = user.getEmail();
        password = user.getPassword();
        authorities = user.getPrivileges().stream().map((p) -> p.getRole()).map((r) -> r.getRoleName()).map(SimpleGrantedAuthority::new).collect(Collectors.toList());
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
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
}
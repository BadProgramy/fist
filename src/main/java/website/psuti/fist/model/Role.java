package website.psuti.fist.model;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {
    FULLADMIN;


    @Override
    public String getAuthority() {
        return name();
    }
}
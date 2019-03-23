package website.psuti.fist.model;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {
    ADMIN("Администратор"),
    MODERATOR("Модератор"),
    DEVELOPER("Разработчик"),
    SUBSCRIBER("Подписчик");

    private String name;

    Role(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getAuthority() {
        return name();
    }
}
package website.psuti.fist.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import website.psuti.fist.dao.user.DAOUser;
import website.psuti.fist.model.Role;
import website.psuti.fist.model.User;

import javax.sql.DataSource;
import javax.validation.constraints.NotNull;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private DAOUser daoUser;

    @Autowired
    private DataSource dataSource;

    @Transactional
    public void save(User User) throws SQLException {
        RequestPostConnection.requestions(dataSource);
        daoUser.save(User);
    }

    @Transactional
    public List<User> getAll() {
        List<User> temp = new ArrayList<>();
        daoUser.findAll().forEach(temp::add);
        return temp;
    }

    @Transactional
    public void update(User user) {
        daoUser.save(user);
    }

    @Transactional
    public void delete(User user) {
        daoUser.delete(user);
    }

    @Transactional
    public void deleteAll() {
        daoUser.deleteAll();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = findUserByName(username);
        if (user!=null) return user;
        else throw new UsernameNotFoundException("Пользователь " + username + " не был найден!");
    }


    @Transactional
    public User findUserByName(@NotNull String username) {
        for (User user: daoUser.findAll()) {
            if (user.getUsername().equals(username)) {
                return user;
            }
        }
        return null;
    }

    @Transactional
    public User findUserById(@NotNull long id) {
        return daoUser.findById(id).get();
    }

    @Transactional
    public List<User> getUsersByRole(Role role) {
        List<User> users = new ArrayList<>();
        for (User user: getAll()) {
            if (user.getRole().contains(Role.SUBSCRIBER) && user.isEnabled())
                users.add(user);
        }
        return users;
    }
}

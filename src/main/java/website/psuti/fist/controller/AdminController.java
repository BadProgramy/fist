package website.psuti.fist.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import website.psuti.fist.model.NewsOfFaculty;
import website.psuti.fist.model.Role;
import website.psuti.fist.model.User;
import website.psuti.fist.service.MainPageService;
import website.psuti.fist.service.NewsFacultyService;
import website.psuti.fist.service.UserService;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Controller
public class AdminController {
    @Autowired
    private MainPageService mainPageService;

    @Autowired
    private NewsFacultyService newsFacultyService;

    @Autowired
    private UserService userService;

    private User user;
    private NewsOfFaculty newFaculty;

    @RequestMapping("/admin")
    public String adminPage() {
        return "admin";
    }

    @RequestMapping("/admin/login")
    public String login() {
        return "authentication";
    }

    @RequestMapping("/admin/news")
    public String news(Model model) {
        if (this.user == null) {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            this.user = userService.findUserByName(authentication.getName());
        }
        model.addAttribute("newFaculty", new NewsOfFaculty());
        model.addAttribute("news", newsFacultyService.getAll());
        return "adminnews";
    }

    @RequestMapping(value = "/admin/news/delete/{id}",method = RequestMethod.DELETE)
    public String deleteNewFaculty(@PathVariable("id") long newsId, Model model) {
        if (this.user == null) {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            this.user = userService.findUserByName(authentication.getName());
        }
        newsFacultyService.delete(newsId);
        model.addAttribute("news", newsFacultyService.getAll());
        return "redirect:../../news";
    }

    @RequestMapping(value = "/admin/news/update/{id}", method = RequestMethod.GET)
    public String updateFaculty(@PathVariable("id") Long id, Model model) throws Exception {
        if (this.user == null) {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            this.user = userService.findUserByName(authentication.getName());
        }
        if (newFaculty == null)
            newFaculty = newsFacultyService.findById(id);
        else throw new Exception("Занято");
        model.addAttribute("newFaculty", newFaculty);
        return "adminUpdateNews";
    }

    @RequestMapping(value = "/admin/news/update/submit", method = RequestMethod.POST)
    public String updateNewFaculty(@ModelAttribute NewsOfFaculty newFaculty) {
        if (this.user == null) {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            this.user = userService.findUserByName(authentication.getName());
        }
        this.newFaculty.update(newFaculty);
        newsFacultyService.update(this.newFaculty);
        this.newFaculty = null;
        return "redirect:../../news";
    }


    /*@RequestMapping("/admin/login/submit")
    public String testCreateUser() throws SQLException {
        List<Role> roles = new ArrayList<>();
        roles.add(Role.FULLADMIN);
        User user = new User();
        user.setAccountNonExpired(true);
        user.setAccountNonLocked(true);
        user.setCredentialsNonExpired(true);
        user.setEnabled(true);
        user.setFirstname("Айрат");
        user.setSecondname("Мухутдинов");
        user.setUsername("airat23059");
        user.setPassword("19970808");
        user.setPagevk("https://vk.com/id109488730");
        user.setRole(roles);
        userService.save(user);
        return authPage();
    }*/
}

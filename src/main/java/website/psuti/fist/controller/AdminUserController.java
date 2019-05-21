package website.psuti.fist.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.annotation.ScheduledAnnotationBeanPostProcessor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import website.psuti.fist.configuration.Sender;
import website.psuti.fist.constant.*;
import website.psuti.fist.model.Role;
import website.psuti.fist.model.User;
import website.psuti.fist.scheduler.SendMessageScheduler;
import website.psuti.fist.service.UserService;

import javax.mail.MessagingException;
import java.io.*;
import java.sql.SQLException;
import java.util.*;

@Controller
public class AdminUserController {

    @Autowired
    private Sender sender;

    @Autowired
    private UserService userService;

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private BCryptPasswordEncoder bcryptEncoder;

    @RequestMapping("/admin/user/cms")
    public ModelAndView userCmsAdd(Model model) {
        return adminUserPage(1, "cms", model);
    }

    @RequestMapping("/admin/user/subscriber")
    public ModelAndView userSubscriberAdd(Model model) {
        return adminUserPage(1, "subscriber", model);
    }

    @RequestMapping("/admin/user/{check}/page/{idPage}")
    public ModelAndView adminUserPage(@PathVariable int idPage,
                                          @PathVariable String check,
                                          Model model) {
        ModelAndView modelAndView = new ModelAndView("404");
        List<User> cmsUsers = new ArrayList<>();
        List<User> resulUser = new ArrayList<>();
        List<Role> roles = new ArrayList<>();
        if (idPage <= 0) idPage = 1;
        model.addAttribute("firstPage", idPage);
        roles.add(Role.ADMIN);
        roles.add(Role.DEVELOPER);
        roles.add(Role.MODERATOR);

        if (check.equals("cms")) {
            for (User user: userService.getAll()) {
                if (user.getRole().contains(Role.ADMIN) ||
                    user.getRole().contains(Role.DEVELOPER) ||
                    user.getRole().contains(Role.MODERATOR))
                    cmsUsers.add(user);
            }
            model.addAttribute("checkPage", true);
        } else if (check.equals("subscriber")) {
            cmsUsers.addAll(userService.getUsersByRole(Role.SUBSCRIBER));
            model.addAttribute("checkPage", false);
        }

        model.addAttribute("pageCount", (int) (Math.ceil((double) cmsUsers.size() / UserConstant.COUNT_USER_FOR_OUTPUT.getCount())));
        for (int i = (idPage - 1) * UserConstant.COUNT_USER_FOR_OUTPUT.getCount(), j = 0; i < cmsUsers.size() && j < UserConstant.COUNT_USER_FOR_OUTPUT.getCount(); i++, j++) {
            resulUser.add(cmsUsers.get(i));
        }
        model.addAttribute("user", new User());
        model.addAttribute("roles", roles);
        model.addAttribute("users", resulUser);
        modelAndView.setViewName("adminUsersAdd");
        modelAndView.addAllObjects(model.asMap());
        return modelAndView;
    }

    @RequestMapping(value = "/admin/user/update/{id}", method = RequestMethod.GET)
    public ModelAndView userUpdate(@PathVariable("id") Long id) {
        ModelAndView modelAndView = new ModelAndView("adminUsersUpdate");
        User user = userService.findUserById(id);
        modelAndView.addObject("user", user);
        modelAndView.addObject("roles", Role.values());
        return modelAndView;
    }

    @RequestMapping("/admin/user/update/submit")
    public String adminUserUpdateSubmit(@ModelAttribute("item") User item) throws SQLException {
        boolean checkMove = false;
        List<Role> roles = new ArrayList<>();
        for (String role: item.getRolesString()) {
            roles.add(Role.valueOf(role));
            if (Role.valueOf(role).equals(Role.ADMIN) ||
                    Role.valueOf(role).equals(Role.DEVELOPER) ||
                    Role.valueOf(role).equals(Role.MODERATOR)) checkMove = true;
        }
        item.setRole(roles);
        userService.update(item);
        if (checkMove)
            return "redirect:../cms";
        else return "redirect:../subscriber";
    }

    @RequestMapping("/admin/user/add/submit")
    public String userAddSubmit(@ModelAttribute("user") User user) {
        List<Role> roles = new ArrayList<>();
        for (String role: user.getRolesString()) {
            roles.add(Role.valueOf(role));
        }
        User userBD = userService.findUserByName(user.getUsername());
        if (userBD == null) {
            user.setRole(roles);
            user.setEnabled(false);
        } else {
            for (Role role: roles) {
                if (!userBD.getRole().contains(role))
                    userBD.addRole(role);
            }
            userBD.setCredentialsNonExpired(true);
            userBD.setAccountNonLocked(true);
            userBD.setAccountNonExpired(true);
            userBD.setFirstname(user.getFirstname());
            userBD.setPassword(user.getPassword());
            userBD.setPagevk(user.getPagevk());
            userBD.setSecondname(user.getSecondname());
            user = userBD;
        }
        try {
            userService.save(user);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        Reader s = null;
        try {
            s = new InputStreamReader(new FileInputStream(PathConstant.HTML_FILE_FOR_USER_ADD_CMS.getPath()) );
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        char c[] = new char[(int)(new File(PathConstant.HTML_FILE_FOR_USER_ADD_CMS.getPath())).length()];
        try {
            s.read(c);
        } catch (IOException e) {
            e.printStackTrace();
        }
        String rolesString = "";
        for (int i=0; i<user.getRole().size(); i++) {
            if (i != user.getRole().size() - 1)
                rolesString += user.getRole().get(i).getName() + ", ";
            else rolesString += user.getRole().get(i).getName();
        }
        String htmlBody = String.copyValueOf(c)
                .replace("#headerTop", "Факультет информационных систем и технологий")
                .replace("#logotipFIST", UrlForSearch.getUrlSite() + "/main/picture/"+ MainPageConstant.LOGOTIP_FIST.getId())
                .replace("#logotipPSUTI", UrlForSearch.getUrlSite() + "/main/picture/"+ MainPageConstant.LOGOTIP_PSUTI.getId())
                .replace("#logotipTwitter", UrlForSearch.getUrlSite() + "/main/picture/"+ 73)
                .replace("#logotipInstagram", UrlForSearch.getUrlSite() + "/main/picture/"+ 72)
                .replace("#logotipVK", UrlForSearch.getUrlSite() + "/main/picture/"+ 71)
                .replace("#headerEmailHtml", "Добавлен пользователь в CMS")
                .replace("#footer", "Вы получаете это письмо, потому что вас добавили в пользователи CMS сайта ФИСТ.")
                .replace("#nameClient", "Здраствуйте, "+user.getFirstname()+",")
                .replace("#textClient", "На сайте факультета информационных систем и технологий ПГУТИ. Вам доступны такие роли - " + rolesString + ". Подтвердите свою почту, чтобы использовать её в качестве логина.")
                .replace("#buttonCheck", "Подтвердить почту")
                .replace("#buttonHref", UrlForSearch.getUrlSite() + "/user/enable/email="+ user.getUsername());

        try {
            sender.send("Пригласительный", htmlBody, user.getUsername());
        } catch (Exception e) {
            System.out.println("Я открыл scheduler");
            HashMap<String, String> message = new HashMap<>();
            message.put("Пример загаловка", htmlBody);
            SendMessageEmailConstant.addSendMessage(user, message);
            ScheduledAnnotationBeanPostProcessor scheduledAnnotationBeanPostProcessor = applicationContext.getBean(ScheduledAnnotationBeanPostProcessor.class);
            scheduledAnnotationBeanPostProcessor.postProcessAfterInitialization(applicationContext.getBean(SendMessageScheduler.class), "scheduler");
            e.printStackTrace();
        }
        try {
            s.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "redirect:../cms";
    }

    @RequestMapping("/user/enable/email={email}")
    public String activationUser(@PathVariable("email") String email) throws SQLException {
        User user = userService.findUserByName(email);
        user.setEnabled(true);
        userService.save(user);
        return "enabledAccount";
    }

    @RequestMapping("/admin/setting/profile")
    public String settingProfile(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        model.addAttribute("user", userService.findUserByName(authentication.getName()));
        model.addAttribute("newPassword", "");
        model.addAttribute("passwordProfile", "");
        model.addAttribute("prevPassword", "");
        return "adminSettingProfile";
    }

    @RequestMapping("/admin/setting/profile/update/submit")
    public String settingProfileUpdateSubmit(Model model,
                                             @ModelAttribute("user") User user,
                                             @ModelAttribute("newPassword") String newPassword,
                                             @ModelAttribute("prevPassword") String prevPassword,
                                             @ModelAttribute("passwordProfile") String passwordProfile) throws SQLException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User userOriginal = userService.findUserByName(authentication.getName());
        if (user.getUsername().equals(userOriginal.getUsername())) {
            if (bcryptEncoder.matches(passwordProfile, userOriginal.getPassword())) {
                user.setPassword(passwordProfile);
                userService.save(user);
                model.addAttribute("checkPassword", true);
            }
            else if (bcryptEncoder.matches(prevPassword, userOriginal.getPassword())) {
                user.setPassword(newPassword);
                userService.save(user);
                model.addAttribute("checkPassword", true);
            } else {
                model.addAttribute("user", userOriginal);
                return "redirect:../../profile?error";
                /*model.addAttribute("checkPassword", false);*/
            }
        } else {
            model.addAttribute("user", userOriginal);
            /*model.addAttribute("checkPassword", false);*/
            return "redirect:../../profile?error";
        }
        model.addAttribute("user", userOriginal);
        return "redirect:../../profile";
    }

    /*@RequestMapping("/login/submit")
    public String testCreateUser() throws Exception {
        List<Role> roles = new ArrayList<>();
        roles.add(Role.DEVELOPER);
        roles.add(Role.ADMIN);
        roles.add(Role.MODERATOR);
        User user = new User();
        user.setAccountNonExpired(true);
        user.setAccountNonLocked(true);
        user.setCredentialsNonExpired(true);
        user.setEnabled(true);
        user.setFirstname("Айрат");
        user.setSecondname("Мухутдинов");
        user.setUsername("airatmyhytdinov08@gmail.com");
        user.setPassword("19970808");
        user.setPagevk("https://vk.com/id109488730");
        user.setRole(roles);
        userService.save(user);
        return "adminSettingProfile";
    }*/
}

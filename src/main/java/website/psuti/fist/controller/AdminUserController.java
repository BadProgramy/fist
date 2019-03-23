package website.psuti.fist.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.annotation.ScheduledAnnotationBeanPostProcessor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import website.psuti.fist.configuration.Sender;
import website.psuti.fist.constant.MainPageConstant;
import website.psuti.fist.constant.PathConstant;
import website.psuti.fist.constant.SendMessageEmailConstant;
import website.psuti.fist.constant.UrlForSearch;
import website.psuti.fist.model.Role;
import website.psuti.fist.model.User;
import website.psuti.fist.scheduler.SendMessageScheduler;
import website.psuti.fist.service.UserService;

import javax.mail.MessagingException;
import java.io.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Controller
public class AdminUserController {

    @Autowired
    private Sender sender;

    @Autowired
    private UserService userService;

/*    @Autowired
    private ScheduledAnnotationBeanPostProcessor scheduledAnnotationBeanPostProcessor;*/

    @Autowired
    private ApplicationContext applicationContext;

    @RequestMapping("/admin/user/add")
    public String userAdd(Model model) {
        model.addAttribute("user", new User());
        model.addAttribute("roles", Role.values());
        return "adminUsersAdd";
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
                .replace("#textClient", "Здесь какой нить текст")
                .replace("#buttonCheck", "Подтвердить почту")
                .replace("#buttonHref", UrlForSearch.getUrlSite() + "/user/enable/email="+ user.getUsername());

        try {
            sender.send("Пример загаловка", htmlBody, user.getUsername());
        } catch (MessagingException e) {
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
        return "redirect:../add";
    }

    @RequestMapping("/user/enable/email={email}")
    public String activationUser(@PathVariable("email") String email) throws SQLException {
        User user = userService.findUserByName(email);
        user.setEnabled(true);
        userService.save(user);
        return "enabledAccount";
    }
}

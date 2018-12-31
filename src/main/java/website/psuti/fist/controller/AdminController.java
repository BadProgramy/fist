package website.psuti.fist.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import website.psuti.fist.service.MainPageService;

@Controller
public class AdminController {
    @Autowired
    private MainPageService mainPageService;


    @RequestMapping("/admin")
    public String adminPage() {
        return "adminPage";
    }

}

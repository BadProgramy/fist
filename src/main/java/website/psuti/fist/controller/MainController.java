package website.psuti.fist.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MainController {
    @RequestMapping("/main")
    public String mainPage() {
        return "index";
    }
    @RequestMapping("/main2")
    public String secondPage() {
        return "index1";
    }
}

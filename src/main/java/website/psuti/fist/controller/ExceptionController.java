package website.psuti.fist.controller;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ExceptionController implements ErrorController {

    private static final String PATH = "/error";

    @RequestMapping(value = PATH)
    public String error(Model model) {
        model.addAttribute("searchword", "");
        return "404";
    }

    @Override
    public String getErrorPath() {
        return PATH;
    }
}

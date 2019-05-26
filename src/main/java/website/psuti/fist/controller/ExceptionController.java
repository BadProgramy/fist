package website.psuti.fist.controller;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;

@Controller
public class ExceptionController implements ErrorController {

    private static final String PATH = "/error";

    @RequestMapping(value = PATH)
    public String error(Model model, HttpServletResponse response) {
        model.addAttribute("searchword", "");
        switch (response.getStatus()) {
            case HttpServletResponse.SC_FORBIDDEN : {
                model.addAttribute("header", "УПС! У вас нет доступа к этой странице");
                model.addAttribute("httpStatus", "403");
                model.addAttribute("title", "403 Статус ошибки");
                break;
            }
            case HttpServletResponse.SC_NOT_FOUND : {
                model.addAttribute("header", "УПС! Страница не найдена");
                model.addAttribute("httpStatus", "404");
                model.addAttribute("title", "404 Статус ошибки");
                break;
            }
            case HttpServletResponse.SC_INTERNAL_SERVER_ERROR : {
                model.addAttribute("header", "УПС! Разработчик, что то не учел, простите, мы его уведомим об этом");
                model.addAttribute("httpStatus", "500");
                model.addAttribute("title", "500 Статус ошибки");
                break;
            }
        }
        return "404";
    }

    @Override
    public String getErrorPath() {
        return PATH;
    }
}

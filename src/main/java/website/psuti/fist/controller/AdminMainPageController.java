package website.psuti.fist.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import website.psuti.fist.model.MenuItemHeaderInMainPage;
import website.psuti.fist.service.MenuItemHeaderInMainPagesService;

@Controller
public class AdminMainPageController {

    @Autowired
    private MenuItemHeaderInMainPagesService menuItemHeaderInMainPagesService;

    @RequestMapping("/admin/mainPage/update")
    public String adminMainPageUpdate(Model model) {
        model.addAttribute("menuItems", menuItemHeaderInMainPagesService.getAll());
        model.addAttribute("item", new MenuItemHeaderInMainPage());
        return "adminUpdateMainPage";
    }

    @RequestMapping("/admin/mainPage/update/submit")
    public ModelAndView adminMainPageUpdateSubmit(@ModelAttribute("item") MenuItemHeaderInMainPage item) {
        ModelAndView modelAndView = new ModelAndView("adminUpdateMainPage");
        modelAndView.addObject("menuItems", menuItemHeaderInMainPagesService.getAll());
        modelAndView.addObject("item", new MenuItemHeaderInMainPage());
        menuItemHeaderInMainPagesService.update(item);
        return modelAndView;
    }
}

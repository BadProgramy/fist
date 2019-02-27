package website.psuti.fist.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import website.psuti.fist.configuration.ModelAndViewConfiguration;
import website.psuti.fist.constant.MainPageConstant;
import website.psuti.fist.model.EducationProcess;
import website.psuti.fist.model.MenuItemHeaderInMainPage;
import website.psuti.fist.service.EducationProcessService;
import website.psuti.fist.service.MenuItemHeaderInMainPagesService;

@Controller
public class AdminMainPageController {

    @Autowired
    private MenuItemHeaderInMainPagesService menuItemHeaderInMainPagesService;

    @Autowired
    private ModelAndViewConfiguration modelAndViewConfiguration;

    @Autowired
    private EducationProcessService educationProcessService;

    /*@RequestMapping("/admin/mainPage/headers/update")
    public String adminMainPageHeadersUpdate(Model model) {
        model.addAttribute("menuItems", menuItemHeaderInMainPagesService.findItemByKeyWord(MainPageConstant.HEADERS.getKeyWord()));
        model.addAttribute("item", new MenuItemHeaderInMainPage());
        return "adminUpdateHeadersMainPage";
    }

    @RequestMapping("/admin/mainPage/headers/update/submit")
    public String adminMainPageHeadersUpdateSubmit(@ModelAttribute("item") MenuItemHeaderInMainPage item) {
        ModelAndView modelAndView = new ModelAndView("adminUpdateHeadersMainPage");
        modelAndView.addObject("menuItems", menuItemHeaderInMainPagesService.findItemByKeyWord(MainPageConstant.HEADERS.getKeyWord()));
        modelAndView.addObject("item", new MenuItemHeaderInMainPage());
        menuItemHeaderInMainPagesService.update(item);
        return "redirect:../update";
    }*/



    /*@RequestMapping("/admin/mainPage/labels/update")
    public String adminMainPageLabelUpdate(Model model) {
        model.addAttribute("menuItems", menuItemHeaderInMainPagesService.findItemByKeyWord(MainPageConstant.LABEL_HEADER.getKeyWord()));
        model.addAttribute("item", new MenuItemHeaderInMainPage());
        return "adminUpdateLabelMainPage";
    }

    @RequestMapping("/admin/mainPage/labels/update/submit")
    public String adminMainPageLabelsUpdateSubmit(@ModelAttribute("item") MenuItemHeaderInMainPage item) {
        ModelAndView modelAndView = new ModelAndView("adminUpdateLabelMainPage");
        modelAndView.addObject("menuItems", menuItemHeaderInMainPagesService.findItemByKeyWord(MainPageConstant.LABEL_HEADER.getKeyWord()));
        modelAndView.addObject("item", new MenuItemHeaderInMainPage());
        menuItemHeaderInMainPagesService.update(item);
        return "redirect:../update";
    }*/

    /*@RequestMapping("/admin/mainPage/statistic/update")
    public String adminMainPageStatisticUpdate(Model model) {
        model.addAttribute("menuItems", menuItemHeaderInMainPagesService.findItemByKeyWord(MainPageConstant.CHARACTER_UNIVERSITY.getKeyWord()));
        model.addAttribute("item", new MenuItemHeaderInMainPage());
        return "adminUpdateStatisticMainPage";
    }

    @RequestMapping("/admin/mainPage/statistic/update/submit")
    public String adminMainPageStatisticUpdateSubmit(@ModelAttribute("item") MenuItemHeaderInMainPage item) {
        ModelAndView modelAndView = new ModelAndView("adminUpdateStatisticMainPage");
        modelAndView.addObject("menuItems", menuItemHeaderInMainPagesService.findItemByKeyWord(MainPageConstant.CHARACTER_UNIVERSITY.getKeyWord()));
        modelAndView.addObject("item", new MenuItemHeaderInMainPage());
        menuItemHeaderInMainPagesService.update(item);
        return "redirect:../update";
    }*/

    /*@RequestMapping("/admin/mainPage/educationProcess/update")
    public String adminMainPageEducationProcessUpdate(Model model) {
        model.addAttribute("menuItems", menuItemHeaderInMainPagesService.findItemByKeyWord(MainPageConstant.EDUCATION_PROCESS.getKeyWord()));
        model.addAttribute("educationProcess", educationProcessService.getAll());
        model.addAttribute("item", new MenuItemHeaderInMainPage());
        model.addAttribute("itemEducationProcess", new EducationProcess());
        return "adminUpdateEducationProcessMainPage";
    }

    @RequestMapping("/admin/mainPage/educationProcess/update/submit")
    public String adminMainPageEducationProcessUpdateSubmit(@ModelAttribute("item") MenuItemHeaderInMainPage item,
                                                            @ModelAttribute("itemEducationProcess") EducationProcess itemEducationProcess) {
        ModelAndView modelAndView = new ModelAndView("adminUpdateEducationProcessMainPage");
        modelAndView.addObject("menuItems", menuItemHeaderInMainPagesService.findItemByKeyWord(MainPageConstant.EDUCATION_PROCESS.getKeyWord()));
        modelAndView.addObject("educationProcess", educationProcessService.getAll());
        modelAndView.addObject("item", new MenuItemHeaderInMainPage());
        modelAndView.addObject("itemEducationProcess", new EducationProcess());
        educationProcessService.update(itemEducationProcess);
        menuItemHeaderInMainPagesService.update(item);
        return "redirect:../update";
    }*/

    /*@RequestMapping("/admin/mainPage/navigation/update")
    public String adminMainPageNavigationUpdate(Model model) {
        model.addAttribute("menuItems", menuItemHeaderInMainPagesService.findItemByKeyWord(MainPageConstant.NAVIGATION.getKeyWord()));
        model.addAttribute("item", new MenuItemHeaderInMainPage());
        return "adminUpdateNavigationMainPage";
    }

    @RequestMapping("/admin/mainPage/navigation/update/submit")
    public String adminMainPageNavigationUpdateSubmit(@ModelAttribute("item") MenuItemHeaderInMainPage item) {
        ModelAndView modelAndView = new ModelAndView("adminUpdateNavigationMainPage");
        modelAndView.addObject("menuItems", menuItemHeaderInMainPagesService.findItemByKeyWord(MainPageConstant.NAVIGATION.getKeyWord()));
        modelAndView.addObject("item", new MenuItemHeaderInMainPage());
        menuItemHeaderInMainPagesService.update(item);
        return "redirect:../update";
    }*/

    @RequestMapping("/admin/table/menuItem/headers/update")
    public String adminHeaderUpdate(Model model) {
        model.addAttribute("menuItems", menuItemHeaderInMainPagesService.findItemByKeyWord(MainPageConstant.HEADERS.getKeyWord()));
        model.addAttribute("item", new MenuItemHeaderInMainPage());
        return "adminTableUpdateMenuItems";
    }

    @RequestMapping("/admin/table/menuItem/navigation/update")
    public String adminNavigationUpdate(Model model) {
        model.addAttribute("menuItems", menuItemHeaderInMainPagesService.findItemByKeyWord(MainPageConstant.NAVIGATION.getKeyWord()));
        model.addAttribute("item", new MenuItemHeaderInMainPage());
        return "adminTableUpdateMenuItems";
    }

    @RequestMapping("/admin/table/menuItem/educationProcess/update")
    public String adminEducationProcessUpdate(Model model) {
        model.addAttribute("menuItems", menuItemHeaderInMainPagesService.findItemByKeyWord(MainPageConstant.EDUCATION_PROCESS.getKeyWord()));
        model.addAttribute("item", new MenuItemHeaderInMainPage());
        return "adminTableUpdateMenuItems";
    }

    @RequestMapping("/admin/table/menuItem/statistic/update")
    public String adminStatisticUpdate(Model model) {
        model.addAttribute("menuItems", menuItemHeaderInMainPagesService.findItemByKeyWord(MainPageConstant.CHARACTER_UNIVERSITY.getKeyWord()));
        model.addAttribute("item", new MenuItemHeaderInMainPage());
        return "adminTableUpdateMenuItems";
    }

    @RequestMapping("/admin/table/menuItem/labels/update")
    public String adminLabelUpdate(Model model) {
        model.addAttribute("menuItems", menuItemHeaderInMainPagesService.findItemByKeyWord(MainPageConstant.LABEL_HEADER.getKeyWord()));
        model.addAttribute("item", new MenuItemHeaderInMainPage());
        return "adminTableUpdateMenuItems";
    }

    @RequestMapping("/admin/table/menuItem/update/submit")
    public String adminMainPageUpdateSubmit(@ModelAttribute("item") MenuItemHeaderInMainPage item) {
        menuItemHeaderInMainPagesService.update(item);
        modelAndViewConfiguration.initModelAndView();
        return returnPage(item);
    }

    @RequestMapping("/admin/table/menuItem/delete/id={id}")
    public String adminMenuItemDelete(@PathVariable("id") Long id) {
        MenuItemHeaderInMainPage menuItemHeaderInMainPage = menuItemHeaderInMainPagesService.findItemById(id);
        menuItemHeaderInMainPagesService.delete(id);
        modelAndViewConfiguration.initModelAndView();
        return returnPage(menuItemHeaderInMainPage);
    }

    private String returnPage(MenuItemHeaderInMainPage item) {
        if (item.getKeyWord().equals(MainPageConstant.LABEL_HEADER.getKeyWord())) return "redirect:../labels/update";
        else if(item.getKeyWord().equals(MainPageConstant.CHARACTER_UNIVERSITY.getKeyWord())) return "redirect:../statistic/update";
        else if(item.getKeyWord().equals(MainPageConstant.EDUCATION_PROCESS.getKeyWord())) return "redirect:../educationProcess/update";
        else if(item.getKeyWord().equals(MainPageConstant.NAVIGATION.getKeyWord())) return "redirect:../navigation/update";
        else if(item.getKeyWord().equals(MainPageConstant.HEADERS.getKeyWord())) return "redirect:../headers/update";
        else return "404";
    }
}

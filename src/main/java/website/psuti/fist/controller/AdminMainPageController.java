package website.psuti.fist.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import website.psuti.fist.configuration.ModelAndViewConfiguration;
import website.psuti.fist.constant.MainPageConstant;
import website.psuti.fist.model.EducationProcess;
import website.psuti.fist.model.MenuItemHeaderInMainPage;
import website.psuti.fist.service.EducationProcessService;
import website.psuti.fist.service.MenuItemHeaderInMainPagesService;

import java.util.ArrayList;
import java.util.List;

@Controller
public class AdminMainPageController {

    @Autowired
    private MenuItemHeaderInMainPagesService menuItemHeaderInMainPagesService;

    @Autowired
    private ModelAndViewConfiguration modelAndViewConfiguration;

    @RequestMapping(value = "/admin/page/menuItem/add", method = RequestMethod.GET)
    public String adminAddMenuItemInHeader(Model model) {
        model.addAttribute("menuItem", new MenuItemHeaderInMainPage());
        model.addAttribute("menuItems", menuItemHeaderInMainPagesService.findItemByKeyWord(MainPageConstant.HEADERS.getKeyWord()));
        model.addAttribute("parentMenuItems", menuItemHeaderInMainPagesService.findItemByIdParent(MainPageConstant.HEADERS.getId()));
        return "adminPageAddMenuItem";
    }

    @RequestMapping(value = "/admin/page/menuItem/add/submit", method = RequestMethod.POST)
    public String adminAddMenuItemInHeaderSubmit(@ModelAttribute("menuItem") MenuItemHeaderInMainPage menuItem) {
        menuItem.setIdPicture(0);
        menuItem.setPinNumber(menuItemHeaderInMainPagesService.findItemByIdParent(menuItem.getIdMenuItemParentHeaderInMainPage()).size());
        menuItem.setKeyWord(MainPageConstant.HEADERS.getKeyWord());
        menuItemHeaderInMainPagesService.insert(menuItem);
        return "redirect:../add";
    }

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

    private List<MainPageConstant> constantsMainPageItem() {
        List<MainPageConstant> constants = new ArrayList<>();
        constants.add(MainPageConstant.HEADERS);
        constants.add(MainPageConstant.CHARACTER_UNIVERSITY);
        constants.add(MainPageConstant.EDUCATION_PROCESS);
        constants.add(MainPageConstant.LABEL_HEADER);
        constants.add(MainPageConstant.NAVIGATION);
        return constants;
    }

    @RequestMapping(value = "/admin/table/menuItem/headers/update", method = RequestMethod.GET)
    public String adminHeaderUpdate(Model model) {
        model.addAttribute("menuItems", menuItemHeaderInMainPagesService.findItemByKeyWord(MainPageConstant.HEADERS.getKeyWord()));
        model.addAttribute("menuItem", new MenuItemHeaderInMainPage());
        model.addAttribute("keyWords", constantsMainPageItem());
        model.addAttribute("item", new MenuItemHeaderInMainPage());
        return "adminTableUpdateMenuItems";
    }

    @RequestMapping(value = "/admin/table/menuItem/navigation/update", method = RequestMethod.GET)
    public String adminNavigationUpdate(Model model) {
        model.addAttribute("menuItems", menuItemHeaderInMainPagesService.findItemByKeyWord(MainPageConstant.NAVIGATION.getKeyWord()));
        model.addAttribute("menuItem", new MenuItemHeaderInMainPage());
        model.addAttribute("keyWords", constantsMainPageItem());
        model.addAttribute("item", new MenuItemHeaderInMainPage());
        return "adminTableUpdateMenuItems";
    }

    @RequestMapping(value = "/admin/table/menuItem/educationProcess/update", method = RequestMethod.GET)
    public String adminEducationProcessUpdate(Model model) {
        model.addAttribute("menuItems", menuItemHeaderInMainPagesService.findItemByKeyWord(MainPageConstant.EDUCATION_PROCESS.getKeyWord()));
        model.addAttribute("menuItem", new MenuItemHeaderInMainPage());
        model.addAttribute("keyWords", constantsMainPageItem());
        model.addAttribute("item", new MenuItemHeaderInMainPage());
        return "adminTableUpdateMenuItems";
    }

    @RequestMapping(value = "/admin/table/menuItem/statistic/update", method = RequestMethod.GET)
    public String adminStatisticUpdate(Model model) {
        model.addAttribute("menuItems", menuItemHeaderInMainPagesService.findItemByKeyWord(MainPageConstant.CHARACTER_UNIVERSITY.getKeyWord()));
        model.addAttribute("menuItem", new MenuItemHeaderInMainPage());
        model.addAttribute("keyWords", constantsMainPageItem());
        model.addAttribute("item", new MenuItemHeaderInMainPage());
        return "adminTableUpdateMenuItems";
    }

    @RequestMapping(value = "/admin/table/menuItem/labels/update", method = RequestMethod.GET)
    public String adminLabelUpdate(Model model) {
        model.addAttribute("menuItems", menuItemHeaderInMainPagesService.findItemByKeyWord(MainPageConstant.LABEL_HEADER.getKeyWord()));
        model.addAttribute("menuItem", new MenuItemHeaderInMainPage());
        model.addAttribute("keyWords", constantsMainPageItem());
        model.addAttribute("item", new MenuItemHeaderInMainPage());
        return "adminTableUpdateMenuItems";
    }

    @RequestMapping(value = "/admin/table/menuItem/update/submit", method = RequestMethod.POST)
    public String adminMainPageUpdateSubmit(@ModelAttribute("item") MenuItemHeaderInMainPage item) {
        menuItemHeaderInMainPagesService.update(item);
        modelAndViewConfiguration.initModelAndView();
        return returnPage(item);
    }

    @RequestMapping(value = "/admin/table/menuItem/add/submit", method = RequestMethod.POST)
    public String adminMainPageAddSubmit(@ModelAttribute("menuItem") MenuItemHeaderInMainPage menuItem) {
        menuItemHeaderInMainPagesService.insertById(menuItem);
        modelAndViewConfiguration.initModelAndView();
        return returnPage(menuItem);
    }

    @RequestMapping(value = "/admin/table/menuItem/delete/id={id}", method = RequestMethod.GET)
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

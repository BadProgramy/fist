package website.psuti.fist.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import website.psuti.fist.configuration.ModelAndViewConfiguration;
import website.psuti.fist.model.HTMLStructurePage;
import website.psuti.fist.service.HTMLStructurePageService;

@Controller
public class AdminHtmlStructurePageController {

    @Autowired
    private HTMLStructurePageService htmlStructurePageService;

    @Autowired
    private ModelAndViewConfiguration modelAndViewConfiguration;

    @RequestMapping("/admin/page/htmlStructure/update")
    public ModelAndView updateHtmlStructure() {
        ModelAndView modelAndView = new ModelAndView("adminPageUpdateHtmlStructure");
        modelAndView.addObject("item", new HTMLStructurePage());
        modelAndView.addObject("pages", htmlStructurePageService.getAll());
        return modelAndView;
    }


    @RequestMapping("/admin/page/htmlStructure/update/submit")
    public String adminHtmlStructureUpdateSubmit(@ModelAttribute("item") HTMLStructurePage item) {
        htmlStructurePageService.update(item);
        modelAndViewConfiguration.initModelAndView();
        return "redirect:../update";
    }
}

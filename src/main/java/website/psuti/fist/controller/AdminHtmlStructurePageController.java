package website.psuti.fist.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import website.psuti.fist.configuration.ModelAndViewConfiguration;
import website.psuti.fist.model.HTMLStructurePage;
import website.psuti.fist.model.TypeHtmlCode;
import website.psuti.fist.service.HTMLStructurePageService;

@Controller
public class AdminHtmlStructurePageController {

    @Autowired
    private HTMLStructurePageService htmlStructurePageService;

    @Autowired
    private ModelAndViewConfiguration modelAndViewConfiguration;

    @RequestMapping("/admin/page/htmlStructure/body/update")
    public ModelAndView updateHtmlStructureBody() {
        ModelAndView modelAndView = new ModelAndView("adminPageUpdateHtmlStructure");
        modelAndView.addObject("item", new HTMLStructurePage());
        modelAndView.addObject("pages", htmlStructurePageService.findHTMLCodeByType(TypeHtmlCode.BODY));
        return modelAndView;
    }

    @RequestMapping("/admin/page/htmlStructure/head/update")
    public ModelAndView updateHtmlStructureHead() {
        ModelAndView modelAndView = new ModelAndView("adminPageUpdateHtmlStructure");
        modelAndView.addObject("item", new HTMLStructurePage());
        modelAndView.addObject("pages", htmlStructurePageService.findHTMLCodeByType(TypeHtmlCode.HEAD));
        return modelAndView;
    }


    @RequestMapping("/admin/page/htmlStructure/update/submit")
    public String adminHtmlStructureUpdateSubmit(@ModelAttribute("item") HTMLStructurePage item) {
        item.setNamePage(htmlStructurePageService.findHTMLStructurePageById(item.getId()).getNamePage());
        htmlStructurePageService.update(item);
        modelAndViewConfiguration.initModelAndView();
        if (item.getTypeHtmlCode().equals(TypeHtmlCode.BODY))
            return "redirect:../body/update";
        else return "redirect:../head/update";
    }
}

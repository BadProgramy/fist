package website.psuti.fist.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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

    @RequestMapping(value = "/admin/page/htmlStructure/body/update", method = RequestMethod.GET)
    public ModelAndView updateHtmlStructureBody() {
        ModelAndView modelAndView = new ModelAndView("adminPageUpdateHtmlStructure");
        HTMLStructurePage item = new HTMLStructurePage();
        item.setTypeHtmlCode(TypeHtmlCode.BODY);
        modelAndView.addObject("item", item);
        modelAndView.addObject("pages", htmlStructurePageService.findHTMLCodeByType(TypeHtmlCode.BODY));
        modelAndView.addObject("typesHtmlCode", TypeHtmlCode.values());
        return modelAndView;
    }

    @RequestMapping(value = "/admin/page/htmlStructure/head/update", method = RequestMethod.GET)
    public ModelAndView updateHtmlStructureHead() {
        ModelAndView modelAndView = new ModelAndView("adminPageUpdateHtmlStructure");
        HTMLStructurePage item = new HTMLStructurePage();
        item.setTypeHtmlCode(TypeHtmlCode.BODY);
        modelAndView.addObject("item", item);
        modelAndView.addObject("pages", htmlStructurePageService.findHTMLCodeByType(TypeHtmlCode.HEAD));
        modelAndView.addObject("typesHtmlCode", TypeHtmlCode.values());
        return modelAndView;
    }


    @RequestMapping(value = "/admin/page/htmlStructure/update/submit", method = RequestMethod.POST)
    public String adminHtmlStructureUpdateSubmit(@ModelAttribute("item") HTMLStructurePage item) {
        item.setNamePage(htmlStructurePageService.findHTMLStructurePageById(item.getId()).getNamePage());
        htmlStructurePageService.update(item);
        modelAndViewConfiguration.initModelAndView();
        if (item.getTypeHtmlCode().equals(TypeHtmlCode.BODY))
            return "redirect:../body/update";
        else return "redirect:../head/update";
    }
}

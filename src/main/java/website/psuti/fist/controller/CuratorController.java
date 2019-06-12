package website.psuti.fist.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import website.psuti.fist.configuration.ModelAndViewConfiguration;

@Controller
public class CuratorController {
    @Autowired
    private ModelAndViewConfiguration modelAndViewConfiguration;


    @RequestMapping(value = "/students/groupCurators", method = RequestMethod.GET)
    public ModelAndView groupCurators() {
        ModelAndView modelAndView = modelAndViewConfiguration.initModelAndView();
        modelAndView.setViewName("groupCurators");
        return modelAndView;
    }
}

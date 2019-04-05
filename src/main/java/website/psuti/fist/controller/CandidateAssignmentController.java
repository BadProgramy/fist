package website.psuti.fist.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import website.psuti.fist.configuration.ModelAndViewConfiguration;

@Controller
public class CandidateAssignmentController {

    @Autowired
    private ModelAndViewConfiguration modelAndViewConfiguration;


    //TODO кандидаты на отчисление.....................................................................
    @RequestMapping("/candidatesForExpulsion")
    public ModelAndView candidatesForExpulsion() {
        ModelAndView modelAndView = modelAndViewConfiguration.initModelAndView();
        modelAndView.setViewName("candidatesForExpulsion");
        return modelAndView;
    }
}

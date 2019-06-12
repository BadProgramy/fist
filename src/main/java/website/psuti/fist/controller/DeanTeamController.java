package website.psuti.fist.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import website.psuti.fist.configuration.ModelAndViewConfiguration;

@Controller
public class DeanTeamController {

    @Autowired
    private ModelAndViewConfiguration modelAndViewConfiguration;

    @RequestMapping(value = "/deanTeam", method = RequestMethod.GET)
    public ModelAndView prepod() {
        ModelAndView modelAndView = modelAndViewConfiguration.initModelAndView();
        modelAndView.setViewName("deanTeam");
        return modelAndView;
    }

    @RequestMapping(value = "/deanTeams/{id}", method = RequestMethod.GET)
    public ModelAndView characteristicEmployee(@PathVariable long id) {
        ModelAndView modelAndView = modelAndViewConfiguration.initModelAndView();
        modelAndView.setViewName("characteristicEmployee");
        modelAndView.addObject("employee", modelAndViewConfiguration.getItemById(modelAndViewConfiguration.getEmployees(), id));
        return modelAndView;
    }
}

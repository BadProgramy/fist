package website.psuti.fist.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import website.psuti.fist.model.Curator;
import website.psuti.fist.service.CuratorService;

@Controller
public class AdminCuratorController {
    @Autowired
    private CuratorService curatorService;


    @RequestMapping(value = "/admin/content/curator", method = RequestMethod.GET)
    public ModelAndView curator() {
        ModelAndView modelAndView = new ModelAndView("adminContentCurator");
        modelAndView.addObject("curators",  curatorService.getAll());
        modelAndView.addObject("curatorGroup",  new Curator());
        return modelAndView;
    }

    @RequestMapping(value = "/admin/content/curator/add/submit", method = RequestMethod.POST)
    public String addNewCuratorSubmit(@ModelAttribute Curator curator ) {
        curatorService.insert(curator);
        return "redirect:../";
    }

    @RequestMapping(value = "/admin/content/curator/update/{id}", method = RequestMethod.GET)
    public ModelAndView updateCurator(@PathVariable("id") Long id/*, Model model*/) {
        ModelAndView modelAndView = new ModelAndView("adminContentCuratorUpdate");
        Curator curator = curatorService.findById(id);
        modelAndView.addObject("curatorGroup", curator);
        return modelAndView;
        //return "adminUpdateNews";
    }

    @RequestMapping(value = "/admin/content/curator/update/submit", method = RequestMethod.POST)
    public String updateCuratorSubmit(@ModelAttribute Curator curatorGroup) {
        curatorService.update(curatorGroup);
        return "redirect:../../curator";
    }

    @RequestMapping(value = "/admin/content/curator/delete/{id}",method = RequestMethod.GET)
    public String deleteCurator(@PathVariable("id") long curatorId) {
        curatorService.delete(curatorId);
        return "redirect:../../curator";
    }
}

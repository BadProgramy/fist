package website.psuti.fist.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import website.psuti.fist.model.CandidateAssignment;
import website.psuti.fist.service.CandidateAssignmentService;

import java.io.IOException;

@Controller
public class AdminCandidateAssignmentController {
    @Autowired
    private CandidateAssignmentService candidateAssignmentService;

    @RequestMapping("/admin/content/candidateAssignment")
    public ModelAndView candidateAssignment() {
        ModelAndView modelAndView = new ModelAndView("adminContentCandidateAssignment");
        modelAndView.addObject("candidateAssignments",  candidateAssignmentService.getAll());
        modelAndView.addObject("candidateAssignment",  new CandidateAssignment());
        return modelAndView;
    }

    @RequestMapping("/admin/content/candidateAssignment/add/submit")
    public String addCandidateSubmit(@ModelAttribute CandidateAssignment candidateAssignment ) {
        candidateAssignmentService.insert(candidateAssignment);
        return "redirect:../";
    }

    @RequestMapping(value = "/admin/content/candidateAssignment/update/{id}", method = RequestMethod.GET)
    public ModelAndView updateCandidate(@PathVariable("id") Long id/*, Model model*/) {
        ModelAndView modelAndView = new ModelAndView("adminContentCandidateAssignmentUpdate");
        CandidateAssignment student = candidateAssignmentService.findById(id);
        modelAndView.addObject("candidateAssignment", student);
        return modelAndView;
        //return "adminUpdateNews";
    }

    @RequestMapping(value = "/admin/content/candidateAssignment/update/submit", method = RequestMethod.POST)
    public String updateCandidateSubmit(@ModelAttribute CandidateAssignment candidateAssignment) {
        candidateAssignmentService.update(candidateAssignment);
        return "redirect:../../candidateAssignment";
    }

    @RequestMapping(value = "/admin/content/candidateAssignment/delete/{id}",method = RequestMethod.GET)
    public String deleteCandidate(@PathVariable("id") long studentId) {
        candidateAssignmentService.delete(studentId);
        return "redirect:../../candidateAssignment";
    }
}

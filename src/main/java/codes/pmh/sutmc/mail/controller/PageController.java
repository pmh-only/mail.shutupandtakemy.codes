package codes.pmh.sutmc.mail.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class PageController {
    @GetMapping("/")
    public String redirectMainPage () {
        return "redirect:/en/main";
    }

    @GetMapping("/error")
    public String redirectErrorPage () {
        return "redirect:/en/main?message=FAIL";
    }

    @GetMapping("/en/main")
    public ModelAndView getMainPageEN (
            @RequestParam(value = "message", required = false) String message,
            @RequestParam(value = "address", required = false) String address) {

        ModelAndView mv = new ModelAndView("main_en");

        if (message == null) {
            mv.addObject("message", "");
        } else {
            mv.addObject("message", message);
        }

        mv.addObject("address", address);

        return mv;
    }

    @GetMapping("/ko/main")
    public ModelAndView getMainPageKO (
            @RequestParam(value = "message", required = false) String message,
            @RequestParam(value = "address", required = false) String address) {

        ModelAndView mv = new ModelAndView("main_ko");

        if (message == null) {
            mv.addObject("message", "");
        } else {
            mv.addObject("message", message);
        }

        mv.addObject("address", address);

        return mv;
    }
}

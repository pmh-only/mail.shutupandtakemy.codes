package codes.pmh.sutmc.mail.controller;

import codes.pmh.sutmc.mail.service.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Controller
public class MailController {
    private MailService mailService;

    @Autowired
    public void setMailService(MailService mailService) {
        this.mailService = mailService;
    }

    @PostMapping("/en/mail")
    public String postActionMailCreationEN (@RequestParam("email") String email) {
        Optional<Boolean> check = this.mailService.checkAndVerify(email);
        if (check.isEmpty()) {
            return "redirect:/en/main?message=TOO_EARLY";
        }

        if (!check.get()) {
            return "redirect:/en/main?message=CHECK_EMAIL";
        }

        Optional<String> address = this.mailService.addEmail(email);

        if (address.isEmpty()) {
            return "redirect:/en/main?message=FAIL";
        }

        return "redirect:/en/main?message=DONE&address=" + address.get();
    }

    @PostMapping("/ko/mail")
    public String postActionMailCreationKR (@RequestParam("email") String email) {
        Optional<Boolean> check = this.mailService.checkAndVerify(email);
        if (check.isEmpty()) {
            return "redirect:/ko/main?message=TOO_EARLY";
        }

        if (!check.get()) {
            return "redirect:/ko/main?message=CHECK_EMAIL";
        }

        Optional<String> address = this.mailService.addEmail(email);

        if (address.isEmpty()) {
            return "redirect:/ko/main?message=FAIL";
        }

        return "redirect:/ko/main?message=DONE&address=" + address.get();
    }
}

package hieunv.dev.gatewayserver.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/fallback")
public class FallbackController {

    @RequestMapping("/contact-support")
    public String fallback() {
        return "An error occurred. Please try again later";
    }
}

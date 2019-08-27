package com.example.zoom.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * The Home Controller.
 */
@Controller
public class HomeApis {

    /**
     * Swagger home.
     * @return swagger-ui.html
     */
    @RequestMapping("/")
    public String home() {
        return "redirect:swagger-ui.html";
    }
}

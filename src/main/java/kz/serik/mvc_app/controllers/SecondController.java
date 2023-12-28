package kz.serik.mvc_app.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author Admin on 21.09.2023
 * @project spring_mvc_app2
 */
@Controller
@RequestMapping("/second")
public class SecondController {
    @GetMapping("/exit")
    public String exit(){
        return "second/exit";
    }
}

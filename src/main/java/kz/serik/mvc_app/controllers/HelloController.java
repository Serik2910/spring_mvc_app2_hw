package kz.serik.mvc_app.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author Admin on 20.09.2023
 * @project spring_mvc_app1
 */
@Controller
public class HelloController {
    //@RequestMapping(method = RequestMethod.GET, path = "/hello-world", value = "/hello-world")
    @GetMapping(value = "/hello-world", path = "/hello-world")
    public String sayHello(){return "hello_world";}
}

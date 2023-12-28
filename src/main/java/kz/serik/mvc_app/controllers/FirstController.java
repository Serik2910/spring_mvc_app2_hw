package kz.serik.mvc_app.controllers;


import jakarta.servlet.http.*;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


import java.util.*;

/**
 * @author Admin on 21.09.2023
 * @project spring_mvc_app2
 */
@Controller
@RequestMapping("/first")
public class FirstController {
    @GetMapping("/hello")
    public String helloPage(
            HttpServletRequest request,
            HttpEntity<String> httpEntity,
            @RequestHeader HttpHeaders headers,
            @RequestParam(name = "name", required = false) Optional<String> name,
            @RequestParam(name = "age", required = false) Optional<String> age,
            Model model
            ){
        Enumeration<String> stringEnumeration = request.getHeaderNames();
        while (stringEnumeration.hasMoreElements())
        {
            String key = stringEnumeration.nextElement();
            System.out.println(key+":");
            Enumeration<String> values = request.getHeaders(key);
            while (values.hasMoreElements()){
                System.out.println("\t"+values.nextElement());
            }
        }
        HttpHeaders httpHeaders =  httpEntity.getHeaders();
        Set<Map.Entry<String,List<String>>> setEntry =  httpHeaders.entrySet();
        for (Map.Entry<String,List<String>> entry:
             setEntry) {
            System.out.println(entry.getKey()+":");
            for (String val:
                    entry.getValue()) {
                System.out.println("\t"+val);
            }
        }
        setEntry =  headers.entrySet();
        for (Map.Entry<String,List<String>> entry:
             setEntry) {
            System.out.println(entry.getKey()+":");
            for (String val:
                    entry.getValue()) {
                System.out.println("\t"+val);
            }
        }

        Optional<String> name_ = Optional.ofNullable(request.getParameter("name"));
        Optional<String> ageString = Optional.ofNullable(request.getParameter("age"));
        Optional<Integer> age_ = Optional.ofNullable(ageString.orElse(null)).filter(p->p.matches("-?(0|[1-9]\\d*)")).map(Integer::parseInt);

        Optional<Integer> _age = Optional.ofNullable(age.orElse(null)).filter(p->p.matches("-?(0|[1-9]\\d*)")).map(Integer::parseInt);
        model.addAttribute("message", "name: "+name_.orElse("no name")+", age: "+age_.orElse(0));
        model.addAttribute("message_","name: "+name.orElse("no name")+", age: "+_age.orElse(0));
        return "first/hello";
    }
    @GetMapping("/bye")
    public String byePage(){

        return "first/bye";
    }
}

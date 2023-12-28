package kz.serik.mvc_app.controllers;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import kz.serik.mvc_app.DAO.BooksDAO;
import kz.serik.mvc_app.DAO.PersonDAO;
import kz.serik.mvc_app.models.Book;
import kz.serik.mvc_app.models.Person;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Admin on 23.09.2023
 * @project spring_mvc_app2
 */
@Controller
@RequestMapping("/people")
public class PeopleController {
    private final PersonDAO personDAO;
    private final BooksDAO booksDAO;

    public PeopleController(PersonDAO personDAO, BooksDAO booksDAO) {
        this.personDAO = personDAO;
        this.booksDAO = booksDAO;
    }

    @GetMapping(path = {"/", "/index", ""})
    public String index( Model model){
        model.addAttribute("people",personDAO.indexTemplate());
        model.addAttribute("result", "success");
        return "people/index";
    }

    @GetMapping("/{id}")
    public String show(
            @PathVariable("id") int id,
            Model model
    ){
        Person person = personDAO.getPersonTemplate(id);
        List<Book> books = booksDAO.getBooksTemplate(person.getId());
        model.addAttribute("person", person);
        model.addAttribute("books", books);
        return "people/person_detail";
    }
//    @GetMapping("/new")
//    public String newPerson(Model model){
//        Person person = new Person();
//        model.addAttribute("person", person);
//        return "people/new";
//    }
    // снизу идентично свыше закомментированному
    @GetMapping("/new")
    public String newPerson(@ModelAttribute("person") Person person){
        return "people/new";
    }

    @PostMapping(value = "/new")
    public String newPerson(
            @ModelAttribute("person") @Valid Person person,
            BindingResult bindingResult,
            //@RequestParam(name="name") String name,
            Model model,
            HttpServletRequest request){
        if(bindingResult.hasErrors()){
            return "people/new";
        }
        if(personDAO.addPersonTemplate(person)){
            model.addAttribute("result", "success");
        }else {
            model.addAttribute("result", "fail");
        }
        return "redirect:/people";
    }

    @GetMapping(path = {"/{id}/edit"})
    public String editPerson(@PathVariable("id") int id, Model model){
        Person person = personDAO.getPersonTemplate(id);
        model.addAttribute("person", person);
        return "people/edit";
    }

    @PatchMapping("/{id}")
    public String updatePerson(
            @ModelAttribute("person") @Valid Person person,
            BindingResult bindingResult,
            @PathVariable("id") int id){
        if(bindingResult.hasErrors()){
            return "people/edit";
        }
        personDAO.updatePersonTemplate(person);
        return "redirect:/people";
    }

    @DeleteMapping("/{id}")
    public String deletePerson(@PathVariable("id") int id){
        personDAO.deletePerson(id);
        return "redirect:/people";
    }
}

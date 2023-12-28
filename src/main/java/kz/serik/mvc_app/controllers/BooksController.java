package kz.serik.mvc_app.controllers;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import kz.serik.mvc_app.DAO.BooksDAO;
import kz.serik.mvc_app.DAO.PersonDAO;
import kz.serik.mvc_app.models.Book;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;

@Controller
@RequestMapping("/books")
public class BooksController {
    private final BooksDAO booksDAO;
    private final PersonDAO personDAO;

    public BooksController(BooksDAO booksDAO, PersonDAO personDAO) {
        this.booksDAO = booksDAO;
        this.personDAO = personDAO;
    }

    @GetMapping(path={"/", "/index", ""})
    public String index(Model model){
        model.addAttribute("books", booksDAO.indexTemplate());
        model.addAttribute("result", "success");
        return "books/index";
    }

    @GetMapping("/{id}")
    public String show(
            @PathVariable("id") int id,
            Model model
    ){
        Book book = booksDAO.getBookTemplate(id);
        model.addAttribute("book", book);
        model.addAttribute("people", book.getPersonId()==null?personDAO.indexTemplate(): Arrays.asList(personDAO.getPersonTemplate(book.getPersonId())));
        return "books/book_detail";
    }

    @GetMapping("/new")
    public String newBook(@ModelAttribute("book") Book book){
        return "books/new";
    }

    @PostMapping(value = "/new")
    public String newBook(
            @ModelAttribute("book") @Valid Book book,
            BindingResult bindingResult,
            //@RequestParam(name="name") String name,
            Model model,
            HttpServletRequest request){
        if(bindingResult.hasErrors()){
            return "books/new";
        }
        if(booksDAO.addBookTemplate(book)){
            model.addAttribute("result", "success");
        }else {
            model.addAttribute("result", "fail");
        }
        return "redirect:/books";
    }

    @GetMapping(path = {"/{id}/edit"})
    public String editBook(@PathVariable("id") int id, Model model){
        Book book = booksDAO.getBookTemplate(id);
        model.addAttribute("book", book);
        return "books/edit";
    }

    @PatchMapping("/{id}")
    public String updateBook(
            @ModelAttribute("person") @Valid Book book,
            BindingResult bindingResult,
            @PathVariable("id") int id){
        if(bindingResult.hasErrors()){
            return "books/edit";
        }
        booksDAO.updateBookTemplate(book);
        return "redirect:/books";
    }

    @DeleteMapping("/{id}")
    public String deleteBook(@PathVariable("id") int id) {
        booksDAO.deleteBook(id);
        return "redirect:/books";
    }
}

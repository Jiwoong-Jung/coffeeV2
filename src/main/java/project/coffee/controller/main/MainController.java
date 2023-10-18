package project.coffee.controller.main;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import project.coffee.domain.dto.main.CoffeeListSearchCond;
import project.coffee.domain.entity.CoffeeList;
import project.coffee.service.main.CoffeeListService;

import javax.servlet.http.HttpSession;
import java.util.List;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@Controller
@RequiredArgsConstructor
public class MainController {

    private final CoffeeListService coffeeListService;

    @GetMapping("/")
    public String main(@ModelAttribute("bookSearch") CoffeeListSearchCond bookSearch, Model model, HttpSession session){
        List<CoffeeList> books = coffeeListService.findBooks(bookSearch);
        model.addAttribute("books",books);
        return "main";
    }


}

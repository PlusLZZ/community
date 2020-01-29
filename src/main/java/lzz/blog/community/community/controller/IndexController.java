package lzz.blog.community.community.controller;

import lzz.blog.community.community.dto.PageutilDTO;
import lzz.blog.community.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class IndexController {

    @Autowired
    private QuestionService questionService;

    @GetMapping("/")
    public String index(Model model,
                        @RequestParam(name = "page", defaultValue = "1") Integer page,
                        @RequestParam(name = "size", defaultValue = "7") Integer size,
                        @RequestParam(name = "search", required = false) String search) {
        PageutilDTO pageutilDTO = questionService.list(search, page, size);
        model.addAttribute("pageutilDTO", pageutilDTO);
        model.addAttribute("search", search);
        return "index";
    }
}

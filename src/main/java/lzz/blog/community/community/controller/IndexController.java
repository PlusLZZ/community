package lzz.blog.community.community.controller;

import lzz.blog.community.community.dto.PageutilDTO;
import lzz.blog.community.community.dto.QuestionDTO;
import lzz.blog.community.community.mapper.QuestionMapper;
import lzz.blog.community.community.mapper.UserMapper;
import lzz.blog.community.community.model.Question;
import lzz.blog.community.community.model.User;
import lzz.blog.community.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.List;


@Controller
public class IndexController {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private QuestionService questionService;

    @GetMapping("/")
    public String index(HttpServletRequest request,
                        Model model,
                        @RequestParam(name = "page", defaultValue = "1") Integer page,
                        @RequestParam(name = "size", defaultValue = "5") Integer size) {
        try {
            Cookie[] cookies = request.getCookies();
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("token")) {
                    String token = cookie.getValue();
                    User user = userMapper.findByToken(token);
                    if (user != null) {
                        request.getSession().setAttribute("user", user);
                    }
                    break;
                }
            }
        } catch (Exception ex) {

        }

        PageutilDTO pageutilDTO = questionService.list(page, size);
        model.addAttribute("pageutilDTO", pageutilDTO);
        return "index";
    }
}

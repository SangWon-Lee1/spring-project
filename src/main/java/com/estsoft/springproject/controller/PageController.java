package com.estsoft.springproject.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDateTime;
import java.util.Arrays;

@Controller
public class PageController {
    // Person   GET /thymeleaf/example
    @GetMapping("/thymeleaf/example")
    public String show(Model model) {
        Person person = new Person();
        person.setId(1L);
        person.setName("고윤정");
        person.setAge(28);
        person.setHobbies(Arrays.asList("웃기", "이쁘기", "매력 뽐내기"));

        model.addAttribute("person", person);
        model.addAttribute("today", LocalDateTime.now());

        return "examplePage";  // html 페이지
    }
}

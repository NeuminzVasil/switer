package com.example.servingwebcontent.controller;

import com.example.servingwebcontent.domain.Customer;
import com.example.servingwebcontent.domain.Message;
import com.example.servingwebcontent.repos.MessageRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@Controller
public class MainController {
    @Autowired
    private MessageRepo messageRepo;

    @GetMapping("/")
    public String greeting(Map<String, Object> model) {
        return "greeting";
    }

    @GetMapping("/main")
    public String main(@RequestParam (required = false, defaultValue = "") String filter_param,
                       Model model) {

        Iterable<Message> messages = messageRepo.findAll();

        if (filter_param != null && !filter_param.isEmpty())
            messages = messageRepo.findByTag(filter_param);
        else
            messages = messageRepo.findAll();

        model.addAttribute("messages", messages);
        model.addAttribute("filter", filter_param);
        return "main";
    }

    @PostMapping("/main")
    public String add(@AuthenticationPrincipal Customer customer,
                      @RequestParam String text,
                      @RequestParam String tag,
                      Map<String, Object> model) {
        Message message = new Message(text, tag, customer);
        messageRepo.save(message);
        Iterable<Message> messages = messageRepo.findAll();
        model.put("messages", messages);
        return "main";
    }

}

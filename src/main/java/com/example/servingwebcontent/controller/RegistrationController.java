package com.example.servingwebcontent.controller;

import com.example.servingwebcontent.domain.Customer;
import com.example.servingwebcontent.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Map;

@Controller
public class RegistrationController {
    @Autowired
    private CustomerService customerService;

    @GetMapping("/registration")
    public String registration(Map<String, Object> model) {
        return "registration";
    }


    @PostMapping("/registration")
    public String addUser(Customer customer, Map<String, Object> model) {

        System.out.println(customer);

        if (!customerService.addCustomer(customer)) {
            model.put("message", "User " + customer.getLogin() + " exist!");
            return "registration";
        }

        return "redirect:/login";
    }

    @GetMapping("/activate/{code}")
    public String activate(Model model, @PathVariable String code) {
        boolean isActivated = customerService.activateUser(code);

        if (isActivated){
            model.addAttribute("message", "Activation Successfully");
        } else
            model.addAttribute("message", "Activation code not found");

        return "login";
    }

}

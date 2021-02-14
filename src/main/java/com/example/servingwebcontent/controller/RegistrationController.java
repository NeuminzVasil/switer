package com.example.servingwebcontent.controller;

import com.example.servingwebcontent.domain.Customer;
import com.example.servingwebcontent.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
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
    public String addUser(@Valid Customer customer, BindingResult bindingResult, Model model) {

        System.out.println(customer);

        if (customer.getPassword() != null && customer.getPassword().equals(customer.getPassword2())) {
            model.addAttribute("passwordError", "passwords are different!");
        }

        if (bindingResult.hasErrors()){
            Map<String, String> errors = ControllerUtils.getErrors(bindingResult);
            model.addAllAttributes(errors);
            return "registration";
        }

        if (!customerService.addCustomer(customer)) {
            model.addAttribute("usernameError", "User " + customer.getLogin() + " exist!");
            return "registration";
        }

        return "redirect:/login";
    }

    @GetMapping("/activate/{code}")
    public String activate(Model model, @PathVariable String code) {
        boolean isActivated = customerService.activateUser(code);

        if (isActivated) {
            model.addAttribute("message", "Activation Successfully");
        } else
            model.addAttribute("message", "Activation code not found");

        return "login";
    }

}

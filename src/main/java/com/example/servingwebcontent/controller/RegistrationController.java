package com.example.servingwebcontent.controller;

import com.example.servingwebcontent.domain.Customer;
import com.example.servingwebcontent.domain.Role;
import com.example.servingwebcontent.repos.CustomerRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@Controller
public class RegistrationController {
    @Autowired
    private CustomerRepo customerRepo;


    @GetMapping("/registration")
    public String registration(Map<String, Object> model) {
        return "registration";
    }


    @PostMapping("/registration")
    public String addUser(Customer customer, Map<String, Object> model) {

        System.out.println(customer);

        Customer customerFromDB = customerRepo.findByLogin(customer.getLogin());

        if (customerFromDB != null) {
            model.put("message", "User " + customerFromDB + " exist!");
            return "registration";
        }

        customer.setActive(true);
        customer.setAuthorities(Collections.singletonList(new Role(customer, "ROLE_GUEST")));
        System.out.println(customer);
        customerRepo.save(customer);

        return "redirect:/login";
    }

}

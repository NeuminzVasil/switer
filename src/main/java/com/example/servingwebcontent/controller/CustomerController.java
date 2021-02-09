package com.example.servingwebcontent.controller;

import com.example.servingwebcontent.domain.Customer;
import com.example.servingwebcontent.repos.CustomerRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Controller
@RequestMapping("/customer")
@PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
public class CustomerController {
    @Autowired
    private CustomerRepo customerRepo;

    @GetMapping
    public String customerList(Model model) {
        model.addAttribute("customers", customerRepo.findAll());
        return "customerList";
    }

    @GetMapping("{customer}")
    public String customerEditForm(@PathVariable Customer customer,
                                   Model model) {
        model.addAttribute("customer", customer);
        return "customerEdit";
    }

    @PostMapping
    public String customerSave(@RequestParam("id") Customer customer,
                               @RequestParam Map<String, String> form) {
//        customer.setLogin(customerLogin);
        customer.setFirstName(form.get("firstName"));
        customer.setLastName(form.get("lastName"));
        System.out.println(form.get("login"));


        customerRepo.save(customer);
        return "redirect:/customer";
    }


}

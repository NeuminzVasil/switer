package com.example.servingwebcontent.controller;

import com.example.servingwebcontent.domain.Customer;
import com.example.servingwebcontent.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Controller
@RequestMapping("/customer")
public class CustomerController {
    @Autowired
    private CustomerService customerService;

    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
    @GetMapping
    public String customerList(Model model) {
        model.addAttribute("customers", customerService.findAll());
        return "customerList";
    }

    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
    @GetMapping("{customer}")
    public String customerEditForm(@PathVariable Customer customer,
                                   Model model) {
        model.addAttribute("customer", customer);
        return "customerEdit";
    }

    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
    @PostMapping
    public String customerSave(@RequestParam("id") Customer customer,
                               @RequestParam Map<String, String> form) {

        customerService.saveCustomer(customer, form);

        return "redirect:/customer";
    }


    @GetMapping("profile")
    public String getProfile(Model model,
                             @AuthenticationPrincipal Customer customer) {
        model.addAttribute("login", customer.getLogin());
        model.addAttribute("email", customer.getEmail());
        return "profile";
    }


    @PostMapping("profile")
    public String updateProfile(@AuthenticationPrincipal Customer customer,
                                @RequestParam String password,
                                @RequestParam String email) {
        customerService.updateProfile(customer, password, email);
        return "redirect:/customer/profile";

    }

}

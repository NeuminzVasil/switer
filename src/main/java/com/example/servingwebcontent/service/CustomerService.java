package com.example.servingwebcontent.service;

import com.example.servingwebcontent.repos.CustomerRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomerService implements UserDetailsService {
    @Autowired
    private CustomerRepo customerRepo;

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        return customerRepo.findByLogin(login);
    }
}

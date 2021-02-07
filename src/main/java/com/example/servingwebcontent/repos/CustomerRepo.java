package com.example.servingwebcontent.repos;

import com.example.servingwebcontent.domain.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepo extends JpaRepository<Customer, Long> {

    Customer findByLogin(String login);
}

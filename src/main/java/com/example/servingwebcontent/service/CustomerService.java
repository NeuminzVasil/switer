package com.example.servingwebcontent.service;

import com.example.servingwebcontent.domain.Customer;
import com.example.servingwebcontent.domain.Role;
import com.example.servingwebcontent.repos.CustomerRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class CustomerService implements UserDetailsService {
    @Autowired
    private CustomerRepo customerRepo;

    @Autowired
    MailSender mailSender;

    @Autowired
    private PasswordEncoder passwordEncoder;


    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        return customerRepo.findByLogin(login);
    }

    public boolean addCustomer(Customer customer) {
        Customer customerFromDB = customerRepo.findByLogin(customer.getLogin());

        if (customerFromDB != null) {
            return false;
        }

        customer.setActive(true);
        customer.setAuthorities(Collections.singletonList(new Role(customer, "ROLE_GUEST")));
        customer.setActivationCode(UUID.randomUUID().toString());
        customer.setPassword(passwordEncoder.encode(customer.getPassword()));
        customerRepo.save(customer);

        sendMessage(customer);
        return true;

    }

    private void sendMessage(Customer customer) {
        if (!StringUtils.isEmpty(customer.getEmail())) {
            String message = String.format(
                    "Hello, %s! \n" +
                            "Подтвердите eMail пройдя по ссылке http://localhost:8080/activate/%s",
                    customer.getLogin(),
                    customer.getActivationCode()
            );
            mailSender.send(customer.getEmail(), "Activation Code", message);
        }
    }

    public boolean activateUser(String code) {
        Customer customer = customerRepo.findByActivationCode(code);

        if (customer == null) {
            return false;
        }

        customer.setActivationCode(null);
        customerRepo.save(customer);

        return true;

    }

    public List<Customer> findAll() {
        return customerRepo.findAll();
    }

    public void saveCustomer(Customer customer, Map<String, String> form) {

        customer.setLogin(customer.getLogin());
        customer.setFirstName(form.get("firstName"));
        customer.setLastName(form.get("lastName"));

        customerRepo.save(customer);
    }

    public void updateProfile(Customer customer, String password, String email) {
        String customerEmail = customer.getEmail();

        boolean isEmailChanged = (email != null && !email.equals(customerEmail)) ||
                (customerEmail != null && !customerEmail.equals(email));

        if (isEmailChanged) {
            customer.setEmail(email);

            if (!StringUtils.isEmpty(email)) {
                customer.setActivationCode(UUID.randomUUID().toString());
            }
        }

        if (!StringUtils.isEmpty(password)) {
            customer.setPassword(password);
        }

        customerRepo.save(customer);

        if (isEmailChanged) {
            sendMessage(customer);
        }
    }
}

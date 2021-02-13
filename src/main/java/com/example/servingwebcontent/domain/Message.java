package com.example.servingwebcontent.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "messages")
@Data
@NoArgsConstructor
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotBlank (message = "Please fill the message")
    @Length (max = 2048, message =  "Message to long")
    private String text;

    private String tag;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "customerid")
    private Customer customer;

    private String filename;


    public Message(String text, String tag, Customer customer) {
        this.text = text;
        this.tag = tag;
        this.customer = customer;
    }

    public String getCustomer() {
        return customer != null ? customer.getLogin() : "инкогнито";
    }

    @Override
    public String toString() {
        return "Message{" +
                "text='" + text + '\'' +
                ", tag='" + tag + '\'' +
                '}';
    }
}

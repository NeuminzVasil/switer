package com.example.servingwebcontent.domain;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "messages")
@Data
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String text;

    private String tag;

    /**
     * Пустой конструктор требуется для создания Entity
     */
    public Message() {
    }

    public Message(String text, String tag) {
        this.text = text;
        this.tag = tag;
    }

    @Override
    public String toString() {
        return "Message{" +
                "text='" + text + '\'' +
                ", tag='" + tag + '\'' +
                '}';
    }
}

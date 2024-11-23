package com.estsoft.springproject.book.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Book {
    @Id
    String id;
    @Column(nullable = false)
    String name;
    @Column(nullable = false)
    String author;
}
package com.PracticeSpringBoot.ConnectMultipleDatabase.modelBook;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "BOOK_TABLE")
public class Book {

    @Id
    private Integer id;
    private String name;
}

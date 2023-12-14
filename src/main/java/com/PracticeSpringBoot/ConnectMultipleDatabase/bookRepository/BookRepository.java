package com.PracticeSpringBoot.ConnectMultipleDatabase.bookRepository;

import com.PracticeSpringBoot.ConnectMultipleDatabase.modelBook.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface BookRepository extends JpaRepository<Book,Integer> {

}

package com.example.Library.repo;

import com.example.Library.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.lang.reflect.Member;
import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long> {
    List<Book> findByAvailable(Boolean available);
}


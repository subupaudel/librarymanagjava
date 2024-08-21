package com.example.Library.controller;
import com.example.Library.entity.Book;
import com.example.Library.entity.Loan;
import com.example.Library.entity.Member;
import com.example.Library.service.LibraryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/api/library")
public class LibraryController {
    private LibraryService libraryService;

    @Autowired
    public LibraryController(LibraryService libraryService) {
        this.libraryService = libraryService;
    }

    @PostMapping("/books")
    public Book addBook(@RequestBody Book book) {
        return libraryService.addBook(book);
    }

    @PostMapping("/members")
    public Member addMember(@RequestBody Member member) {
        return libraryService.addMember(member);
    }

    @PostMapping("/loans")
    public Loan loanBook(@RequestParam Long bookId, @RequestParam Long memberId) {
        return libraryService.loanBook(bookId, memberId);
    }

    @PutMapping("/loans/{loanId}/return")
    public void returnBook(@PathVariable Long loanId) {
        libraryService.returnBook(loanId);
    }

    @GetMapping("/loans/member/{memberId}")
    public List<Loan> getLoansByMemberId(@PathVariable Long memberId) {
        return libraryService.getLoansByMemberId(memberId);
    }
}

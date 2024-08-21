package com.example.Library.service;
import java.util.List;
import com.example.Library.entity.Book;
import com.example.Library.entity.Loan;
import com.example.Library.entity.Member;
import com.example.Library.repo.BookRepository;
import com.example.Library.repo.LoanRepository;
import com.example.Library.repo.MemberRepository;
import org.hibernate.engine.jdbc.env.internal.BlobAndClobCreator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class LibraryService {
    private final BookRepository bookRepository;
    private final MemberRepository memberRepository;
    private final LoanRepository loanRepository;
    @Autowired
    public LibraryService(BookRepository bookRepository, MemberRepository memberRepository, LoanRepository loanRepository){
        this.bookRepository = bookRepository;
        this.memberRepository = memberRepository;
        this.loanRepository  = loanRepository;
    }
    public Book addBook(Book book)
    {
        book.setAvailable(true);
        return bookRepository.save(book);
    }
     public Member addMember(Member member){
        return memberRepository.save(member);
     }
     public Loan loanBook(Long bookId, Long memberId){
        Book book = bookRepository.findById(bookId).orElseThrow(() -> new RuntimeException("Book not found"));
        if(!book.getAvailable()){
            throw new RuntimeException("Book is already loaned out");
        }
        Member member = memberRepository.findById(memberId).orElseThrow(()-> new RuntimeException("Member not found"));
        book.setAvailable(false);
        bookRepository.save(book);

        Loan loan = new Loan();
        loan.setBook(book);
        loan.setMember(member);
        loan.setLoanDate(LocalDate.now());
        return loanRepository.save(loan);
     }
    public void returnBook(Long loanId){
        Loan loan = loanRepository.findById(loanId).orElseThrow(()-> new RuntimeException("loan not found"));
        Book book = loan.getBook();
        book.setAvailable(true);
        bookRepository.save(book);
        loan.setReturnDate(LocalDate.now());
        loanRepository.save(loan);
    }
       public List<Loan> getLoansByMemberId(Long memberId){
        return loanRepository.findByMemberId(memberId);
       }
}

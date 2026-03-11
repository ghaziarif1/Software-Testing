package com.example.demo.service;

import com.example.demo.model.Book;
import com.example.demo.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookService {
    @Autowired
    private BookRepository bookRepository;

    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    public Book getBookById(Long id) {
        Optional<Book> book = bookRepository.findById(id);
        return book.orElse(null);
    }

    public Book saveBook(Book book) {
        return bookRepository.save(book);
    }
    public Book updateBook(Long id, Book bookDetails) {
        // On cherche le livre par son ID
        Optional<Book> optionalBook = bookRepository.findById(id);

        // Si le livre existe, on met à jour title + author
        if (optionalBook.isPresent()) {
            Book existingBook = optionalBook.get();
            existingBook.setTitle(bookDetails.getTitle());
            existingBook.setAuthor(bookDetails.getAuthor());

            // On sauvegarde en base (Hibernate sait que c'est une mise à jour)
            return bookRepository.save(existingBook);
        }

        // Si le livre n'existe pas → on retourne null (comme dans getBookById)
        return null;
    }
}
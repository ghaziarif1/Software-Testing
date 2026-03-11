package com.example.demo;

import com.example.demo.model.Book;
import com.example.demo.repository.BookRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@AutoConfigureMockMvc
public class BookControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    public void setUp() {
        bookRepository.deleteAll();
        bookRepository.save(new Book(null, "The Great Gatsby", "F. Scott Fitzgerald"));
        bookRepository.save(new Book(null, "1984", "George Orwell"));
    }

    @Test
    public void testGetAllBooks() throws Exception {
        mockMvc.perform(get("/api/books")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].title", is("The Great Gatsby")))
                .andExpect(jsonPath("$[1].title", is("1984")));
    }

    @Test
    public void testGetBookById() throws Exception {
        Book book = bookRepository.findAll().get(0);

        mockMvc.perform(get("/api/books/" + book.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title", is(book.getTitle())));
    }

    @Test
    public void testCreateBook() throws Exception {
        Book newBook = new Book(null, "To Kill a Mockingbird", "Harper Lee");

        mockMvc.perform(post("/api/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newBook)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title", is("To Kill a Mockingbird")))
                .andExpect(jsonPath("$.author", is("Harper Lee")));
    }
    @Test
    public void testUpdateBook() throws Exception {
        // ==================== PRÉPARATION DES DONNÉES ====================
        // On récupère le premier livre créé par @BeforeEach
        Book existingBook = bookRepository.findAll().get(0);
        Long bookId = existingBook.getId();

        // Nouvelles données à envoyer dans le body JSON
        Book updateDetails = new Book(null, "Le Grand Gatsby - Édition 2026", "F. Scott Fitzgerald & Grok");

        // ==================== EXÉCUTION DE LA REQUÊTE PUT ====================
        mockMvc.perform(put("/api/books/" + bookId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateDetails)))

                // ==================== VÉRIFICATIONS SUR LA RÉPONSE HTTP ====================
                .andExpect(status().isOk())                          // Code 200
                .andExpect(jsonPath("$.id", is(bookId.intValue()))) // L'ID ne change pas
                .andExpect(jsonPath("$.title", is("Le Grand Gatsby - Édition 2026")))
                .andExpect(jsonPath("$.author", is("F. Scott Fitzgerald & Grok")));

        // ==================== VÉRIFICATION DIRECTE EN BASE DE DONNÉES (comme demandé dans le Hint) ====================
        Book bookInDatabase = bookRepository.findById(bookId).orElse(null);

        assertNotNull(bookInDatabase, "Le livre doit toujours exister après la mise à jour");
        assertEquals("Le Grand Gatsby - Édition 2026", bookInDatabase.getTitle(),
                "Le titre n'a pas été mis à jour en base");
        assertEquals("F. Scott Fitzgerald & Grok", bookInDatabase.getAuthor(),
                "L'auteur n'a pas été mis à jour en base");
    }
}
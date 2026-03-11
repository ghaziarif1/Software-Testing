import React, { useState, useEffect } from "react";
import axios from "axios";
import "./App.css";

function App() {
  const [books, setBooks] = useState([]);
  const [title, setTitle] = useState("");
  const [author, setAuthor] = useState("");

  useEffect(() => {
    fetchBooks();
  }, []);

  const fetchBooks = async () => {
    const response = await axios.get("http://localhost:8080/api/books");
    setBooks(response.data);
  };

  const addBook = async (e) => {
    e.preventDefault();
    await axios.post("http://localhost:8080/api/books", { title, author });
    setTitle("");
    setAuthor("");
    fetchBooks();
  };

  return (
      <div className="App">
        <h1>Book Management</h1>
        <form onSubmit={addBook}>
          <input
              type="text"
              placeholder="Title"
              value={title}
              onChange={(e) => setTitle(e.target.value)}
              data-cy="title-input"
          />
          <input
              type="text"
              placeholder="Author"
              value={author}
              onChange={(e) => setAuthor(e.target.value)}
              data-cy="author-input"
          />
          <button type="submit" data-cy="add-book-button">
            Add Book
          </button>
        </form>
        <h2>Books</h2>
        <ul data-cy="book-list">
          {books.map((book) => (
              <li key={book.id}>
                {book.title} by {book.author}
              </li>
          ))}
        </ul>
      </div>
  );
}

export default App;
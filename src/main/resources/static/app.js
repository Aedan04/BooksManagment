class BooksManager {
    constructor() {
        this.apiBaseUrl = 'http://localhost:8080/api/Books';
        this.currentPage = 0;
        this.pageSize = 5;

        // Initialize event listeners
        this.initEventListeners();
        this.loadBooks();
    }

    initEventListeners() {
        // Search buttons
        const searchGenreButton = document.getElementById("searchGenreButton");
        const searchAuthorButton = document.getElementById("searchAuthorButton");
        const searchNameButton = document.getElementById("searchNameButton");

        // Sort button
        const sortButton = document.getElementById("sortButton");

        // Pagination buttons
        const prevPageButton = document.getElementById("prevPage");
        const nextPageButton = document.getElementById("nextPage");

        // Add new book form
        const addBookForm = document.getElementById("addBookForm");

        // Add event listeners for search buttons
        if (searchGenreButton) {
            searchGenreButton.addEventListener("click", () => this.searchBooks("genre"));
        }
        if (searchAuthorButton) {
            searchAuthorButton.addEventListener("click", () => this.searchBooks("authorName"));
        }
        if (searchNameButton) {
            searchNameButton.addEventListener("click", () => this.searchBooks("bookName"));
        }

        // Event listener for sorting
        if (sortButton) {
            sortButton.addEventListener("click", () => this.sortBooks());
        }

        // Event listeners for pagination
        if (prevPageButton) {
            prevPageButton.addEventListener("click", () => this.changePage(-1));
        }
        if (nextPageButton) {
            nextPageButton.addEventListener("click", () => this.changePage(1));
        }

        // Event listener for adding a new book
        if (addBookForm) {
            addBookForm.addEventListener("submit", (e) => this.addBook(e));
        }
    }

    loadBooks() {
        fetch(`${this.apiBaseUrl}?page=${this.currentPage}&size=${this.pageSize}`)
            .then(response => response.json())
            .then(data => this.displayBooks(data.content))
            .catch(error => console.error('Error fetching books:', error));
    }

    displayBooks(books) {
        const booksTable = document.getElementById("booksTable");
        booksTable.innerHTML = ""; // Clear previous table data

        if (books.length === 0) {
            booksTable.innerHTML = "<tr><td colspan='7'>No books found</td></tr>";
            return;
        }

        books.forEach(book => {
            const row = `<tr>
                <td>${book.id}</td>
                <td>${book.bookName}</td>
                <td>${book.authorName}</td>
                <td>${book.publishYear}</td>
                <td>${book.genre}</td>
                <td>${book.audienceType ? book.audienceType.id : 'N/A'}</td>
                <td><button onclick="deleteBook(${book.id})" class="btn btn-danger">Delete</button></td>
            </tr>`;
            booksTable.insertAdjacentHTML('beforeend', row);
        });
    }

    searchBooks(field) {
        const query = document.getElementById(`search${capitalize(field)}`).value;
        fetch(`${this.apiBaseUrl}/search?${field}=${query}`)
            .then(response => response.json())
            .then(data => this.displayBooks(data))
            .catch(error => console.error('Error searching books:', error));
    }

    sortBooks() {
        const column = document.getElementById("sortColumn").value;
        const algorithm = document.getElementById("sortAlgorithm").value;
        fetch(`${this.apiBaseUrl}/sort?column=${column}&algorithm=${algorithm}`)
            .then(response => response.json())
            .then(data => this.displayBooks(data))
            .catch(error => console.error('Error sorting books:', error));
    }

    changePage(offset) {
        this.currentPage += offset;
        this.loadBooks();
    }

    addBook(event) {
        event.preventDefault(); // Prevent form submission

        const bookData = {
            bookName: document.getElementById("bookName").value,
            authorName: document.getElementById("authorName").value,
            publishYear: document.getElementById("publishYear").value,
            genre: document.getElementById("genre").value,
            audienceType: {
                id: document.getElementById("audienceTypeId").value
            }
        };

        fetch(this.apiBaseUrl, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(bookData)
        })
            .then(response => response.json())
            .then(data => {
                document.getElementById("addBookForm").reset(); // Clear the form
                this.loadBooks(); // Refresh the book list
                this.showNotification("Book added successfully!", "success");
            })
            .catch(error => {
                console.error('Error adding book:', error);
                this.showNotification("Error adding book.", "danger");
            });
    }

    showNotification(message, type) {
        const notification = document.getElementById("notification");
        notification.className = `alert alert-${type}`;
        notification.textContent = message;
        notification.classList.remove("d-none");

        setTimeout(() => {
            notification.classList.add("d-none");
        }, 3000);
    }
}

// Utility function to capitalize the first letter of a string
function capitalize(str) {
    return str.charAt(0).toUpperCase() + str.slice(1);
}

document.addEventListener('DOMContentLoaded', () => {
    new BooksManager();
});

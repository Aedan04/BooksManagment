class BooksManager {
    constructor() {
        this.apiBaseUrl = 'http://localhost:8080/api/Books';
        this.currentPage = 0;
        this.pageSize = 5;

        // Initialize event listeners
        this.initializeEventListeners();
    }

    initializeEventListeners() {
        // Search buttons
        document.getElementById('searchGenreButton').addEventListener('click', () => this.searchBooks('genre'));
        document.getElementById('searchAuthorButton').addEventListener('click', () => this.searchBooks('authorName'));
        document.getElementById('searchNameButton').addEventListener('click', () => this.searchBooks('bookName'));

        // Add book form submission
        document.getElementById('addBookForm').addEventListener('submit', (event) => this.addBook(event));

        // Sorting
        document.getElementById('sortButton').addEventListener('click', () => this.sortBooks());

        // Pagination
        document.getElementById('prevPage').addEventListener('click', () => this.changePage(this.currentPage - 1));
        document.getElementById('nextPage').addEventListener('click', () => this.changePage(this.currentPage + 1));

        // Load initial books
        this.loadBooks();
    }

    async loadBooks() {
        const sortColumn = document.getElementById('sortColumn').value;
        const sortAlgorithm = document.getElementById('sortAlgorithm').value;

        const response = await fetch(`${this.apiBaseUrl}?page=${this.currentPage}&size=${this.pageSize}&sortBy=${sortColumn}&sortOrder=asc&sortMethod=${sortAlgorithm}`);
        const books = await response.json();
        this.updateBooksTable(books);
    }

    async searchBooks(searchType) {
        const searchValue = document.getElementById(`search${capitalizeFirstLetter(searchType)}`).value;

        const response = await fetch(`${this.apiBaseUrl}/search/${searchType}/paginated?${searchType}=${searchValue}&page=${this.currentPage}&size=${this.pageSize}`);
        const books = await response.json();
        this.updateBooksTable(books.content);
    }

    async addBook(event) {
        event.preventDefault();

        const bookName = document.getElementById('bookName').value;
        const authorName = document.getElementById('authorName').value;
        const publishYear = document.getElementById('publishYear').value;
        const genre = document.getElementById('genre').value;
        const audienceTypeId = document.getElementById('audienceTypeId').value;

        const response = await fetch(`${this.apiBaseUrl}/add`, {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({ bookName, authorName, publishYear, genre, audienceTypeId })
        });

        if (response.ok) {
            this.showNotification('Book added successfully!', 'success');
            this.loadBooks();
        } else {
            this.showNotification('Error adding book!', 'danger');
        }
    }

    async sortBooks() {
        this.loadBooks();
    }

    async changePage(pageNumber) {
        if (pageNumber >= 0) {
            this.currentPage = pageNumber;
            this.loadBooks();
        }
    }

    updateBooksTable(books) {
        const tableBody = document.getElementById('booksTable');
        tableBody.innerHTML = '';

        books.forEach(book => {
            const row = `<tr>
                <td>${book.id}</td>
                <td>${book.bookName}</td>
                <td>${book.authorName}</td>
                <td>${book.publishYear}</td>
                <td>${book.genre}</td>
                <td>${book.audienceType ? book.audienceType.name : 'N/A'}</td>
                <td><button class="btn btn-danger" onclick="booksManager.deleteBook(${book.id})">Delete</button></td>
            </tr>`;
            tableBody.innerHTML += row;
        });

        document.getElementById('currentPage').innerText = `Page ${this.currentPage + 1}`;
    }

    async deleteBook(bookId) {
        const response = await fetch(`${this.apiBaseUrl}/${bookId}`, { method: 'DELETE' });

        if (response.ok) {
            this.showNotification('Book deleted successfully!', 'success');
            this.loadBooks();
        } else {
            this.showNotification('Error deleting book!', 'danger');
        }
    }

    showNotification(message, type) {
        const notification = document.getElementById('notification');
        notification.className = `alert alert-${type}`;
        notification.innerText = message;
        notification.classList.remove('d-none');
        setTimeout(() => notification.classList.add('d-none'), 3000);
    }
}

// Utility function to capitalize first letter
function capitalizeFirstLetter(string) {
    return string.charAt(0).toUpperCase() + string.slice(1);
}

// Initialize the BooksManager class
const booksManager = new BooksManager();

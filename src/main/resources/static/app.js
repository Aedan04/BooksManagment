class BooksApp {
    constructor() {
        this.apiUrl = '/api/books';
        this.currentPage = 1;
        this.pageSize = 10;

        this.initEventListeners();
    }

    initEventListeners() {
        document.getElementById('addBookForm').addEventListener('submit', (e) => this.addBook(e));
        document.getElementById('searchGenreButton').addEventListener('click', () => this.searchBooks('genre'));
        document.getElementById('searchAuthorButton').addEventListener('click', () => this.searchBooks('authorName'));
        document.getElementById('searchNameButton').addEventListener('click', () => this.searchBooks('bookName'));
        document.getElementById('sortButton').addEventListener('click', () => this.loadBooks());
        document.getElementById('prevPage').addEventListener('click', () => this.changePage(-1));
        document.getElementById('nextPage').addEventListener('click', () => this.changePage(1));
    }

    async addBook(event) {
        event.preventDefault();
        const bookName = document.getElementById('bookName').value;
        const authorName = document.getElementById('authorName').value;
        const publishYear = document.getElementById('publishYear').value;
        const genre = document.getElementById('genre').value;
        const audienceTypeId = document.getElementById('audienceTypeId').value;

        const response = await fetch(`${this.apiUrl}/add`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded'
            },
            body: new URLSearchParams({
                bookName, authorName, publishYear, genre, audienceTypeId
            })
        });

        if (response.ok) {
            this.showNotification('Book added successfully!', 'success');
            this.loadBooks();
        } else {
            this.showNotification('Failed to add book.', 'danger');
        }
    }

    async searchBooks(field) {
        const searchTerm = document.getElementById(`search${field.charAt(0).toUpperCase() + field.slice(1)}`).value;
        this.loadBooks(field, searchTerm);
    }

    async loadBooks(searchField = '', searchTerm = '') {
        const sortColumn = document.getElementById('sortColumn').value;
        const sortAlgorithm = document.getElementById('sortAlgorithm').value;
        const sortOrder = 'asc';

        let url = `${this.apiUrl}?page=${this.currentPage - 1}&size=${this.pageSize}&sortBy=${sortColumn}&sortOrder=${sortOrder}&sortMethod=${sortAlgorithm}`;

        if (searchField && searchTerm) {
            url += `&${searchField}=${encodeURIComponent(searchTerm)}`;
        }

        const response = await fetch(url);

        if (response.ok) {
            const books = await response.json();
            this.displayBooks(books);
        } else {
            this.showNotification('Failed to load books.', 'danger');
        }
    }

    displayBooks(books) {
        const booksTable = document.getElementById('booksTable');
        booksTable.innerHTML = '';

        books.forEach(book => {
            const row = `
                <tr>
                    <td>${book.id}</td>
                    <td>${book.bookName}</td>
                    <td>${book.authorName}</td>
                    <td>${book.publishYear}</td>
                    <td>${book.genre}</td>
                    <td>${book.audienceType}</td>
                    <td>
                        <button class="btn btn-warning btn-sm" onclick="app.editBook(${book.id})">Edit</button>
                        <button class="btn btn-danger btn-sm" onclick="app.deleteBook(${book.id})">Delete</button>
                    </td>
                </tr>
            `;
            booksTable.insertAdjacentHTML('beforeend', row);
        });

        document.getElementById('currentPage').textContent = this.currentPage;
    }

    async deleteBook(id) {
        const response = await fetch(`${this.apiUrl}/${id}`, {
            method: 'DELETE'
        });

        if (response.ok) {
            this.showNotification('Book deleted successfully!', 'success');
            this.loadBooks();
        } else {
            this.showNotification('Failed to delete book.', 'danger');
        }
    }

    async editBook(id) {
        // You can implement the edit functionality similarly by pre-filling the form with the book's data.
    }

    changePage(change) {
        this.currentPage += change;
        this.loadBooks();
    }

    showNotification(message, type) {
        const notification = document.getElementById('notification');
        notification.className = `alert alert-${type}`;
        notification.textContent = message;
        notification.classList.remove('d-none');

        setTimeout(() => notification.classList.add('d-none'), 3000);
    }
}

// Initialize the BooksApp
const app = new BooksApp();

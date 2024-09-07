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
        document.getElementById("searchGenreButton").addEventListener("click", () => this.searchBooks("genre"));
        document.getElementById("searchAuthorButton").addEventListener("click", () => this.searchBooks("author"));
        document.getElementById("searchNameButton").addEventListener("click", () => this.searchBooks("bookName"));
        document.getElementById("sortButton").addEventListener("click", () => this.sortBooks());

        document.getElementById("prevPage").addEventListener("click", () => this.changePage(-1));
        document.getElementById("nextPage").addEventListener("click", () => this.changePage(1));
    }

    loadBooks() {
        fetch(`${this.apiBaseUrl}?page=${this.currentPage}&size=${this.pageSize}`)
            .then(response => response.json())
            .then(data => this.displayBooks(data.content))
            .catch(error => console.error('Error fetching books:', error));
    }

    displayBooks(books) {
        const booksTable = document.getElementById("booksTable");
        booksTable.innerHTML = "";

        books.forEach(book => {
            const row = `<tr>
                <td>${book.id}</td>
                <td>${book.bookName}</td>
                <td>${book.authorName}</td>
                <td>${book.publishYear}</td>
                <td>${book.genre}</td>
                <td>${book.audienceType}</td>
                <td><button onclick="deleteBook(${book.id})">Delete</button></td>
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
}

document.addEventListener('DOMContentLoaded', () => {
    new BooksManager();
});

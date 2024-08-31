document.addEventListener("DOMContentLoaded", function () {
    let currentPage = 1;
    const pageSize = 10; // Number of books per page

    // Fetch and display books
    function fetchBooks() {
        const sortColumn = document.getElementById("sortColumn").value;
        const sortAlgorithm = document.getElementById("sortAlgorithm").value;

        fetch(`http://localhost:8080/api/books?page=${currentPage}&size=${pageSize}&sort=${sortColumn}&algorithm=${sortAlgorithm}`)
            .then(response => response.json())
            .then(data => {
                displayBooks(data.books);
                updatePagination(data.totalPages);
            })
            .catch(error => console.error('Error fetching books:', error));
    }

    // Display books in the table
    function displayBooks(books) {
        const booksTable = document.getElementById("booksTable");
        booksTable.innerHTML = "";

        books.forEach(book => {
            const row = document.createElement("tr");

            row.innerHTML = `
                <td>${book.id}</td>
                <td>${book.bookName}</td>
                <td>${book.authorName}</td>
                <td>${book.publishYear}</td>
                <td>${book.genre}</td>
                <td>${book.audienceType}</td>
                <td>
                    <button class="btn btn-danger" onclick="deleteBook(${book.id})">Delete</button>
                </td>
            `;

            booksTable.appendChild(row);
        });
    }

    // Update pagination controls
    function updatePagination(totalPages) {
        document.getElementById("currentPage").textContent = `Page ${currentPage} of ${totalPages}`;
        document.getElementById("prevPage").disabled = currentPage === 1;
        document.getElementById("nextPage").disabled = currentPage === totalPages;
    }

    // Handle adding a new book
    document.getElementById("addBookForm").addEventListener("submit", function (event) {
        event.preventDefault();

        const newBook = {
            bookName: document.getElementById("bookName").value,
            authorName: document.getElementById("authorName").value,
            publishYear: document.getElementById("publishYear").value,
            genre: document.getElementById("genre").value,
            audienceTypeId: document.getElementById("audienceTypeId").value
        };

        fetch("http://localhost:8080/api/books", {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify(newBook)
        })
            .then(response => response.json())
            .then(() => {
                alert("Book added successfully!");
                fetchBooks(); // Refresh the list
            })
            .catch(error => console.error('Error adding book:', error));
    });

    // Handle searching by genre
    document.getElementById("searchGenreButton").addEventListener("click", function () {
        const genre = document.getElementById("searchGenre").value;

        fetch(`http://localhost:8080/api/books/search?genre=${genre}`)
            .then(response => response.json())
            .then(data => displayBooks(data))
            .catch(error => console.error('Error searching by genre:', error));
    });

    // Handle searching by author
    document.getElementById("searchAuthorButton").addEventListener("click", function () {
        const author = document.getElementById("searchAuthor").value;

        fetch(`http://localhost:8080/api/books/search?authorName=${author}`)
            .then(response => response.json())
            .then(data => displayBooks(data))
            .catch(error => console.error('Error searching by author:', error));
    });

    // Handle searching by book name
    document.getElementById("searchNameButton").addEventListener("click", function () {
        const bookName = document.getElementById("searchName").value;

        fetch(`http://localhost:8080/api/books/search?bookName=${bookName}`)
            .then(response => response.json())
            .then(data => displayBooks(data))
            .catch(error => console.error('Error searching by book name:', error));
    });

    // Handle sorting
    document.getElementById("sortButton").addEventListener("click", function () {
        fetchBooks(); // Fetch and display books with the selected sort options
    });

    // Handle pagination
    document.getElementById("prevPage").addEventListener("click", function () {
        if (currentPage > 1) {
            currentPage--;
            fetchBooks();
        }
    });

    document.getElementById("nextPage").addEventListener("click", function () {
        currentPage++;
        fetchBooks();
    });

    // Initial fetch
    fetchBooks();
});

// Function to delete a book
function deleteBook(bookId) {
    if (confirm("Are you sure you want to delete this book?")) {
        fetch(`http://localhost:8080/api/books/${bookId}`, {
            method: "DELETE"
        })
            .then(() => {
                alert("Book deleted successfully!");
                fetchBooks(); // Refresh the list
            })
            .catch(error => console.error('Error deleting book:', error));
    }
}

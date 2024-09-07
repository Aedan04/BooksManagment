// Define constants for API endpoints and default values
const API_URL = 'http://localhost:8080/api/Books';
const DEFAULT_PAGE = 0; // API uses 0-based indexing
const DEFAULT_SIZE = 5;
const DEFAULT_SORT_BY = 'id';
const DEFAULT_SORT_ORDER = 'asc';
const DEFAULT_SORT_METHOD = 'bubble';

// Function to fetch books from the backend
async function fetchBooks(page = DEFAULT_PAGE, size = DEFAULT_SIZE, sortBy = DEFAULT_SORT_BY, sortOrder = DEFAULT_SORT_ORDER, sortMethod = DEFAULT_SORT_METHOD) {
    const url = `${API_URL}?page=${page}&size=${size}&sortBy=${sortBy}&sortOrder=${sortOrder}&sortMethod=${sortMethod}`;
    try {
        const response = await fetch(url);
        if (!response.ok) {
            throw new Error('Network response was not ok');
        }
        const data = await response.json();
        displayBooks(data);
    } catch (error) {
        console.error('Error fetching books:', error);
    }
}

// Function to display books in the UI
function displayBooks(books) {
    const booksTableBody = document.getElementById('booksTableBody');
    booksTableBody.innerHTML = '';

    books.forEach(book => {
        const row = document.createElement('tr');
        row.innerHTML = `
            <td>${book.id}</td>
            <td>${book.bookName}</td>
            <td>${book.authorName}</td>
            <td>${book.publishYear}</td>
            <td>${book.genre}</td>
            <td>${book.audienceType ? book.audienceType.id : 'N/A'}</td>
        `;
        booksTableBody.appendChild(row);
    });
}

// Function to handle sorting method change
function handleSortMethodChange() {
    const sortMethod = document.getElementById('sortMethod').value;
    fetchBooks(DEFAULT_PAGE, DEFAULT_SIZE, DEFAULT_SORT_BY, DEFAULT_SORT_ORDER, sortMethod);
}

// Function to handle pagination change
function handlePaginationChange(page) {
    fetchBooks(page, DEFAULT_SIZE, DEFAULT_SORT_BY, DEFAULT_SORT_ORDER, DEFAULT_SORT_METHOD);
}

// Function to handle sorting order change
function handleSortOrderChange() {
    const sortOrder = document.getElementById('sortOrder').value;
    fetchBooks(DEFAULT_PAGE, DEFAULT_SIZE, DEFAULT_SORT_BY, sortOrder, DEFAULT_SORT_METHOD);
}

// Function to handle sort by field change
function handleSortByChange() {
    const sortBy = document.getElementById('sortBy').value;
    fetchBooks(DEFAULT_PAGE, DEFAULT_SIZE, sortBy, DEFAULT_SORT_ORDER, DEFAULT_SORT_METHOD);
}

// Set up event listeners on page load
document.addEventListener('DOMContentLoaded', () => {
    // Initial fetch
    fetchBooks();

    // Add event listeners for dropdowns
    document.getElementById('sortMethod').addEventListener('change', handleSortMethodChange);
    document.getElementById('sortOrder').addEventListener('change', handleSortOrderChange);
    document.getElementById('sortBy').addEventListener('change', handleSortByChange);

    // Pagination buttons (assuming buttons are named `prevPage` and `nextPage`)
    document.getElementById('prevPage').addEventListener('click', () => handlePaginationChange(DEFAULT_PAGE - 1));
    document.getElementById('nextPage').addEventListener('click', () => handlePaginationChange(DEFAULT_PAGE + 1));
});

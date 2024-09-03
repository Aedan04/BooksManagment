# BookManagment

The Books Management System is a simple application designed to help users manage a collection of books . ot help for your personal library, this project provides the tools needed to organize, search, and maintain your book collection with ease. 

Features
Add New Books: Easily add new books to your collection with details like title, author, genre, and publication date.
Search Functionality: Quickly search for books by title, author, or genre.
Delete: remove book details as needed.
User Authentication: login system to ensure only authorized users.
The feature of this project: This project use three way and methode for sorting and arrangeing books that include four methode such
    Bubble sort,qouck sort,merge sort and selection sort



#Service
We have two Service class , first service for book service that include adding a book, removing a book, geting a book, getting all books and sorting
 method.
In the Book service i have 12 service :
Add book: for aading book with this parametr (String bookName, String authorName, int publishYear, String genre, Long audienceTypeId).
delete Book: delete book with id parametr that at first check and if id is note exist :Book with ID " + id + " does not exist.
getBooksByBookNameWithPagination : this methode show the Book name with pagination with this Feature (page, size, Sort.by(Sort.Direction.fromString(sortOrder), sortBy).
getBooksByBookAuthorNameWithPagination : this methode show the Author name with pagination with this Feature (page, size, Sort.by(Sort.Direction.fromString(sortOrder), sortBy).
getBooksBygenereWithPagination : this methode show the Book genere with pagination with this Feature (page, size, Sort.by(Sort.Direction.fromString(sortOrder), sortBy).
getAllBooks : get all books and show a list of book and show in the list with this Feature:
        int fromIndex = Math.min(page * size, sortedBooks.size());
        int toIndex = Math.min(fromIndex + size, sortedBooks.size()); 
now search methode:
searchBooks: thsi methode for finding By Book Name Containing Ignore Case And Genre Containing Ignore Case And Author Name Containing Ignore Case.
findAudienceTypeById: this is for fouding and showing book by audienceTypeId
findBookById: this is for founding book by id of id dos't exist : Book with ID " + id + " does not exist.

and now we start the sorting method:
sortBooks: in this methode we chose showing resault Feature and sort with wich sort method :case "bubble" ->
            bubbleSort(books, comparator);
            case "quick" -> quickSort(books, comparator);
            case "merge" -> mergeSort(books, comparator);
            case "selection" -> selectionSort(books, comparator);
getComparator: in this methode we chose that wich colomns should be sorted :  case "id" -> Comparator.comparing(BooksEntity::getId);
            case "bookname" -> Comparator.comparing(BooksEntity::getBookName);
            case "authorname" -> Comparator.comparing(BooksEntity::getAuthorName);
            case "publishyear" -> Comparator.comparing(BooksEntity::getPublishYear);
            case "genre" -> Comparator.comparing(BooksEntity::getGenre);
            case "audiencetype" ->
                    Comparator.comparing(book -> book.getAudienceType() != null ? book.getAudienceType().getId() : 0);
bubbleSort:Bubble sort is a simple comparison-based sorting algorithm that repeatedly steps through the list to be sorted, compares adjacent elements, and swaps them if they are in the wrong order. This process is repeated until the list is sorted:int n = books.size();
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                if (comparator.compare(books.get(j), books.get(j + 1)) > 0) {
                    BooksEntity temp = books.get(j);
                    books.set(j, books.get(j + 1));
                    books.set(j + 1, temp);
                }
            }
        }
quickSort:QuickSort is a highly efficient and widely used sorting algorithm based on the divide-and-conquer principle. It works by selecting a "pivot" element from the array and partitioning the other elements into two sub-arrays: elements less than the pivot and elements greater than the pivot. The process is then repeated recursively for the sub-arrays:
if (books.size() <= 1) return books;
        BooksEntity pivot = books.get(books.size() / 2);
        List<BooksEntity> less = books.stream().filter(book -> comparator.compare(book, pivot) < 0).collect(Collectors.toList());
        List<BooksEntity> equal = books.stream().filter(book -> comparator.compare(book, pivot) == 0).collect(Collectors.toList());
        List<BooksEntity> greater = books.stream().filter(book -> comparator.compare(book, pivot) > 0).collect(Collectors.toList());
        List<BooksEntity> sortedBooks = quickSort(less, comparator);
        sortedBooks.addAll(equal);
        sortedBooks.addAll(quickSort(greater, comparator));
        return sortedBooks;
mergeSort:Merge Sort is a classic and efficient sorting algorithm based on the divide-and-conquer principle. It works by dividing the array into smaller sub-arrays, sorting those sub-arrays, and then merging them back together to form a sorted array:if (books.size() <= 1) return books;
        int mid = books.size() / 2;
        List<BooksEntity> left = new ArrayList<>(books.subList(0, mid));
        List<BooksEntity> right = new ArrayList<>(books.subList(mid, books.size()));
        return merge(mergeSort(left, comparator), mergeSort(right, comparator), comparator);
selectionSort:int n = books.size();
        for (int i = 0; i < n - 1; i++) {
            int minIndex = i;
            for (int j = i + 1; j < n; j++) {
                if (comparator.compare(books.get(j), books.get(minIndex)) < 0) {
                    minIndex = j;
                }
            }
            BooksEntity temp = books.get(i);
            books.set(i, books.get(minIndex));
            books.set(minIndex, temp);
        }
        return books;

another service class is User services that for authention Users .
it have only three service in User service.
authenticateUser: it take String userName, String password and check with data base and if was exist it will login.
createUser: it will make new User that using the User entity proprety and add it to data base





#Controller
And we have two controller for this project :
first is User Controller that have two methode for Login and sign up and call all User Service metode.


authenticateUser

URL:http://localhost:8080/api/users/authenticate
Method: POST



Usage: with this Service User can authenticate and log in 
Input parameters: UserName , Password 
 input exmaple : 
{
  "userName": "string",
  "password": "string"
}


output:

{
  "id": 14,
  "userName": "string",
  "password": "string",
  "name": "string",
  "lastName": "string",
  "bookName": "string"
}




createUser



URL:http://localhost:8080/api/users
Method: POST

Usage: this service is for sign up and create new user
Input parameters: UserName , Password  , name , lastName , bookName.
output : new user


input Test : 
[
{
  "userName": "test",
  "password": "test",
  "name": "test",
  "lastName": "test",
  "bookName": "test"
}
output Test:{
  "id": 15,
  "userName": "test",
  "password": "test",
  "name": "test",
  "lastName": "test",
  "bookName": "test"
}

the second class for books that have all CRUD opration such a get,post,Put,Delete methode and call all book service method.
getBooks

 Add a New Book

URL: /api/Books/add
Method: POST
Usage: This endpoint allows you to add a new book to the database.
Input Parameters:
bookName (String)
authorName (String)
publishYear (int)
genre (String)
audienceTypeId (Long)
Output Example:
json
{
  "id": 1,
  "bookName": "string",
  "authorName": "string",
  "publishYear": 2024,
  "genre": "string",
  "audienceType": {
    "id": 1,
    "audienceType": "string"
  }
}
2. Update an Existing Book

URL: /api/Books/{id}
Method: PUT
Usage: This endpoint allows you to update the details of an existing book by its ID.
Input Parameters:
id (Path Variable, Long)
bookName (String)
authorName (String)
publishYear (int)
genre (String)
audienceTypeId (Long)
Output Example:
json
{
  "id": 1,
  "bookName": "string",
  "authorName": "string",
  "publishYear": 2024,
  "genre": "string",
  "audienceType": {
    "id": 1,
    "audienceType": "string"
  }
}
3. Delete a Book

URL: /api/Books/{id}
Method: DELETE
Usage: This endpoint allows you to delete a book by its ID.
Input Parameter:
id (Path Variable, Long)
Output Example:
Success: "Book deleted successfully!"
Error: "Book not found!"
4. Search Books by Name with Pagination

URL: /api/Books/search/name/paginated
Method: GET
Usage: This endpoint allows you to search for books by their name with pagination and sorting.
Input Parameters:
bookName (String)
page (int)
size (int)
sortBy (String, default: "bookName")
sortOrder (String, default: "asc")
Output Example:
json
{
  "content": 
    {
      "id": 1,
      "bookName": "string",
      "authorName": "string",
      "publishYear": 2024,
      "genre": "string",
      "audienceType": {
        "id": 1,
        "audienceType": "string"
      }
    }
  ,
  "totalPages": 1,
  "totalElements": 1,
  "size": 10,
  "number": 0,
  "sort": [
    {
      "property": "bookName",
      "direction": "ASC"
    }
  ]
}
5. Search Books by Genre with Pagination

URL: /api/Books/search/genre/paginated
Method: GET
Usage: This endpoint allows you to search for books by their genre with pagination and sorting.
Input Parameters:
genre (String)
page (int)
size (int)
sortBy (String, default: "genre")
sortOrder (String, default: "asc")
Output Example:
json
{
  "content": 
    {
      "id": 1,
      "bookName": "string",
      "authorName": "string",
      "publishYear": 2024,
      "genre": "string",
      "audienceType": {
        "id": 1,
        "audienceType": "string"
      }
    }
  ,
  "totalPages": 1,
  "totalElements": 1,
  "size": 10,
  "number": 0,
  "sort": 
    {
      "property": "genre",
      "direction": "ASC"
    }
  
}
6. Search Books by Author with Pagination

URL: /api/Books/search/author/paginated
Method: GET
Usage: This endpoint allows you to search for books by their author's name with pagination and sorting.
Input Parameters:
authorName (String)
page (int)
size (int)
sortBy (String, default: "authorName")
sortOrder (String, default: "asc")
Output Example:
json
{
  "content": 
    {
      "id": 1,
      "bookName": "string",
      "authorName": "string",
      "publishYear": 2024,
      "genre": "string",
      "audienceType": {
        "id": 1,
        "audienceType": "string"
      }
    }
  ,
  "totalPages": 1,
  "totalElements": 1,
  "size": 10,
  "number": 0,
  "sort": 
    {
      "property": "authorName",
      "direction": "ASC"
    }
  
}
7. Get All Books with Sorting

URL: /api/Books
Method: GET
Usage: This endpoint allows you to retrieve all books with pagination, sorting by a selected method.
Input Parameters:
page (int)
size (int)
sortBy (String, default: "id")
sortOrder (String, default: "asc")
sortMethod (String, default: "bubble")
Output Example:
json

  {
    "id": 1,
    "bookName": "string",
    "authorName": "string",
    "publishYear": 2024,
    "genre": "string",
    "audienceType": {
      "id": 1,
      "audienceType": "string"
    }
  }

8. Search Books with Multiple Filters

URL: /api/Books/search
Method: GET
Usage: This endpoint allows you to search for books by various filters such as book name, genre, and author, with sorting by a selected method.
Input Parameters:
bookName (String, optional, default: "")
genre (String, optional, default: "")
authorName (String, optional, default: "")
sortBy (String, default: "bookName")
sortOrder (String, default: "asc")
sortMethod (String, default: "bubble")
Output Example:
json

  {
    "id": 1,
    "bookName": "string",
    "authorName": "string",
    "publishYear": 2024,
    "genre": "string",
    "audienceType": {
      "id": 1,
      "audienceType": "string"
    }
  }




Installation Instructions
To get started with the Books Management System, follow these steps:

Prerequisites
java 22
sprin boot 3.3.2


Usage
Once the server is running, you can:

Add Books: Navigate to the "Add Book" section and input the necessary details.
View Books: Browse the collection on the homepage.
Delete Books: Use the delete buttons next to each book entry.
Search: Utilize the search bar to find specific books.
Sort: Utilize the sort method bar to sort resault with specific sort method.
    

Contact Information
For any inquiries or issues, please contact farhanbabakhani4@gmail.com.
 

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

// Book class representing a book
class Book {
    private int id;
    private String title;
    private String author;
    private boolean available;

    public Book(int id, String title, String author) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.available = true;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }
}

// User class representing a library user
class User {
    private String registrationNumber;
    private String password;

    public User(String registrationNumber, String password) {
        this.registrationNumber = registrationNumber;
        this.password = password;
    }

    public String getRegistrationNumber() {
        return registrationNumber;
    }

    public String getPassword() {
        return password;
    }
}

// Library class representing a library
class Library {
    private List<Book> books;
    private Map<String, User> users;

    public Library() {
        this.books = new ArrayList<>();
        this.users = new HashMap<>();
        initializeBooks(); // Initialize library with some books
    }

    private void initializeBooks() {
        // Adding 10 books by famous Indian authors
        addBook(new Book(1, "The God of Small Things", "Arundhati Roy"));
        addBook(new Book(2, "Midnight's Children", "Salman Rushdie"));
        addBook(new Book(3, "A Suitable Boy", "Vikram Seth"));
        addBook(new Book(4, "The White Tiger", "Aravind Adiga"));
        addBook(new Book(5, "Train to Pakistan", "Khushwant Singh"));
        addBook(new Book(6, "Interpreter of Maladies", "Jhumpa Lahiri"));
        addBook(new Book(7, "The Palace of Illusions", "Chitra Banerjee Divakaruni"));
        addBook(new Book(8, "The Guide", "R.K. Narayan"));
        addBook(new Book(9, "The Inheritance of Loss", "Kiran Desai"));
        addBook(new Book(10, "Sita: An Illustrated Retelling of the Ramayana", "Devdutt Pattanaik"));
    }

    public void addBook(Book book) {
        books.add(book);
    }

    public List<Book> getBooks() {
        return books;
    }

    public void registerUser(User user) {
        users.put(user.getRegistrationNumber(), user);
    }

    public boolean authenticateUser(String registrationNumber, String password) {
        User user = users.get(registrationNumber);
        return user != null && user.getPassword().equals(password);
    }

    public Book findBookById(int id) {
        for (Book book : books) {
            if (book.getId() == id) {
                return book;
            }
        }
        return null;
    }

    public void borrowBook(User user, int bookId) {
        Book book = findBookById(bookId);
        if (book != null && book.isAvailable()) {
            book.setAvailable(false);
            System.out.println("Book '" + book.getTitle() + "' borrowed successfully by user " + user.getRegistrationNumber());
        } else {
            System.out.println("Book not available for borrowing or invalid book ID.");
        }
    }

    public void returnBook(int bookId) {
        Book book = findBookById(bookId);
        if (book != null && !book.isAvailable()) {
            book.setAvailable(true);
            System.out.println("Book '" + book.getTitle() + "' returned successfully.");
        } else {
            System.out.println("Invalid book ID or book is already available.");
        }
    }

    public List<Book> searchBooks(String keyword) {
        List<Book> results = new ArrayList<>();
        for (Book book : books) {
            if (book.getTitle().toLowerCase().contains(keyword.toLowerCase()) ||
                    book.getAuthor().toLowerCase().contains(keyword.toLowerCase())) {
                results.add(book);
            }
        }
        return results;
    }
}

public class LibraryManagementSystem {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Library library = new Library();

        System.out.println("Welcome to Library Management System");

        while (true) {
            System.out.println("1. Register");
            System.out.println("2. Login");
            System.out.println("3. Exit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    registerUser(library, scanner);
                    break;
                case 2:
                    if (login(library, scanner)) {
                        // After successful login, present library options
                        libraryOptions(library, scanner);
                    }
                    break;
                case 3:
                    System.out.println("Exiting...");
                    System.exit(0);
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void registerUser(Library library, Scanner scanner) {
        System.out.print("Enter registration number: ");
        String registrationNumber = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        // Check if the registration number is already registered
        if (library.authenticateUser(registrationNumber, password)) {
            System.out.println("User already registered. Please login.");
            return;
        }

        // Register the user
        User newUser = new User(registrationNumber, password);
        library.registerUser(newUser);
        System.out.println("Registration successful. You can now login.");
    }

    private static boolean login(Library library, Scanner scanner) {
        System.out.print("Enter registration number: ");
        String registrationNumber = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        if (library.authenticateUser(registrationNumber, password)) {
            System.out.println("Login successful!");
            return true;
        } else {
            System.out.println("Invalid credentials. Please try again.");
            return false;
        }
    }

    private static void libraryOptions(Library library, Scanner scanner) {
        while (true) {
            System.out.println("Library Options:");
            System.out.println("1. Display available books");
            System.out.println("2. Borrow a book");
            System.out.println("3. Return a book");
            System.out.println("4. Search for a book");
            System.out.println("5. Add a book");
            System.out.println("6. Logout");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    displayAvailableBooks(library);
                    break;
                case 2:
                    borrowBook(library, scanner);
                    break;
                case 3:
                    returnBook(library, scanner);
                    break;
                case 4:
                    searchBook(library, scanner);
                    break;
                case 5:
                    addBook(library, scanner);
                    break;
                case 6:
                    System.out.println("Logging out.");
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void displayAvailableBooks(Library library) {
        List<Book> books = library.getBooks();
        System.out.println("Available Books:");
        int count = 1;
        for (Book book : books) {
            if (book.isAvailable()) {
                System.out.println(count + ". ID: " + book.getId() + ", Title: " + book.getTitle() + ", Author: " + book.getAuthor());
                count++;
            }
        }
    }

    private static void borrowBook(Library library, Scanner scanner) {
        System.out.print("Enter the ID of the book to borrow: ");
        int bookId = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        // In a real system, you'd need to get the current user from the session or authentication context
        // For simplicity, let's just use a placeholder user for now
        User currentUser = new User("current", "password");
        library.borrowBook(currentUser, bookId);
    }

    private static void returnBook(Library library, Scanner scanner) {
        System.out.print("Enter the ID of the book to return: ");
        int bookId = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        library.returnBook(bookId);
    }

    private static void searchBook(Library library, Scanner scanner) {
        System.out.print("Enter keyword to search for books: ");
        String keyword = scanner.nextLine();

        List<Book> results = library.searchBooks(keyword);
        if (results.isEmpty()) {
            System.out.println("No books found matching the search criteria.");
        } else {
            System.out.println("Search Results:");
            for (Book book : results) {
                System.out.println("ID: " + book.getId() + ", Title: " + book.getTitle() + ", Author: " + book.getAuthor());
            }
        }
    }

    private static void addBook(Library library, Scanner scanner) {
        System.out.print("Enter title of the book: ");
        String title = scanner.nextLine();
        System.out.print("Enter author of the book: ");
        String author = scanner.nextLine();

        int id = library.getBooks().size() + 1; // Generate ID sequentially
        Book newBook = new Book(id, title, author);
        library.addBook(newBook);
        System.out.println("Book added successfully with ID: " + id);
    }
}

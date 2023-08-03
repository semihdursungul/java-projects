import java.io.*;
import java.util.*;

public class LibraryManagementSystem {
    public static class Book implements Serializable  {
        private String title;
        private String author;
        private String isbn;
        private boolean available;

        public Book(String title, String author, String isbn, boolean available) {
            this.title = title;
            this.author = author;
            this.isbn = isbn;
            this.available = available;
        }

        public String getTitle() {
            return title;
        }

        public String getAuthor() {
            return author;
        }

        public String getIsbn() {
            return isbn;
        }

        public boolean isAvailable() {
            return available;
        }

        public void setAvailable(boolean available) {
            this.available = available;
        }
    }

    public static class User implements Serializable {
        private String name;
        private int id;
        private List<Book> booksBorrowed;

        public User(String name, int id) {
            this.name = name;
            this.id = id;
            this.booksBorrowed = new ArrayList<>();
        }

        public String getName() {
            return name;
        }

        public int getId() {
            return id;
        }

        public List<Book> getBooksBorrowed() {
            return booksBorrowed;
        }
    }

    public static class Library {
        private List<Book> books;
        private List<User> users;
        private int nextUserId;

        public Library() {
            books = new ArrayList<>();
            users = new ArrayList<>();
            nextUserId = 1;
        }

        public void addBook(Book book) {
            books.add(book);
        }

        public void addUser(User user) {
            users.add(user);
        }

        public void borrowBook(User user, Book book) {
            if (book.isAvailable()) {
                user.getBooksBorrowed().add(book);
                book.setAvailable(false);
                System.out.println("Book borrowed successfully.");
            } else {
                System.out.println("Book is not available for borrowing.");
            }
        }

        public void returnBook(User user, Book book) {
            if (user.getBooksBorrowed().contains(book)) {
                user.getBooksBorrowed().remove(book);
                book.setAvailable(true);
                System.out.println("Book returned successfully.");
            } else {
                System.out.println("Book not found in user's borrowed list.");
            }
        }

        public List<Book> searchBooks(String keyword) {
            List<Book> searchResults = new ArrayList<>();
            for (Book book : books) {
                if (book.getTitle().toLowerCase().contains(keyword.toLowerCase())) {
                    searchResults.add(book);
                }
            }
            return searchResults;
        }
        public void setUsers(List<User> users) {
            this.users = users;
        }

        public void setBooks(List<Book> books) {
            this.books = books;
        }

        public int getNextUserId() {
            return nextUserId;
        }

        public List<Book> getBooks() {
            return books;
        }

        public List<User> getUsers() {
            return users;
        }
    }

    // File paths for saving and loading data
    private static final String USERS_FILE = "users.ser";
    private static final String BOOKS_FILE = "books.ser";



    public static void main(String[] args) {
        Library library = loadLibraryData(); // Load data from files
        Scanner scanner = new Scanner(System.in);
        int nextUserId = 1;

        while (true) {
            clearConsole();

            System.out.println("Library Management System");
            System.out.println("1. Add a book");
            System.out.println("2. Borrow a book");
            System.out.println("3. Return a book");
            System.out.println("4. Search for a book");
            System.out.println("5. Register as a user");
            System.out.println("6. Exit");
            System.out.print("Select an option: ");
            int option = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (option) {
                case 1:
                    System.out.print("Enter title: ");
                    String title = scanner.nextLine();
                    System.out.print("Enter author: ");
                    String author = scanner.nextLine();
                    System.out.print("Enter ISBN: ");
                    String isbn = scanner.nextLine();
                    library.addBook(new Book(title, author, isbn, true));
                    System.out.println("Book added successfully.");
                    break;
                case 2:
                    System.out.print("Are you a registered user? (yes/no): ");
                    String registeredUserResponse = scanner.nextLine();
                    if (registeredUserResponse.equalsIgnoreCase("yes")) {
                        System.out.print("Enter your user ID: ");
                        int userId = scanner.nextInt();
                        User user = getUserById(library, userId);
                        if (user != null) {
                            System.out.print("Enter title of the book to borrow: ");
                            scanner.nextLine(); // Consume newline
                            String titleToBorrow = scanner.nextLine();
                            Book bookToBorrow = getAvailableBookByTitle(library, titleToBorrow);
                            if (bookToBorrow != null) {
                                library.borrowBook(user, bookToBorrow);
                            } else {
                                System.out.println("Book is not available for borrowing.");
                            }
                        } else {
                            System.out.println("User not found.");
                        }
                    } else if (registeredUserResponse.equalsIgnoreCase("no")) {
                        System.out.print("Enter your name: ");
                        String userName = scanner.nextLine();
                        User newUser = new User(userName, nextUserId);
                        nextUserId++;
                        library.addUser(newUser);
                        System.out.println("User registered successfully. Your user ID is: " + newUser.getId());
                    } else {
                        System.out.println("Invalid response. Please enter 'yes' or 'no'.");
                    }
                    break;
                case 3:
                    System.out.print("Enter your user ID: ");
                    int userId = scanner.nextInt();
                    User user = getUserById(library, userId);
                    if (user != null) {
                        System.out.print("Enter title of the book to return: ");
                        scanner.nextLine(); // Consume newline
                        String titleToReturn = scanner.nextLine();
                        Book bookToReturn = getBorrowedBookByTitle(user, titleToReturn);
                        if (bookToReturn != null) {
                            library.returnBook(user, bookToReturn);
                        } else {
                            System.out.println("You haven't borrowed this book.");
                        }
                    } else {
                        System.out.println("User not found.");
                    }
                    break;
                case 4:
                    System.out.print("Enter search keyword: ");
                    String keyword = scanner.nextLine();
                    List<Book> searchResults = library.searchBooks(keyword);
                    if (searchResults.isEmpty()) {
                        System.out.println("No matching books found.");
                    } else {
                        System.out.println("Search results:");
                        for (Book book : searchResults) {
                            System.out.println(book.getTitle() + " by " + book.getAuthor());
                        }
                    }
                    break;
                case 5:
                    System.out.print("Enter your name: ");
                    String userName = scanner.nextLine();
                    User newUser = new User(userName, nextUserId);
                    nextUserId++;
                    library.addUser(newUser);
                    System.out.println("User registered successfully. Your user ID is: " + newUser.getId());
                    break;
                case 6:
                    System.out.println("Exiting Library Management System. Goodbye!");
                    saveLibraryData(library); // Save data to files
                    scanner.close();
                    System.exit(0);
                default:
                    System.out.println("Invalid option. Please select again.");
            }

            System.out.print("Press Enter to continue...");
            scanner.nextLine(); // Wait for user to press Enter

            clearConsole();
        }
    }

    // Helper method to clear the console
    private static void clearConsole() {
        for (int i = 0; i < 100; i++) {
            System.out.println();
        }
    }

    private static User getUserById(Library library, int userId) {
        for (User user : library.users) {
            if (user.getId() == userId) {
                return user;
            }
        }
        return null;
    }

    private static Book getAvailableBookByTitle(Library library, String title) {
        for (Book book : library.books) {
            if (book.getTitle().equalsIgnoreCase(title) && book.isAvailable()) {
                return book;
            }
        }
        return null;
    }

    private static Book getBorrowedBookByTitle(User user, String title) {
        for (Book book : user.getBooksBorrowed()) {
            if (book.getTitle().equalsIgnoreCase(title)) {
                return book;
            }
        }
        return null;
    }

    // Helper method to save library data to files
    private static void saveLibraryData(Library library) {
        try {
            ObjectOutputStream usersOut = new ObjectOutputStream(new FileOutputStream(USERS_FILE));
            usersOut.writeObject(library.getUsers());
            usersOut.close();

            ObjectOutputStream booksOut = new ObjectOutputStream(new FileOutputStream(BOOKS_FILE));
            booksOut.writeObject(library.getBooks());
            booksOut.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Helper method to load library data from files
    private static Library loadLibraryData()    {
        Library library = new Library();
        try {
            ObjectInputStream usersIn = new ObjectInputStream(new FileInputStream(USERS_FILE));
            List<User> users = (List<User>) usersIn.readObject();
            usersIn.close();
            library.setUsers(users);

            ObjectInputStream booksIn = new ObjectInputStream(new FileInputStream(BOOKS_FILE));
            List<Book> books = (List<Book>) booksIn.readObject();
            booksIn.close();
            library.setBooks(books);
        } catch (IOException | ClassNotFoundException e) {
            // If files don't exist or data couldn't be loaded, start with empty lists
            library.setUsers(new ArrayList<>());
            library.setBooks(new ArrayList<>());
        }
        return library;
    }
}

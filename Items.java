import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Items {

    private int id;
    private String title;
    private String category;
    private String type;
    private boolean available = true;
    private String borrowedBy;
    private LocalDate borrowedDate;
    private String borrowDate;

    public Items(int id, String title, String category, String type) {
        this.id = id;
        this.title = title;
        this.category = category;
        this.type = type;
    }

    public int getId() {
        return id;
    }
    public String getTitle() {
        return title;
    }
    public String getCategory() {
        return category;
    }
    public String getType() {
        return type;
    }
    public boolean getAvailable() {
        return available;
    }
    public void setAvailable(boolean available) {
        this.available = available;
    }

    // Marks the item as borrowed by a user and sets the borrow date
    public void setBorrowed(Users user, Items item , LocalDate borrowDate) {
        this.available = false;
        this.borrowedBy = user.getName();
        this.borrowedDate = borrowDate;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        this.borrowDate = borrowDate.format(formatter);
    }

    // Returns a formatted string including borrow date and borrower name
    public String getBorrowed(Items item) {
        return " Borrowed Date: " + borrowDate.replace("-", "/") + " Borrowed by: " + borrowedBy;
    }
}

// Book class representing a book item
class Book extends Items {
    private String author;

    public Book(int id, String title, String author, String category, String type) {
        super(id, title, category, type);
        this.author = author;
    }

    public String getAuthor() {
        return author;
    }
}

// DVD class representing a DVD item
class DVD extends Items {
    private String director;
    private String runtime;

    public DVD(int id, String title, String director, String category ,String runtime, String type) {
        super(id, title, category, type);
        this.director = director;
        this.runtime = runtime;
    }

    public String getDirector() {
        return director;
    }

    public String getRuntime() {
        return runtime;
    }
}

// Magazine class representing a magazine item
class Magazine extends Items {
    private String publisher;

    public Magazine(int id, String title, String publisher, String category, String type) {
        super(id, title, category, type);
        this.publisher = publisher;
    }

    public String getPublisher() {
        return publisher;
    }
}

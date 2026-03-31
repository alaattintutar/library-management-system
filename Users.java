import java.util.*;
import java.time.LocalDate;

// Abstract class representing a user of the library
abstract class Users {
    private String name;
    private int id;
    private String phoneNumber;
    private double penalty;
    private List<Items> borrowedItems = new ArrayList<>();
    protected Map<Integer, LocalDate> borrowDates = new HashMap<>();

    public Users(String name, int id, String phoneNumber) {
        this.name = name;
        this.id = id;
        this.phoneNumber = phoneNumber;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    // Resets the penalty amount
    public void pay() {
        penalty = 0;
    }

    // Abstract methods to be implemented by subclasses
    public abstract int getMaxItems();
    public abstract int getOverdueDays();
    public abstract boolean isRestricted(Items item);

    // Checks if the user is allowed to borrow (penalty must be less than 6)
    public boolean canBorrow() {
        return penalty < 6;
    }

    // Adds an item to the user's borrowed list and records the borrow date
    public void borrowItem(Items item, LocalDate borrowDate) {
        borrowedItems.add(item);
        borrowDates.put(item.getId(), borrowDate);
    }

    // Removes an item from the borrowed list
    public void returnItem(int itemId) {
        for (int i = 0; i < borrowedItems.size(); i++) {
            if (borrowedItems.get(i).getId() == itemId) {
                borrowedItems.remove(i);
                break;
            }
        }
    }

    // Adds a penalty amount
    public void addPenalty() {
        penalty += 2;
    }

    // Checks if the user has reached their borrow limit
    public boolean hasReachedBorrowLimit() {
        return borrowedItems.size() >= getMaxItems();
    }
}

// Student user type with specific restrictions and limits
class Student extends Users {
    private String department;
    private String faculty;
    private String grade;

    public Student(String name, int id, String phoneNumber, String department, String faculty, String grade) {
        super(name, id, phoneNumber);
        this.department = department;
        this.faculty = faculty;
        this.grade = grade;
    }

    public String getDepartment() {
        return department;
    }

    public String getFaculty() {
        return faculty;
    }

    public String getGrade() {
        return grade;
    }

    @Override
    public int getMaxItems() { return 5; }

    @Override
    public int getOverdueDays() { return 30; }

    // Students cannot borrow reference items
    @Override
    public boolean isRestricted(Items item) {
        return item.getType().equals("reference");
    }
}

// Academic member user type with different borrowing rights
class AcademicMember extends Users {
    private String department;
    private String faculty;
    private String title;

    public AcademicMember(String name, int id, String phoneNumber, String department, String faculty, String title) {
        super(name, id, phoneNumber);
        this.department = department;
        this.faculty = faculty;
        this.title = title;
    }

    public String getDepartment() {
        return department;
    }

    public String getFaculty() {
        return faculty;
    }

    public String getTitle() {
        return title;
    }

    @Override
    public int getMaxItems() { return 3; }

    @Override
    public int getOverdueDays() { return 15; }

    // Academic members have no restrictions on item types
    @Override
    public boolean isRestricted(Items item) { return false; }
}

// Guest user type with strict restrictions
class Guest extends Users {
    private String occupation;

    public Guest(String name, int id, String phoneNumber, String occupation) {
        super(name, id, phoneNumber);
        this.occupation = occupation;
    }

    public String getOccupation() {
        return occupation;
    }

    @Override
    public int getMaxItems() { return 1; }

    @Override
    public int getOverdueDays() { return 7; }

    // Guests cannot borrow rare or limited items
    @Override
    public boolean isRestricted(Items item) {
        return item.getType().equals("rare") || item.getType().equals("limited");
    }
}

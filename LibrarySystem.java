import java.time.LocalDate;
import java.util.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

public class LibrarySystem {

    private HashMap<Integer, Items> itemMap = new HashMap<>();
    private HashMap<Integer, Users>  userMap = new HashMap<>();
    FileManager fileManager = new FileManager();

    // Reads items from the file and stores them in the itemMap
    public void itemManager(String itemsFile) {
        for (String[] item : fileManager.readTxt(itemsFile)) {
            String itemClass = item[0];
            int id = Integer.parseInt(item[1]);
            Items newItem;
            switch (itemClass) {
                case "B":
                    newItem = new Book(id, item[2], item[3], item[4], item[5]);
                    break;
                case "M":
                    newItem = new Magazine(id, item[2], item[3], item[4], item[5]);
                    break;
                case "D":
                    newItem = new DVD(id, item[2], item[3], item[4], item[5], item[6]);
                    break;
                default:
                    System.out.println("Unknown itemClass: " + itemClass);
                    continue;
            }
            itemMap.put(id, newItem);
        }
    }

    // Reads users from the file and stores them in the userMap
    public void userManager(String usersFile) {
        for (String[] user : fileManager.readTxt(usersFile)) {
            String userClass = user[0];
            int id = Integer.parseInt(user[2]);
            Users newUser;

            switch (userClass) {
                case "S":
                    newUser = new Student(user[1], id, user[3], user[4], user[5], user[6]);
                    break;
                case "A":
                    newUser = new AcademicMember(user[1], id, user[3], user[4], user[5], user[6]);
                    break;
                case "G":
                    newUser = new Guest(user[1], id, user[3], user[4]);
                    break;
                default:
                    System.out.println("Unknown userClass: " + userClass);
                    continue;
            }
            userMap.put(id, newUser);
        }
    }

    // Handles all the commands from the file and processes each accordingly
    public void commandManager(String commandsFile, String outputFile){
        List<String> output = new ArrayList<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        for (String[] cmd : fileManager.readTxt(commandsFile)) {
            switch (cmd[0]) {
                case "borrow":
                    int userId = Integer.parseInt(cmd[1]);
                    int itemId = Integer.parseInt(cmd[2]);
                    Users user = userMap.get(userId);
                    Items item = itemMap.get(itemId);

                    if (!user.canBorrow()) {
                        output.add(user.getName() + " cannot borrow " + item.getTitle() + ", you must first pay the penalty amount! 6$");
                    } else if (!item.getAvailable()) {
                        output.add(user.getName() + " cannot borrow " + item.getTitle() + ", it is not available!");
                    } else if (user.hasReachedBorrowLimit()) {
                        output.add(user.getName() + " cannot borrow " + item.getTitle() + ", since the borrow limit has been reached!");
                    } else if (user.isRestricted(item)) {
                        output.add(user.getName() + " cannot borrow " + item.getType() + " item!");
                    } else {
                        LocalDate borrowDate = LocalDate.parse(cmd[3], formatter);
                        user.borrowItem(item, borrowDate);
                        item.setBorrowed(user,item, borrowDate);
                        output.add(user.getName() + " successfully borrowed! " + item.getTitle());
                    }

                    LocalDate borrowDate = user.borrowDates.get(itemId);
                    if (borrowDate != null) {
                        LocalDate now = LocalDate.now();
                        long daysBetween = ChronoUnit.DAYS.between(borrowDate, now);
                        if (daysBetween > user.getOverdueDays()) {
                            item.setAvailable(true);
                            user.addPenalty();
                        }
                    }
                    break;

                case "return":
                    userId = Integer.parseInt(cmd[1]);
                    itemId = Integer.parseInt(cmd[2]);
                    user = userMap.get(userId);
                    item = itemMap.get(itemId);
                    user.returnItem(itemId);
                    item.setAvailable(true);
                    output.add(user.getName() + " successfully returned " + item.getTitle());
                    user.returnItem(itemId);
                    user.borrowDates.remove(itemId);
                    break;

                case "pay":
                    userId = Integer.parseInt(cmd[1]);
                    user = userMap.get(userId);
                    user.pay();
                    output.add(user.getName() + " has paid penalty");
                    break;

                case "displayUsers":
                    output.add("");
                    userMap.values().stream()
                            .sorted(Comparator.comparingInt(Users::getId))
                            .forEach(u -> {
                                StringBuilder userOutput = new StringBuilder();
                                userOutput.append("\n------ User Information for ").append(u.getId()).append(" ------\n");
                                if (u instanceof AcademicMember) {
                                    userOutput.append("Name: ").append(((AcademicMember) u).getTitle()).append(" ").append(u.getName());
                                } else {
                                    userOutput.append("Name: ").append(u.getName());
                                }
                                userOutput.append(" Phone: ").append(u.getPhoneNumber()).append("\n");
                                if (u instanceof Student) {
                                    Student student = (Student) u;
                                    userOutput.append("Faculty: ").append(student.getFaculty());
                                    userOutput.append(" Department: ").append(student.getDepartment());
                                    userOutput.append(" Grade: ").append(student.getGrade()).append("th");
                                } else if (u instanceof AcademicMember) {
                                    AcademicMember academic = (AcademicMember) u;
                                    userOutput.append("Faculty: ").append(academic.getFaculty());
                                    userOutput.append(" Department: ").append(academic.getDepartment());
                                } else if (u instanceof Guest) {
                                    Guest guest = (Guest) u;
                                    userOutput.append("Occupation: ").append(guest.getOccupation());
                                }

                                output.add(userOutput.toString());
                            });
                    break;

                case "displayItems":
                    output.add("");
                    itemMap.values().stream()
                            .sorted(Comparator.comparingInt(Items::getId))
                            .forEach(i -> {
                                StringBuilder itemOutput = new StringBuilder();
                                itemOutput.append("\n------ Item Information for ").append(i.getId()).append(" ------\n");
                                itemOutput.append("ID: ").append(i.getId());
                                itemOutput.append(" Name: ").append(i.getTitle());
                                if (i.getAvailable()) {
                                    itemOutput.append(" Status: Available");
                                } else {
                                    itemOutput.append(" Status: Borrowed");
                                    itemOutput.append(i.getBorrowed(i));
                                }
                                if (i instanceof Book) {
                                    Book book = (Book) i;
                                    itemOutput.append("\nAuthor: ").append(book.getAuthor());
                                    itemOutput.append(" Genre: ").append(book.getCategory());
                                } else if (i instanceof Magazine) {
                                    Magazine magazine = (Magazine) i;
                                    itemOutput.append("\nPublisher: ").append(magazine.getPublisher());
                                    itemOutput.append(" Category: ").append(magazine.getCategory());
                                } else if (i instanceof DVD) {
                                    DVD dvd = (DVD) i;
                                    itemOutput.append("\nDirector: ").append(dvd.getDirector());
                                    itemOutput.append(" Category: ").append(dvd.getCategory());
                                    itemOutput.append(" Runtime: ").append(dvd.getRuntime());
                                }

                                output.add(itemOutput.toString());
                            });
                    break;
            }
        }
        fileManager.writeToFile(outputFile, output);
    }
}

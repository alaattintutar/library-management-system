// Entry point for the Library Management System
public class Main {

    public static void main(String[] args) {
        // Input file paths passed from command line arguments
        String itemsFile = args[0];
        String usersFile = args[1];
        String commandsFile = args[2];
        String outputFile = args[3];

        // Create an instance of the system
        LibrarySystem manager = new LibrarySystem();

        // Load items, users, and execute commands
        manager.itemManager(itemsFile);
        manager.userManager(usersFile);
        manager.commandManager(commandsFile, outputFile);
    }
}

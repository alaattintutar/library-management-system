# Library Management System

A robust and object-oriented Library Management System built in Java. It allows libraries to manage different types of users, physical items, borrowing parameters, and late return penalties.

## 📖 Overview
This project simulates a complete library workflow through file-based commands. It reads data from input text files (`items.txt`, `users.txt`, `commands.txt`) and outputs the results of the operations to an output file.

## 🚀 Key Features
- **User Management**: Supports multiple types of users (`Student`, `AcademicMember`, `Guest`), each with distinct borrowing limits, periods, and restrictions.
- **Item Categorization**: Handles various library items like `Books`, `Magazines`, and `DVDs`.
- **Borrowing & Returns**: Enforces borrowing limits based on user type and handles returning mechanisms.
- **Penalty System**: Automatically tracks overdue items and applies monetary penalties. Users cannot borrow new materials until their debt is cleared.
- **File I/O Parsing**: Reads the entire system state and interactions directly from structured text files.

## 🏗️ Architecture & OOP Design
The system relies heavily on **Object-Oriented Programming (OOP)** principles:
- **Inheritance & Polymorphism:** Abstract `Users` class inherited by distinct user types, and parent `Items` class for library objects.
- **Encapsulation:** Controlled data access for item status and user data.
- **File Management Interface:** Dedicated utility class `FileManager` handling all input/output stream processes safely.

## ⚙️ How to Run
The program is entry-pointed at `Main.java` and expects 4 command-line arguments:
```bash
java Main <items_file> <users_file> <commands_file> <output_file>
```

*Example command:*
```bash
java Main items.txt users.txt commands.txt output.txt
```

## 🛠️ Built With
- **Java 8+**
- **Core Java Libraries** (No external dependencies)
<div align="center">
  
# 📚 Library Management System

![Java](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)
![Storage](https://img.shields.io/badge/System-Inventory%20Control-purple?style=for-the-badge)
![Clean Code](https://img.shields.io/badge/Design-Decoupled%20Architecture-teal?style=for-the-badge)

*A sophisticated cataloging application mapping physical literature and active patron borrowing logic.*

</div>

## 📌 Overview
The **Library Management System** scales backend literature deployment. Operating via highly unified object-mapping protocols, it provides rigid tracking of digital constraints regarding `Users` interactions with library `Items`. It systematically handles borrowing, returning, deadlines, and inventory tracking.

## ✨ Core Features
- 📖 **Item Cataloging:** Classifies variations of literature (`Items.java`) assigning each an inescapable digital presence in the library tree.
- 👤 **Patron Management:** Registers and manages users (`Users.java`), seamlessly mapping their current checkout capacities and loan status.
- 🔄 **Transaction Engine:** A heavy-duty `LibrarySystem` handles borrowing and tracking lifecycles natively via fast indexing structures.
- 📁 **Data Separation:** `FileManager` logic segregates data loading from runtime logic, reading raw commands and writing output states directly to files.

## ⚙️ Technical Architecture
- **Language Stack:** Java 8+
- **Pattern:** Centralized Controller. `LibrarySystem.java` works to ingest I/O data streams and execute appropriate member functions from memory references.
- **Modularity:** Implemented to strict Solid-Architecture styles ensuring maintainability and scalability independent of UI implementations.

## 🚀 Getting Started
Launch the backend processor:

```bash
# Compile the framework
javac *.java

# Execute using your provided test parameters
java Main <items_database.txt> <users.txt> <system_commands.txt> <console_output.txt>
```
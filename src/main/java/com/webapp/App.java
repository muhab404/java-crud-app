package com.webapp;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class App {
    private static UserDAO userDAO = new UserDAO();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        try {
            userDAO.createTable();
            System.out.println("Java CRUD Application Started");
            
            while (true) {
                showMenu();
                int choice = scanner.nextInt();
                scanner.nextLine(); // consume newline
                
                switch (choice) {
                    case 1: createUser(); break;
                    case 2: readUser(); break;
                    case 3: readAllUsers(); break;
                    case 4: updateUser(); break;
                    case 5: deleteUser(); break;
                    case 6: System.exit(0); break;
                    default: System.out.println("Invalid choice");
                }
            }
        } catch (SQLException e) {
            System.err.println("Database error: " + e.getMessage());
        }
    }

    private static void showMenu() {
        System.out.println("\n=== User Management ===");
        System.out.println("1. Create User");
        System.out.println("2. Read User");
        System.out.println("3. Read All Users");
        System.out.println("4. Update User");
        System.out.println("5. Delete User");
        System.out.println("6. Exit");
        System.out.print("Choose option: ");
    }

    private static void createUser() throws SQLException {
        System.out.print("Enter name: ");
        String name = scanner.nextLine();
        System.out.print("Enter email: ");
        String email = scanner.nextLine();
        
        userDAO.createUser(new User(name, email));
        System.out.println("User created successfully");
    }

    private static void readUser() throws SQLException {
        System.out.print("Enter user ID: ");
        int id = scanner.nextInt();
        
        User user = userDAO.getUserById(id);
        if (user != null) {
            System.out.println(user);
        } else {
            System.out.println("User not found");
        }
    }

    private static void readAllUsers() throws SQLException {
        List<User> users = userDAO.getAllUsers();
        if (users.isEmpty()) {
            System.out.println("No users found");
        } else {
            users.forEach(System.out::println);
        }
    }

    private static void updateUser() throws SQLException {
        System.out.print("Enter user ID to update: ");
        int id = scanner.nextInt();
        scanner.nextLine();
        
        User user = userDAO.getUserById(id);
        if (user != null) {
            System.out.print("Enter new name: ");
            user.setName(scanner.nextLine());
            System.out.print("Enter new email: ");
            user.setEmail(scanner.nextLine());
            
            userDAO.updateUser(user);
            System.out.println("User updated successfully");
        } else {
            System.out.println("User not found");
        }
    }

    private static void deleteUser() throws SQLException {
        System.out.print("Enter user ID to delete: ");
        int id = scanner.nextInt();
        
        userDAO.deleteUser(id);
        System.out.println("User deleted successfully");
    }
}
package com.fooddeliveryapp;

import com.fooddeliveryapp.dao.*;
import com.fooddeliveryapp.model.*;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class App {

    private static final Scanner scanner = new Scanner(System.in);
    private static final UserDAO userDAO = new UserDAO();
    private static final RestaurantDAO restaurantDAO = new RestaurantDAO();
    private static final MenuItemDAO menuDAO = new MenuItemDAO();
    private static final OrdersDAO ordersDAO = new OrdersDAO();

    public static void main(String[] args) throws SQLException, InterruptedException {
        while (true) {
            System.out.println("\n--- Food Delivery App ---");
            System.out.println("1. Place Order");
            System.out.println("2. Admin Login");
            System.out.println("0. Exit");
            System.out.print("Enter choice: ");
            int choice = Integer.parseInt(scanner.nextLine());

            switch (choice) {
                case 1:
                    placeOrder();
                    break;
                case 2:
                    adminLogin();
                    break;
                case 0:
                    System.out.println("Exiting... Bye!");
                    return;
                default:
                    System.out.println("Invalid choice!");
                    break;
            }
        }
    }

    // ------------------ USER FLOW ------------------
    private static void placeOrder() throws SQLException, InterruptedException {
        List<Restaurant> restaurants = restaurantDAO.getAll();
        if (restaurants.isEmpty()) {
            System.out.println("No restaurants available.");
            return;
        }

        displayRestaurants(restaurants);
        System.out.print("Select restaurant: ");
        int ridChoice = Integer.parseInt(scanner.nextLine());
        if (ridChoice < 1 || ridChoice > restaurants.size()) {
            System.out.println("Invalid restaurant selection!");
            return;
        }
        long rid = restaurants.get(ridChoice - 1).getId();

        List<MenuItem> menuItems = menuDAO.getByRestaurant(rid);
        if (menuItems.isEmpty()) {
            System.out.println("No menu items available for this restaurant.");
            return;
        }

        double total = 0;
        Orders order = new Orders();
        order.setStatus("Pending");

        while (true) {
            displayMenuItems(menuItems);
            System.out.print("Select item: ");
            int itemChoice = Integer.parseInt(scanner.nextLine());
            if (itemChoice == 0)
                break;
            if (itemChoice < 1 || itemChoice > menuItems.size()) {
                System.out.println("Invalid selection!");
                continue;
            }
            MenuItem selected = menuItems.get(itemChoice - 1);
            System.out.print("Quantity: ");
            int qty = Integer.parseInt(scanner.nextLine());
            total += selected.getPrice() * qty;
        }

        if (total == 0) {
            System.out.println("No items selected. Order cancelled.");
            return;
        }

        // ------------------ USER LOGIN / SIGNUP ------------------
        System.out.println("\n--- Enter Your Details ---");
        System.out.print("Name: ");
        String name = scanner.nextLine();
        System.out.print("Email: ");
        String email = scanner.nextLine();
        System.out.print("Password: ");
        String password = scanner.nextLine();

        User user = userDAO.getByEmail(email);
        if (user == null) {
            User newUser = new User();
            newUser.setName(name);
            newUser.setEmail(email);
            newUser.setPassword(password);
            userDAO.create(newUser);
            user = newUser;
            System.out.println("✅ Account created successfully!");
        } else {
            if (!user.getPassword().equals(password)) {
                System.out.println("❌ Incorrect password. Order cannot be linked.");
                return;
            }
            System.out.println("✅ Logged in successfully!");
        }

        order.setUserId(user.getId());
        order.setTotal(total);

        // ------------------ ASK FOR DELIVERY ADDRESS ------------------
        System.out.print("Enter Delivery Address: ");
        String deliveryAddress = scanner.nextLine();
        order.setDeliveryAddress(deliveryAddress);

        // ------------------ PAYMENT ------------------
        System.out.println("\n--- Payment ---");
        System.out.println("Total Amount: ₹" + total);
        System.out.println("Choose Payment Method:");
        System.out.println("1. Cash");
        System.out.println("2. Card");
        System.out.println("3. UPI");
        int payChoice = Integer.parseInt(scanner.nextLine());

        String paymentMethod;
        switch (payChoice) {
            case 1:
                paymentMethod = "Cash";
                break;
            case 2:
                paymentMethod = "Card";
                break;
            case 3:
                paymentMethod = "UPI";
                break;
            default:
                paymentMethod = "Unknown";
                break;
        }

        System.out.println("Processing payment...");
        Thread.sleep(1000);
        System.out.println("✅ Payment of ₹" + total + " successful via " + paymentMethod);
        order.setStatus("Completed");

        ordersDAO.create(order);
        System.out.println("✅ Order placed successfully!");
    }

    private static void displayRestaurants(List<Restaurant> restaurants) {
        System.out.println("\n--- Available Restaurants ---");
        for (int i = 0; i < restaurants.size(); i++) {
            Restaurant r = restaurants.get(i);
            System.out.printf("%d. %s (%s)%n", i + 1, r.getName(), r.getAddress());
        }
    }

    private static void displayMenuItems(List<MenuItem> menuItems) {
        System.out.println("\n--- Menu Items ---");
        System.out.printf("%-3s %-25s %-10s%n", "No", "Item Name", "Price");
        for (int i = 0; i < menuItems.size(); i++) {
            MenuItem m = menuItems.get(i);
            System.out.printf("%-3d %-25s ₹%-10.2f%n", i + 1, m.getName(), m.getPrice());
        }
        System.out.println("0. Finish Order");
    }

    // ------------------ ADMIN FLOW ------------------
    private static void adminLogin() throws SQLException {
        System.out.print("Admin Email: ");
        String email = scanner.nextLine();
        System.out.print("Password: ");
        String password = scanner.nextLine();

        if (!email.equals("xyz@gmail.com") || !password.equals("1234")) {
            System.out.println("❌ Invalid admin credentials!");
            return;
        }

        while (true) {
            System.out.println("\n--- Admin Menu ---");
            System.out.println("1. View All Users");
            System.out.println("2. Add Restaurant");
            System.out.println("3. Add Menu Item");
            System.out.println("4. View All Orders");
            // Removed update order status
            System.out.println("0. Logout");
            System.out.print("Enter choice: ");
            int choice = Integer.parseInt(scanner.nextLine());

            switch (choice) {
                case 1:
                    viewAllUsers();
                    break;
                case 2:
                    addRestaurant();
                    break;
                case 3:
                    addMenuItem();
                    break;
                case 4:
                    viewAllOrders();
                    break;
                case 0:
                    System.out.println("Logging out...");
                    return;
                default:
                    System.out.println("Invalid choice!");
                    break;
            }

        }
    }

    private static void viewAllUsers() throws SQLException {
        List<User> users = userDAO.getAll();
        System.out.println("\n--- Users ---");
        for (User u : users) {
            System.out.println(u.getId() + ". " + u.getName() + " - " + u.getEmail());
        }
    }

    private static void addRestaurant() throws SQLException {
        System.out.print("Restaurant Name: ");
        String name = scanner.nextLine();
        System.out.print("Address: ");
        String address = scanner.nextLine();
        Restaurant r = new Restaurant();
        r.setName(name);
        r.setAddress(address);
        restaurantDAO.create(r);
        System.out.println("✅ Restaurant added successfully!");
    }

    private static void addMenuItem() throws SQLException {
        List<Restaurant> restaurants = restaurantDAO.getAll();
        if (restaurants.isEmpty()) {
            System.out.println("No restaurants available.");
            return;
        }

        displayRestaurants(restaurants);
        System.out.print("Select restaurant: ");
        int ridChoice = Integer.parseInt(scanner.nextLine());
        if (ridChoice < 1 || ridChoice > restaurants.size())
            return;
        long rid = restaurants.get(ridChoice - 1).getId();

        System.out.print("Menu Item Name: ");
        String name = scanner.nextLine();
        System.out.print("Price: ");
        double price = Double.parseDouble(scanner.nextLine());

        MenuItem m = new MenuItem();
        m.setRestaurantId(rid);
        m.setName(name);
        m.setPrice(price);
        menuDAO.create(m);
        System.out.println("✅ Menu item added successfully!");
    }

    private static void viewAllOrders() throws SQLException {
        List<Orders> orders = ordersDAO.getAll();
        System.out.println("\n--- Orders ---");
        for (Orders o : orders) {
            System.out.println("OrderID: " + o.getId() + " | UserID: " + o.getUserId() +
                    " | Total: ₹" + o.getTotal() + " | Status: " + o.getStatus());
        }
    }

    private static void updateOrderStatus() throws SQLException {
        viewAllOrders();
        System.out.print("Enter Order ID to update: ");
        long oid = Long.parseLong(scanner.nextLine());
        Orders order = ordersDAO.getById(oid);
        if (order == null) {
            System.out.println("Invalid Order ID!");
            return;
        }
        System.out.print("Enter new status (Pending/Completed): ");
        String status = scanner.nextLine();
        order.setStatus(status);
        ordersDAO.update(order);
        System.out.println("✅ Order status updated!");
    }
}

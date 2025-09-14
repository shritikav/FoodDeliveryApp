# Food Delivery App

A **console-based Java application** for managing restaurants, menu items, users, and orders. Users can place orders and make payments, while the admin can manage restaurants, menu items, and view all orders. Built with **Java, JDBC, MySQL, and Maven**.

---

## Features

### User Features
- Place orders from available restaurants.
- Select menu items and specify quantities.
- Enter delivery address.
- Make payments via Cash, Card, or UPI.
- New users can sign up while placing an order.
- Existing users can log in to link orders.

### Admin Features
- Login with credentials:  
  - Email: `xyz@gmail.com`  
  - Password: `1234`
- View all users.
- Add new restaurants.
- Add menu items to restaurants.
- View all orders.

---

## Technologies Used
- **Java 17**
- **JDBC** (Database connectivity)
- **MySQL** (Database)
- **Maven** (Build and dependency management)
- **Console-based interface** (CLI)

---

## Database Setup

1. Create the database:


CREATE DATABASE FoodDeliveryApp;
USE FoodDeliveryApp;
CREATE TABLE user (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100),
    email VARCHAR(100),
    password VARCHAR(100)
);

CREATE TABLE restaurant (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(150),
    address VARCHAR(255)
);

CREATE TABLE menu_item (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    restaurant_id BIGINT,
    name VARCHAR(100),
    price DECIMAL(10,2),
    FOREIGN KEY (restaurant_id) REFERENCES restaurant(id)
);

CREATE TABLE orders (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT,
    total DECIMAL(10,2),
    status VARCHAR(20),
    delivery_address VARCHAR(255),
    FOREIGN KEY (user_id) REFERENCES user(id)
);

---

## Installation & Setup

1. **Clone the repository:**

git clone https://github.com/shritikav/FoodDeliveryApp.git
cd FoodDeliveryApp

2. **Configure database:**
-Ensure MySQL is running.
-Update DBConnection.java with your MySQL username and password:
private static final String USER = "root";
private static final String PASSWORD = "your_password";
3. **Build the project with Maven:
mvn clean install
4. **Run the application:
java -cp target/FoodDeliveryApp-1.0-SNAPSHOT.jar com.fooddeliveryapp.App

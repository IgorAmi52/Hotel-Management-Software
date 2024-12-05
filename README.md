# Hotel Management Software

This is an open-source **Hotel Management Software** developed using **Java** and **Java Swing** to demonstrate Object-Oriented Programming (OOP) principles. The project simulates real-world hotel management scenarios, allowing various users to perform tasks such as booking rooms, managing schedules, and more. It was created as part of my **Object-Oriented Programming course**.

---

## Table of Contents

- [Introduction](#introduction)
- [Features](#features)
- [Technologies Used](#technologies-used)
- [Installation](#installation)
- [Usage](#usage)
- [License](#license)

---

## Introduction

The **Hotel Management Software** aims to provide a comprehensive system to manage hotel operations efficiently. The application demonstrates the use of **polymorphism**, **inheritance**, **encapsulation**, and other OOP principles through the implementation of various user roles and functionalities. The graphical user interface (GUI) is built using **Java Swing**, offering an interactive experience.

---

## Features

- **User Roles**:
  - **Admin**: Manage users, view reports, and handle system settings.
  - **Cleaner**: Update room statuses and schedules.
  - **Agent**: Manage bookings and customer details.
  - **Guests**: Book rooms, view personal details, and request services.
- **Room Management**: Book, cancel, and view room availability.
- **Service Requests**: Allow guests to request additional services during their stay.
- **Reporting**: Generate reports for bookings and room statuses (Admin only).
- **Authentication**: Secure login system for different user roles.
- **Interactive GUI**: User-friendly interface built with **Java Swing**.

---

## Technologies Used

- **Programming Language**: Java
- **GUI Framework**: Java Swing

---

## Installation

1. **Clone the Repository**:
   ```bash
   git clone https://github.com/IgorAmi52/Hotel-Management-Software.git
   ```
2. **Navigate to the Project Directory**:
   ```bash
   cd Hotel-Management-Software
   ```
3. **Compile the Project**:
   ```bash
   javac -d bin src/**/*.java
   ```
4. **Run the Application**:
   ```bash
   java -cp bin Main
   ```

---

## Usage

- **Login**:
  - Use role-specific credentials to access features (e.g., Admin, Cleaner, Agent, Guest).
- **Book a Room**:
  - Navigate to the "Book Room" section to search for available rooms and complete a booking.
- **Manage Rooms**:
  - Admins can add or update room details, and Cleaners can update room statuses.
- **View Reports**:
  - Admins can generate detailed reports on bookings and room statuses.

---

## License

This project is licensed under the MIT License.

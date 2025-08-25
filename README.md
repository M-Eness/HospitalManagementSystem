# 🏥 Hospital Management System

This project is a comprehensive desktop application for a Hospital Management System, built using **Java** with **Swing** for the GUI and **JDBC** for database connectivity. It offers robust functionalities for both patients and administrators, ensuring a seamless and secure management experience.

### 🌟 Core Modules & Functionality

#### 🔒 Authentication
-   **Secure Login**: Both patients and administrators can log in using their SSN (or a username for admins) and password. The `LoginPage.java` handles this process, verifying credentials against the database.
-   **Patient Registration**: New users can register via `PatientRegister.java`, which collects personal details and validates them using regular expressions for data integrity.
-   **Password Reset**: `ResetFrame.java` provides a password reset feature.

#### 👤 User Management
-   **Patient Profile**: The `PatientPage.java` serves as the main dashboard for patients, allowing them to view and update their personal information, including age, height, and weight.
-   **Admin Panel**: `AdminPage.java` is the central hub for administrators. It provides full control over the system, enabling them to:
    -   Add, update, and delete doctor profiles.
    -   Manage patient accounts.
    -   View and export a comprehensive list of all appointments.

#### 📅 Appointment Scheduling
-   **Patient-Side**: Patients can easily add, edit, and delete their appointments. The system ensures that appointments are scheduled on valid dates, preventing past dates from being selected.
-   **Admin-Side**: Administrators have a global view of all appointments and can manage them as needed. The appointment data can be exported to a `.txt` file for record-keeping.

### ⚙️ Technical Details

#### 📁 Project Structure
The code is organized into several key classes, each with a specific responsibility:
-   `Person.java`, `Patient.java`, `Doctor.java`, `Admins.java`: These classes use an object-oriented approach to model users within the system, inheriting common properties from the `Person` class.
-   `DatabaseManager.java`: This is the backbone of the application's data layer. It manages all database connections and operations using SQL queries, ensuring data persistence and integrity.
-   `LoginPage.java`, `PatientPage.java`, `AdminPage.java`, `PatientRegister.java`, `ResetFrame.java`: These classes handle the graphical user interfaces and user interactions for their respective sections of the application.

#### 🛡️ Data Validation
-   `Patient.java` and `Doctor.java` include built-in validation checks using **regular expressions** to ensure that all user inputs (SSN, name, surname, age, password, etc.) are in the correct format.

### 🔧 Setup and Installation

1.  **Database**: The application requires a **SQL database**. Before running, you must configure the database connection settings in `DatabaseManager.java`. Update the `url`, `username`, and `password` variables with your specific database credentials.
2.  **Dependencies**:
    -   You will need a **JDBC driver** for your database (e.g., MySQL Connector/J).
    -   The `com.toedter.calendar` library is also required to handle date selections within the GUI.
3.  **Execution**: Once the dependencies are set up and the database is configured, you can run the application by compiling and executing `LoginPage.java`.

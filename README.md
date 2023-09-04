# Simple Spring Boot Student Management Project - Testing README

This README provides quick instructions for testing the basic functionalities of the Spring Boot Student Management Project.

### Prerequisites
Java 8 or later
Maven

### Getting Started
1. Clone the Repository:
git clone https://github.com/your-username/student-management-spring-boot.git
cd student-management-spring-boot

2. Build the Project:
mvn clean install

3. Run the Application:
mvn spring-boot:run
The application will start on http://localhost:8080.

### Testing the Endpoints
You can use tools like curl, Postman, or any API testing tool to test the endpoints.

1. Get All Students
URL: http://localhost:8080/api/students
Method: GET
Description: Retrieve a list of all students.

2. Add a Student
URL: http://localhost:8080/api/students
Method: POST
Description: Add a new student to the system.

3. Delete a Student
Replace {studentId} with the actual ID of the student you want to delete.
URL: http://localhost:8080/api/students/{studentId}
Method: DELETE
Description: Delete a student by ID.

### Configuration
You can configure the application properties in the src/main/resources/application.properties file.

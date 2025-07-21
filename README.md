

# User Sector Management Spring Boot App

A simple Spring Boot web application that lets users register their name and select multiple sectors they are involved in. It includes form validation, session management, sector removal, and user data deletion.

---

## Features

* Enter and save user name with validation
* Select multiple sectors (with multi-select UI)
* Agree to terms checkbox validation
* Save and update user data with sectors
* Remove individual sectors from the user
* Delete entire user data and invalidate session
* Session timeout set to 15 minutes
* User-friendly feedback messages on save and delete
* Simple UI built with Thymeleaf templates
* Uses Spring Data JPA with an embedded database (PostgreSQL)

---

## Technologies

* Java 17+
* Spring Boot 3.5.3
* Spring Data JPA
* Thymeleaf templating engine
* Validation API (Jakarta Validation)
* Embedded database (H2 by default)
* Gradle build system

---

### Why These Technologies?

I chose Spring Boot, Thymeleaf, and Spring Data JPA because they offer a great balance of simplicity and scalability. These technologies are straightforward for small projects but powerful and robust enough to handle much larger applications as they grow. Additionally, I’m most familiar with these tools, which allows me to create clean, maintainable, and efficient code. Using technologies I know well helps ensure quality work and faster development.

---

## Getting Started

### Prerequisites

* JDK 17 or higher installed
* Maven or Gradle installed
* IDE like IntelliJ IDEA, VSCode, or Eclipse (optional)

### Running the Project

1. Clone this repository:

   ```
   git clone https://github.com/Joojist/test_work_helmes
   cd your-repo-name
   ```

2. Build and run with Gradle:

   ```
   ./gradlew bootRun
   ```

3. Open your browser and go to:

   ```
   http://localhost:8080/
   ```

---

## Usage

* Fill in your name.
* Select one or more sectors from the multi-select list (hold Ctrl/Cmd to select multiple).
* Agree to the terms checkbox.
* Click **Save**.
* After saving, your selected sectors will be shown with the option to remove individual sectors.
* A **Delete My Data** button appears to delete your user data entirely.
* Your session will expire after 15 minutes of inactivity.

---

## Project Structure

* `controller/` - Spring MVC controller handling HTTP requests and responses
* `service/` - Business logic including user and sector management
* `entity/` - JPA entities for User and Sector
* `repository/` - Spring Data JPA repositories
* `templates/` - Thymeleaf HTML templates for the UI with simple CSS

---

## Validation

* User name is required.
* At least one sector must be selected.
* User must agree to terms.
* Validation errors show messages next to fields.

---

## Session Management

* User ID is stored in HTTP session.
* Session timeout is 15 minutes.
* Session invalidation happens on user data deletion.

---

## To Do / Improvements

* Add user authentication
* Unit and integration testing

---

## License

MIT License © Daniel Kupinski

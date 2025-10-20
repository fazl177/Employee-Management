

# Employee Management System

## Overview
RESTful Spring Boot service to manage Employees and Departments with JPA. Implements CRUD operations, department/employee relationships (including self-referencing reporting manager), reporting-chain resolution, department analytics, pagination, and lookup/expand query options.

## Author
**Muhammed Fazal Rahiman K**  
Full Stack Java Developer intern at Thinkingcode Technologies, Cochin  

- 📧 Email: fazalrahmanlts@gmail.com
- 📱 Phone: +918139838416
- 📍 Location: Kasaragod, Kerala
- 🔗 LinkedIn: [linkedin.com/in/muhammed-fazal-rahiman-k-3ab847351](https://linkedin.com/in/muhammed-fazal-rahiman-k-3ab847351)
- 💻 GitHub: [github.com/fazl177](https://github.com/fazl177)

**Education**: BTech in Computer Science and Engineering  
LBS College of Engineering Kasaragod, APJ Abdul Kalam Technological University

**Technical Skills**: Java, JavaScript, HTML, CSS, MySQL, Spring Boot, Angular, Bootstrap, Hibernate, Eclipse IDE, Visual Studio, Git, GitHub

## Features
- Create / Update / Get / Delete Employees and Departments
- Move employee between departments
- Prevent deleting a department that has employees
- Reporting chain for any employee (managers up the hierarchy)
- Department analytics: headcount, average salary, headcount by title
- `expand=employee` on department GET to include employees
- `lookup=true` on employee list to return only id and name
- Default pagination (20 items per page) with page metadata
- JPA entities and repositories, Service → Repository flow
- Global exception handling for user-friendly errors

## Tech Stack
- Java 11+
- Spring Boot (web, data-jpa)
- MySQL or PostgreSQL (JDBC driver)
- Maven or Gradle
- JUnit / MockMvc for tests

## Repo Layout
- `src/main/java/com/retailcloud/employee/...` — code (entities, controllers, services, repos, DTOs, exceptions)  
- `src/main/resources/application.yml` — app configuration / datasource  
- `db/create_tables.sql` — database creation + seed script  
- `docs/schemas.json` — request/response JSON schemas  
- `pom.xml` or `build.gradle` — build file

## Database
DDL script: `db/create_tables.sql`  
Key tables:
- `departments` (id, name, description, created_at, head_id)
- `employees` (id, first_name, last_name, email, dob, salary, title, joining_date, bonus_pct, address, manager_id, department_id, created_at)

Note: `manager_id` and `head_id` reference `employees.id` (self / cross-table references); FK rules enforce integrity and deletion rules are handled in service layer.

## Minimum Data
Repository includes seed data to satisfy minimum requirements (at least 3 departments and 25 employees) in `db/create_tables.sql` or a separate `db/seeds.sql`.

## Configuration & Run
1. Prerequisites: Java 11+, MySQL/Postgres, Maven
2. Create DB:
   - `CREATE DATABASE retailcloud;`
   - Run `db/create_tables.sql` or enable `spring.jpa.hibernate.ddl-auto=update`
3. Configure DB in `src/main/resources/application.yml` (or `application.properties`) with `spring.datasource.url`, `username`, `password`.
4. Build & run:
   - Build: `mvn clean package`
   - Run: `mvn spring-boot:run` or `java -jar target/*.jar`

## API Summary
Base path: `/api`

Departments
- `POST /api/departments` — create department  
  Request: `DepartmentRequest` `{ name, description, departmentHeadId? }`  
  Response: `DepartmentResponse` `{ id, name, description, createdAt, employeeCount }`
- `GET /api/departments` — list departments (paginated)  
  Query: `page` (0-based), `size` (default 20)
- `GET /api/departments/{id}` — get department  
  Query: `expand=employee` to include employees list
- `PATCH /api/departments/{id}` — update department
- `DELETE /api/departments/{id}` — delete (fails if department has employees)

Employees
- `POST /api/employees` — create employee  
  Request: `EmployeeRequest` `{ firstName, lastName, email, dob, salary, title, joiningDate, bonusPct, address, managerId, departmentId }`
- `GET /api/employees` — list employees (paginated)  
  Query: `page`, `size` (default 20), `lookup=true` to return only `id` and `name`
- `GET /api/employees/{id}` — get employee (includes manager and department info)
- `PATCH /api/employees/{id}` — update employee (partial)
- `DELETE /api/employees/{id}` — delete employee (subordinates' `manager` set to `null` to preserve integrity)
- `GET /api/employees/{id}/reporting-chain` — managers up the chain
- `GET /api/employees/department/{departmentId}/analytics` — department analytics

## Pagination & Responses
All list endpoints return:
- `content` — array of items
- `page` — current page index (0-based)
- `size` — page size
- `totalPages` — total pages
- `totalElements` — total items

Default `size` is 20.

## Query Options
- `expand=employee` on department GET returns department plus `employees` array.
- `lookup=true` on `GET /api/employees` returns simplified list: `{ id, name }`.

## JSON Schemas
Request/response schemas available at `docs/schemas.json` — useful for validation and API documentation.

## Error Handling
- `404 Not Found` for missing resources
- `400 Bad Request` for validation or business rule violations (e.g., delete department with employees)
- `500 Internal Server Error` for unexpected errors  
Global handler located at `src/main/java/com/retailcloud/employee/exception/GlobalExceptionHandler.java`

## Testing
- Unit tests: `mvn test`
- Controller integration tests: implemented with `MockMvc` (examples under `src/test/java/...`)

## Seed & Test Data
Seed SQL (creates 3+ departments and 25+ employees) available in `db/seeds.sql`. Use for local testing.

## Git
1. Initialize: `git init`
2. Add & commit: `git add . && git commit -m "Initial commit - Employee Management System"`
3. Create remote repo on GitHub and push:
   - `git remote add origin https://github.com/fazl177/employee-management-system.git`
   - `git push -u origin main`

## Notes & Implementation Details
- Department head is stored as an `Employee` reference; ensure head exists before assigning.
- Reporting chain algorithm guards against cycles by tracking visited employee IDs.
- Deleting an employee clears `manager_id` for direct reports to avoid FK constraint errors.
- Average salary ignores null salaries; headcount by title groups by `title` with `"Unknown"` for nulls.

## License
Add `LICENSE` as appropriate.

## Contact
For questions about this project, please contact Muhammed Fazal Rahiman K at fazalrahmanlts@gmail.com.

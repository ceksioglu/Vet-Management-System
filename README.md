# Veteriner Yönetim Sistemi - Patika.dev Bitirme Projesi

## Mesut Hocaya Not:
Proje 8081 portunda çalışmak için ayarlandı.
(http://localhost:8081/swagger-ui/index.html#/)
Projeye daha sonra front end entegrasyonu yapacağım, ama bitmediği için bakmanızın bir anlamı yok.
Bu not kendini proje değerlendirildikten sonra kendini imha edecektir, saygılarımla.

# Veterinary Management System

This project is a comprehensive API designed to manage the daily operations of a veterinary clinic. It manages essential entities such as pet owners, animals, veterinarians, appointments, vaccines, and available dates.

## Features

- Pet and owner management
- Veterinarian management
- Appointment scheduling and management
- Vaccine records and tracking
- Management of doctors' available dates

## Technology Stack

- Java 17
- Spring Boot 3.3.1
- Spring Data JPA
- PostgreSQL / MySQL
- Maven
- Swagger (OpenAPI) for API documentation

## Project Structure

The project includes the following main packages:

- `controller`: Contains REST endpoints
- `service`: Contains business logic
- `repository`: Contains data access layer
- `entity`: Contains JPA entities
- `dto`: Contains data transfer objects
- `exception`: Contains custom exception classes

## Setup

1. Clone the repo:

    ```bash
    git clone https://github.com/yourusername/vet-management-system.git
    cd vet-management-system
    ```

2. Install dependencies:

    ```bash
    mvn clean install
    ```

3. Configure `application.properties`:

    Find `src/main/resources/application.properties` and configure the database connection.

    ```properties
    spring.datasource.url=jdbc:postgresql://localhost:5432/vet_management_db
    spring.datasource.username=yourusername
    spring.datasource.password=yourpassword
    spring.jpa.hibernate.ddl-auto=update
    ```

4. Start the app:

    ```bash
    mvn spring-boot:run
    ```

## Usage

### API Endpoints
    Animals: /api/animals
    Customers: /api/customers
    Doctors: /api/doctors
    Appointments: /api/appointments
    Vaccines: /api/vaccines
    Available Dates: /api/available-dates

CRUD operations (CREATE, READ, UPDATE, DELETE) are available for each endpoint.
Some controllers have more endpoints and functionalities.

## Special Functions

### View all animals of a pet owner:

```bash
GET /api/animals/customer/{customerId}
```

### List all vaccine records for a specific animal:

```bash
GET /api/vaccines/animal/{animalId}
```

### List animals with vaccine protection end dates approaching:

```bash
GET /api/vaccines/protection-end-date?startDate=2023-01-01&endDate=2023-12-31
```

### Filter appointments by date range and doctor:


```bash
GET /api/appointments/doctor/{doctorId}?startDate=2023-01-01&endDate=2023-12-31
```

## Database

Class diagram and entity relationships are as follows:

![Class Diagram](https://github.com/user-attachments/assets/ae30ad0f-60de-42a7-a3c3-bd27cfb82f4a)

## Contact

Contact me for suggestions or improvements: [ceksioglu@hotmail.com]

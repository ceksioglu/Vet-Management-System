![veterinary-clinic-database-svg-enhanced](https://github.com/user-attachments/assets/2aabc0b8-f565-4710-b396-3e4f98efbbf2)# Veteriner Yönetim Sistemi - Patika.dev Bitirme Projesi

## Mesut Hocaya Not:
-Proje 8081 portunda çalışmak için ayarlandı.
```
http://localhost:8081/swagger-ui/index.html#/
```
Randevular saat ile kaydedilmek yerine sıra ile kaydediliyor (proje geliştirirken bazı sorunlar yaşandı). Bunun mantığı bir doktor bir günde en fazla 10 (çalışma saatleri 10 saat olarak belirlendi) randevu alabilir  şeklinde kodlandı (Her randevu 1 saat). Bu randevular sonra sıraya sokuldu. Bu değerleri API'den çekerek görebilirsiniz.

Tarih formatı: 2023-07-15 (YIL / AY / GÜN) şeklindedir.

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

![Uploading v<svg xmlns="http://www.w3.org/2000/svg" xmlns:xlink="http://www.w3.org/1999/xlink" viewBox="0 0 1000 700">
    <defs>
        <style>
            .entity { stroke: #333; stroke-width: 2; }
            .title { fill: #ffffff; font-family: Arial, sans-serif; font-size: 16px; font-weight: bold; }
            .attr { fill: #333333; font-family: Arial, sans-serif; font-size: 12px; }
            .pk { font-weight: bold; }
            .fk { font-style: italic; }
            .relation { stroke: #333333; stroke-width: 2; fill: none; }
            .multiplicity { fill: #333333; font-family: Arial, sans-serif; font-size: 12px; }
        </style>
        <linearGradient id="grad1" x1="0%" y1="0%" x2="100%" y2="100%">
            <stop offset="0%" style="stop-color:#4e79a7;stop-opacity:1" />
            <stop offset="100%" style="stop-color:#2b4d6f;stop-opacity:1" />
        </linearGradient>
        <linearGradient id="grad2" x1="0%" y1="0%" x2="100%" y2="100%">
            <stop offset="0%" style="stop-color:#f28e2b;stop-opacity:1" />
            <stop offset="100%" style="stop-color:#d15f00;stop-opacity:1" />
        </linearGradient>
        <linearGradient id="grad3" x1="0%" y1="0%" x2="100%" y2="100%">
            <stop offset="0%" style="stop-color:#e15759;stop-opacity:1" />
            <stop offset="100%" style="stop-color:#b50d0f;stop-opacity:1" />
        </linearGradient>
        <linearGradient id="grad4" x1="0%" y1="0%" x2="100%" y2="100%">
            <stop offset="0%" style="stop-color:#76b7b2;stop-opacity:1" />
            <stop offset="100%" style="stop-color:#498f8a;stop-opacity:1" />
        </linearGradient>
        <linearGradient id="grad5" x1="0%" y1="0%" x2="100%" y2="100%">
            <stop offset="0%" style="stop-color:#edc948;stop-opacity:1" />
            <stop offset="100%" style="stop-color:#c7a010;stop-opacity:1" />
        </linearGradient>
        <linearGradient id="grad6" x1="0%" y1="0%" x2="100%" y2="100%">
            <stop offset="0%" style="stop-color:#59a14f;stop-opacity:1" />
            <stop offset="100%" style="stop-color:#307326;stop-opacity:1" />
        </linearGradient>
    </defs>
    
    <!-- Customer Entity -->
    <rect x="50" y="50" width="220" height="160" rx="10" ry="10" class="entity" fill="url(#grad1)"/>
    <text x="160" y="75" class="title" text-anchor="middle">Customer</text>
    <line x1="50" y1="85" x2="270" y2="85" stroke="#ffffff" stroke-width="2"/>
    <text x="60" y="105" class="attr pk">customer_id (PK)</text>
    <text x="60" y="125" class="attr">customer_name</text>
    <text x="60" y="145" class="attr">customer_phone</text>
    <text x="60" y="165" class="attr">customer_mail</text>
    <text x="60" y="185" class="attr">customer_address</text>
    <text x="60" y="205" class="attr">customer_city</text>
    
    <!-- Animal Entity -->
    <rect x="370" y="50" width="220" height="200" rx="10" ry="10" class="entity" fill="url(#grad2)"/>
    <text x="480" y="75" class="title" text-anchor="middle">Animal</text>
    <line x1="370" y1="85" x2="590" y2="85" stroke="#ffffff" stroke-width="2"/>
    <text x="380" y="105" class="attr pk">animal_id (PK)</text>
    <text x="380" y="125" class="attr">animal_name</text>
    <text x="380" y="145" class="attr">animal_species</text>
    <text x="380" y="165" class="attr">animal_breed</text>
    <text x="380" y="185" class="attr">animal_gender</text>
    <text x="380" y="205" class="attr">animal_color</text>
    <text x="380" y="225" class="attr">animal_date_of_birth</text>
    <text x="380" y="245" class="attr fk">customer_id (FK)</text>
    
    <!-- Doctor Entity -->
    <rect x="690" y="50" width="220" height="160" rx="10" ry="10" class="entity" fill="url(#grad3)"/>
    <text x="800" y="75" class="title" text-anchor="middle">Doctor</text>
    <line x1="690" y1="85" x2="910" y2="85" stroke="#ffffff" stroke-width="2"/>
    <text x="700" y="105" class="attr pk">doctor_id (PK)</text>
    <text x="700" y="125" class="attr">doctor_name</text>
    <text x="700" y="145" class="attr">doctor_phone</text>
    <text x="700" y="165" class="attr">doctor_mail</text>
    <text x="700" y="185" class="attr">doctor_address</text>
    <text x="700" y="205" class="attr">doctor_city</text>
    
    <!-- Appointment Entity -->
    <rect x="370" y="350" width="220" height="140" rx="10" ry="10" class="entity" fill="url(#grad4)"/>
    <text x="480" y="375" class="title" text-anchor="middle">Appointment</text>
    <line x1="370" y1="385" x2="590" y2="385" stroke="#ffffff" stroke-width="2"/>
    <text x="380" y="405" class="attr pk">appointment_id (PK)</text>
    <text x="380" y="425" class="attr">appointment_date</text>
    <text x="380" y="445" class="attr">appointment_order</text>
    <text x="380" y="465" class="attr fk">doctor_id (FK)</text>
    <text x="380" y="485" class="attr fk">animal_id (FK)</text>
    
    <!-- Vaccine Entity -->
    <rect x="50" y="350" width="220" height="160" rx="10" ry="10" class="entity" fill="url(#grad5)"/>
    <text x="160" y="375" class="title" text-anchor="middle">Vaccine</text>
    <line x1="50" y1="385" x2="270" y2="385" stroke="#ffffff" stroke-width="2"/>
    <text x="60" y="405" class="attr pk">vaccine_id (PK)</text>
    <text x="60" y="425" class="attr">vaccine_name</text>
    <text x="60" y="445" class="attr">vaccine_code</text>
    <text x="60" y="465" class="attr">protection_start_date</text>
    <text x="60" y="485" class="attr">protection_finish_date</text>
    <text x="60" y="505" class="attr fk">animal_id (FK)</text>
    
    <!-- AvailableDate Entity -->
    <rect x="690" y="350" width="220" height="140" rx="10" ry="10" class="entity" fill="url(#grad6)"/>
    <text x="800" y="375" class="title" text-anchor="middle">AvailableDate</text>
    <line x1="690" y1="385" x2="910" y2="385" stroke="#ffffff" stroke-width="2"/>
    <text x="700" y="405" class="attr pk">available_date_id (PK)</text>
    <text x="700" y="425" class="attr">available_date</text>
    <text x="700" y="445" class="attr">current_appointment_count</text>
    <text x="700" y="465" class="attr">daily_appointment_limit</text>
    <text x="700" y="485" class="attr fk">doctor_id (FK)</text>
    
    <!-- Relationships -->
    <path d="M270 130 L320 130 L320 245 L370 245" class="relation"/>
    <text x="280" y="125" class="multiplicity">1</text>
    <text x="360" y="240" class="multiplicity">0..*</text>
    
    <path d="M480 250 L480 300 L480 350" class="relation"/>
    <text x="490" y="270" class="multiplicity">1</text>
    <text x="490" y="340" class="multiplicity">0..*</text>
    
    <path d="M370 150 L320 150 L320 430 L270 430" class="relation"/>
    <text x="360" y="145" class="multiplicity">1</text>
    <text x="280" y="425" class="multiplicity">0..*</text>
    
    <path d="M590 420 L640 420 L640 130 L690 130" class="relation"/>
    <text x="600" y="415" class="multiplicity">0..*</text>
    <text x="680" y="125" class="multiplicity">1</text>
    
    <path d="M800 210 L800 280 L800 350" class="relation"/>
    <text x="810" y="230" class="multiplicity">1</text>
    <text x="810" y="340" class="multiplicity">0..*</text>
</svg>
eterinary-clinic-database-svg-enhanced.svg…]()


## Contact

Contact me for suggestions or improvements: [ceksioglu@hotmail.com]

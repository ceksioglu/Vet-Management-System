# Veteriner Yönetim Sistemi

## Mesut Hocaya Not:
Postman ile sürünmeyin diye projeye OpenAPI dökümantasyonunu entegre ettim. Buradan ulaşabilirsiniz:
(http://localhost:8081/swagger-ui/index.html#/)
Projeye daha sonra front end entegrasyonu yapacağım, ama bitmediği için bakmanızın bir anlamı yok.
Bu not kendini proje değerlendirildikten sonra imha edecektir, kolay gelsin.

## Proje Açıklaması

Veteriner Yönetim Sistemi, bir veteriner kliniğinin işlerini yönetmesine olanak tanıyan bir API'dir. Bu sistem sayesinde veteriner çalışanları, doktorları, müşterileri, hayvanları ve randevuları yönetebilir. Proje, Java Spring Boot kullanılarak geliştirilmiştir ve PostgreSQL veya MySQL veri tabanlarını kullanabilir.

## Özellikler

- Veteriner doktorlarını kaydetme, güncelleme, görüntüleme ve silme
- Doktorların müsait günlerini ekleme, güncelleme, görüntüleme ve silme
- Müşterileri kaydetme, güncelleme, görüntüleme ve silme
- Müşterilere ait hayvanları kaydetme, güncelleme, görüntüleme ve silme
- Hayvanlara uygulanmış aşıları kaydetme, güncelleme, görüntüleme ve silme
- Hayvanlar için randevu oluşturma, güncelleme, görüntüleme ve silme
- Randevuları tarih ve doktora göre filtreleme
- Randevuları tarih ve hayvana göre filtreleme

## Gereksinimler

- Java 17
- Maven
- PostgreSQL veya MySQL

## Kurulum

1. Bu repository'yi klonlayın:

    ```bash
    git clone https://github.com/yourusername/vet-management-system.git
    cd vet-management-system
    ```

2. Gerekli bağımlılıkları yükleyin:

    ```bash
    mvn clean install
    ```

3. `application.properties` dosyasını yapılandırın:

    `src/main/resources/application.properties` dosyasını açın ve veri tabanı bağlantı ayarlarını yapın.

    ```properties
    spring.datasource.url=jdbc:postgresql://localhost:5432/vet_management_db
    spring.datasource.username=yourusername
    spring.datasource.password=yourpassword
    spring.jpa.hibernate.ddl-auto=update
    ```

4. Uygulamayı başlatın:

    ```bash
    mvn spring-boot:run
    ```

## Kullanım

### API Endpoints

- **Doktorlar**
    - `GET /api/doctors`: Tüm doktorları getirir.
    - `GET /api/doctors/{id}`: Belirli bir doktora ait bilgileri getirir.
    - `POST /api/doctors`: Yeni bir doktor ekler.
    - `PUT /api/doctors/{id}`: Belirli bir doktorun bilgilerini günceller.
    - `DELETE /api/doctors/{id}`: Belirli bir doktoru siler.

- **Müşteriler**
    - `GET /api/customers`: Tüm müşterileri getirir.
    - `GET /api/customers/{id}`: Belirli bir müşteriye ait bilgileri getirir.
    - `POST /api/customers`: Yeni bir müşteri ekler.
    - `PUT /api/customers/{id}`: Belirli bir müşterinin bilgilerini günceller.
    - `DELETE /api/customers/{id}`: Belirli bir müşteriyi siler.

- **Hayvanlar**
    - `GET /api/animals`: Tüm hayvanları getirir.
    - `GET /api/animals/{id}`: Belirli bir hayvana ait bilgileri getirir.
    - `POST /api/animals`: Yeni bir hayvan ekler.
    - `PUT /api/animals/{id}`: Belirli bir hayvanın bilgilerini günceller.
    - `DELETE /api/animals/{id}`: Belirli bir hayvanı siler.

- **Aşılar**
    - `GET /api/vaccines`: Tüm aşıları getirir.
    - `GET /api/vaccines/{id}`: Belirli bir aşıya ait bilgileri getirir.
    - `POST /api/vaccines`: Yeni bir aşı ekler.
    - `PUT /api/vaccines/{id}`: Belirli bir aşının bilgilerini günceller.
    - `DELETE /api/vaccines/{id}`: Belirli bir aşıyı siler.

- **Randevular**
    - `GET /api/appointments`: Tüm randevuları getirir.
    - `GET /api/appointments/{id}`: Belirli bir randevuya ait bilgileri getirir.
    - `POST /api/appointments`: Yeni bir randevu ekler.
    - `PUT /api/appointments/{id}`: Belirli bir randevunun bilgilerini günceller.
    - `DELETE /api/appointments/{id}`: Belirli bir randevuyu siler.
    - `GET /api/appointments/by-doctor-and-date-range`: Belirli bir doktor ve tarih aralığı için randevuları getirir.
    - `GET /api/appointments/by-animal-and-date-range`: Belirli bir hayvan ve tarih aralığı için randevuları getirir.

- **Müsait Günler**
    - `GET /api/available-dates`: Tüm müsait günleri getirir.
    - `GET /api/available-dates/{id}`: Belirli bir müsait güne ait bilgileri getirir.
    - `POST /api/available-dates`: Yeni bir müsait gün ekler.
    - `PUT /api/available-dates/{id}`: Belirli bir müsait günün bilgilerini günceller.
    - `DELETE /api/available-dates/{id}`: Belirli bir müsait günü siler.

## Veri Tabanı

Veri tabanı yapısını ve ilişkilerini gösteren sınıf diyagramı aşağıda verilmiştir:

![Class Diagram](https://github.com/user-attachments/assets/ae30ad0f-60de-42a7-a3c3-bd27cfb82f4a)

## İletişim

Geri bildirimleriniz için lütfen iletişime geçin: [ceksioglu@hotmail.com]

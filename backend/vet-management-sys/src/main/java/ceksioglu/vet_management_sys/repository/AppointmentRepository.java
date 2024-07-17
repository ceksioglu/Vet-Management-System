package ceksioglu.vet_management_sys.repository;

import ceksioglu.vet_management_sys.entity.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

    /**
     * Belirli bir doktor ve tarih için mevcut olan randevuyu döndürür.
     *
     * @param doctorId Doktorun ID'si
     * @param appointmentDate Randevunun tarihi ve saati
     * @return Mevcut olan randevu nesnesi
     */
    boolean existsByDoctorIdAndAppointmentDate(Long doctorId, Date appointmentDate);

    /**
     * Belirli bir doktor ve tarih aralığı için mevcut olan randevuları döndürür.
     *
     * @param doctorId Doktorun ID'si
     * @param startDate Başlangıç tarihi
     * @param endDate Bitiş tarihi
     * @return Belirli bir tarih aralığında ve doktora ait randevuların listesi
     */
    List<Appointment> findByDoctorIdAndAppointmentDateBetween(Long doctorId, Date startDate, Date endDate);

    /**
     * Belirli bir hayvan ve tarih aralığı için mevcut olan randevuları döndürür.
     *
     * @param animalId Hayvanın ID'si
     * @param startDate Başlangıç tarihi
     * @param endDate Bitiş tarihi
     * @return Belirli bir tarih aralığında ve hayvana ait randevuların listesi
     */
    List<Appointment> findByAnimalIdAndAppointmentDateBetween(Long animalId, Date startDate, Date endDate);
}

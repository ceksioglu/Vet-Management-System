package ceksioglu.vet_management_sys.repository;

import ceksioglu.vet_management_sys.entity.AvailableDate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface AvailableDateRepository extends JpaRepository<AvailableDate, Long> {

    /**
     * Belirli bir doktor ve tarih için mevcut olan müsait günü kontrol eder.
     *
     * @param doctorId Doktorun ID'si
     * @param availableDate Müsait günün tarihi
     * @return Mevcut olan müsait gün
     */
    boolean existsByDoctorIdAndAvailableDate(Long doctorId, Date availableDate);

    /**
     * Belirli bir doktor ve tarih aralığı için mevcut olan müsait günleri döndürür.
     *
     * @param doctorId Doktorun ID'si
     * @param startDate Başlangıç tarihi
     * @param endDate Bitiş tarihi
     * @return Belirli bir tarih aralığında ve doktora ait müsait günlerin listesi
     */
    List<AvailableDate> findByDoctorIdAndAvailableDateBetween(Long doctorId, Date startDate, Date endDate);
}

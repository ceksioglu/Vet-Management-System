package ceksioglu.vet_management_sys.repository;

import ceksioglu.vet_management_sys.entity.AvailableDate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface AvailableDateRepository extends JpaRepository<AvailableDate, Long> {
    List<AvailableDate> findByDoctorId(Long doctorId);
    boolean existsByDoctorIdAndAvailableDate(Long doctorId, Date availableDate);
}
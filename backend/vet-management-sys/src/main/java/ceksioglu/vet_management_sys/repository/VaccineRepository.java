package ceksioglu.vet_management_sys.repository;

import ceksioglu.vet_management_sys.entity.Vaccine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * Repository interface for Vaccine entity.
 */
@Repository
public interface VaccineRepository extends JpaRepository<Vaccine, Long> {
    List<Vaccine> findByProtectionFinishDateBetween(Date startDate, Date endDate);
    List<Vaccine> findByAnimalId(Long animalId);
    boolean existsByAnimalIdAndNameAndCodeAndProtectionFinishDateAfter(Long animalId, String name, String code, Date date);
}
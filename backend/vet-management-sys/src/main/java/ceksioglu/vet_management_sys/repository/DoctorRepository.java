package ceksioglu.vet_management_sys.repository;

import ceksioglu.vet_management_sys.entity.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for Doctor entity.
 */
@Repository
public interface DoctorRepository extends JpaRepository<Doctor, Long> {
    boolean existsByMail(String mail);
}
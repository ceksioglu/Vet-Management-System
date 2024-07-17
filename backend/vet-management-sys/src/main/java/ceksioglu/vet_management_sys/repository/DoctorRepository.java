package ceksioglu.vet_management_sys.repository;

import ceksioglu.vet_management_sys.entity.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DoctorRepository extends JpaRepository<Doctor,Long> {
}

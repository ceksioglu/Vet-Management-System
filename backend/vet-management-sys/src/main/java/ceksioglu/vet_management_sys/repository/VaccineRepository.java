package ceksioglu.vet_management_sys.repository;

import ceksioglu.vet_management_sys.entity.Vaccine;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VaccineRepository extends JpaRepository<Vaccine,Long> {
}

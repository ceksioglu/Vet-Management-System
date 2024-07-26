package ceksioglu.vet_management_sys.repository;

import ceksioglu.vet_management_sys.entity.Animal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository interface for Animal entity.
 */
@Repository
public interface AnimalRepository extends JpaRepository<Animal, Long> {
    List<Animal> findByName(String name);
    List<Animal> findByCustomerId(Long customerId);
}

package ceksioglu.vet_management_sys.repository;

import ceksioglu.vet_management_sys.entity.Animal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AnimalRepository extends JpaRepository<Animal, Long> {
    // Özel sorgular buraya eklenebilir
}

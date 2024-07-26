package ceksioglu.vet_management_sys.repository;

import ceksioglu.vet_management_sys.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository interface for Customer entity.
 */
@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    boolean existsByPhone(String phone);
    List<Customer> findByNameContainingIgnoreCase(String name);
}

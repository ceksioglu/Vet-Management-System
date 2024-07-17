package ceksioglu.vet_management_sys.repository;

import ceksioglu.vet_management_sys.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer,Long> {
}

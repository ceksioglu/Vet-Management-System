package ceksioglu.vet_management_sys.service.abstracts;

import ceksioglu.vet_management_sys.dto.CustomerDTO;
import java.util.List;

public interface CustomerService {
    CustomerDTO saveCustomer(CustomerDTO customerDTO);
    CustomerDTO updateCustomer(Long id, CustomerDTO customerDTO);
    void deleteCustomer(Long id);
    CustomerDTO getCustomerById(Long id);
    List<CustomerDTO> getAllCustomers();
    List<CustomerDTO> getCustomersByName(String name);
}
package ceksioglu.vet_management_sys.service.concretes;

import ceksioglu.vet_management_sys.dto.CustomerDTO;
import ceksioglu.vet_management_sys.entity.Customer;
import ceksioglu.vet_management_sys.repository.CustomerRepository;
import ceksioglu.vet_management_sys.service.abstracts.CustomerService;
import ceksioglu.vet_management_sys.core.exception.ResourceNotFoundException;
import ceksioglu.vet_management_sys.core.exception.ResourceAlreadyExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Service implementation for managing customers.
 */
@Service
public class CustomerManager implements CustomerService {

    private final CustomerRepository customerRepository;

    /**
     * Constructor for CustomerManager.
     *
     * @param customerRepository the customer repository
     */
    @Autowired
    public CustomerManager(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    /**
     * Saves a customer.
     *
     * @param customerDTO the customer DTO
     * @return the saved customer DTO
     * @throws ResourceAlreadyExistsException if a customer with the same phone number already exists
     */
    @Override
    public CustomerDTO saveCustomer(CustomerDTO customerDTO) {
        if (customerRepository.existsByPhone(customerDTO.getPhone())) {
            throw new ResourceAlreadyExistsException("Customer with this phone number already exists");
        }
        Customer customer = convertToEntity(customerDTO);
        Customer savedCustomer = customerRepository.save(customer);
        return convertToDTO(savedCustomer);
    }

    /**
     * Updates a customer.
     *
     * @param id the customer ID
     * @param customerDTO the customer DTO
     * @return the updated customer DTO
     * @throws ResourceNotFoundException if the customer is not found
     * @throws ResourceAlreadyExistsException if a customer with the same phone number already exists
     */
    @Override
    public CustomerDTO updateCustomer(Long id, CustomerDTO customerDTO) {
        Customer existingCustomer = customerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found with id: " + id));

        if (!existingCustomer.getPhone().equals(customerDTO.getPhone()) &&
                customerRepository.existsByPhone(customerDTO.getPhone())) {
            throw new ResourceAlreadyExistsException("Customer with this phone number already exists");
        }

        existingCustomer.setName(customerDTO.getName());
        existingCustomer.setPhone(customerDTO.getPhone());
        existingCustomer.setMail(customerDTO.getMail());
        existingCustomer.setAddress(customerDTO.getAddress());
        existingCustomer.setCity(customerDTO.getCity());

        Customer updatedCustomer = customerRepository.save(existingCustomer);
        return convertToDTO(updatedCustomer);
    }

    /**
     * Deletes a customer by ID.
     *
     * @param id the customer ID
     * @throws ResourceNotFoundException if the customer is not found
     */
    @Override
    public void deleteCustomer(Long id) {
        if (!customerRepository.existsById(id)) {
            throw new ResourceNotFoundException("Customer not found with id: " + id);
        }
        customerRepository.deleteById(id);
    }

    /**
     * Gets a customer by ID.
     *
     * @param id the customer ID
     * @return the customer DTO
     * @throws ResourceNotFoundException if the customer is not found
     */
    @Override
    public CustomerDTO getCustomerById(Long id) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found with id: " + id));
        return convertToDTO(customer);
    }

    /**
     * Gets all customers.
     *
     * @return the list of customer DTOs
     */
    @Override
    public List<CustomerDTO> getAllCustomers() {
        return customerRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    /**
     * Gets customers by name.
     *
     * @param name the customer name
     * @return the list of customer DTOs
     */
    @Override
    public List<CustomerDTO> getCustomersByName(String name) {
        return customerRepository.findByNameContainingIgnoreCase(name).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    /**
     * Converts a customer entity to a DTO.
     *
     * @param customer the customer entity
     * @return the customer DTO
     */
    private CustomerDTO convertToDTO(Customer customer) {
        CustomerDTO dto = new CustomerDTO();
        dto.setId(customer.getId());
        dto.setName(customer.getName());
        dto.setPhone(customer.getPhone());
        dto.setMail(customer.getMail());
        dto.setAddress(customer.getAddress());
        dto.setCity(customer.getCity());
        return dto;
    }

    /**
     * Converts a customer DTO to an entity.
     *
     * @param dto the customer DTO
     * @return the customer entity
     */
    private Customer convertToEntity(CustomerDTO dto) {
        Customer customer = new Customer();
        customer.setName(dto.getName());
        customer.setPhone(dto.getPhone());
        customer.setMail(dto.getMail());
        customer.setAddress(dto.getAddress());
        customer.setCity(dto.getCity());
        return customer;
    }
}

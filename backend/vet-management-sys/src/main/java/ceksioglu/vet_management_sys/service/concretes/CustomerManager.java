package ceksioglu.vet_management_sys.service.concretes;

import ceksioglu.vet_management_sys.entity.Customer;
import ceksioglu.vet_management_sys.core.exception.ResourceNotFoundException;
import ceksioglu.vet_management_sys.repository.CustomerRepository;
import ceksioglu.vet_management_sys.service.abstracts.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * CustomerManager, CustomerService arayüzünü implement eden sınıftır.
 * Müşterilerle ilgili iş mantığını içerir.
 */
@Service
public class CustomerManager implements CustomerService {

    private final CustomerRepository customerRepository;

    @Autowired
    public CustomerManager(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    /**
     * Tüm müşterileri getirir.
     *
     * @return Tüm müşterilerin listesi
     */
    @Override
    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    /**
     * Belirli bir ID'ye sahip müşteriyi getirir.
     *
     * @param id Müşterinin ID'si
     * @return Müşteri nesnesi
     * @throws ResourceNotFoundException Belirtilen ID'ye sahip müşteri bulunamadığında fırlatılır
     */
    @Override
    public Customer getCustomerById(Long id) {
        return customerRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Bu ID'ye ait müşteri bulunamadı: " + id));
    }

    /**
     * Yeni bir müşteri oluşturur.
     *
     * @param customer Oluşturulacak müşteri nesnesi
     * @return Oluşturulan müşteri nesnesi
     */
    @Override
    public Customer createCustomer(Customer customer) {
        return customerRepository.save(customer);
    }

    /**
     * Belirli bir ID'ye sahip müşteriyi günceller.
     *
     * @param id Güncellenecek müşterinin ID'si
     * @param updatedCustomer Güncellenmiş müşteri nesnesi
     * @return Güncellenmiş müşteri nesnesi
     * @throws ResourceNotFoundException Belirtilen ID'ye sahip müşteri bulunamadığında fırlatılır
     */
    @Override
    public Customer updateCustomer(Long id, Customer updatedCustomer) {
        return customerRepository.findById(id).map(customer -> {
            customer.setName(updatedCustomer.getName());
            customer.setPhone(updatedCustomer.getPhone());
            customer.setMail(updatedCustomer.getMail());
            customer.setAddress(updatedCustomer.getAddress());
            customer.setCity(updatedCustomer.getCity());
            return customerRepository.save(customer);
        }).orElseThrow(() -> new ResourceNotFoundException("Bu ID'ye ait müşteri bulunamadı: " + id));
    }

    /**
     * Belirli bir ID'ye sahip müşteriyi siler.
     *
     * @param id Silinecek müşterinin ID'si
     * @throws ResourceNotFoundException Belirtilen ID'ye sahip müşteri bulunamadığında fırlatılır
     */
    @Override
    public void deleteCustomer(Long id) {
        if (!customerRepository.existsById(id)) {
            throw new ResourceNotFoundException("Bu ID'ye ait müşteri bulunamadı: " + id);
        }
        customerRepository.deleteById(id);
    }
}

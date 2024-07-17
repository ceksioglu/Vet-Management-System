package ceksioglu.vet_management_sys.controller;

import ceksioglu.vet_management_sys.entity.Customer;
import ceksioglu.vet_management_sys.service.concretes.CustomerManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * CustomerController, müşterilerle ilgili API endpointlerini sağlar.
 */
@RestController
@RequestMapping("/api/customers")
public class CustomerController {

    private final CustomerManager customerManager;

    @Autowired
    public CustomerController(CustomerManager customerManager) {
        this.customerManager = customerManager;
    }

    /**
     * Tüm müşterileri getirir.
     *
     * @return Tüm müşterilerin listesi
     */
    @GetMapping
    public List<Customer> getAllCustomers() {
        return customerManager.getAllCustomers();
    }

    /**
     * Belirli bir ID'ye sahip müşteriyi getirir.
     *
     * @param id Müşterinin ID'si
     * @return Müşteri nesnesi
     */
    @GetMapping("/{id}")
    public Customer getCustomerById(@PathVariable Long id) {
        return customerManager.getCustomerById(id);
    }

    /**
     * Yeni bir müşteri oluşturur.
     *
     * @param customer Oluşturulacak müşteri nesnesi
     * @return Oluşturulan müşteri nesnesi
     */
    @PostMapping
    public Customer createCustomer(@RequestBody Customer customer) {
        return customerManager.createCustomer(customer);
    }

    /**
     * Belirli bir ID'ye sahip müşteriyi günceller.
     *
     * @param id Güncellenecek müşterinin ID'si
     * @param customer Güncellenmiş müşteri nesnesi
     * @return Güncellenmiş müşteri nesnesi
     */
    @PutMapping("/{id}")
    public Customer updateCustomer(@PathVariable Long id, @RequestBody Customer customer) {
        return customerManager.updateCustomer(id, customer);
    }

    /**
     * Belirli bir ID'ye sahip müşteriyi siler.
     *
     * @param id Silinecek müşterinin ID'si
     * @return NoContent durumunda HTTP yanıtı
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCustomer(@PathVariable Long id) {
        customerManager.deleteCustomer(id);
        return ResponseEntity.noContent().build();
    }
}

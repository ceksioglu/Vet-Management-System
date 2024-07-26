package ceksioglu.vet_management_sys.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Data Transfer Object for Customer entity.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerDTO {

    private Long id;
    private String name;
    private String phone;
    private String mail;
    private String address;
    private String city;

    public CustomerDTO(Long customerId) {
    }
}

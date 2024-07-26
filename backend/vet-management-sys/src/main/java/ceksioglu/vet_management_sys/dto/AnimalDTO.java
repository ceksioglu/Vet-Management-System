package ceksioglu.vet_management_sys.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * Data Transfer Object for Animal entity.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AnimalDTO {

    private Long id;
    private String name;
    private String species;
    private String breed;
    private String gender;
    private String color;
    private Date dateOfBirth;
    private Long customerId;
}

package ceksioglu.vet_management_sys.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * Data Transfer Object for Vaccine entity.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class VaccineDTO {

    private Long id;
    private String name;
    private String code;
    private Date protectionStartDate;
    private Date protectionFinishDate;
    private Long animalId;
}

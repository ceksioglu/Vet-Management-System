package ceksioglu.vet_management_sys.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * Data Transfer Object for Appointment entity.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AppointmentDTO {

    private Long id;
    private Date appointmentDate;
    private Long doctorId;
    private Long animalId;
}

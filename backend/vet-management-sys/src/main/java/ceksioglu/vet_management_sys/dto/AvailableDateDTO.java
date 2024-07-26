package ceksioglu.vet_management_sys.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * Data Transfer Object for AvailableDate entity.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AvailableDateDTO {

    private Long id;
    private Date availableDate;
    private Long DoctorId;
    private Integer dailyAppointmentLimit;
    private Integer currentAppointmentCount;
}

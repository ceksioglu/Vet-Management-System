package ceksioglu.vet_management_sys.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Table(name = "available_dates")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AvailableDate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "available_date_id")
    private Long id;

    @Temporal(TemporalType.DATE)
    @Column(name = "available_date", nullable = false)
    private Date availableDate;

    //Bir doktorun birden fazla müsait zamanı olabilir.
    @ManyToOne
    @JoinColumn(name = "doctor_id", nullable = false)
    @JsonIgnore
    private Doctor doctor;

    @Column(name = "daily_appointment_limit")
    private Integer dailyAppointmentLimit = 10;

    @Column(name = "current_appointment_count")
    private Integer currentAppointmentCount = 0;
}

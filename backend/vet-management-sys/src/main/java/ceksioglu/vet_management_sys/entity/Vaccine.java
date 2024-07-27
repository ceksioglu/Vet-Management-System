package ceksioglu.vet_management_sys.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Table(name = "vaccines")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Vaccine {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "vaccine_id")
    private Long id;

    @Column(name = "vaccine_name", nullable = false)
    private String name;

    @Column(name = "vaccine_code", nullable = false)
    private String code;

    @Temporal(TemporalType.DATE)
    @Column(name = "protection_start_date", nullable = false)
    private Date protectionStartDate;

    @Temporal(TemporalType.DATE)
    @Column(name = "protection_finish_date", nullable = false)
    private Date protectionFinishDate;

    //Bir hayvanın birden fazla aşısı olabilir.
    @ManyToOne
    @JoinColumn(name = "animal_id", nullable = false)
    @JsonIgnore
    private Animal animal;
}

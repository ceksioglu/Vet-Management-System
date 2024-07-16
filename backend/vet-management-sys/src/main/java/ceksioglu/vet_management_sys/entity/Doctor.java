package ceksioglu.vet_management_sys.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "doctors")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Doctor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "doctor_id")
    private Long id;

    @Column(name = "doctor_name", nullable = false)
    private String name;

    @Column(name = "doctor_phone", nullable = false)
    private String phone;

    @Column(name = "doctor_mail", nullable = false)
    private String mail;

    @Column(name = "doctor_address", nullable = false)
    private String address;

    @Column(name = "doctor_city", nullable = false)
    private String city;

    //Bir doktorun birden fazla müsait olduğu tarih mevcut olabilir.
    @OneToMany(mappedBy = "doctor", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<AvailableDate> availableDates;

    //Bir doktorun birden fazla randevusu olabilir.
    @OneToMany(mappedBy = "doctor", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Appointment> appointments;
}

package ceksioglu.vet_management_sys.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "animals")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Animal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "animal_id")
    private Long id;

    @Column(name = "animal_name", nullable = false)
    private String name;

    @Column(name = "animal_species", nullable = false)
    private String species;

    @Column(name = "animal_breed", nullable = false)
    private String breed;

    @Enumerated(EnumType.STRING)
    @Column(name = "animal_gender", nullable = false)
    private Gender gender;

    @Column(name = "animal_color", nullable = false)
    private String color;

    @Temporal(TemporalType.DATE)
    @Column(name = "animal_date_of_birth", nullable = false)
    private Date dateOfBirth;

    //Bir müşterinin birden fazla hayvanı olabilir.
    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    @JsonIgnore
    private Customer customer;

    //Bir hayvanın birden fazla aşısı olabilir.
    @OneToMany(mappedBy = "animal", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Vaccine> vaccines;

    public enum Gender {
        MALE,
        FEMALE
    }
}

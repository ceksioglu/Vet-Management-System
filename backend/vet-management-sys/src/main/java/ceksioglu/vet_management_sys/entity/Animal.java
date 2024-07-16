package ceksioglu.vet_management_sys.entity;

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

    @Column(name = "animal_name")
    private String name;

    @Column(name = "animal_species")
    private String species;

    @Column(name = "animal_breed")
    private String breed;

    @Enumerated(EnumType.STRING)
    @Column(name = "animal_gender")
    private Gender gender;

    @Column(name = "animal_color")
    private String color;

    @Temporal(TemporalType.DATE)
    @Column(name = "animal_date_of_birth")
    private Date dateOfBirth;

    // Her hayvanın bir sahibi olabilir.
    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    //Bir hayvana birden fazla aşı uygulanabilir.
    @OneToMany(mappedBy = "animal", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Vaccine> vaccines;

    public enum Gender {
        MALE,
        FEMALE
    }
}

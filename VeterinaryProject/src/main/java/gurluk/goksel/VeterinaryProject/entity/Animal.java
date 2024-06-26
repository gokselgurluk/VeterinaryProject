package gurluk.goksel.VeterinaryProject.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "animals")
public class  Animal { //Animal Entitylerimizin bulundugu sınıfımız
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "animal_id")
    private Long id;

    @Column(name = "animal_name", nullable = false)
    private String name;

    @Column(name = "animal_species", nullable = false)
    private String species;

    @Column(name = "breed", length = 255)
    private String breed;

    @Column(name = "animal_colour", length = 255)
    private String colour;

    //@Temporal(TemporalType.DATE)
    @Column(name = "animal_date_of_birth")
    private LocalDate dateOfBirth;

    @Enumerated(EnumType.STRING)
    @Column(name = "animal_gender")
    private Gender gender;
    public enum Gender {
        erkek,
        dişi,
    }
// 9 Entity’ler arasındaki ilişkiler (@OneToMany, @ManyToOne, @ManyToMany vs.) doğru bir şekilde tanımlanmış mı? (4 Puan)


    @ManyToOne
    @JoinColumn(name = "customer_id")// Customer entity'sindeki id ile ilişkilendirir
    private Customer customer;

    @OneToMany(mappedBy = "animal", cascade = CascadeType.REMOVE)
    private List<Vaccine> vaccines;

    @OneToMany(mappedBy = "animal",fetch = FetchType.LAZY,cascade = CascadeType.REMOVE)
    private List<Appointment> appointment;


    @Override
    public String toString() {
        return "Animal{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", species='" + species + '\'' +
                ", breed='" + breed + '\'' +
                ", colour='" + colour + '\'' +
                ", dateOfBirth=" + dateOfBirth +
                ", gender=" + gender +
                '}';
    }


}
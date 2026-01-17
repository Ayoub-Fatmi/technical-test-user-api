package com.ayoub.technicaltestuserapi.metier.persistence;

import com.ayoub.technicaltestuserapi.metier.model.Gender;
import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(
        name = "users",
        uniqueConstraints = @UniqueConstraint(name = "uk_users_username", columnNames = "username")
)
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 30)
    private String username;

    @Column(nullable = false)
    private LocalDate birthdate;

    @Column(nullable = false, length = 56)
    private String country;

    @Column
    private String phone;

    @Enumerated(EnumType.STRING)
    @Column
    private Gender gender;

    protected UserEntity() {
    }

    public UserEntity(String username, LocalDate birthdate, String country, String phone, Gender gender) {
        this.username = username;
        this.birthdate = birthdate;
        this.country = country;
        this.phone = phone;
        this.gender = gender;
    }

    public String getUsername() { return username; }
    public LocalDate getBirthdate() { return birthdate; }
    public String getCountry() { return country; }
    public String getPhone() { return phone; }
    public Gender getGender() { return gender; }

}

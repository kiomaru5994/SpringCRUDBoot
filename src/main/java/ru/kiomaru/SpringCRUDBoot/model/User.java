package ru.kiomaru.SpringCRUDBoot.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "users")
public class User {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotEmpty(message = "Имя не должно быть пустым")
    @Size(min = 2, max = 30, message = "Имя должно содержать от 2 до 30 символов")
    @Column(name = "first_name")
    private String firstName;

    @NotEmpty(message = "Фамилия не должна быть пустой")
    @Column(name = "last_name")
    private String lastName;

    @Column(name = "age")
    @Min(value = 0, message = "Возраст не может быть меньше нуля")
    private int age;

    @NotEmpty(message = "Login не должен быть пустым")
    @Size(min = 5, max = 15, message = "Никнейм должен содержать от 5 до 15 символов")
    @Column(name = "user_login")
    private String userLogin;

    @NotEmpty(message = "Email не должен быть пустым")
    @Email(message = "Email некорректный")
    @Column(name = "email")
    private String email;

    public User() {

    }

    public int getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getUserLogin() {
        return userLogin;
    }

    public void setUserLogin(String userLogin) {
        this.userLogin = userLogin;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
package ru.kiomaru.SpringCRUDBoot.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users_table")
public class User {

    @Id
    @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @NotEmpty(message = "Имя не должно быть пустым")
    @Column(name = "first_name")
    private String firstName;

    @NotEmpty(message = "Фамилия не должна быть пустой")
    @Column(name = "last_name")
    private String lastName;

    @NotEmpty(message = "Отчество не должно быть пустым")
    @Column(name = "middle_name")
    private String middleName;

    @NotEmpty(message = "Необходимо выбрать пол")
    @Column(name = "gender")
    private String gender;

    @NotEmpty(message = "Введите дату рождения")
    @Column(name = "date_of_birth")
    private LocalDate dateOfBirth;

    @NotEmpty(message = "Укажите Email")
    @Email(message = "Email некорректный")
    @Column(name = "email")
    private String email;

    @NotEmpty(message = "Укажите номер телефона")
    @Column(name = "phone_number")
    private String phoneNumber;

    @NotEmpty(message = "Укажите адрес")
    @Column(name = "address")
    private String address; // country, city, street, home

    @NotEmpty(message = "Укажите ваше гражданство")
    @Column(name = "citizenship")
    private String citizenship;

    @NotEmpty(message = "Введите свой телеграм аккаунт (не номер телефона)")
    @Column(name = "telegram_account")
    private String telegramAccount;

    @NotEmpty(message = "Login не должен быть пустым")
    @Size(min = 5, max = 15, message = "Никнейм должен содержать от 5 до 15 символов")
    @Column(name = "user_name")
    private String userName;

    @NotEmpty(message = "Введите пароль")
    @Size(min = 6, max = 20, message = "Пароль должен содержать от 6 до 20 символов")
    @Column(name = "password")
    private String password;

    @NotEmpty
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Role> roles = new HashSet<>();

    public User() {

    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
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

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public @NotEmpty(message = "Укажите ваше гражданство") String getCitizenship() {
        return citizenship;
    }

    public void setCitizenship(String citizenship) {
        this.citizenship = citizenship;
    }

    public String getTelegramAccount() {
        return telegramAccount;
    }

    public void setTelegramAccount(String telegramAccount) {
        this.telegramAccount = telegramAccount;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }
}
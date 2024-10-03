package ru.kiomaru.SpringCRUDBoot.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "users_table")
public class User implements UserDetails {

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

    @NotEmpty(message = "Укажите Email")
    @Email(message = "Email некорректный")
    @Column(name = "email", unique = true)
    private String email;

    @NotEmpty(message = "Укажите номер телефона")
    @Pattern(regexp = "^\\+7(\\d{10}|-\\d{3}-\\d{3}-\\d{2}-\\d{2})",
            message = "Номер телефона в формате +7-123-456-78-90 или +71234567890")
    @Column(name = "phone_number")
    private String phoneNumber;

    @NotEmpty(message = "Укажите адрес")
    @Pattern(regexp = "^([A-Za-zА-Яа-яЁё]+(?:\\s[A-Za-zА-Яа-яЁё]+)*)," +
            "\\s([A-Za-zА-Яа-яЁё]+(?:\\s[A-Za-zА-Яа-яЁё]+)*)," +
            "\\s([A-Za-zА-Яа-яЁё]+(?:\\s[A-Za-zА-Яа-яЁё0-9]+)*)," +
            "\\s(\\d+.*)") // country, city, street, home
    @Column(name = "address")
    private String address;

    @NotEmpty(message = "Укажите ваше гражданство")
    @Column(name = "citizenship")
    private String citizenship;

    @NotEmpty(message = "Введите свой телеграм аккаунт (не номер телефона)")
    @Column(name = "telegram_account", unique = true)
    private String telegramAccount;


    @NotEmpty(message = "Login не должен быть пустым")
    @Size(min = 5, max = 15, message = "Никнейм должен содержать от 5 до 15 символов")
    @Column(name = "user_name", unique = true)
    @Pattern(regexp = "^[a-zA-Z0-9]+$")
    private String userName;

    @NotEmpty(message = "Введите пароль")
    @Column(name = "password")
    private String password;

    @NotEmpty
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private List<Role> roles = new ArrayList<>();

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

    public String getCitizenship() {
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

    @Override
    public String getUsername() {
        return getUserName();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return getRoles();
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }
}
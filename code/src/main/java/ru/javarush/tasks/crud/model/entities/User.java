package ru.javarush.tasks.crud.model.entities;

import org.hibernate.annotations.Type;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Calendar;

/**
 * Created by eGarmin
 */
@Entity
@Table(catalog = "test") // catalog задает имя БД/схемы в MySQL
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(length = 25)
    private String name;

    @Column
    private Integer age;

    @Column(name="isAdmin", nullable = false)
    @Type(type = "org.hibernate.type.NumericBooleanType")
    private Boolean admin;

    @Column(nullable = false, insertable = false, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "dd.MM.yyyy HH:mm:ss") // Когда с формы прилетит строка-дата она будет парситься в эту модель по указанному паттерну
    private Calendar createdDate;

    /*
     * Конструкторы
     */
    public User() {
        // Конструктор по умолчанию
    }

    /*
     * Геттеры и сеттеры
     */
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }
    public void setAge(Integer age) {
        this.age = age;
    }

    // public Boolean isAdmin() { - префикс is недопустим для геттера по Boolean, только для boolean
    public Boolean getAdmin() {
        return admin;
    }
    public void setAdmin(Boolean admin) {
        this.admin = admin;
    }

    public Calendar getCreatedDate() {
        return createdDate;
    }
    public void setCreatedDate(Calendar createdDate) {
        this.createdDate = createdDate;
    }

}
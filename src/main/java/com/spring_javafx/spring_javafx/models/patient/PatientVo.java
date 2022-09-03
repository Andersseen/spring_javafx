package com.spring_javafx.spring_javafx.models.patient;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import javax.persistence.*;
import java.sql.Date;

@ToString
@Entity
@Table(name = "customers")
public class PatientVo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name= "id") @Getter @Setter
    private int id;
    @Column(name= "name") @Getter @Setter
    private String name;
    @Column(name= "lastName") @Getter @Setter
    private String lastName;
    @Column(name= "sex") @Getter @Setter
    private String sex;
    @Column(name= "birthday") @Getter @Setter
    private Date birthday;
    @Column(name= "phone") @Getter @Setter
    private String phone;
    @Column(name= "email") @Getter @Setter
    private String email;
    @Column(name= "note") @Getter @Setter
    private String note;
    @Column(name= "date") @Getter @Setter
    private Date date;
}

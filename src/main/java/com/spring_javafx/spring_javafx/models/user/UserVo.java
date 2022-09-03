package com.spring_javafx.spring_javafx.models.user;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import javax.persistence.*;
import java.io.Serializable;

@ToString
@Entity
@Table(name = "users")
public class UserVo implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name= "id") @Getter @Setter
    private int id;
    @Column(name= "name") @Getter @Setter
    private String name;
    @Column(name= "last_name") @Getter @Setter
    private String last_name;
    @Column(name= "username") @Getter @Setter
    private String username;
    @Column(name= "password") @Getter @Setter
    private String password;
    @Column(name= "status") @Getter @Setter
    private String status;

}

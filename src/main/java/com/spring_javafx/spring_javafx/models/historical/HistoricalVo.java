package com.spring_javafx.spring_javafx.models.historical;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import javax.persistence.*;

@ToString
@Entity
@Table(name = "historicals")
public class HistoricalVo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name= "id") @Getter @Setter
    private int id;
    @Column(name= "id_customer") @Getter @Setter
    private int idCustomer;
    @Column(name= "name") @Getter @Setter
    private String name;
    @Column(name= "lastName") @Getter @Setter
    private String lastName;
    @Column(name= "historical") @Getter @Setter
    private String historical;
}

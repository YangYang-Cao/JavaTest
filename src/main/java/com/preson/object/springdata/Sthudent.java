package com.preson.object.springdata;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "shudent")
@ToString
public class Sthudent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id",nullable = false)
    private Integer id;


    @Column(name = "class_name_id",insertable = false ,updatable = false )
    private Integer classNameId;

    @Column(name = "name")
    private String name;

    @Column(name = "number")
    private Integer number;

    @Column(name = "sex")
    private Integer sex;

}

//@ManyToOne(targetEntity = Clsasss.class, fetch = FetchType.EAGER)
// @JoinColumn(name="class_name_id", referencedColumnName = "id")
// private  Clsasss clsasss;

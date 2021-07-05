package com.preson.object.springdata;


import lombok.Data;
import lombok.ToString;
import org.springframework.data.annotation.Id;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author admin
 */
@Entity
@Data
@Table(name = "class_user")
@ToString
public class Clsasss {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "class_name")
    private String ClassNmae;

    @Column(name = "level")
    private Integer level;

//    @OneToMany(mappedBy = "clsasss",fetch = FetchType.EAGER)
//    private List<Sthudent> sthudent =new ArrayList<>();

    @OneToMany(targetEntity = Sthudent.class, fetch = FetchType.EAGER)
    @JoinColumn(name = "class_name_id",referencedColumnName = "id")
    //private Set<Sthudent> hashSet = new HashSet<>();

    private List<Sthudent> list = new ArrayList<>();
}


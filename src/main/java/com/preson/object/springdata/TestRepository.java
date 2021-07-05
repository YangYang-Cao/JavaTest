package com.preson.object.springdata;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TestRepository extends JpaRepository<Clsasss, Integer> {


    @Query(name = "SELECT\n" +
            "\t* \n" +
            "FROM\n" +
            "\tclass_user\n" +
            "\tLEFT JOIN shudent ON class_user.id = shudent.class_name_id \n" +
            "WHERE\n" +
            "\tclass_user.id =:id ", nativeQuery = true)
    List<Clsasss> queryByid(@Param("id") Integer id);

}

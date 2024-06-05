package com.riwi.pruebaDesempenoSpringBoot.domain.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.riwi.pruebaDesempenoSpringBoot.domain.entities.Student;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long>{
    Page<Student> findByNameAndActive(String name, PageRequest pagination, Boolean active);
}

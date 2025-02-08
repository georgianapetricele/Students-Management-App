package org.example.mpp_backend.repository;

import org.example.mpp_backend.model.Student;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long>, CrudRepository<Student, Long>{
    Page<Student> findStudentByUser_UserId(Long userId, Pageable pageable);
}
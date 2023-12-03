package com.javasample.executor.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.javasample.executor.api.entity.Student;

public interface StudentRepository extends JpaRepository<Student,Integer> {
}

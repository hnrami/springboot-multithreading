package com.javasample.executor.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.javasample.executor.api.entity.Employee;

public interface EmployeeRepository extends JpaRepository<Employee,Integer> {
}

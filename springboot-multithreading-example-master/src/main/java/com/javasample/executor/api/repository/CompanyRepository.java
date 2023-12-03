package com.javasample.executor.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.javasample.executor.api.entity.Company;

public interface CompanyRepository extends JpaRepository<Company,Integer> {
}

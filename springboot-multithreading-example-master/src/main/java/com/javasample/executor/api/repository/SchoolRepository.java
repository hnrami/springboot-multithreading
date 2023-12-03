package com.javasample.executor.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.javasample.executor.api.entity.School;

public interface SchoolRepository extends JpaRepository<School,Integer> {
}

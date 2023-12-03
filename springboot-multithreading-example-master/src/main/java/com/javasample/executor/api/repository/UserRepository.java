package com.javasample.executor.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.javasample.executor.api.entity.User;

public interface UserRepository extends JpaRepository<User,Integer> {
}

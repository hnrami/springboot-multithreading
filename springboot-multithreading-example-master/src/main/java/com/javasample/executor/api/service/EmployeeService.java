package com.javasample.executor.api.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.javasample.executor.api.entity.Employee;
import com.javasample.executor.api.repository.EmployeeRepository;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository repository;

    Object target;
    Logger logger = LoggerFactory.getLogger(EmployeeService.class);

    @Async
    public CompletableFuture<List<Employee>> saveEmployees(MultipartFile file) throws Exception {
        long start = System.currentTimeMillis();
        List<Employee> employees = parseCSVFile(file);
        logger.info("saving list of employees of size {}", employees.size(), "" + Thread.currentThread().getName());
        employees = repository.saveAll(employees);
        long end = System.currentTimeMillis();
        logger.info("Total time {}", (end - start));
        return CompletableFuture.completedFuture(employees);
    }
    @Async
    public CompletableFuture<List<Employee>> findAllEmployees(){
        logger.info("get list of employee by "+Thread.currentThread().getName());
        List<Employee> employees=repository.findAll();
        System.out.println("Number of Records"+employees.size());
        return CompletableFuture.completedFuture(employees);
    }

    private List<Employee> parseCSVFile(final MultipartFile file) throws Exception {
        final List<Employee> employees = new ArrayList<>();
        try {
            try (final BufferedReader br = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
                String line;
                while ((line = br.readLine()) != null) {
                    final String[] data = line.split(",");
                    final Employee employee = new Employee();
                    employee.setName(data[0]);
                    employee.setEmail(data[1]);
                    employee.setGender(data[2]);
                    employees.add(employee);
                }
                return employees;
            }
        } catch (final IOException e) {
            logger.error("Failed to parse CSV file {}", e);
            throw new Exception("Failed to parse CSV file {}", e);
        }
    }
}

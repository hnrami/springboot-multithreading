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

import com.javasample.executor.api.entity.Company;
import com.javasample.executor.api.repository.CompanyRepository;

@Service
public class CompanyService {

    @Autowired
    private CompanyRepository repository;

    Object target;
    Logger logger = LoggerFactory.getLogger(CompanyService.class);

    @Async
    public CompletableFuture<List<Company>> saveCompanys(MultipartFile file) throws Exception {
        long start = System.currentTimeMillis();
        List<Company> companys = parseCSVFile(file);
        logger.info("saving list of Company of size {}", companys.size(), "" + Thread.currentThread().getName());
        companys = repository.saveAll(companys);
        long end = System.currentTimeMillis();
        logger.info("Total time {}", (end - start));
        return CompletableFuture.completedFuture(companys);
    }
    @Async
    public CompletableFuture<List<Company>> findAllCompanys(){
        logger.info("get list of company by "+Thread.currentThread().getName());
        List<Company> companys=repository.findAll();
        System.out.println("Number of Records"+companys.size());
        return CompletableFuture.completedFuture(companys);
    }

    private List<Company> parseCSVFile(final MultipartFile file) throws Exception {
        final List<Company> companys = new ArrayList<>();
        try {
            try (final BufferedReader br = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
                String line;
                while ((line = br.readLine()) != null) {
                    final String[] data = line.split(",");
                    final Company company = new Company();
                    company.setName(data[0]);
                    company.setEmail(data[1]);
                    company.setSize(data[2]);
                    companys.add(company);
                }
                return companys;
            }
        } catch (final IOException e) {
            logger.error("Failed to parse CSV file {}", e);
            throw new Exception("Failed to parse CSV file {}", e);
        }
    }
}

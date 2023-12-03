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

import com.javasample.executor.api.entity.School;
import com.javasample.executor.api.repository.SchoolRepository;

@Service
public class SchoolService {

    @Autowired
    private SchoolRepository repository;

    Object target;
    Logger logger = LoggerFactory.getLogger(SchoolService.class);

    @Async
    public CompletableFuture<List<School>> saveSchools(MultipartFile file) throws Exception {
        long start = System.currentTimeMillis();
        List<School> schools = parseCSVFile(file);
        logger.info("saving list of schools of size {}", schools.size(), "" + Thread.currentThread().getName());
        schools = repository.saveAll(schools);
        long end = System.currentTimeMillis();
        logger.info("Total time {}", (end - start));
        return CompletableFuture.completedFuture(schools);
    }
    @Async
    public CompletableFuture<List<School>> findAllSchools(){
        logger.info("get list of school by "+Thread.currentThread().getName());
        List<School> schools=repository.findAll();
        System.out.println("Number of Records"+schools.size());
        return CompletableFuture.completedFuture(schools);
    }

    private List<School> parseCSVFile(final MultipartFile file) throws Exception {
        final List<School> schools = new ArrayList<>();
        try {
            try (final BufferedReader br = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
                String line;
                while ((line = br.readLine()) != null) {
                    final String[] data = line.split(",");
                    final School school = new School();
                    school.setName(data[0]);
                    school.setEmail(data[1]);
                    school.setSize(data[2]);
                    schools.add(school);
                }
                return schools;
            }
        } catch (final IOException e) {
            logger.error("Failed to parse CSV file {}", e);
            throw new Exception("Failed to parse CSV file {}", e);
        }
    }
}

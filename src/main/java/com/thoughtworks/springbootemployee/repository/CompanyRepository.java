package com.thoughtworks.springbootemployee.repository;

import com.thoughtworks.springbootemployee.entity.Company;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CompanyRepository extends MongoRepository<Company, String> {
}

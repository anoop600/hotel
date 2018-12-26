package com.example.databaseProject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.example.databaseProject.model.Customer;

@RepositoryRestResource
public interface CustomerRepository extends JpaRepository<Customer, String>{

}

package com.example.databaseProject.service.serviceImpl;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.databaseProject.model.Customer;
import com.example.databaseProject.repository.CustomerRepository;

@Service
public class CustomerServiceImpl {
	@Autowired
	CustomerRepository customerRepo;

	// create entry for customer
	public Customer addCustomer(@Valid Customer customer) {
		return customerRepo.save(customer);
	}

	// read entry from customer database using id
	public Customer getCustomerById(String customerId) {
		return customerRepo.findOne(customerId);
	}

	// Retrieve all the entries of the database
	public List<Customer> getAllCustomers() {
		return customerRepo.findAll();
	}

	// delete customer from the database
	public void deleteHotel(String customerId) {

		customerRepo.delete(customerRepo.findOne(customerId));

	}
}

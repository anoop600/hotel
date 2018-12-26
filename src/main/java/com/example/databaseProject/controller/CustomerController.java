package com.example.databaseProject.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.databaseProject.model.Customer;
import com.example.databaseProject.service.serviceImpl.CustomerServiceImpl;

@RestController
@CrossOrigin("*")
@RequestMapping("/customer")
public class CustomerController {

	@Autowired
	CustomerServiceImpl customerService;

	// checking if customer exists or not
	@PostMapping(value = "/verify", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> verfiyLoggin(@RequestBody String logginParam[]) {
		Customer customer = (Customer) customerService.getAllCustomers().stream()
				.filter(customer1 -> customer1.getEmail().equals(logginParam[0]))
				.filter(customer2 -> customer2.getPassword().equals(logginParam[1])).findFirst().get();
		System.out.println(customer);
		if (customer != null)
			return new ResponseEntity<>("Valid customer details", HttpStatus.OK);
		else
			return new ResponseEntity<>("Invalid CUstomer details", HttpStatus.BAD_REQUEST);

	}

	// request for making entry in table
	@PostMapping(value = "/addCustomer", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> addCustomer(@RequestBody Customer customer) {
		customer.setCustomerId();
		if (customerService.addCustomer(customer) != null) {
			return new ResponseEntity<String>("inserted successfully",HttpStatus.OK);
		} else {
			return new ResponseEntity<String>("insertion unsuccessfull",HttpStatus.BAD_REQUEST);
		}
		// response entity need to return from here
	}
	// getcustomer by email id and password
	@PostMapping(value="/getcustomer", produces = MediaType.APPLICATION_JSON_VALUE)
	public Customer getCustomerWithEP(@RequestBody String logginParam[]) {
		Customer customer = (Customer) customerService.getAllCustomers().stream()
				.filter(customer1 -> customer1.getEmail().equals(logginParam[0]))
				.filter(customer2 -> customer2.getPassword().equals(logginParam[1])).findFirst().get();
		return customer;
	}

	// get customer entry by id
	@GetMapping(value = "/{customerId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public Customer getCustomerById(@PathVariable(value = "customerId") String customerId) {
		Customer customer = customerService.getCustomerById(customerId);
		if (customer != null) {
			return customer;
		} else {
			// add response entity
			return null;
		}
	}

	// get all customers
	@GetMapping(value = "/allCustomers", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<Customer> getAllCustomers() {
		return customerService.getAllCustomers();
	}

	// get id of customer to delete the customer from the table
	@DeleteMapping(value = "/{customerId}")
	public void deleteHotelById(@PathVariable String customerId) {
		customerService.deleteHotel(customerId);

	}

}

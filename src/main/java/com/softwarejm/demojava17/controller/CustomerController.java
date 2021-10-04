package com.softwarejm.demojava17.controller;

import com.softwarejm.demojava17.model.Customer;
import com.softwarejm.demojava17.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.Collection;

import static com.softwarejm.demojava17.config.Paths.API_PATH;
import static com.softwarejm.demojava17.config.Paths.CUSTOMERS_PATH;

@RequestMapping(API_PATH + CUSTOMERS_PATH)
@RestController
public class CustomerController {

    private final CustomerService customerService;

    @Autowired
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Collection<Customer>> getAll() {
        var customers = customerService.findAll();
        return ResponseEntity.ok(customers);
    }

    @GetMapping(
            value = "/{customerId}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Customer> getById(@PathVariable Integer customerId) {
        var customer = customerService.findById(customerId);
        return ResponseEntity.ok(customer);
    }

    @PostMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Void> create(@RequestBody Customer customer) {
        var customerId = customerService.create(customer);
//        URI customerURI = URI.create(String.format("/api/customers/%s", customerId));
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping(
            value = "/{customerId}",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> update(@PathVariable Integer customerId, @RequestBody Customer customer) {
        customerService.update(customerId, customer);
        return ResponseEntity.noContent().build();
    }

    @PutMapping(
            value = "/{customerId}",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> delete(@PathVariable Integer customerId) {
        customerService.delete(customerId);
        return ResponseEntity.noContent().build();
    }
}

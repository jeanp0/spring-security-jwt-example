package com.softwarejm.demojava17.service;

import com.softwarejm.demojava17.model.Customer;
import com.softwarejm.demojava17.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

@Service
@Transactional
public class CustomerService {

    private final CustomerRepository customerRepository;

    @Autowired
    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public Collection<Customer> findAll() {
        return customerRepository.findAll();
    }

    public Customer findById(Integer customerId) {
        return customerRepository.findById(customerId)
                .orElseThrow(() -> new RuntimeException("Entidad no encontrada"));
    }

    public Integer create(Customer resource) {
        var customer = customerRepository.save(resource);
        return customer.getId();
    }

    public void update(Integer customerId, Customer resource) {
        var customer = findById(customerId);
        customer.setName(resource.getName());
        customer.setSurname(resource.getSurname());
        customer.setState(resource.getState());
    }

    public void delete(Integer customerId) {
        customerRepository.deleteById(customerId);
    }
}

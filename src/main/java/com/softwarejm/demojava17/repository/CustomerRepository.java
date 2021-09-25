package com.softwarejm.demojava17.repository;

import com.softwarejm.demojava17.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Integer> {
}

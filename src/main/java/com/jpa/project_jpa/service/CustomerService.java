package com.jpa.project_jpa.service;

// import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jpa.project_jpa.persitence.entity.CustomerEntity;
import com.jpa.project_jpa.persitence.repository.CustomerRepository;

@Service
public class CustomerService {
    private final CustomerRepository customerRepository;

    // @Autowired
    //inyectamos el repositorio a través del constructor
    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public CustomerEntity findByPhone(String phone) {
        return this.customerRepository.findByPhone(phone);
    }
}
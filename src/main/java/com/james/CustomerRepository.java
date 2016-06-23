package com.james;

import org.springframework.data.repository.CrudRepository;

/**
 * Created by jamesyburr on 6/22/16.
 */
public interface CustomerRepository extends CrudRepository<Customer, Integer> {
    public Customer findById(int id);
}

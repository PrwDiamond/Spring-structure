package com.example.demo.repository;

import com.example.demo.entity.Address;
import com.example.demo.entity.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface AddressRepository extends CrudRepository<Address, String /*Primary Key*/> {

    List<Address> findByUser(User user);
}

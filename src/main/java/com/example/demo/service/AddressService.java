package com.example.demo.service;

import com.example.demo.entity.Address;
import com.example.demo.entity.Social;
import com.example.demo.entity.User;
import com.example.demo.repository.AddressRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class AddressService {
    private final AddressRepository addressRepository;

    public AddressService(AddressRepository addressRepository) {
        this.addressRepository = addressRepository;
    }

    public List<Address> findByUser(User user){
        return addressRepository.findByUser(user);
    }

    public Address createAddress(User user,String line1, String line2, String zipcode){
        Address entity = new Address();
        entity.setUser(user);
        entity.setLine1(line1);
        entity.setLine2(line2);
        entity.setZipcode(zipcode);
        return addressRepository.save(entity);
    }
}

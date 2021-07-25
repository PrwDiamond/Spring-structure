package com.example.demo.repository;

import com.example.demo.entity.Social;
import com.example.demo.entity.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface SocialRepository extends CrudRepository<Social, String /*Primary Key*/> {

    Optional<Social> findByUser(User user);
}

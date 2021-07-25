package com.example.demo.service;

import com.example.demo.entity.Social;
import com.example.demo.entity.User;
import com.example.demo.repository.SocialRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SocialService {
    private final SocialRepository socialRepository;

    public SocialService(SocialRepository socialRepository) {
        this.socialRepository = socialRepository;
    }

    public Optional<Social> findByUser(User user){
        return socialRepository.findByUser(user);
    }

    public Social createSocial(User user,String facebook,String line, String instagram, String tiktiok){
        Social entity = new Social();
        entity.setUser(user);
        entity.setFacebook(facebook);
        entity.setLine(line);
        entity.setInstagram(instagram);
        entity.setTiktok(tiktiok);
        return socialRepository.save(entity);
    }
}

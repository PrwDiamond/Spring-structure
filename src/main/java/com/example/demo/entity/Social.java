package com.example.demo.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity(name = "m_social")
public class Social extends BaseEnity {

    @Column(length = 120)
    private String facebook;

    @Column(length = 120)
    private String line;

    @Column(length = 120)
    private String instagram;

    @Column(length = 120)
    private String tiktok;

    @OneToOne
    @JoinColumn(name = "m_user_id", nullable = false)
    private User user;
}

package com.yj.mini.data.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name= "board")
public class Board {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String description;

}

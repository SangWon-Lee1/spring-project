package com.estsoft.springproject.domain;

import jakarta.persistence.*;

@Entity
@Table(name = "members")
public class Members {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MEMBER_ID")
    private long id;

    @Column
    private String username;

    @ManyToOne
    @JoinColumn(name = "TEAM_ID")   // FK
    private Team team;      // 일대다 단방향 연관관계

    @OneToOne
    @JoinColumn(name = "LOCKER_ID")     // FK
    private Locker locker;
}

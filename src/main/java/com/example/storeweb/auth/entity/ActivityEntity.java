package com.example.storeweb.auth.entity;

import com.example.storeweb.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;

@Getter
@Entity
public class ActivityEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // role_id @ManyToOne
    @ManyToOne
    @JoinColumn(name = "role_id", referencedColumnName = "id")
    private RoleEntity role;

    private String value;

    @Column(name = "view_name", nullable = false)
    private String viewName;

    @Column(name = "url_pattern", nullable = false)
    private String urlPattern;
}

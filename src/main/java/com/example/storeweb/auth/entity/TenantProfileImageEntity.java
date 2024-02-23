package com.example.storeweb.auth.entity;


import com.example.storeweb.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;

@Getter
@Entity
@Table(name = "tenant_profile_image")
public class TenantProfileImageEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "tenant_id", referencedColumnName = "id")
    private TenantEntity tenant;

    private String sort;
    private String url;

    @Column(name = "file_view_name", nullable = false)
    private String fileViewName;

    @Column(name = "file_original_name", nullable = false)
    private String fileOriginalName;

    @Column(name = "is_thumbnail", nullable = false)
    private boolean isThumbnail;
    private String format;
    private double size;
}

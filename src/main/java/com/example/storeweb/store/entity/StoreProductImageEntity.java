package com.example.storeweb.store.entity;

import com.example.storeweb.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;

@Getter
@Entity
public class StoreProductImageEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Integer sort;

    @ManyToOne
    @JoinColumn(name = "product_id", referencedColumnName = "id")
    private StoreProductEntity product;

    private String url;

    @Column(name = "file_view_name",nullable = false)
    private String fileViewName;

    @Column(name = "original_file_name", nullable = false)
    private String originalFileName;

    @Column(name = "is_thumbnail", nullable = false)
    private boolean isThumbnail;
    private String format;
    private String size;
}

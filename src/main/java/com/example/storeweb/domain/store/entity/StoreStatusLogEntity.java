package com.example.storeweb.domain.store.entity;

import com.example.storeweb.common.entity.BaseEntity;
import com.example.storeweb.domain.status.entity.StatusEntity;
import jakarta.persistence.*;
import lombok.Getter;

@Getter
@Entity
@Table(name = "store_status_log")
public class StoreStatusLogEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "store_id", referencedColumnName = "id")
    private StoreEntity store;

    @ManyToOne
    @JoinColumn(name = "status_id",referencedColumnName = "id")
    private StatusEntity status;
    private String desc;
}

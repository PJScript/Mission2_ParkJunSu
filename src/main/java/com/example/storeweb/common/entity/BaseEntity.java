package com.example.storeweb.common.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;


@Getter
@SuperBuilder
@NoArgsConstructor
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseEntity {

    /**행이 삭제 되었는지 여부를 논리적으로 처리*/
    @Column(name = "is_delete")
    @ColumnDefault("false")
    private boolean isDelete;

    /**행 생성시간*/
    @CreatedDate
    @Column(name = "created_at", updatable = false, nullable = false)
    private LocalDateTime createDate;

    /**행 마지막 업데이트 시간*/
    @LastModifiedDate
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updateDate;
}


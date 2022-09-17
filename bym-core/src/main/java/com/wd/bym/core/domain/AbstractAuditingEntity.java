package com.wd.bym.core.domain;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import java.io.Serial;
import java.io.Serializable;
import java.sql.Timestamp;
import java.time.Instant;

@Getter
@Setter
@MappedSuperclass
public abstract class AbstractAuditingEntity<T extends Serializable> extends AbstractEntity<T> {

    @Serial
    private static final long serialVersionUID = -2838716085544569835L;

    @CreatedBy
    @Column(name = "created_by", nullable = false, length = 50, updatable = false)
    private String createdBy;

    @CreatedDate
    @Column(name = "created_date", nullable = false, updatable = false)
    private final Timestamp createdDate = Timestamp.from(Instant.now());

    @LastModifiedBy
    @Column(name = "last_modified_by", length = 50)
    private String lastModifiedBy;

    @LastModifiedDate
    @Column(name = "last_modified_date")
    private final Timestamp lastModifiedDate = Timestamp.from(Instant.now());
}

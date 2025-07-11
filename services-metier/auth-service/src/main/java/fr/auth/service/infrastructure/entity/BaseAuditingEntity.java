package fr.auth.service.infrastructure.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.sql.Timestamp;

@MappedSuperclass
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
public class BaseAuditingEntity {
    @CreatedBy
    @Column(name = "cree_par", updatable = false)
    protected String creerPar;

    @LastModifiedBy
    @Column(name = "modifie_par")
    protected String modifiePar;

    @Column(name = "date_creation", updatable = false)
    @CreatedDate
    private Timestamp dateCreation;

    @Column(name = "date_modification")
    @LastModifiedDate
    private Timestamp dateModification;
}

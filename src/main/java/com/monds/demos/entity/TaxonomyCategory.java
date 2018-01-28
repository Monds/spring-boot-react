package com.monds.demos.entity;

import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "TB_ARA_TAXONOMY_CATEGORY")
public class TaxonomyCategory {

    @Id
    @Column(name = "CD_TAXONOMY")
    @Getter
    private String code;

    @Column(name = "NM_TAXONOMY")
    @Getter
    private String name;
}

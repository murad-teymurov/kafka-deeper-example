package com.company.kafkapublisher.entity;

import com.fasterxml.jackson.annotation.JsonTypeId;
import com.vladmihalcea.hibernate.type.json.JsonType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import java.util.UUID;

@Entity
@Table(name = "outbox")
@Getter
@Setter
@NoArgsConstructor
@TypeDef(name = "json", typeClass = JsonType.class)
public class Outbox {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(columnDefinition = "varchar(255)", name = "aggregatetype")
    private String aggregateType;

    @Column(columnDefinition = "varchar(255)", name = "aggrefateid")
    private String aggregateId;

    @Column(columnDefinition = "varchar(255)")
    private String type;

    @Type(type = "json")
    @Column(columnDefinition = "jsonb")
    private Object payload;

    public Outbox(String aggregateType, String aggregateId, String type, Object payload) {
        this.aggregateType = aggregateType;
        this.aggregateId = aggregateId;
        this.type = type;
        this.payload = payload;
    }
}

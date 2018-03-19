package com.domain;

import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

@Entity(name = "GROUPX")
@Data
public class Group {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotNull
    @NotEmpty
    @Column(unique = true)
    private String name;

    private Timestamp drawDate;

    private boolean isDrawn;

    private boolean countChildren;

    private BigDecimal giftValue;

    private BigDecimal childGiftValue;

    private String collectorContact;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "group", cascade = CascadeType.ALL)
    List<Membership> memberships;
}
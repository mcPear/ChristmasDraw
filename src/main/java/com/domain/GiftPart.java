package com.domain;

import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Entity
@Data
public class GiftPart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotNull
    @NotEmpty
    private String name;

    @NotNull
    private BigDecimal value;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "membership_id")
    private Membership membership;
}
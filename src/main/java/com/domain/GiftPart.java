package com.domain;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class GiftPart {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "id_giftPart_seq")
    @SequenceGenerator(name = "id_giftPart_seq", sequenceName = "id_giftPart_seq", allocationSize = 1)
    private Long id;

    @NotNull
    @NotEmpty
    private String name;

    @NotNull
    private BigDecimal value;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "membership_id")
    private Membership membership;
}
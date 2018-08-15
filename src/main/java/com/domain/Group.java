package com.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Set;

@Entity(name = "GROUPX")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Group {

    public Group(String name) {
        this(null, name, new Timestamp(System.currentTimeMillis()), false, false,
                new BigDecimal(0), new BigDecimal(0), null, "-", null);
        this.name = name;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "id_group_seq")
    @SequenceGenerator(name = "id_group_seq", sequenceName = "id_group_seq", allocationSize = 1)
    private Long id;

    @NotNull
    @NotEmpty
    @Column(unique = true)
    private String name;

    private Timestamp drawDate;

    private boolean isDrawn;

    private boolean countChildren;

    private BigDecimal giftValue;

    private BigDecimal childGiftValue;

    private BigDecimal calculatedChildGiftValue;

    private String collectorContact;

    @OneToMany(mappedBy = "group", cascade = CascadeType.ALL)
    Set<Membership> memberships;
}
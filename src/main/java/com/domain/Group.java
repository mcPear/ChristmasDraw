package com.domain;

import lombok.*;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;
import java.util.Set;

@Entity(name = "GROUPX")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Group {

    public Group(String name) {
        this(null, name, null, false, false,
                new BigDecimal(0), new BigDecimal(0), "not provided", null);
        this.name = name;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

    private String collectorContact;

    @OneToMany(mappedBy = "group", cascade = CascadeType.ALL)
    Set<Membership> memberships;
}
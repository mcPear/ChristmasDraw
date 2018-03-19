package com.domain;

import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotEmpty
    @NotNull
    @Column(unique = true)
    private String login;

    @NotEmpty
    @NotNull
    private String firstName;

    @NotEmpty
    @NotNull
    private String lastName;

    @Lob
    private String about;

    private int children;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user", cascade = CascadeType.ALL)
    List<Membership> membershipsWhereUser;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "drawnUser", cascade = CascadeType.ALL)
    List<Membership> membershipsWhereDrawnUser;
}
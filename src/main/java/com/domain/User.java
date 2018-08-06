package com.domain;

import lombok.*;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty
    @NotNull
    @Column(unique = true)
    private String preferredUsername;

    @NotEmpty
    @NotNull
    private String firstName;

    @NotEmpty
    @NotNull
    private String lastName;

    @Lob
    private String about;

    private Integer children;

    private Boolean virtual;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    List<Membership> membershipsWhereUser;

    @OneToMany(mappedBy = "drawnUser", cascade = CascadeType.ALL)
    List<Membership> membershipsWhereDrawnUser;
}
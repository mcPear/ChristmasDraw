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
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "id_user_seq")
    @SequenceGenerator(name = "id_user_seq", sequenceName = "id_user_seq", allocationSize = 1)
    private Long id;

    @NotEmpty
    @NotNull
    @Column(unique = true)
    private String preferredUsername;

    @NotEmpty
    @NotNull
    private String firstName;

    private String lastName;

    private String email;

    @Lob
    private String about;

    private Integer children;

    private Boolean virtual;

    private String lang;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    List<Membership> membershipsWhereUser;

    @OneToMany(mappedBy = "drawnUser", cascade = CascadeType.ALL)
    List<Membership> membershipsWhereDrawnUser;
}
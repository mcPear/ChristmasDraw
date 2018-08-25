package com.domain;

import lombok.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"user_id", "group_id"}))
public class Membership {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "id_membership_seq")
    @SequenceGenerator(name = "id_membership_seq", sequenceName = "id_membership_seq", allocationSize = 1)
    private Long id;

    private boolean owns;

    private Boolean accepted;

    private boolean includeInFutureDraw;

    private Boolean includedInLastDraw;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "group_id")
    private Group group;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "drawnUser_id")
    private User drawnUser;

    @OneToMany(mappedBy = "membership", cascade = CascadeType.ALL)
    List<GiftPart> giftParts;
}
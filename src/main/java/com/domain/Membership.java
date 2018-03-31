package com.domain;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Membership {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private boolean owns;

    private boolean accepted;

    private boolean includeChildrenInDraw;

    private boolean includeInDraw;

    @Lob
    private String about;

    private Integer children;

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
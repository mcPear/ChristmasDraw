package com.domain;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
public class Membership {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private boolean isOwner;

    private boolean accepted;

    private boolean includeChildrenInDraw;

    private boolean includeInDraw;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "group_id")
    private Group group;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "drawnUser_id")
    private User drawnUser;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "membership", cascade = CascadeType.ALL)
    List<GiftPart> giftParts;
}
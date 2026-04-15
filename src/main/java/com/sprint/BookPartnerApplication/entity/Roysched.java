package com.sprint.BookPartnerApplication.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "roysched")
public class Roysched {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "roysched_id")
    private Integer royschedId;

    @Column(name = "lorange")
    private Integer lorange;

    @Column(name = "hirange")
    private Integer hirange;

    @Column(name = "royalty")
    private Integer royalty;

    @ManyToOne
    @JoinColumn(name = "title_id")
    private Title title;

    public Roysched() {
    }

    public Integer getRoyschedId() {
        return royschedId;
    }

    public void setRoyschedId(Integer royschedId) {
        this.royschedId = royschedId;
    }

    public Integer getLorange() {
        return lorange;
    }

    public void setLorange(Integer lorange) {
        this.lorange = lorange;
    }

    public Integer getHirange() {
        return hirange;
    }

    public void setHirange(Integer hirange) {
        this.hirange = hirange;
    }

    public Integer getRoyalty() {
        return royalty;
    }

    public void setRoyalty(Integer royalty) {
        this.royalty = royalty;
    }

    public Title getTitle() {
        return title;
    }

    public void setTitle(Title title) {
        this.title = title;
    }
}
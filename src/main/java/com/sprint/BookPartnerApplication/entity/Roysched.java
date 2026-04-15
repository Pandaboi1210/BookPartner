package com.sprint.BookPartnerApplication.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "roysched")
public class Roysched {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "roysched_id", nullable = false)
    private Integer royschedId;

    @Column(name = "lorange", nullable = true)
    private Integer lorange;

    @Column(name = "hirange", nullable = true)
    private Integer hirange;

    @Column(name = "royalty", nullable = true)
    private Integer royalty;

    @ManyToOne
    @JoinColumn(
            name = "title_id",
            nullable = true,
            foreignKey = @ForeignKey(name = "fk_roysched_title")
    )
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
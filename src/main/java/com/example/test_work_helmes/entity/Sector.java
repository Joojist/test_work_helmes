package com.example.test_work_helmes.entity;

import jakarta.persistence.*;

@Entity
public class Sector {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ManyToOne
    @JoinColumn(name = "parent_id")
    private Sector parent;

    private int level;

    public Sector() {}

    public Sector(String name, Sector parent) {
        this.name = name;
        setParent(parent);
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Sector getParent() {
        return parent;
    }

    public void setParent(Sector parent) {
        this.parent = parent;
        if (parent == null) {
            this.level = 0;
        } else {
            this.level = parent.getLevel() + 1;
        }
    }

    public int getLevel() {
        return level;
    }

    private void setLevel(int level) {
        this.level = level;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Sector sector = (Sector) o;
        return id != null && id.equals(sector.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }



}

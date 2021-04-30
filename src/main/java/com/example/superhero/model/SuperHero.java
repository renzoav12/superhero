package com.example.superhero.model;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Data
@AllArgsConstructor
public class SuperHero {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    private String firstName;

    @NotNull
    private String superHeroName;


    public SuperHero(){

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSuperHeroName() {
        return superHeroName;
    }

    public void setSuperHeroName(String superHeroName) {
        this.superHeroName = superHeroName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SuperHero)) return false;
        SuperHero superHero = (SuperHero) o;
        return Objects.equals(id, superHero.id) && Objects.equals(superHeroName, superHero.superHeroName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, superHeroName);
    }
}

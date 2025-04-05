package com.ronit.remitly_notifier.repository.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Entity
@Data
@NoArgsConstructor
public class UserConduitTarget {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;
    private String conduit;
    private float target;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserConduitTarget that = (UserConduitTarget) o;
        return Objects.equals(conduit, that.conduit);
    }

    @Override
    public int hashCode() {
        return Objects.hash(conduit);
    }
}

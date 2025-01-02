package ru.kuksov.compapi.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "complects")
public class Complect {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "complect_id_seq")
    @SequenceGenerator(name = "complect_id_seq", initialValue = 0, sequenceName = "complect_id_seq")
    private int id;

    @Column(name = "name", unique = true, nullable = false)
    private String name;

    @OneToMany
    @Column(name = "DEVICES_ID")
    private List<Device> devices = new ArrayList<>();
}

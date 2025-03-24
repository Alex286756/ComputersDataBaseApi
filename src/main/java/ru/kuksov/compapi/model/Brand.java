package ru.kuksov.compapi.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Модель данных по брендам комплектующих
 *
 * @id идентификатор
 * @name название бренда
 * @author Куксов Алексей Генндьевич
 * @version 2.0
 */
@Entity
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "brands")
public class Brand {

    /**
     * Идентификатор бренда
     */
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "brand_id_seq")
    @SequenceGenerator(name = "brand_id_seq", sequenceName = "brand_id_seq", allocationSize = 1)
    private int id;

    /**
     * Название бренда
     */
    @Column(name = "name", unique = true, nullable = false)
    private String name;
}

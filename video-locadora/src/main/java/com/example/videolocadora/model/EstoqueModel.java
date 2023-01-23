package com.example.videolocadora.model;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.UUID;

@Entity
@Table(name="TB_ESTOQUE")
public class EstoqueModel implements Serializable {

    private static final Long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @OneToOne
    @JoinColumn(table = "TB_LOCADORA", name = "produtoId")
    private UUID id;
    @Column
    private String produto;

    @Column
    private boolean isRented;

    private enum Genero{
        TERROR, ROMANCE, COMEDIA, DOCUMENTARIO, DRAMA, AVENTURA, FANTASIA, INFANTIL
    }

    @Column
    @Enumerated(EnumType.STRING)
    private Genero genero;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getProduto() {
        return produto;
    }

    public void setProduto(String produto) {
        this.produto = produto;
    }

    public Genero getGenero() {
        return genero;
    }

    public void setGenero(Genero genero) {
        this.genero = genero;
    }

    public boolean isRented() {
        return isRented;
    }

    public void setRented(boolean rented) {
        isRented = rented;
    }
}

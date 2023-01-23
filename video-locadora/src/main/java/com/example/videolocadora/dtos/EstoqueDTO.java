package com.example.videolocadora.dtos;

import jakarta.validation.constraints.NotBlank;

public class EstoqueDTO {

    @NotBlank
    private String produto;

    @NotBlank
    private String genero;

    @NotBlank
    private boolean isRented;

    public String getProduto() {
        return produto;
    }

    public void setProduto(String produto) {
        this.produto = produto;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public boolean isRented() {
        return isRented;
    }

    public void setRented(boolean rented) {
        isRented = rented;
    }
}

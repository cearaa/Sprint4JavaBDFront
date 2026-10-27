package br.com.fiap.to;

// Integrantes da Challenge
//
// Tárik Moussa Alma - RM: 571411
// Giovanni Azevedo - RM: 572894
// Fabricio Aquiles Sales da Silva - RM: 570985
// Carlos Eduardo Tsucamoto Chiarelli - RM: 569574
// Ítalo Neto - RM: 572912

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

// Corpo da requisição de "registrar reciclagem" (não é uma tabela do banco)
public class ReciclagemTO {
    // atributos
    @NotBlank private String tipoMaterial;
    @NotNull @Positive private Double quilos;

    // construtores
    public ReciclagemTO() {
    }

    public ReciclagemTO(String tipoMaterial, Double quilos) {
        this.tipoMaterial = tipoMaterial;
        this.quilos = quilos;
    }

    // métodos getters/setters
    public String getTipoMaterial() {
        return tipoMaterial;
    }

    public void setTipoMaterial(String tipoMaterial) {
        this.tipoMaterial = tipoMaterial;
    }

    public Double getQuilos() {
        return quilos;
    }

    public void setQuilos(Double quilos) {
        this.quilos = quilos;
    }
}

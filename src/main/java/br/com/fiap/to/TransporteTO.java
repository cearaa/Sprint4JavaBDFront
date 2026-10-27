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

// Corpo da requisição de "registrar transporte sustentável" (não é uma tabela do banco)
public class TransporteTO {
    // atributos
    @NotBlank private String tipoTransporte;
    @NotNull @Positive private Double km;

    // construtores
    public TransporteTO() {
    }

    public TransporteTO(String tipoTransporte, Double km) {
        this.tipoTransporte = tipoTransporte;
        this.km = km;
    }

    // métodos getters/setters
    public String getTipoTransporte() {
        return tipoTransporte;
    }

    public void setTipoTransporte(String tipoTransporte) {
        this.tipoTransporte = tipoTransporte;
    }

    public Double getKm() {
        return km;
    }

    public void setKm(Double km) {
        this.km = km;
    }
}

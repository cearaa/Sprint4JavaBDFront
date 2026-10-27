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
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;

public class FantasmaTO {
    // atributos
    private Long codigo;
    @NotBlank @Size(max = 50) private String nome;
    @NotBlank @Size(max = 30) private String tipo;
    @NotBlank @Size(max = 30) private String raridade;
    @NotNull @PositiveOrZero private Double valorEssencia;
    @NotNull private Long codUsuario; // dono do fantasma (chave estrangeira)

    // construtores
    public FantasmaTO() {
    }

    public FantasmaTO(Long codigo, String nome, String tipo, String raridade, Double valorEssencia, Long codUsuario) {
        this.codigo = codigo;
        this.nome = nome;
        this.tipo = tipo;
        this.raridade = raridade;
        this.valorEssencia = valorEssencia;
        this.codUsuario = codUsuario;
    }

    // métodos getters/setters
    public Long getCodigo() {
        return codigo;
    }

    public void setCodigo(Long codigo) {
        this.codigo = codigo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getRaridade() {
        return raridade;
    }

    public void setRaridade(String raridade) {
        this.raridade = raridade;
    }

    public Double getValorEssencia() {
        return valorEssencia;
    }

    public void setValorEssencia(Double valorEssencia) {
        this.valorEssencia = valorEssencia;
    }

    public Long getCodUsuario() {
        return codUsuario;
    }

    public void setCodUsuario(Long codUsuario) {
        this.codUsuario = codUsuario;
    }
}

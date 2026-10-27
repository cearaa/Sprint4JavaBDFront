package br.com.fiap.to;

// Integrantes da Challenge
//
// Tárik Moussa Alma - RM: 571411
// Giovanni Azevedo - RM: 572894
// Fabricio Aquiles Sales da Silva - RM: 570985
// Carlos Eduardo Tsucamoto Chiarelli - RM: 569574
// Ítalo Neto - RM: 572912

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class UsuarioTO {
    // atributos
    private Long codigo;
    @NotBlank @Size(max = 100) private String nome;
    @NotBlank @Email @Size(max = 100) private String email;
    private Double essencia; // somente leitura: só muda pelas regras de reciclagem e transporte
    private String titulo;   // somente leitura: calculado a partir da Essência

    // construtores
    public UsuarioTO() {
    }

    public UsuarioTO(Long codigo, String nome, String email, Double essencia, String titulo) {
        this.codigo = codigo;
        this.nome = nome;
        this.email = email;
        this.essencia = essencia;
        this.titulo = titulo;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Double getEssencia() {
        return essencia;
    }

    public void setEssencia(Double essencia) {
        this.essencia = essencia;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }
}

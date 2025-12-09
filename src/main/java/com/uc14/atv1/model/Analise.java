package com.uc14.atv1.model;

import jakarta.persistence.*; // Se usar Spring Boot 3+ (ou javax.persistence.* para versões mais antigas)
import java.time.LocalDateTime;

@Entity
@Table(name = "analise")
public class Analise {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Mapeamento: Muitas Análises pertencem a um Filme
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "filme_id", nullable = false)
    private Filme filme;

    private String autor;
    private Integer nota;
    private String comentario;

    @Column(name = "data_analise")
    private LocalDateTime dataAnalise;

    // Método para preencher a data antes de salvar
    @PrePersist
    public void prePersist() {
        if (dataAnalise == null) {
            dataAnalise = LocalDateTime.now();
        }
    }

    // Construtores
    public Analise() {}
    
    // Getters e Setters (Obrigatórios para o JPA)
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Filme getFilme() { return filme; }
    public void setFilme(Filme filme) { this.filme = filme; }
    public String getAutor() { return autor; }
    public void setAutor(String autor) { this.autor = autor; }
    public Integer getNota() { return nota; }
    public void setNota(Integer nota) { this.nota = nota; }
    public String getComentario() { return comentario; }
    public void setComentario(String comentario) { this.comentario = comentario; }
    public LocalDateTime getDataAnalise() { return dataAnalise; }
    public void setDataAnalise(LocalDateTime dataAnalise) { this.dataAnalise = dataAnalise; }
}
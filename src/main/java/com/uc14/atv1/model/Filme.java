package com.uc14.atv1.model;

import jakarta.persistence.*; 
import java.util.List;

@Entity
@Table(name = "filme")
public class Filme {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titulo;
    
    @Column(name = "ano_lancamento")
    private Integer anoLancamento;
    
    private String genero;

    // --- NOVO CAMPO ADICIONADO PARA FUNCIONAR COM O HTML ---
    @Column(columnDefinition = "TEXT") // Permite textos longos no banco
    private String sinopse; 
    // -------------------------------------------------------

    @OneToMany(mappedBy = "filme", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Analise> analises;

    public Filme() {}
    
    // Getters e Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }
    public Integer getAnoLancamento() { return anoLancamento; }
    public void setAnoLancamento(Integer anoLancamento) { this.anoLancamento = anoLancamento; }
    public String getGenero() { return genero; }
    public void setGenero(String genero) { this.genero = genero; }
    
    // Getter e Setter da Sinopse
    public String getSinopse() { return sinopse; }
    public void setSinopse(String sinopse) { this.sinopse = sinopse; }

    public List<Analise> getAnalises() { return analises; }
    public void setAnalises(List<Analise> analises) { this.analises = analises; }
}
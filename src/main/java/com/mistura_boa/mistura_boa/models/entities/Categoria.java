package com.mistura_boa.mistura_boa.models.entities;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "CATEGORIA")
public class Categoria {

    @Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID_CATEGORIA")
	private Long id;

    @Column(name = "DS_CATEGORIA")
    private String descricao;

    @Column(name = "NM_CATEGORIA")
    private String nome;

    @Column(name = "ICONE_CATEGORIA")
    private String icone;

    @Column(name = "DT_EXCLUSAO")
    private LocalDateTime dataExclusao;

    @OneToMany(mappedBy = "categoria", fetch = FetchType.LAZY)
	private List<Produto> produtos;

}

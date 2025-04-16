package com.senac.cotacao_api.model.entity;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="TB_COTACAO")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Cotacao {

	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name="MOEDA", length=50, nullable=false, unique=false)
	private String moeda;
	
	@Column(name="VALOR_MOEDA", nullable=false, unique=false)
	private Double valorMoeda;
	
	@Column(name="MOEDA_RELACAO", length=50, nullable=false, unique=false)
	private String moedaRelacao;
	
	@Column(name="VALOR_MOEDA_RELACAO", nullable=false, unique=false)
	private Double valorMoedaRelacao;
	
	@Column(name="DATA_COTACAO", nullable=false, unique=false)
	private Date dataCotacao;
}

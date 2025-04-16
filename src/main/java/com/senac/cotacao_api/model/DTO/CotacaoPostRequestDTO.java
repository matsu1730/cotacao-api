package com.senac.cotacao_api.model.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CotacaoPostRequestDTO {
	private String moeda;
	private Double valorMoeda;
	private String moedaRelacao;
	private Double valorMoedaRelacao;
}

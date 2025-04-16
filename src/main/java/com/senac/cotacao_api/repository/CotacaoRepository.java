package com.senac.cotacao_api.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.senac.cotacao_api.model.entity.Cotacao;

@Repository("cotacaoRepository")
public interface CotacaoRepository extends JpaRepository<Cotacao, Long> {
	
	public Optional<Cotacao> findByMoedaAndMoedaRelacao(String moeda, String moedaRelacao);

}

package com.senac.cotacao_api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.senac.cotacao_api.model.entity.CotacaoHist;

@Repository("cotacaoHistRepository")
public interface CotacaoHistRepository extends JpaRepository<CotacaoHist, Long>{
	
	public List<CotacaoHist> findAllByMoedaAndMoedaRelacaoOrderByIdAsc(String moeda, String moedaRelacao);
	
}

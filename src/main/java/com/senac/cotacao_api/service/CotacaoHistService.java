package com.senac.cotacao_api.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.senac.cotacao_api.model.DTO.CotacaoResponseDTO;
import com.senac.cotacao_api.model.entity.CotacaoHist;
import com.senac.cotacao_api.repository.CotacaoHistRepository;

@Service("cotacaoHistService")
public class CotacaoHistService {

	@Autowired
	CotacaoHistRepository cotacaoHistRepository;
	
	public List<CotacaoResponseDTO> getCotacaoHist(String moeda, String moedaRelacao) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		List<CotacaoResponseDTO> returnList = new ArrayList<>();
		List<CotacaoHist> cotacaoHistList = cotacaoHistRepository.findAllByMoedaAndMoedaRelacaoOrderByIdAsc(moeda, moedaRelacao);
		
		for (CotacaoHist histEntity : cotacaoHistList) {
			returnList.add(CotacaoResponseDTO.builder()
					.moeda(histEntity.getMoeda())
					.valorMoeda(histEntity.getValorMoeda())
					.moedaRelacao(histEntity.getMoedaRelacao())
					.valorMoedaRelacao(histEntity.getValorMoedaRelacao())
					.dataCotacao(sdf.format(histEntity.getInsertDate()))
					.build());
		}
		
		return returnList;
	}
}

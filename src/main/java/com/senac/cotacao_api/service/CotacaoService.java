package com.senac.cotacao_api.service;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Optional;

import org.hibernate.service.spi.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.JsonNode;
import com.senac.cotacao_api.model.DTO.CotacaoPostRequestDTO;
import com.senac.cotacao_api.model.DTO.CotacaoResponseDTO;
import com.senac.cotacao_api.model.entity.Cotacao;
import com.senac.cotacao_api.model.entity.CotacaoHist;
import com.senac.cotacao_api.repository.CotacaoHistRepository;
import com.senac.cotacao_api.repository.CotacaoRepository;

@Service("cotacaoService")
public class CotacaoService {
	
	@Autowired
	CotacaoRepository cotacaoRepository;
	
	@Autowired
	CotacaoHistRepository cotacaoHistRepository;
	
	@Value(value = "${exchangerate.api.host}")
	String exchangeRateApiHost;
	
	@Value(value = "${exchangerate.api.key}")
	String exchangeRateApiKey;

	public CotacaoResponseDTO getCotacao(String moeda, String moedaRelacao) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Cotacao returnCotacao = new Cotacao();
		Optional<Cotacao> cotacaoOpt = cotacaoRepository.findByMoedaAndMoedaRelacao(moeda, moedaRelacao);
		
		if (cotacaoOpt.isPresent()) {
			returnCotacao = cotacaoOpt.get();
		} else {
			String apiUrl = exchangeRateApiHost + exchangeRateApiKey + String.format("/pair/%s/%s/1", moeda, moedaRelacao);
			RestTemplate rt = new RestTemplate();
			HttpHeaders headers = new HttpHeaders();
			headers.setAccept(Arrays.asList(MediaType.ALL));
			ResponseEntity<JsonNode> re = rt.exchange(apiUrl, HttpMethod.GET, new HttpEntity<>(null, headers), JsonNode.class);
			
			returnCotacao = Cotacao.builder()
					.moeda(moeda)
					.valorMoeda(Double.valueOf(1))
					.moedaRelacao(moedaRelacao)
					.valorMoedaRelacao(re.getBody().get("conversion_result").asDouble())
					.dataCotacao(new Date())
					.build();
			returnCotacao = cotacaoRepository.saveAndFlush(returnCotacao);
			
			cotacaoHistRepository.saveAndFlush(CotacaoHist.builder()
					.moeda(moeda)
					.valorMoeda(Double.valueOf(1))
					.moedaRelacao(moedaRelacao)
					.valorMoedaRelacao(re.getBody().get("conversion_result").asDouble())
					.insertDate(new Date())
					.build());
		}
		return CotacaoResponseDTO.builder()
				.moeda(returnCotacao.getMoeda())
				.valorMoeda(returnCotacao.getValorMoeda())
				.moedaRelacao(returnCotacao.getMoedaRelacao())
				.valorMoedaRelacao(returnCotacao.getValorMoedaRelacao())
				.dataCotacao(sdf.format(returnCotacao.getDataCotacao()))
				.build();
	}
	
	public void saveCotacao(CotacaoPostRequestDTO cotacao) {
		Optional<Cotacao> cotacaoOpt = cotacaoRepository.findByMoedaAndMoedaRelacao(cotacao.getMoeda(), cotacao.getMoedaRelacao());
		
		if (cotacaoOpt.isPresent()) {
			Cotacao cotacaoEntity = cotacaoOpt.get();
			cotacaoEntity.setValorMoeda(cotacao.getValorMoeda());
			cotacaoEntity.setValorMoedaRelacao(cotacao.getValorMoedaRelacao());
			cotacaoEntity.setDataCotacao(new Date());
			cotacaoRepository.saveAndFlush(cotacaoEntity);
		} else {
			cotacaoRepository.saveAndFlush(Cotacao.builder()
					.moeda(cotacao.getMoeda())
					.valorMoeda(Double.valueOf(cotacao.getValorMoeda()))
					.moedaRelacao(cotacao.getMoedaRelacao())
					.valorMoedaRelacao(cotacao.getValorMoedaRelacao())
					.dataCotacao(new Date())
					.build());
		}
		
		cotacaoHistRepository.saveAndFlush(CotacaoHist.builder()
				.moeda(cotacao.getMoeda())
				.valorMoeda(Double.valueOf(cotacao.getValorMoeda()))
				.moedaRelacao(cotacao.getMoedaRelacao())
				.valorMoedaRelacao(cotacao.getValorMoedaRelacao())
				.insertDate(new Date())
				.build());
	}
	
}

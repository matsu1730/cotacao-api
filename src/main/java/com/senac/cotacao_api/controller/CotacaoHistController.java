package com.senac.cotacao_api.controller;

import java.util.List;

import org.hibernate.service.spi.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.senac.cotacao_api.model.DTO.CotacaoResponseDTO;
import com.senac.cotacao_api.model.DTO.ResponseDTO;
import com.senac.cotacao_api.service.CotacaoHistService;

@RestController
@RequestMapping("/cotacao-hist")
public class CotacaoHistController {

	@Autowired
	CotacaoHistService cotacaoHistService;
	
	@GetMapping("/get-hist")
	public ResponseEntity<?> getCotacaoHist(@RequestParam String moeda, @RequestParam String moedaRelacao) {
		try {
			List<CotacaoResponseDTO> returnList = cotacaoHistService.getCotacaoHist(moeda, moedaRelacao);
			return ResponseEntity.ok(returnList);
		} catch (ServiceException ex) {
			ResponseEntity.ofNullable(ResponseDTO.builder().message(ex.getMessage()).build());
			return (ResponseEntity<?>) ResponseEntity.status(HttpStatusCode.valueOf(400));
		} catch (Exception ex) {
			ResponseEntity.ofNullable(ResponseDTO.builder().message(ex.getMessage()).build());
			return (ResponseEntity<?>) ResponseEntity.status(HttpStatusCode.valueOf(500));
		}
	}
}

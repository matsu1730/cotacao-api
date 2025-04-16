package com.senac.cotacao_api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.senac.cotacao_api.model.DTO.CotacaoPostRequestDTO;
import com.senac.cotacao_api.model.DTO.CotacaoResponseDTO;
import com.senac.cotacao_api.model.DTO.ResponseDTO;
import com.senac.cotacao_api.service.CotacaoService;

@RestController
@RequestMapping("/cotacao")
public class CotacaoController {

	@Autowired
	CotacaoService cotacaoService;
	
	@GetMapping("/get")
	public ResponseEntity<CotacaoResponseDTO> getCotacao(@RequestParam String moeda, @RequestParam String moedaRelacao) {
		CotacaoResponseDTO returnDto = cotacaoService.getCotacao(moeda, moedaRelacao);
		return ResponseEntity.ok(returnDto);
	}
	
	@PostMapping("/post")
	public ResponseEntity<?> postCotacao(@RequestBody CotacaoPostRequestDTO requestBody) {
		cotacaoService.saveCotacao(requestBody);
		return ResponseEntity.ok(ResponseDTO.builder().message("Success").build());
	}
}

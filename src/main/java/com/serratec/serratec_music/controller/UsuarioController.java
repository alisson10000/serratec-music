package com.serratec.serratec_music.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("/usuario")
public class UsuarioController {
	
	
	
	
	@GetMapping
	public String home() {
		return "Minha api esta rodando!";
	}
	

}

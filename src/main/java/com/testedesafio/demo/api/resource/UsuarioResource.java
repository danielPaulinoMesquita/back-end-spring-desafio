package com.testedesafio.demo.api.resource;

import com.testedesafio.demo.api.dto.UsuarioDTO;
import com.testedesafio.demo.exception.ErroDeAutenticacao;
import com.testedesafio.demo.model.Usuario;
import com.testedesafio.demo.service.UsuarioService;
import com.testedesafio.demo.service.impl.UsuarioServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
public class UsuarioResource {

    private UsuarioService usuarioService;

    public UsuarioResource(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @PostMapping
    public ResponseEntity login(@RequestBody UsuarioDTO usuarioDTO){
        try {
            Usuario usuarioAutenticado = usuarioService.autenticar(usuarioDTO.getUsuario(), usuarioDTO.getSenha());
            return ResponseEntity.ok(UsuarioServiceImpl.converte(usuarioAutenticado));
        }catch (ErroDeAutenticacao e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }
}

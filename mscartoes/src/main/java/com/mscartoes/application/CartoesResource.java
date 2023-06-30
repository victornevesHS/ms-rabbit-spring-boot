package com.mscartoes.application;

import com.mscartoes.application.representation.CartaoSaveRequest;
import com.mscartoes.domain.Cartao;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("cartoes")
@RequiredArgsConstructor
public class CartoesResource {
    private final CartaoService service;

    @GetMapping
    public String status(){
        return "ok";
    }
    @PostMapping
    public ResponseEntity cadastra  (@RequestBody CartaoSaveRequest request) {
        Cartao cartao = request.toModel();
        service.save(cartao);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

}

package com.example.videolocadora.controllers;

import com.example.videolocadora.dtos.EmailDTO;
import com.example.videolocadora.dtos.LocadoraDTO;
import com.example.videolocadora.model.EmailModel;
import com.example.videolocadora.model.LocadoraModel;
import com.example.videolocadora.services.EmailService;
import com.example.videolocadora.services.LocadoraService;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@CrossOrigin(origins="*", maxAge = 3600)
@RequestMapping(value = "/locadora")
public class LocadoraController {


    final LocadoraService locadoraService;
    final EmailService emailService;
    final EmailController emailController;

    public LocadoraController(LocadoraService locadoraService, EmailService emailService, EmailController emailCOntroller, EmailController emailController) {
        this.locadoraService = locadoraService;
        this.emailService = emailService;
        this.emailController = emailController;
    }

    @PostMapping
    public ResponseEntity<Object> savePedido(@RequestBody @Valid LocadoraDTO locadoraDTO){
        if(locadoraService.existsByNome(locadoraDTO.getNome())){
            return ResponseEntity.status(HttpStatus.CREATED).body("Conflict:  Order already exists!");
        }

        var locadoraModel = new LocadoraModel();
        var emailDTO = new EmailDTO();

        BeanUtils.copyProperties(locadoraDTO, locadoraModel);
        locadoraModel.setDataCompra(LocalDateTime.now(ZoneId.of("UTC")));

        emailDTO.setEmailFrom("nevescaioneves@gmail.com");
        emailDTO.setEmailTo(locadoraModel.getEmail());
        emailDTO.setSubject("Pedido efetuado com sucesso!");
        emailDTO.setText("Olá senhor(a) " + locadoraModel.getNome() + "o filme " + locadoraModel.getProduto() + "foi alugado! " + "\nData de devolução: " + locadoraModel.getDataDevolucao());
        emailController.sendEmail(emailDTO);

        return ResponseEntity.status(HttpStatus.CREATED).body(locadoraService.save(locadoraModel));
    }

    @GetMapping
    public ResponseEntity<List<LocadoraModel>>getAllPedidos(){return ResponseEntity.status(HttpStatus.OK).body(locadoraService.findAll());}

    @GetMapping("/{nome}")
    public ResponseEntity<Object> getOrderByName(@PathVariable(value = "nome") String nome){
        Optional<LocadoraModel> locadoraModelOptional =locadoraService.findByNome(nome);

        if(!locadoraModelOptional.isPresent()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Conflict: Order not exists by name!");
        }

        return ResponseEntity.status(HttpStatus.OK).body(locadoraModelOptional.get());
    }

    @GetMapping("/{produto}")
    public ResponseEntity<Object> getOrderByProduct(@PathVariable(value = "produto") String produto){
        Optional<LocadoraModel> locadoraModelOptional =locadoraService.findByProduto(produto);

        if(!locadoraModelOptional.isPresent()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Conflict: Order not exists by name!");
        }

        return ResponseEntity.status(HttpStatus.OK).body(locadoraModelOptional.get());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteOrderByName(@PathVariable(value = "id") UUID id,
                                               @RequestBody @Valid LocadoraDTO locadoraDTO){
        Optional<LocadoraModel> locadoraModelOptional =locadoraService.findById(id);

        if(!locadoraModelOptional.isPresent()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Not Found: Order by name not found!");
        }

        locadoraService.deleteById(locadoraModelOptional.get().getId());
        return ResponseEntity.status(HttpStatus.OK).body("Order deleted successfully");
    }

    @PutMapping("/{nome}")
    public ResponseEntity<Object> updateOrder(@PathVariable(value = "nome") String nome,
                                              @RequestBody @Valid LocadoraDTO locadoraDTO){

        Optional<LocadoraModel> locadoraModelOptional = locadoraService.findByNome(nome);

        if(!locadoraModelOptional.isPresent()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Conflict: Order not found!");
        }

        var locadoraModel = locadoraModelOptional.get();
        BeanUtils.copyProperties(locadoraDTO, locadoraModel);
        locadoraModel.setId(locadoraModelOptional.get().getId());
        locadoraModel.setDataCompra(locadoraModelOptional.get().getDataCompra());

        return ResponseEntity.status(HttpStatus.OK).body(locadoraService.save(locadoraModel));
    }

}

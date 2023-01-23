package com.example.videolocadora.controllers;

import com.example.videolocadora.dtos.EstoqueDTO;
import com.example.videolocadora.model.EstoqueModel;
import com.example.videolocadora.services.EstoqueService;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping(value = "/estoque")
public class EstoqueController {

    final EstoqueService estoqueService;

    public EstoqueController(EstoqueService estoqueService) {
        this.estoqueService = estoqueService;
    }

    @PostMapping
    public ResponseEntity<Object> saveProduct(@RequestBody @Valid EstoqueDTO estoqueDTO){
        var estoqueModel = new EstoqueModel();
        BeanUtils.copyProperties(estoqueDTO, estoqueModel);
        return ResponseEntity.status(HttpStatus.OK).body(estoqueService.save(estoqueModel));
    }

    @GetMapping
    public ResponseEntity<List<EstoqueModel>> findAllProducts(){return ResponseEntity.status(HttpStatus.OK).body(estoqueService.findAll());}

    @GetMapping("/{nome}")
    public ResponseEntity<Object> findProductByName(@PathVariable(value = "produto") String produto){
        Optional<EstoqueModel> estoqueModelOptional = estoqueService.findByProduto(produto);

        if(!estoqueModelOptional.isPresent()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Conflict: Product not found!");
        }

        return ResponseEntity.status(HttpStatus.OK).body(estoqueModelOptional.get());
    }

    @DeleteMapping
    public ResponseEntity<Object> deleteByProduct(@PathVariable(value = "produto") String produto){

        Optional<EstoqueModel> estoqueModelOptional = estoqueService.findByProduto(produto);

        if(!estoqueModelOptional.isPresent()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Conflict: Prodct not found!");
        }

        estoqueService.deleteByProduto(estoqueModelOptional.get().getProduto());
        return ResponseEntity.status(HttpStatus.OK).body("Product deleted succesfully!");
    }

    @PutMapping
    public  ResponseEntity<Object> updateProduct(@PathVariable(value = "produto") String produto,
                                                 @RequestBody @Valid EstoqueDTO estoqueDTO){
        Optional<EstoqueModel> estoqueModelOptional = estoqueService.findByProduto(produto);

        if(!estoqueModelOptional.isPresent()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Prodct not found!");
        }
        var estoqueModel = estoqueModelOptional.get();
        BeanUtils.copyProperties(estoqueDTO, estoqueModel);
        estoqueModel.setId(estoqueModelOptional.get().getId());

        return ResponseEntity.status(HttpStatus.OK).body(estoqueService.save(estoqueModel));

    }
}

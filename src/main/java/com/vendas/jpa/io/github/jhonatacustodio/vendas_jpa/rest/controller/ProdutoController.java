package com.vendas.jpa.io.github.jhonatacustodio.vendas_jpa.rest.controller;


import java.util.List;

import javax.validation.Valid;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import static org.springframework.http.HttpStatus.*;

import com.vendas.jpa.io.github.jhonatacustodio.vendas_jpa.domain.entity.Produto;
import com.vendas.jpa.io.github.jhonatacustodio.vendas_jpa.domain.repository.Produtos;


@RestController
@RequestMapping("/api/produtos")
public class ProdutoController {

    private Produtos produtos;

    public ProdutoController(Produtos produtos) {
        this.produtos = produtos;
    }
    
    @GetMapping("{id}")
    public Produto getByProductsId(@PathVariable Integer id){
        return produtos.findById(id).orElseThrow(
            () -> new ResponseStatusException(NOT_FOUND, "Produto não encontrado")
        );

    }

    @PostMapping
    @ResponseStatus(CREATED)
    public Produto save(@RequestBody @Valid Produto produto){
        return produtos.save(produto);
        
    }

    @DeleteMapping("{id}")
    @ResponseStatus(NO_CONTENT)
    public void delete(@PathVariable Integer id){
        produtos.findById(id).map(
            produto -> {
                produtos.delete(produto);
                return produto;
            }
        ).orElseThrow(
            () -> new ResponseStatusException(NOT_FOUND, "Produto não encontrado!")
        ); 
    }

    @PutMapping("{id}")
    @ResponseStatus(CREATED)
    public void update(@PathVariable Integer id, @RequestBody @Valid Produto produto){
       produtos.findById(id).map(
            produtoExistente -> {
                produto.setId(produtoExistente.getId());
                produtos.save(produto);
                return produtoExistente;
            }
       ).orElseThrow( ()-> new ResponseStatusException(NOT_FOUND, "Produto não encontrado!"));
    }

    @GetMapping
    public List<Produto> find(Produto filter){
        ExampleMatcher matcher = ExampleMatcher.matching().withIgnoreCase()
                                    .withStringMatcher(
                                        ExampleMatcher.StringMatcher.CONTAINING
                                    );
        Example example = Example.of(filter, matcher);
        return produtos.findAll(example);
      
    }
}

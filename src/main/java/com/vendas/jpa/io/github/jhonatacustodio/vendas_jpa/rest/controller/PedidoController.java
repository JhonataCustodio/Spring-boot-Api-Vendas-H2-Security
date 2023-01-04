package com.vendas.jpa.io.github.jhonatacustodio.vendas_jpa.rest.controller;

import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.vendas.jpa.io.github.jhonatacustodio.vendas_jpa.domain.entity.ItemPedido;
import com.vendas.jpa.io.github.jhonatacustodio.vendas_jpa.domain.entity.Pedido;
import com.vendas.jpa.io.github.jhonatacustodio.vendas_jpa.domain.enums.StatusPedido;
import com.vendas.jpa.io.github.jhonatacustodio.vendas_jpa.domain.service.PedidoService;
import com.vendas.jpa.io.github.jhonatacustodio.vendas_jpa.rest.dto.AtualizacaoStatusPedidoDTO;
import com.vendas.jpa.io.github.jhonatacustodio.vendas_jpa.rest.dto.InformacoesItemPedidoDTO;
import com.vendas.jpa.io.github.jhonatacustodio.vendas_jpa.rest.dto.InformacoesPedidoDTO;
import com.vendas.jpa.io.github.jhonatacustodio.vendas_jpa.rest.dto.PedidoDTO;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/pedidos")
public class PedidoController {
    PedidoService pedidoService;
    
    public PedidoController(PedidoService pedidoService) {
        this.pedidoService = pedidoService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Integer save(@RequestBody @Valid PedidoDTO pedidoDTO){
        Pedido pedido = pedidoService.salvar(pedidoDTO);
        return pedido.getId();
    }

    @GetMapping("{id}")
    public InformacoesPedidoDTO getById(@PathVariable Integer id){
        return pedidoService.obterPedidoCompleto(id)
                            .map(
                                p -> converter(p)
                            ).orElseThrow(
                                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Pedido não encontrado")
                            );
    }

    private InformacoesPedidoDTO converter(Pedido pedido){
            return InformacoesPedidoDTO.builder()
                            .codigo(pedido.getId())
                            .dataPedido(pedido.getData_pedido().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")))
                            .cpf(pedido.getCliente().getCpf())
                            .nomeCliente(pedido.getCliente().getNome())
                            .total(pedido.getTotal())
                            .status(pedido.getStatus().name())
                            .items(converter(pedido.getItens()))
                            .build();
    }

    private List<InformacoesItemPedidoDTO> converter(List<ItemPedido> items){
        if(CollectionUtils.isEmpty(items)){
            return Collections.emptyList();
        }
        return items.stream().map( item -> InformacoesItemPedidoDTO.builder()
                                            .descricaoProduto(item.getProduto().getDescricao())
                                            .precoUnitario(item.getProduto().getPreco_unitario())
                                            .quantidade(item.getQuantidade())
                                            .build()
        ).collect(Collectors.toList());
    }

    @PatchMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateStatus(@PathVariable Integer id, 
                            @RequestBody AtualizacaoStatusPedidoDTO dto){
        String novoStatus = dto.getNovoStatus();
        pedidoService.atualizaStatus(id, StatusPedido.valueOf(novoStatus));
    }
}

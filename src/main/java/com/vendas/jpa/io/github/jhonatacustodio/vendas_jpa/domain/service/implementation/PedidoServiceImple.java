package com.vendas.jpa.io.github.jhonatacustodio.vendas_jpa.domain.service.implementation;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.vendas.jpa.io.github.jhonatacustodio.vendas_jpa.domain.entity.Cliente;
import com.vendas.jpa.io.github.jhonatacustodio.vendas_jpa.domain.entity.ItemPedido;
import com.vendas.jpa.io.github.jhonatacustodio.vendas_jpa.domain.entity.Pedido;
import com.vendas.jpa.io.github.jhonatacustodio.vendas_jpa.domain.entity.Produto;
import com.vendas.jpa.io.github.jhonatacustodio.vendas_jpa.domain.enums.StatusPedido;
import com.vendas.jpa.io.github.jhonatacustodio.vendas_jpa.domain.repository.Clientes;
import com.vendas.jpa.io.github.jhonatacustodio.vendas_jpa.domain.repository.ItensPedidos;
import com.vendas.jpa.io.github.jhonatacustodio.vendas_jpa.domain.repository.Pedidos;
import com.vendas.jpa.io.github.jhonatacustodio.vendas_jpa.domain.repository.Produtos;
import com.vendas.jpa.io.github.jhonatacustodio.vendas_jpa.domain.service.PedidoService;
import com.vendas.jpa.io.github.jhonatacustodio.vendas_jpa.exception.BusinessRuleException;
import com.vendas.jpa.io.github.jhonatacustodio.vendas_jpa.exception.PedidoNaoEncontradoException;
import com.vendas.jpa.io.github.jhonatacustodio.vendas_jpa.rest.dto.ItemPedidoDTO;
import com.vendas.jpa.io.github.jhonatacustodio.vendas_jpa.rest.dto.PedidoDTO;

import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PedidoServiceImple implements PedidoService{

    private final Pedidos pedidos;
    private final Clientes clientesRepository;
    private final Produtos produtosRepository;
    private final ItensPedidos itemsPedidoRepository;

    @Override
    @Transactional
    public Pedido salvar(PedidoDTO pedidoDTO) {
        Integer idCliente = pedidoDTO.getCliente();
        Cliente cliente = clientesRepository.findById(idCliente).orElseThrow(() -> 
            new BusinessRuleException("Código de cliente inválido!")
        );
        Pedido pedido = new Pedido();
        pedido.setTotal(pedidoDTO.getTotal());
        pedido.setData_pedido(LocalDate.now());
        pedido.setCliente(cliente);
        pedido.setStatus(StatusPedido.REALIZADO);

        List<ItemPedido> itemsPedido = converterItems(pedido, pedidoDTO.getItems());

        pedidos.save(pedido);
        itemsPedidoRepository.saveAll(itemsPedido);
        pedido.setItens(itemsPedido);
        return pedido;
    }

    private List<ItemPedido> converterItems(Pedido pedido, List<ItemPedidoDTO> items){
        if(items.isEmpty()){
            throw new BusinessRuleException("Não é possível realizar o pedido sem itens: ") ;
        }

        return items.stream().map(
            pedidoDTO -> {
                Integer idProduto = pedidoDTO.getProduto();
                
                Produto produto = produtosRepository.findById(idProduto).orElseThrow(
                    () -> new BusinessRuleException("Código de produto inválido!"));
                ItemPedido itemPedido = new ItemPedido();
                itemPedido.setQuantidade(pedidoDTO.getQuantidade());
                itemPedido.setPedido(pedido);
                itemPedido.setProduto(produto);
                return itemPedido;
            }
        ).collect(Collectors.toList());
    }

    @Override
    public Optional<Pedido> obterPedidoCompleto(Integer id) {
        return pedidos.findByIdFetchItens(id);
    }

    @Override
    @Transactional
    public void atualizaStatus(Integer id, StatusPedido statusPedido) {
        pedidos.findById(id).map(
            pedido -> {
                pedido.setStatus(statusPedido);
                return pedidos.save(pedido);
            }).orElseThrow (() -> new PedidoNaoEncontradoException() );
    }
}

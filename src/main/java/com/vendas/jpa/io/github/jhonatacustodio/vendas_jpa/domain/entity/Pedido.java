package com.vendas.jpa.io.github.jhonatacustodio.vendas_jpa.domain.entity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import com.vendas.jpa.io.github.jhonatacustodio.vendas_jpa.domain.enums.StatusPedido;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "pedido")
public class Pedido {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "cliente_id")
    private Cliente cliente;

    private LocalDate data_pedido;

    @Column(precision = 20, scale  = 2)
    private BigDecimal total;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private StatusPedido status;

    @OneToMany(mappedBy = "pedido")
    private List<ItemPedido> itens;

}

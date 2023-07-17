package me.dio.sacola.service.Impl;

import lombok.RequiredArgsConstructor;
import me.dio.sacola.enumaration.FormaPagamento;
import me.dio.sacola.model.Item;
import me.dio.sacola.model.Restaurante;
import me.dio.sacola.model.Sacola;
import me.dio.sacola.repository.ItemRepository;
import me.dio.sacola.repository.ProdutoRepository;
import me.dio.sacola.repository.SacolaRepository;
import me.dio.sacola.resource.dto.ItemDto;
import me.dio.sacola.service.SacolaService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SacolaServiceImpl implements SacolaService {
    private final SacolaRepository sacolaRepository;
    private final ProdutoRepository produtorepository;
    private final ItemRepository itemRepository;

    @Override
    public Item incluirItemNaSacola(ItemDto itemDto) {
     Sacola sacola = verSacola(itemDto.getIdSacola());

     if(sacola.isFechada()) {
         throw new RuntimeException("Essa sacola já está fechada!");
    }

       Item itemParaSerInserido = Item.builder()

                .quantidade(itemDto.getQuantidade())
                .sacola(sacola)
                .produto(produtorepository.findById(itemDto.getProdutoId()).orElseThrow(
                () -> {
                    throw new RuntimeException("Esse produto não existe!");
             }
         ))

         .build();

        List<Item> itensDaSacola = sacola.getItens();
        if(itensDaSacola.isEmpty()) {
            itensDaSacola.add(itemParaSerInserido);
        }else {
            Restaurante restauranteAtual = itensDaSacola.get(0).getProduto().getRestaurante();

           Restaurante restauranteDoItemParaadicionar =itemParaSerInserido.getProduto().getRestaurante();

           if(restauranteAtual.equals(restauranteDoItemParaadicionar)) {
               itensDaSacola.add(itemParaSerInserido);
           }else {
               throw new RuntimeException("Esse restaurante já está em uso!");
           }
        }

        List<Double> valorDosItens = new ArrayList<>();

        for(Item itemDaSacola : itensDaSacola) {
            double valortotalItem = itemDaSacola.getProduto().getValorUnitario() * itemDaSacola.getQuantidade();
            valorDosItens.add(valortotalItem);
        }

        double valorTotalSacola = valorDosItens.stream()
                .mapToDouble(valortotalDeCadaItem -> valortotalDeCadaItem)
                .sum();

        sacola.setValorTotal(valorTotalSacola);
        sacolaRepository.save(sacola);
        return itemParaSerInserido;
    }

    @Override
    public Sacola verSacola(Long id) {
        return sacolaRepository.findById(id).orElseThrow(
                () -> {
                    throw new RuntimeException("Essa sacola não existe!");
        }
        );
    }

    @Override
    public Sacola fecharSacola(Long id, int numeroformaPagamento) {
        Sacola sacola = verSacola(id);

        if (sacola.getItens().isEmpty()) {
            throw new RuntimeException("Inclua itens na sacola!");
        }

        FormaPagamento formaPagamento =
                numeroformaPagamento == 0 ? FormaPagamento.DINHEIRO : FormaPagamento.MAQUINETA;


        sacola.setFormaPagamento(formaPagamento);
        sacola.setFechada(true);
        return sacolaRepository.save(sacola);

    }
}

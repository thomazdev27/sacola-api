package me.dio.sacola.resource;

import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import me.dio.sacola.model.Item;
import me.dio.sacola.model.Sacola;
import me.dio.sacola.resource.dto.ItemDto;
import me.dio.sacola.service.SacolaService;
import org.springframework.web.bind.annotation.*;

@Api(value = "/ifood-devweek/sacolas")
@RestController
@RequestMapping("/ifood-devweek/sacolas")
@RequiredArgsConstructor

public class SacolaResource {

    private final SacolaService SacolaService;

    @PostMapping
    public Item incluirItemNaSacola(@RequestBody ItemDto itemdto) {
       return SacolaService.incluirItemNaSacola(itemdto);

    }
    @GetMapping("/{id}")
    public Sacola verSacola(@PathVariable("id") Long id) {
        return SacolaService.verSacola(id);

    }
    @PatchMapping("/fecharSacola/{sacolaid}")
    public Sacola fecharSacola(@PathVariable("sacolaid") Long idSacola,
                               @RequestParam("formaPagamento") int formaPagamento) {
        return SacolaService.fecharSacola(idSacola, formaPagamento);
    }


}

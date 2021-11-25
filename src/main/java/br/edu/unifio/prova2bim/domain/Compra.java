package br.edu.unifio.prova2bim.domain;


import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.*;
import java.time.LocalDateTime;

@Data
@Entity
public class Compra {
    @Id
    @GeneratedValue
    private Integer codigoCompra;

    @NotNull
    private LocalDateTime horario = LocalDateTime.now();

    @ManyToOne
    @NotNull(message = "O campo Gênero é obrigatório")
    private Jogo jogo;

    @ManyToOne
    @NotNull(message = "O campo Desenvolvedor é obrigatório")
    private Usuario usuario;
}

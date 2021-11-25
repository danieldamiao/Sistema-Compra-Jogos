package br.edu.unifio.prova2bim.domain;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Entity

public class Analise {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer codigo;

    @NotBlank(message = "O campo TITULO é obrigatório")
    @Size(min = 2, max = 50, message = "O tamanho do campo TITULO deve ser entre 2 e 50")
    private String titulo;

    @NotNull
    private LocalDateTime periodo = LocalDateTime.now();


    private String descricao;

    @NotBlank(message = "O campo SITUAÇÃO é obrigatório")
    private String situacao;

    @ManyToOne
    @NotNull(message = "O campo USUARIO é obrigatório")
    private Usuario usuario;

    @ManyToOne
    @NotNull(message = "O campo JOGO é obrigatório")
    private Jogo jogo;
}

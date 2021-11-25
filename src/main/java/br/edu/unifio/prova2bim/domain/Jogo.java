package br.edu.unifio.prova2bim.domain;


import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.*;


@Table(uniqueConstraints = {
        @UniqueConstraint(columnNames = {"tituloJogo"})
})
@Data
@Entity
public class Jogo {
    @Id
    @GeneratedValue
    private Integer codigoJogo;

    @NotBlank(message = "O campo nome não pode ficar em branco")
    @Size(min = 2, max = 50, message = "O campo título deve conter de 3 a 50 caracteres")
    private String tituloJogo;

    @DecimalMin(value = "0.01", message = "O valor minímo para o campo PREÇO é 0,01")
    @DecimalMax(value = "1000.0", message = "O valor máximo para o campo PREÇO é 1000,0")
    @NotNull(message = "O campo PREÇO é obrigatório")
    private Double precoJogo;

    @ManyToOne
    @NotNull(message = "O campo Gênero é obrigatório")
    private Genero genero;

    @ManyToOne
    @NotNull(message = "O campo Desenvolvedor é obrigatório")
    private Desenvolvedor desenvolvedor;


}

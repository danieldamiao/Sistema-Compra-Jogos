package br.edu.unifio.prova2bim.domain;


import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Table(uniqueConstraints = {
        @UniqueConstraint(columnNames = {"nomeGenero"})
})
@Data
@Entity
public class Genero {
    @Id
    @GeneratedValue
    private Integer codigoGenero;

    @NotBlank(message = "O campo nome não pode ficar em branco")
    @Size(min = 2, max = 50, message = "O campo nome deve conter de 3 a 50 caracteres")
    private String nomeGenero;
}

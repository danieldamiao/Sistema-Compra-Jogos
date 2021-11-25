package br.edu.unifio.prova2bim.domain;

import ch.qos.logback.classic.db.names.ColumnName;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity

@Table(uniqueConstraints = {
@UniqueConstraint(columnNames = {"cpf"})
})

@Data
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer codigo;

    @NotBlank(message = "O campo NOME é obrigatório")
    @Size(min = 2, max = 50, message = "O tamanho do CAMPO nome deve ser entre 2 e 50")
    private String nome;

    @NotBlank(message = "O campo CPF é obrigatório")
    private String cpf;

    @NotBlank(message = "O campo E-MAIL é obrigatório")
    @Size(min = 2, max = 50, message = "O tamanho do campo E-MAIL deve ser entre 2 e 50")
    @Email
    private String email;

    @NotBlank(message = "O campo SENHA é obrigatório")
    @Size(min = 6, max = 20, message = "O do campo SENHA deve conter pelo menos 6 caracteres")
    private String senha;
}

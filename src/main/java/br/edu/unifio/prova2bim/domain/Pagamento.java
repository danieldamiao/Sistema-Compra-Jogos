package br.edu.unifio.prova2bim.domain;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.*;
//import java.time.LocalDate;
//import java.time.LocalDateTime;

@Entity
@Table(uniqueConstraints = {
        @UniqueConstraint(columnNames = {"codigo"})
})
@Data
public class Pagamento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer codigo;

    //@NotNull(message = "O campo hora é obrigatório")
    //@FutureOrPresent(message = "a data informada deve ser presente ou futura")
    //private LocalDateTime hora;

    @NotBlank(message = "O campo numero do cartão é obrigatório")
    @Size(min = 2, max = 50, message = "O tamanho do campo deve ser entre 3 e 50")
    private String cartao;

    @ManyToOne
    @NotNull(message = "o campo compra é obrigatorio")
    private Compra compra;

    /*@ManyToOne
     @NotNull(message = "o campo hora é obrigatorio")
    private Hora hora;*/
}

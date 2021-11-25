package br.edu.unifio.prova2bim.repository;
import br.edu.unifio.prova2bim.domain.Pagamento;
import org.springframework.data.jpa.repository.JpaRepository;
public interface PagamentoRepository  extends JpaRepository<Pagamento, Integer>{
}

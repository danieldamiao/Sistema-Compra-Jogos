package br.edu.unifio.prova2bim.repository;

import br.edu.unifio.prova2bim.domain.Desenvolvedor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DesenvolvedorRepository extends JpaRepository<Desenvolvedor, Integer> {
}

package br.edu.unifio.prova2bim.repository;

import br.edu.unifio.prova2bim.domain.Jogo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JogoRepository extends JpaRepository<Jogo, Integer> {
}

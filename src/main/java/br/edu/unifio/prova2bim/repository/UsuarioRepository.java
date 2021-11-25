package br.edu.unifio.prova2bim.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import br.edu.unifio.prova2bim.domain.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {
}

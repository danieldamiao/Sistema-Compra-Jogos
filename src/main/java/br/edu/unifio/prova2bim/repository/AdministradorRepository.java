package br.edu.unifio.prova2bim.repository;

import br.edu.unifio.prova2bim.domain.Administrador;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AdministradorRepository extends JpaRepository<Administrador, Integer> {
}

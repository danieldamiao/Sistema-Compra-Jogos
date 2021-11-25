package br.edu.unifio.prova2bim.bean;

import br.edu.unifio.prova2bim.domain.Genero;
import br.edu.unifio.prova2bim.repository.GeneroRepository;
import br.edu.unifio.prova2bim.repository.GeneroRepository;
import lombok.Data;
import org.omnifaces.util.Faces;
import org.omnifaces.util.Messages;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import javax.faces.view.ViewScoped;
import java.util.List;


@Component
@ViewScoped
@Data
public class GeneroBean {
    private Genero genero;
    private List<Genero> generos;

    @Autowired
    private GeneroRepository generoRepository;

    public void novo(){
        genero =  new Genero();
    }

    public void Listar(){
        generos = generoRepository.findAll(Sort.by(Sort.Direction.ASC, "codigoGenero"));
    }
    public void salvar(){
        try {
            generoRepository.save(genero);
            Messages.addGlobalInfo("Sucesso");
        }
        catch (DataIntegrityViolationException exception){
            Messages.addGlobalError("Gênero Duplicado");
        }
    }
    public void excluir(){
        try {
            generoRepository.deleteById(genero.getCodigoGenero());
            Messages.addFlashGlobalInfo("Gênero removido com Sucesso");

            Faces.navigate("genero-listar.xhtml?faces-redirect=true");
        } catch (DataIntegrityViolationException exception) {
            Messages.addGlobalError("Gênero vinculado com outros registros");
        }
    }

    public void voltar(){
        Faces.navigate("genero-listar.xhtml?faces-redirect=true");
    }
    public void selecionarEdicao(Genero genero){
        Faces.setFlashAttribute("genero", genero);
        Faces.navigate("genero-editar.xhtml?faces-redirect=true");
    }

    public void selecionarExclusao(Genero genero){
        Faces.setFlashAttribute("genero", genero);
        Faces.navigate("genero-excluir.xhtml?faces-redirect=true");
    }

    public void carregarEdicao(){
        genero = Faces.getFlashAttribute("genero");

        if (genero == null){
            Faces.navigate("genero-listar.xhtml?faces-redirect=true");
        }
    }
}

package br.edu.unifio.prova2bim.bean;

import br.edu.unifio.prova2bim.domain.Desenvolvedor;
import br.edu.unifio.prova2bim.repository.DesenvolvedorRepository;
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
public class DesenvolvedorBean {
    private Desenvolvedor desenvolvedor;
    private List<Desenvolvedor> desenvolvedores;
    @Autowired
    private DesenvolvedorRepository desenvolvedorRepository;

    public void novo(){
        desenvolvedor =  new Desenvolvedor();
    }
    public void Listar(){
        desenvolvedores = desenvolvedorRepository.findAll(Sort.by(Sort.Direction.ASC, "codigoDev"));
    }
    public void salvar(){
        try {
            desenvolvedorRepository.save(desenvolvedor);
            Messages.addGlobalInfo("Sucesso");
        }
        catch (DataIntegrityViolationException exception){
            Messages.addGlobalError("Desenvolvedor Duplicado");
        }
    }
    public void excluir(){
        try {
            desenvolvedorRepository.deleteById(desenvolvedor.getCodigoDev());
            Messages.addFlashGlobalInfo("Gênero removido com Sucesso");

            Faces.navigate("desenvolvedor-listar.xhtml?faces-redirect=true");
        } catch (DataIntegrityViolationException exception) {
            Messages.addGlobalError("Desenvolvedor vinculado com outros registros");
        }
    }
    public void selecionarEdicao(Desenvolvedor desenvolvedor){
        Faces.setFlashAttribute("desenvolvedor", desenvolvedor);
        Faces.navigate("desenvolvedor-editar.xhtml?faces-redirect=true");
    }

    public void selecionarExclusao(Desenvolvedor desenvolvedor){
        Faces.setFlashAttribute("desenvolvedor", desenvolvedor);
        Faces.navigate("desenvolvedor-excluir.xhtml?faces-redirect=true");
    }

    public void carregarEdicao(){
        desenvolvedor = Faces.getFlashAttribute("desenvolvedor");

        if (desenvolvedor == null){
            Faces.navigate("desenvolvedor-listar.xhtml?faces-redirect=true");
        }
    }

    public void Voltar(){
        Faces.navigate("desenvolvedor-editar.xhtml?faces-redirect=true");
    }
}
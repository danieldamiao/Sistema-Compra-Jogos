package br.edu.unifio.prova2bim.bean;

import br.edu.unifio.prova2bim.domain.Analise;
import br.edu.unifio.prova2bim.domain.Jogo;
import br.edu.unifio.prova2bim.domain.Usuario;
import br.edu.unifio.prova2bim.repository.AnaliseRepository;
import br.edu.unifio.prova2bim.repository.JogoRepository;
import br.edu.unifio.prova2bim.repository.UsuarioRepository;
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

public class AnaliseBean {
    private Analise analise;
    private List<Analise> analises;
    private List<Usuario> usuarios;
    private List<Jogo> jogos;
    @Autowired
    private AnaliseRepository analiseRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private JogoRepository jogoRepository;

    public void novo(){
        analise = new Analise();
        usuarios = usuarioRepository.findAll(Sort.by(Sort.Direction.ASC, "nome"));
        jogos = jogoRepository.findAll(Sort.by(Sort.Direction.ASC, "tituloJogo"));
    }

    public void salvar(){
        try{
            analiseRepository.save(analise);
            Messages.addFlashGlobalInfo("Salvo!");
            Faces.navigate("jogo-listar-compra.xhtml?faces-redirect=true");

        }catch (DataIntegrityViolationException exception){
            Messages.addGlobalError("Analise já existe");
        }
    }

    public void listar(){
        analises = analiseRepository.findAll(Sort.by(Sort.Direction.ASC, "codigo"));
    }

    public void selectEdicao(Analise analise){
        Faces.setFlashAttribute("analise", analise);
        Faces.navigate("analise-edicao.xhtml?faces-redirect=true");
    }

    public void selectExclusao(Analise analise){
        Faces.setFlashAttribute("analise", analise);
        Faces.navigate("analise-exclusao.xhtml?faces-redirect=true");
    }

    public void carregar(){
        analise = Faces.getFlashAttribute("analise");
        usuarios = usuarioRepository.findAll(Sort.by(Sort.Direction.ASC, "nome"));

        if(analise == null){
            Faces.navigate("analise-listagem.xhtml?faces-redirect=true");
        }
    }

    public void excluir(){
        try{
            analiseRepository.deleteById(analise.getCodigo());
            Messages.addFlashGlobalInfo("Analise removida com sucesso!");
            Faces.navigate("analise-listagem.xhtml?faces-redirect=true");
        }catch (DataIntegrityViolationException exception){
            Messages.addGlobalError("Analise não pôde ser removida");
        }
    }
}

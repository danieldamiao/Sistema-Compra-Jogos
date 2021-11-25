package br.edu.unifio.prova2bim.bean;

import br.edu.unifio.prova2bim.domain.*;
import br.edu.unifio.prova2bim.repository.*;
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

public class CompraBean {

    private Compra compra;
    private Usuario usuario;
    private List<Jogo> jogos;
    private List<Compra> compras;
    private List<Genero> generos;
    private List<Usuario> usuarios;
    private List<Desenvolvedor> desenvolvedores;

    @Autowired
    CompraRepository compraRepository;

    @Autowired
    DesenvolvedorRepository desenvolvedorRepository;

    @Autowired
    GeneroRepository generoRepository;

    @Autowired
    JogoRepository jogoRepository;

    @Autowired
    UsuarioRepository usuarioRepository;

//METODO PARA CRIAR NOVA COMPRA
    public void novo(){
        compra = new Compra();
        generos = generoRepository.findAll(Sort.by(Sort.Direction.ASC, "nomeGenero"));
        jogos = jogoRepository.findAll(Sort.by(Sort.Direction.ASC, "tituloJogo"));
        desenvolvedores = desenvolvedorRepository.findAll(Sort.by(Sort.Direction.ASC, "nomeDev"));
        usuarios = usuarioRepository.findAll(Sort.by(Sort.Direction.ASC, "nome"));
        compras = compraRepository.findAll(Sort.by(Sort.Direction.ASC, "codigoCompra"));
    }

    //METODO PARA SALVAR
    public void salvar(){
        try{
            compraRepository.save(compra);
            Messages.addFlashGlobalInfo("Salvo!");
            Faces.navigate("compra-listagem.xhtml?faces-redirect=true");
        }catch (DataIntegrityViolationException exception) {
            Messages.addGlobalError("Compra já existe");
        }
    }

    //METODO PARA LISTAR
    public void listar(){
        compras = compraRepository.findAll(Sort.by(Sort.Direction.ASC, "codigoCompra"));
        jogos = jogoRepository.findAll(Sort.by(Sort.Direction.ASC, "tituloJogo"));
        usuarios = usuarioRepository.findAll(Sort.by(Sort.Direction.ASC, "nome"));
    }

    public void selecionarEdicao (Compra compra){
        Faces.setFlashAttribute("compra", compra);
        Faces.navigate("compra-edicao.xhtml?faces-redirect=true");
    }
    public void selecionarExclusao (Compra compra){
        Faces.setFlashAttribute("compra", compra);
        Faces.navigate("compra-exclusao.xhtml?faces-redirect=true");
    }


    public void carregar(){

        compra = Faces.getFlashAttribute("compra");
        usuarios = usuarioRepository.findAll(Sort.by(Sort.Direction.ASC, "nome"));
        jogos = jogoRepository.findAll(Sort.by(Sort.Direction.ASC, "tituloJogo"));

       /* if(compra == null){
            Faces.navigate("jogo-listar.xhtml?faces-redirect=true");
        }*/
    }


    public void excluir(){
        try{
            compraRepository.deleteById(compra.getCodigoCompra());
            Messages.addFlashGlobalInfo("Compra removida com sucesso!");
            Faces.navigate("compra-listagem.xhtml?faces-redirect=true");
        }catch (DataIntegrityViolationException exception){
            Messages.addGlobalError("Compra não pôde ser removido");
        }
    }

    public void salvarPagamento(){
        try{
            compraRepository.save(compra);
            Messages.addFlashGlobalInfo("Salvo!");
            Faces.navigate("pagamento-novo.xhtml?faces-redirect=true");
        }catch (DataIntegrityViolationException exception) {
            Messages.addGlobalError("Compra já existe");
        }
    }


}

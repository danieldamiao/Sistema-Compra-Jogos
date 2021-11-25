package br.edu.unifio.prova2bim.bean;


import br.edu.unifio.prova2bim.HibernateUtil;
import br.edu.unifio.prova2bim.domain.*;
import br.edu.unifio.prova2bim.repository.DesenvolvedorRepository;
import br.edu.unifio.prova2bim.repository.GeneroRepository;
import br.edu.unifio.prova2bim.repository.GeneroRepository;
import br.edu.unifio.prova2bim.repository.JogoRepository;
import lombok.Data;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperPrintManager;
import org.omnifaces.util.Faces;
import org.omnifaces.util.Messages;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import javax.faces.view.ViewScoped;
import java.sql.Connection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
@Component
@ViewScoped
public class JogoBean {
    private Jogo jogo;
    private List<Jogo> jogos;
    private List<Genero> generos;
    private List<Desenvolvedor> desenvolvedores;

    @Autowired
    JogoRepository jogoRepository;

    @Autowired
    GeneroRepository generoRepository;

    @Autowired
    DesenvolvedorRepository desenvolvedorRepository;


    public void novo(){
        jogo =  new Jogo();
        generos = generoRepository.findAll(Sort.by(Sort.Direction.ASC, "nomeGenero"));
        desenvolvedores = desenvolvedorRepository.findAll(Sort.by(Sort.Direction.ASC, "nomeDev"));
    }

    public void Listar(){
        jogos = jogoRepository.findAll(Sort.by(Sort.Direction.ASC, "codigoJogo"));
    }

    public void salvar(){
        try {
            jogoRepository.save(jogo);
            Messages.addGlobalInfo("Sucesso");
            Faces.navigate("jogo-listar.xhtml?faces-redirect=true");
        }
        catch (DataIntegrityViolationException exception){
            Messages.addGlobalError("Jogo Duplicado");
        }
    }
    public void listar(){
        jogos = jogoRepository.findAll(Sort.by(Sort.Direction.ASC, "codigoJogo"));
    }
    public void voltar(){
        Faces.navigate("jogo-listar.xhtml?faces-redirect=true");
    }
    public void excluir(){
        try {
            jogoRepository.deleteById(jogo.getCodigoJogo());
            Messages.addFlashGlobalInfo("Jogo removido com Sucesso");
            Faces.navigate("jogo-listar.xhtml?faces-redirect=true");

        } catch (DataIntegrityViolationException exception) {
            Messages.addGlobalError("Jogo vinculado com outros registros");
        }
    }
    public void selecionarEdicao(Jogo jogo){
        Faces.setFlashAttribute("jogo", jogo);
        Faces.navigate("jogo-editar.xhtml?faces-redirect=true");
    }

    public void selecionarExclusao(Jogo jogo){
        Faces.setFlashAttribute("jogo", jogo);
        Faces.navigate("jogo-excluir.xhtml?faces-redirect=true");
    }


    public void carregarEdicao(){
        jogo = Faces.getFlashAttribute("jogo");
        generos = generoRepository.findAll(Sort.by(Sort.Direction.ASC, "nomeGenero"));
        desenvolvedores = desenvolvedorRepository.findAll(Sort.by(Sort.Direction.ASC, "nomeDev"));
        if (jogo == null){
            Faces.navigate("jogo-listar.xhtml?faces-redirect=true");
        }
    }
    public void selecionarAnalise(Jogo jogo){
        Faces.setFlashAttribute("jogo", jogo);
        Faces.navigate("analise-listagem-usuario.xhtml?faces-redirect=true");
    }
    public void compra(Jogo jogo){
        Faces.setFlashAttribute("jogo", jogo);
        Faces.navigate("compra-confirmar.xhtml?faces-redirect=true");
    }

    public void imprimir(){
        try {

            String caminho = Faces.getRealPath("/Prova-Final-master/src/main/webapp/reports/prova.jasper");
            Map<String, Object> parametros = new HashMap<>();
            Connection conexao = HibernateUtil.getConexao();
            JasperPrint relatorio = JasperFillManager.fillReport(caminho, parametros, conexao);
            JasperPrintManager.printReport(relatorio, true);
        }catch (JRException erro){
            Messages.addGlobalError("erro ao gerar relatorio");
            erro.printStackTrace();
        }
    }
}

package br.edu.unifio.prova2bim.bean;

import br.edu.unifio.prova2bim.domain.Compra;
import br.edu.unifio.prova2bim.domain.Jogo;
import br.edu.unifio.prova2bim.domain.Pagamento;
//import br.edu.unifio.pagamento.domain.Compra;
//import br.edu.unifio.pagamento.repository.CompraRepository;
import br.edu.unifio.prova2bim.repository.CompraRepository;
import br.edu.unifio.prova2bim.repository.JogoRepository;
import br.edu.unifio.prova2bim.repository.PagamentoRepository;
import lombok.Data;
import org.omnifaces.util.Faces;
import org.omnifaces.util.Messages;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import javax.faces.view.ViewScoped;
import java.io.IOException;
import java.util.List;

@Component
@ViewScoped
@Data
public class PagamentoBean {
    private Pagamento pagamento;
    private List<Pagamento> pagamentos;
    private List<Compra> compras;
    private List<Jogo> jogos;

    @Autowired
    private PagamentoRepository pagamentoRepository;

    @Autowired
    private CompraRepository compraRepository;


    public void novo() {
        pagamento = new Pagamento();
        compras = compraRepository.findAll(Sort.by(Sort.Direction.ASC, "codigoCompra"));

    }

    public void listar() {
        pagamentos = pagamentoRepository.findAll(Sort.by(Sort.Direction.ASC, "cartao"));
    }

    public void selecionarEdicao(Pagamento pagamento) {
        Faces.setFlashAttribute("pagamento", pagamento);
        Faces.navigate("pagamento-edicao.xhtml?faces-redirect=true");
    }

    public void selecionarExclusao (Pagamento pagamento){
        Faces.setFlashAttribute("pagamento", pagamento);
        Faces.navigate("pagamento-exclusao.xhtml?faces-redirect=true");
    }

    public void excluir() {
        try {

            pagamentoRepository.deleteById(pagamento.getCodigo());
            Messages.addFlashGlobalInfo("Pagamento removido com sucesso");
            novo();
            Faces.navigate("produto-listagem.xhtml?faces-redirect=true");
        } catch (DataIntegrityViolationException excecao) {
            Messages.addGlobalError("o produto está vinculado com outro registro");
        }
    }

    public void salvar() {
        try {

            pagamentoRepository.save(pagamento);
            Messages.addFlashGlobalInfo("Pagamento salvo com sucesso");
            novo();
            Faces.navigate("jogo-listar-compra.xhtml?faces-redirect=true");
        } catch (DataIntegrityViolationException excecao) {
            Messages.addGlobalError("já existe um cartão cadastrado para o nome informado");
        }
    }

    public void carregar() {
        pagamento = Faces.getFlashAttribute("pagamento");

        /*compra = compraRepository.findAll(Sort.by(Sort.Direction.ASC, "nome"));*/

        if (pagamento == null) {
            Faces.navigate("produto-listagem.xhtml?faces-redirect=true");
        }
    }


}

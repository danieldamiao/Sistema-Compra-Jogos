package br.edu.unifio.prova2bim.bean;

import br.edu.unifio.prova2bim.domain.Jogo;
import br.edu.unifio.prova2bim.domain.Usuario;
import br.edu.unifio.prova2bim.repository.UsuarioRepository;
import lombok.Data;
import org.omnifaces.util.Faces;
import org.omnifaces.util.Messages;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Component
@ViewScoped
@Data

public class UsuarioBean {
    private Usuario usuario;
    private List<Usuario> usuarios;
    private boolean logged = false;
    HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
    @Autowired
    private UsuarioRepository usuarioRepository;

    public void novo(){
        usuario = new Usuario();
    }

    public void salvar(){
        try{
            usuarioRepository.save(usuario);
            Messages.addFlashGlobalInfo("Salvo!");


        }catch (DataIntegrityViolationException exception){
            Messages.addGlobalError("Usuário já existe");
        }
    }

    public void listar(){
        usuarios = usuarioRepository.findAll(Sort.by(Sort.Direction.ASC, "codigo"));
    }

    public void selectEdicao(Usuario usuario){
        Faces.setFlashAttribute("usuario", usuario);
        Faces.navigate("usuario-edicao.xhtml?faces-redirect=true");
    }

    public void selectExclusao(Usuario usuario){
        Faces.setFlashAttribute("usuario", usuario);
        Faces.navigate("usuario-exclusao.xhtml?faces-redirect=true");
    }

    public void carregar(){
        usuario = Faces.getFlashAttribute("usuario");

        if(usuario == null){
            Faces.navigate("usuario-listagem.xhtml?faces-redirect=true");
        }
    }

    public void excluir(){
        try{
            usuarioRepository.deleteById(usuario.getCodigo());
            Messages.addFlashGlobalInfo("Usuário removido com sucesso!");
            Faces.navigate("usuario-listagem.xhtml?faces-redirect=true");
        }catch (DataIntegrityViolationException exception){
            Messages.addGlobalError("Usuário não pôde ser removido");
        }
    }
    public void user(Usuario usuario){
        Faces.setFlashAttribute("usuario", usuario);
        Faces.navigate("loginusuario.xhtml?faces-redirect=true");
    }

    public void adm(Usuario usuario){
        Faces.setFlashAttribute("usuario", usuario);
        Faces.navigate("loginadmin.xhtml?faces-redirect=true");
    }

    public void login() {
        try {
            Faces.navigate("/loginusuario.xhtml");
        } catch (Exception e) {
        }
    }

    public void autenticacaoUsuario() {
        if (usuario != null) {
            boolean encontrado = false;
            Usuario usuarioEncontrado = new Usuario();
            Usuario entradaUsuario = usuario;
            HttpSession session = request.getSession();
            Example<Usuario> informacaoLogin = Example.of(entradaUsuario);
            Iterable<Usuario> resultadoQuery = usuarioRepository.findAll(informacaoLogin);
            for (Usuario e : resultadoQuery) {
                encontrado = true;
                usuarioEncontrado = e;
            }
            if (encontrado == true) {
                if (session.getAttribute("sessao") == null) {
                    session.setAttribute("sessao", usuarioEncontrado);
                    Messages.addFlashGlobalInfo("Login realizado com sucesso!");
                    Faces.navigate("jogo-listar-compra.xhtml?faces-redirect=true");
                } else {
                    Messages.addFlashGlobalInfo("Sessão já iniciada!");
                    Faces.navigate("jogo-listar-compra.xhtml?faces-redirect=true");
                }
            } else {
                Messages.addFlashGlobalError("Usuário não encontrado!");
            }
        }
    }


    public void verificarSessaoUsuario() {
        HttpSession session = request.getSession(false);
        String caminho = request.getRequestURI();
        if (session != null) {
            Usuario sessionUsuario = (Usuario) session.getAttribute("sessao");
            if (session.getAttribute("sessao") != null) {
                logged = true;
            } else {
                logged = false;
                System.out.println(caminho);
                if ((caminho != "/usuario-novo.xhtml") && (caminho != "/loginusuario.xhtml")) {
                    try {
                        Messages.addFlashGlobalInfo("Você precisa estar logado para realizar essa ação!");
                        Faces.redirect("/loginusuario.xhtml");
                    } catch (Exception e) {
                    }
                }
            }
        }
    }


    public void finalizar() {
        HttpSession httpSession = request.getSession(false);
        if (httpSession != null) {
            httpSession.invalidate();
            try {
                Faces.redirect("/home.xhtml");
            } catch (Exception e) {

            }
        }
    }

    public boolean confirmar() {
        return logged;
    }
}


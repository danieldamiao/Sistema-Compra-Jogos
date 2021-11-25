package br.edu.unifio.prova2bim.bean;

import br.edu.unifio.prova2bim.domain.Administrador;
import br.edu.unifio.prova2bim.domain.Usuario;
import br.edu.unifio.prova2bim.repository.AdministradorRepository;
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

public class AdministradorBean {
    private Administrador administrador;
    private List<Administrador> administradors;
    private boolean logged = false;
    HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
    @Autowired
    private AdministradorRepository administradorRepository;

    public void novo(){
        administrador = new Administrador();
    }

    public void salvar(){
        try{
            administradorRepository.save(administrador);
            Messages.addFlashGlobalInfo("Salvo!");
            Faces.navigate("loginadmin.xhtml?faces-redirect=true");

        }catch (DataIntegrityViolationException exception){
            Messages.addGlobalError("Administrador já existe");
        }
    }

    public void listar(){
        administradors = administradorRepository.findAll(Sort.by(Sort.Direction.ASC, "codigo"));
    }

    public void selectEdicao(Administrador administrador){
        Faces.setFlashAttribute("admin", administrador);
        Faces.navigate("admin-edicao.xhtml?faces-redirect=true");
    }

    public void selectExclusao(Administrador administrador){
        Faces.setFlashAttribute("admin", administrador);
        Faces.navigate("admin-exclusao.xhtml?faces-redirect=true");
    }

    public void carregar(){
        administrador = Faces.getFlashAttribute("admin");

        if(administrador == null){
            Faces.navigate("admin-listagem.xhtml?faces-redirect=true");
        }
    }

    public void excluir(){
        try{
            administradorRepository.deleteById(administrador.getCodigo());
            Messages.addFlashGlobalInfo("Administrador removido com sucesso!");
            Faces.navigate("admin-listagem.xhtml?faces-redirect=true");
        }catch (DataIntegrityViolationException exception){
            Messages.addGlobalError("Administrador não pôde ser removido");
        }
    }
    public void user(Administrador administrador){
        Faces.setFlashAttribute("admin", administrador);
        Faces.navigate("loginusuario.xhtml?faces-redirect=true");
    }

    public void adm(Administrador administrador){
        Faces.setFlashAttribute("usuario", administrador);
        Faces.navigate("loginadmin.xhtml?faces-redirect=true");
    }

    public void login() {
        try {
            Faces.navigate("/loginusuario.xhtml");
        } catch (Exception e) {
        }
    }


    public void autenticacaoAdmin() {
        if (administrador != null) {
            boolean encontrado = false;
            Administrador administradorEncontrado = new Administrador();
            Administrador entradaAdmin = administrador;
            HttpSession session = request.getSession();
            Example<Administrador> informacaoLogin = Example.of(administradorEncontrado);
            Iterable<Administrador> resultadoQuery = administradorRepository.findAll(informacaoLogin);
            for (Administrador e : resultadoQuery) {
                encontrado = true;
                administradorEncontrado = e;
            }
            if (encontrado == true) {
                if (session.getAttribute("sessao") == null) {
                    session.setAttribute("sessao", administradorEncontrado);
                    Messages.addFlashGlobalInfo("Login realizado com sucesso!");
                    Faces.navigate("menu-inicial.xhtml?faces-redirect=true");
                } else {
                    Messages.addFlashGlobalInfo("Sessão já iniciada!");
                    Faces.navigate("menu-inicial.xhtml?faces-redirect=true");
                }
            } else {
                Messages.addFlashGlobalError("Usuário não encontrado!");
            }
        }
    }

    public void verificarSessaoAdmin() {
        HttpSession session = request.getSession(false);
        String caminho = request.getRequestURI();
        if (session != null) {
            Administrador sessionAdmin = (Administrador) session.getAttribute("sessao");
            if (session.getAttribute("sessao") != null) {
                logged = true;
            } else {
                logged = false;
                System.out.println(caminho);
                if ((caminho != "/menu-inicial.xhtml") && (caminho != "/loginadmin.xhtml")) {
                    try {
                        Messages.addFlashGlobalInfo("Você precisa estar logado para realizar essa ação!");
                        Faces.redirect("/loginadmin.xhtml");
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


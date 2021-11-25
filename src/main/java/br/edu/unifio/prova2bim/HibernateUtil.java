package br.edu.unifio.prova2bim;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.jdbc.ReturningWork;

import java.sql.Connection;
import java.sql.SQLException;

public class HibernateUtil {
    private static SessionFactory fabricaDeSessoes;
    public static SessionFactory getFabricaDeSessoes() {
        return fabricaDeSessoes;
    }
    public static Connection getConexao(){
        Session sessao = fabricaDeSessoes.openSession();
        Connection conexao = sessao.doReturningWork(new ReturningWork<Connection>() {
            @Override
            public Connection execute(Connection conn) throws SQLException {
                return conn;
            }
        });
        return conexao;
    }
    private static SessionFactory criarFabricaDeSessoes() {
        try {
            Configuration configuracao = new Configuration().configure();
            StandardServiceRegistry registro  =  new StandardServiceRegistryBuilder().applySettings(configuracao.getProperties()).build();
            SessionFactory fabrica = configuracao.buildSessionFactory(registro);
            return fabrica;
        } catch (Throwable ex) {
            System.err.println("Initial SessionFactory creation failed." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }
}

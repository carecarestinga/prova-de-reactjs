/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifrs.restinga.ds.prova;

import br.edu.ifrs.restinga.ds.prova.dao.ComentarioDAO;
import br.edu.ifrs.restinga.ds.prova.dao.PublicacaoDAO;
import br.edu.ifrs.restinga.ds.prova.modelo.Comentario;
import br.edu.ifrs.restinga.ds.prova.modelo.Publicacao;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Inicializador {

    @Autowired
    PublicacaoDAO publicacaoDAO;
    @Autowired
    ComentarioDAO comentarioDAO;

    @PostConstruct
    public void init() throws ParseException {

        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        if (publicacaoDAO.count() == 0) {
            criaPublicacao(1,
                    "Exemplo de publicação com 2 comentários",true,
                    dateFormat.parse("2018-06-20 17:30:00"),
                    criaComentario(
                            dateFormat.parse("2018-06-20 17:35:00"),
                            "Comentário 1"),
                    criaComentario(
                            dateFormat.parse("2018-06-20 17:40:00"),
                            "Comentário 2")
            );
            criaPublicacao(2,
                    "Exemplo de publicação sem comentário",false,
                    dateFormat.parse("2018-06-20 18:30:00")
            );
            criaPublicacao(3,
                    "Exemplo de publicação com 1 comentário",true,
                    dateFormat.parse("2018-06-21 20:00:00"), 
                    criaComentario(
                            dateFormat.parse("2018-06-21 20:10:00"),
                            "Comentário b 1")
            );

        }

    }

    Publicacao criaPublicacao(int id, String texto,boolean podeComentar, Date data, Comentario... comentarios) {
        Publicacao publicacao = new Publicacao();
        publicacao.setId(id);
        publicacao.setPodeComentar(podeComentar);
        publicacao.setTexto(texto);
        publicacao.setData(data);
        if (comentarios != null) {
            publicacao.setComentarios(Arrays.asList(comentarios));
        }
        return publicacaoDAO.save(publicacao);
    }
 
    Comentario criaComentario(Date data, String texto) {

        Comentario comentario = new Comentario();
        comentario.setData(data);
        comentario.setTexto(texto);
        return comentarioDAO.save(comentario);

    }
}

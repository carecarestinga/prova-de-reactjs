/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifrs.restinga.ds.prova.controller;

import br.edu.ifrs.restinga.ds.prova.dao.ComentarioDAO;
import br.edu.ifrs.restinga.ds.prova.dao.PublicacaoDAO;
import br.edu.ifrs.restinga.ds.prova.erros.NaoEncontrado;
import br.edu.ifrs.restinga.ds.prova.erros.RequisicaoInvalida;
import br.edu.ifrs.restinga.ds.prova.modelo.Comentario;
import br.edu.ifrs.restinga.ds.prova.modelo.Publicacao;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author jezer
 */
@RestController
@RequestMapping("/api")
public class Publicacoes {

    @RequestMapping(path = "/publicacoes/", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public Iterable<Publicacao> listar() {
        return publicacaoDAO.findAll();
    }

    @Autowired
    PublicacaoDAO publicacaoDAO;
    @Autowired
    ComentarioDAO comentarioDAO;

    @RequestMapping(path = "/publicacoes/{id}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public Publicacao recuperar(@PathVariable int id) {

        Optional<Publicacao> findById = publicacaoDAO.findById(id);
        if (findById.isPresent()) {
            return findById.get();
        } else {
            throw new NaoEncontrado("Não encontrado");

        }
    }

    @RequestMapping(path = "/publicacoes/", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public Publicacao inserir(@RequestBody Publicacao publicacao) {
        publicacao.setId(0);
        if(publicacao.getTexto()==null||publicacao.getTexto().isEmpty()) {
           throw  new RequisicaoInvalida("Texto não pode ser vazio!");
        }
        publicacao.setData(new Date());
        Publicacao publicacaoComId = publicacaoDAO.save(publicacao);
        return publicacaoComId;
    }

    @RequestMapping(path = "/publicacoes/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void apagar(@PathVariable int id) {

        if (!publicacaoDAO.existsById(id)) {
            throw new NaoEncontrado("ID não encontrada");
        }

        publicacaoDAO.deleteById(id);
    }

    @RequestMapping(path = "/publicacoes/{idPublicacao}/comentarios/",
            method = RequestMethod.GET)
    public Iterable<Comentario> listarComentario(@PathVariable int idPublicacao) {
        return this.recuperar(idPublicacao).getComentarios();
    }

    @RequestMapping(path = "/publicacoes/{idPublicacao}/comentarios/",
            method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public Comentario inserirComentario(@PathVariable int idPublicacao,
            @RequestBody Comentario comentario) {
        comentario.setId(0);
        
        if(comentario.getTexto()==null||comentario.getTexto().isEmpty()) {
           throw  new RequisicaoInvalida("Texto não pode ser vazio!");
        }

        Publicacao publicacao = this.recuperar(idPublicacao);
        
        if(!publicacao.isPodeComentar()) {
            throw  new RequisicaoInvalida("Publicação não aceita comentários!");
        }
        
        comentario.setData(new Date()); 
        Comentario comentarioSalvo = comentarioDAO.save(comentario);
        
        publicacao.getComentarios().add(comentarioSalvo);
        publicacaoDAO.save(publicacao);
        return comentarioSalvo;
    }

    @RequestMapping(path = "/publicacoes/{idPublicacao}/comentarios/{id}", method = RequestMethod.GET)
    public Comentario recuperarComentario(@PathVariable int idPublicacao, @PathVariable int id) {
        Optional<Comentario> findById = comentarioDAO.findById(id);
        if (findById.isPresent()) {
            return findById.get();
        } else {
            throw new NaoEncontrado("Não encontrado");
        }
    }

    @RequestMapping(path = "/publicacoes/{idPublicacao}/comentarios/{id}",
            method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.OK)
    public void atualizarComentario(@PathVariable int idPublicacao, @PathVariable int id, @RequestBody Comentario comentario) {
        if (comentarioDAO.existsById(id)) {
            comentario.setId(id);
            comentarioDAO.save(comentario);
        } else {
            throw new NaoEncontrado("Não encontrado");
        }

    }

    @RequestMapping(path = "/publicacoes/{idPublicacao}/comentarios/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.OK)
    public void apagarComentario(@PathVariable int idPublicacao,
            @PathVariable int id) {

        Comentario comentarioAchada = null;
        Publicacao publicacao = this.recuperar(idPublicacao);
        List<Comentario> comentarios = publicacao.getComentarios();
        for (Comentario comentarioLista : comentarios) {
            if (id == comentarioLista.getId()) {
                comentarioAchada = comentarioLista;
            }
        }
        if (comentarioAchada != null) {
            publicacao.getComentarios().remove(comentarioAchada);
            publicacaoDAO.save(publicacao);
        } else {
            throw new NaoEncontrado("Não encontrado");
        }
    }

}

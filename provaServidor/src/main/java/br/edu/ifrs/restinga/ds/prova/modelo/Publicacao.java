
package br.edu.ifrs.restinga.ds.prova.modelo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class Publicacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
 
    private String texto;
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm") 
    @Temporal(TemporalType.TIMESTAMP)
    private Date data;
   
    private boolean podeComentar;
              
    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comentario> comentarios;

    @JsonProperty
    int getQuantidadeComentarios() {
        return this.getComentarios()==null?0:this.getComentarios().size();
    }
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public List<Comentario> getComentarios() {
        return comentarios;
    }

    public void setComentarios(List<Comentario> comentarios) {
        this.comentarios = comentarios;
    }

    public boolean isPodeComentar() {
        return podeComentar;
    }

    public void setPodeComentar(boolean podeComentar) {
        this.podeComentar = podeComentar;
    }

    
    
}

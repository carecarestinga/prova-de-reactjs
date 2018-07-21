import React, { Component } from 'react';
import API from '../API';
import PublicacaoLista from './PublicacaoLista';
import PublicacaoItem from './PublicacaoItem';



export default class PublicacaoPagina extends Component {
  
    constructor() {
        super();
        this.state = {
            publicacoes: [],
        }
        
    }

    componentDidMount() {  	
        this.listar();
    }

    tratarErro(erro) {
        console.log(erro.response);
        if(erro.response.data.message)
            alert(erro.response.data.message);
        else
            alert(erro.response.data);
    
    }

    listar() {
        API.get("publicacoes/").then(
            (retorno) => {

                this.setState({ publicacoes: retorno.data });
                
            }
        ).catch((erro) => this.tratarErro(erro));
    }

    inserir(item) {
        API.post("publicacoes/", item).then(
            ()=>{
                this.listar();
                this.limpar();
            }
        ).catch((erro) => this.tratarErro(erro));
        
    }

    atualizar(item) {
        API.put("publicacoes/"+item.id,item).then(
            ()=>{
                this.listar();
                this.limpar();
                this.listarComentarios(item);
            }
        ).catch((erro) => this.tratarErro(erro));
    }

    excluir(item) {
        API.delete("publicacoes/"+item.id).then(
            ()=>{
                this.listar();
                this.limpar();
            }
        ).catch((erro) => this.tratarErro(erro));
    }


    editar(item) {
        this.setState({
            exibirFormulario:true,
            editar:item
        });
    }

    confirmar(item) {
        if(item.id) {
            this.atualizar(item);
        } else {
            this.inserir(item);
        }
    }

    limpar() {
        this.setState({
            exibirFormulario:false,
            editar:null
        });
    }

    novo() {
        this.setState({
            exibirFormulario:true,
            editar:null
        });
    }


    render() {

    return (

    <div>


    <PublicacaoLista onExcluir={(item)=>this.excluir(item)}
                                  itens={this.state.publicacoes}
                                  comentarios={this.state.comentarios} />
                                  <br/><br/>
          
    <PublicacaoItem onConfirmar={(item) =>this.confirmar(item)} />
                   
    </div>
      );

    }

}
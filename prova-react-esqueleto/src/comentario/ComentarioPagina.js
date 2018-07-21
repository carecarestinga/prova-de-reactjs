import React, { Component } from 'react';
import API from '../API';
import ComentarioLista from './ComentarioLista';
import ComentarioItem from './ComentarioItem';



export default class ComentarioPagina extends Component {
  
    constructor(props) {
        super(props);
        this.state = {      
            publicacoes:[],
            comentarios: []
        }
        
    }

    componentDidMount() {
        this.listarComentarios();
        
    }

    tratarErro(erro) {
        console.log(erro.response);
        if(erro.response.data.message)
            alert(erro.response.data.message);
        else
            alert(erro.response.data);
    
    }

    listarComentarios() {
        API.get("publicacoes/" + this.props.pgId + "/comentarios/").then(
            (retorno) => {
                this.setState({ comentarios: retorno.data });
            }
        ).catch((erro) => this.tratarErro(erro));
    }

    inserir(item) {
        API.post("publicacoes/" + this.props.pgId + "/comentarios/",item).then(
            ()=>{
                this.listar();
                this.limpar();
            }
        ).catch((erro) => this.tratarErro(erro));
        
    }

    atualizar(item) {
        API.put("publicacoes/" + this.props.pgId + "/comentarios/"+item.id,item).then(
            ()=>{
                this.listar();
                this.limpar();
            }
        ).catch((erro) => this.tratarErro(erro));
    }

    excluir(item) {
        API.delete("publicacoes/" + this.props.pgId + "/comentarios/"+item.id).then(
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

    abrirForm() {
        this.setState({
            exibirFormulario:true,
            editar:null
        });
    }

    render() {
      

    return (
    <div>
        <button onClick={()=>this.abrirForm()} >
            comentarios({this.state.comentarios.length})
        </button>

        { this.state.exibirFormulario ?
        <div>
        <ComentarioLista onExcluir={(item)=>this.excluir(item)}
                                  listaCom={this.state.comentarios}
                                  listaPub={this.props.publicacoes} />
    {/* COMENTÁRIO{"testeIdPub =" + this.props.pgId} JSX */}
        <ComentarioItem onConfirmar={(item) =>this.confirmar(item)}
       onSubmit={(evento) => { evento.preventDefault(); this.componentWillMount()}} />   
        </div>    : " " }
                 
     </div>
      );

    }

}
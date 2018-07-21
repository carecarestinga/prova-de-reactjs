import React, { Component } from 'react';

import ComentarioPagina from '../comentario/ComentarioPagina'
import ComentarioLista from '../comentario/ComentarioLista'

export default class PublicacaoLista extends Component {

  constructor(props) {
          super(props);
          this.state = { comentarios:{} };  		
      }

    componentDidMount() {  
        this.setState({
        comentarios: this.props.itens
        });
    }
          

   novo() {
        this.setState({
            exibirFormulario:true,
            editar:null
        });
    }


render() {
if (!this.props.itens || !this.props.itens.length) {
    return <div>Vazio!</div>;
        } else {
        return (
        <div>
   
          {this.props.itens.map((publicacao) => { return <ul key={publicacao.id }>
   
            <li>{publicacao.id}--{publicacao.texto}---{publicacao.data}
                 <button onClick={()=>this.props.onExcluir(publicacao)}>ExcluirPub</button> </li>  
                  { publicacao.podeComentar  ?
                  	<div>

                        <ComentarioPagina
    					               pgId={this.state.getId= publicacao.id }
                             publicacoes ={ this.props.itens } /> 

                    </div> :  ""}
            </ul>;
          
          })}
               
        </div>
          );
        }
    }
}
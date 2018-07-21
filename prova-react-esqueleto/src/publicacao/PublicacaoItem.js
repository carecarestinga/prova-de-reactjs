import React, { Component } from 'react';

export default class PublicacaoItem extends Component {
    constructor() {
        super();

        this.state = {
            texto: "",
            quantidadeComentarios: "",
            podeComentar:"",
            data: ''
        };
    }

    static getDerivedStateFromProps(nextProps, prevState) {
        if(nextProps.editar) {
            return {
                id:nextProps.editar.id,
                texto:nextProps.editar.texto,
                podeComentar:nextProps.editar.podeComentar
            }
        }
        return null;
    }

    setValor(campo, valor) {
        this.setState(
                (anterior) =>{
                    anterior[campo]=valor;
                    return anterior;
                }
                );
        
    }
    
    confirmar() {
        if(this.state.texto ){
                this.props.onConfirmar({
                    id:this.state.id,
                    texto:this.state.texto,
                    data:this.state.data,
                    podeComentar:this.state.podeComentar
                });
                } else {
                    alert("Preencha todos os campos!");
                }
       
    }
  
    render() {
        return <form onSubmit={(evento) => { evento.preventDefault(); this.confirmar()}}>
            <label>Adicionar Publicação:</label>
            <br/><br/>
             <input
                id="podeComentar"
                type="checkbox"
                checked={this.props.podeComentar}
                onChange={(evento) => this.setValor("podeComentar", evento.target.checked)} />
            <label htmlFor="podeComentar" >Pode comentar </label>
            <br/><br/>
            <input type="text" value={this.props.texto} required
                onChange={(evento) => this.setValor("texto", evento.target.value)} />
          

            <button type="submit">Adicionar</button>
        </form>
    }

}

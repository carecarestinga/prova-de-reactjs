import React, { Component } from 'react';

export default class ComentarioItem extends Component {
    constructor() {
        super();

        this.state = {
            id: null,
            texto: "",
            data: ''
        };
    }

    static getDerivedStateFromProps(nextProps, prevState) {
        if(nextProps.editar) {
            return {
                id:nextProps.editar.id,
                texto:nextProps.editar.texto,
                data:nextProps.editar.data,
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
                    texto:this.state.texto
                });
                } else {
                    alert("Preencha todos o campo texto!");
                }
       
    }
  
    render() {
        return <form onSubmit={(evento) => { evento.preventDefault(); this.confirmar()}}>
        
            <input type="text" value={this.props.texto} required
                onChange={(evento) => this.setValor("texto", evento.target.value)} />
          

            <button type="submit">AdicionarComentario</button>
        </form>
    }

}

import React, { Component } from 'react';
import API from '../API';
import ComentarioItem from './ComentarioItem'

export default class ComentarioLista extends Component {


  constructor(props) {
          super(props);
  
          this.state = { listaPub:[] };
  
  }
        
  render() {

    return (
      <div>
 
        {this.props.listaCom.map((comentario) => {
                        return <ul key={comentario.id}>
                             
            <div>
              <ul>
                  <li>{comentario.id}--{comentario.texto}---{comentario.data}
                    <button onClick={()=>this.props.onExcluir(comentario)}>ExcluirCom</button>
                  </li>
              </ul>
             
              </div>
                           
                                </ul>; 
                    })}
                
      </div>
            );
        }
    }

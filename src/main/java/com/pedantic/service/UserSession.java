package com.pedantic.service;

import java.io.Serializable;

import javax.ejb.PostActivate;
import javax.ejb.PrePassivate;
import javax.ejb.Stateful;

@Stateful /**
           * É o exato Oposto do @Stateless, ou seja Após invocação, ele mantem o Estado,
           * é indicado implementar o Serializable, para diferenciar as INSTANCIAS criadas, pois elas se mantem.
           * Por mais que tenha 10 metodos aqui, será uma unica conexão entre o Cliente e o Servidor
           */
public class UserSession implements Serializable {
          /**Neste caso, se fosse um loja virtual, onde visita-se varias paginas de diferentes produtos, a conexão se manterá ate que o commit seja feito
           * caso haja erro o CDI irá lançar este erro, é parecido com @Session. Aqui, se mantem a conexão apos a invocação do Metodo.
           */
    public String getCurrentUserName() {
        return "";
    }

 /**@Stateful tb tem 2 Filecycle */

   @PrePassivate
   private void passivate() {

   }

   @PostActivate
   private void active() {}


}

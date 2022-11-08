package com.pedantic.service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.Singleton;

@Singleton /**
            * è o mesmo que o Scopo do Application, somente 1 instancia é gerada entre o
            * Cliente e Server durante o TimeLife e depois da execução do metodos é fechada toda conexão.
            Esta conexão é Eagerly.
            @Singleton é usado para gerenciar compartilhamento de DATA
            */
public class ApplicationState {



     /**@Stateless traz mais 2 @notações @PostConstruct e @PreDestroy  */


     @PostConstruct /*É o Lifecycle, esta @notação faz com que este Metodo Init() seja  injetado ANTES da a injeção do EntityManager */   
     private void init() {
     }
 
 
     @PreDestroy /**É outro Lifecycle, esta @notação exculta os metodo antes de mandar para guarbadColletion do Java */
     private void destroy() {
 
     }

}

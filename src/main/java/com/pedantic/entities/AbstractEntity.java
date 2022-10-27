package com.pedantic.entities;

import javax.persistence.*;
import java.io.Serializable;

@MappedSuperclass /**Transforma esta class em uma SuperClass e ao ser Extendida em qualquer Outra Classe, faz com que Seja Sobre Escrita em que extendeu, desta forma esta classe que extendeu Não precisa 
Cria os Atributos que já existente na SuperCLass , que se tornarão colunas da Db de que Extender a SuperClass*/
public abstract class AbstractEntity implements Serializable {


    @Id // Igual ao do Spring  Identifica e fala para Mapeamento que é um ID da table
    @GeneratedValue(strategy = GenerationType.AUTO) // Iqual ao do Spring
    protected Long id;

    protected String userEmail;

    @Version
    protected Long version;

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }


//    @Id
//    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}

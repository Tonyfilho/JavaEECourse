package com.pedantic.config;

import javax.enterprise.inject.Produces;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

public class Producers {
    /** Tenho uma class chamada de Producers que cria EntityManager */
    /*
     * @PersistenceContext é uma forma CURTA da @notação. pois so temos 1
     * <persistence-unit name="pu" transaction-type="JTA"> ou seja 1 ligação com a 1 DB,caso tivessemos mais 1
     * teriamos que declará la forma explicativa  @PersistenceContext(unitName = "pu")
     */

    @Produces
    @PersistenceContext(unitName = "pu")
    EntityManager entityManager;  /**Injetando aqui o EntityManager,temos context de injeção no EntityManager com a DB */

}

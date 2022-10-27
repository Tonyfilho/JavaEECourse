package com.pedantic.entities;


import javax.persistence.AttributeOverride;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import java.math.BigDecimal;

@Entity //transforma uma class em um Entidade
@AttributeOverride(name = "id", column = @Column(name = "tax_id"))  /**Quando Extendemos esta Class AbstractEntity,
 recebemos por Herença os Atributos,e temos que OVERRIDE este atributos extends AbstractEntity, pos ito que temos esta @notação*/

// @Table(name = "IRS_SALARY_TAX_TABLE",schema = "HR")A Table @notation É Construtor p/ Mapear a relação da Tabela da DB com a sua ENTITY, que vale é que está na anotação e não o nome da Entidade
public class Tax extends AbstractEntity {

    @Column(name = "TAX_RATE") /**Percionaliza o nome da entidate na DB, Será TAX_RATE e não taxRate */
    private BigDecimal taxRate;


    public BigDecimal getTaxRate() {
        return taxRate;
    }

    public void setTaxRate(BigDecimal taxRate) {
        this.taxRate = taxRate;
    }
}

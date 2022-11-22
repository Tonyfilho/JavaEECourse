package com.pedantic.config;

import java.time.LocalDate;
import java.time.Period;

import javax.persistence.PrePersist;

import com.pedantic.entities.Employee;

/**Exemplo de Lifecycle class 247 */
public class EmployeeListener {

    /**
     * Esta Classe é um Entitie LISTENER que Substitui funções dos Atributos 
     * Cria -se um metodo que ira SETAR um atributo na entidade Employee
     * na propriedade do atributo do SET iremos passar um metodo para persistir na tabela
     */
    @PrePersist
    public void calculateEmployeeAge(Employee employee) {
        employee.setAge(Period.between(employee.getDateOfBirth(), LocalDate.now()).getYears());   

        
    }
    
}

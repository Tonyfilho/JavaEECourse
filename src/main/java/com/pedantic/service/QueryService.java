package com.pedantic.service;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import com.pedantic.entities.Department;
import com.pedantic.entities.Employee;

@Stateless /**
            * a @notação @Stateless tem a mesma função Scope @Request, onde cada instancia
            * é destruida pelo CDI apos o Uso. Em cada Invocação de metodos teremos uma conexão entre o Cliente e Servidor.Assim que usanda e destruida.
            * Cria uma Transaction data com o CDI e DB para cada metodo aqui criado, abre e fecha sem precisarmos nos
            * preocuparmos com isto. Commit, rollback, so precisamos nos preocupar com a Logica dentro dos metodos
            */
public class QueryService {


    /**@inject EntityManager tem acesso metodos da DB */
    @Inject
    EntityManager entityManager;


    /**@Stateless traz mais 2 @notações @PostConstruct e @PreDestroy  */


    @PostConstruct /*É o Lifecycle, esta @notação faz com que este Metodo Init() seja  injetado ANTES da a injeção do EntityManager */   
    private void init() {
    }


    @PreDestroy /**É outro Lifecycle, esta @notação exculta os metodo antes de mandar para guarbadColletion do Java */
    private void destroy() {

    }

    /*FindById */
    public Department findByIdDepartment(Long id ) {
        return entityManager.find(Department.class, id);
    }

    public Employee findEmployeeById(long id) {
        return entityManager.find(Employee.class, id);
    }
    
    public List<Employee> getEmployees() {
        return null;
    }

    public List<Department> getDepartments() {
        return null;
    }

}

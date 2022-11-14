package com.pedantic.service;

import java.util.Collection;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import com.pedantic.entities.Department;
import com.pedantic.entities.Employee;
import com.pedantic.entities.ParkingSpace;

@Stateless /**
            * a @notação @Stateless tem a mesma função Scope @Request, onde cada instancia
            * é destruida pelo CDI apos o Uso. Em cada Invocação de metodos teremos uma
            * conexão entre o Cliente e Servidor.Assim que usanda e destruida.
            * Cria uma Transaction data com o CDI e DB para cada metodo aqui criado, abre e
            * fecha sem precisarmos nos
            * preocuparmos com isto. Commit, rollback, so precisamos nos preocupar com a
            * Logica dentro dos metodos
            */
public class QueryService {

    /** @inject EntityManager tem acesso metodos da DB */
    @Inject
    EntityManager entityManager;

    /** @Stateless traz mais 2 @notações @PostConstruct e @PreDestroy */

    @PostConstruct /*
                    * É o Lifecycle, esta @notação faz com que este Metodo Init() seja injetado
                    * ANTES da a injeção do EntityManager
                    */
    private void init() {
    }

    @PreDestroy /**
                 * É outro Lifecycle, esta @notação exculta os metodo antes de mandar para
                 * guarbadColletion do Java
                 */
    private void destroy() {

    }

    /**
     * Foi Criando uma @QueryName GET_DEPARTMENT_LIST lá na Class Department e será
     * injetada aqui Pelo EntityManager e invocada dentro do metodo
     * getAllDepartments()
     */
    public List<Department> getAllDepartments() {
        /**
         * Posso retorna assim ou dentro de uma Local Variavel do tipo <Department>
         * TypedQuery<Department>
         */
        TypedQuery<Department> localVarNameQuery = entityManager.createNamedQuery(Department.GET_DEPARTMENT_LIST,
                Department.class);
        /** ou retornar direto com o .getResultList() */
        return entityManager.createNamedQuery(Department.GET_DEPARTMENT_LIST, Department.class).getResultList();
    }

    /**
     * Foi Criando uma @QueryName GET_DEPARTMENT_NAMES lá na Class Department e será
     * injetada aqui Pelo EntityManager e invocada dentro do metodo
     * getAllDepartmentsNames().
     * OBS: O 2ª Paramentro do entityManager.createNamedQuery(...) é o TIPO de dado,
     * neste caso aqui STRING
     * Vai receber um Department e converter em String
     */
    public List<String> getAllDepartmentNames() {
        return entityManager.createNamedQuery(Department.GET_DEPARTMENT_NAMES, String.class).getResultList();
    }

    /** Foi criado uma @QueryName GEL_ALL-PARKING_SPACE em Department */
    public List<ParkingSpace> getAllAllocateParkingSpace() {
        return entityManager.createQuery(Employee.GET_ALL_PARKING_SPACE, ParkingSpace.class).getResultList();
    }

    /**
     * Foi criado uma @QueryName Employee.EMPLOYEE_PROJECTION, que Retornará uma
     * Collection de Array de Objeto, por terem Mais de 1 tipo de dados
     */
    public Collection<Object[]> GetEmployeeProjection() {
        return entityManager.createQuery(Employee.EMPLOYEE_PROJECTION, Object[].class).getResultList();
    }


    

    /* FindById */
    public Department findByIdDepartment(Long id) {
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

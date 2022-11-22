package com.pedantic.service;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import com.pedantic.entities.Allowance;
import com.pedantic.entities.Department;
import com.pedantic.entities.Employee;
import com.pedantic.entities.EmployeeDetails;
import com.pedantic.entities.ParkingSpace;
import com.pedantic.entities.Project;

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

    /**
     * Foi criado uma @QueryName EMPLOYEE_CONSTRUCTOR_PROJECTION, que Retornará uma
     * Collection de Array de Objeto usando construtor da class
     */
    public List<EmployeeDetails> getEmployeeProjectionDetails() {
        return entityManager.createQuery(Employee.EMPLOYEE_CONSTRUCTOR_PROJECTION, EmployeeDetails.class)
                .getResultList();
    }

    /**
     * Foi criado uma @QueryName que Recebe PARAMENTRO (name=
     * Employee.GET_WHERE_CLAUSE_EMPLOYEE_PARAMITERS_ALLOWANCES que receberá um
     * parametro
     */
    public Collection<Allowance> getEmployeeAllowance(BigDecimal greaterThanValue) {
        return entityManager.createQuery(Employee.GET_WHERE_CLAUSE_EMPLOYEE_PARAMITERS_ALLOWANCES, Allowance.class)
                .setParameter("greaterThanValue", greaterThanValue).getResultList();
    }

    /**
     * Foi criado uma @QueryName limita o que virá com Minimo e Maximo
     */
    public Collection<Employee> filterEmployeesSalary(BigDecimal lowerBound, BigDecimal upperBound) {
        return entityManager.createQuery(Employee.EMPLOYEE_SALARY_BOUND, Employee.class)
                .setParameter("lowerBound", lowerBound).setParameter("upperBound", upperBound).getResultList();
    }

    /**
     * Esta sendo Criando Localmente a Query com o LIKE
     */
    public Collection<Employee> filterEmployees(String pattern) {
        return entityManager.createQuery("SELECT e FROM Employee e where e.fullName LIKE :filter", Employee.class)
                .setParameter("filter", pattern).getResultList(); // No Paramentro posso passar um Nome Ex. joão, ele
                                                                  // buscará todos q tem João
    }

    /**
     * Esta sendo Criando Localmente a Query com SUBQuerys
     * (SELECT MAX(emp.basicSalary) FROM Employee emp).
     * OBS: foi usando SINGLE .getSingleResult()
     */
    public Employee getEmployeeWithHighestSalary() {
        return entityManager.createQuery(
                "SELECT e FROM EMPLOYEE e WHERE e.basicSalary = (SELECT MAX(emp.basicSalary) FROM Employee emp)",
                Employee.class).getSingleResult();
    }

    /**
     * Esta sendo Criando Localmente a Query com o IN
     * Me dê a lista de Employee com Endereço que vivem IN(.....)
     */
    public Collection<Employee> filterEmployeeByState() {
        return entityManager
                .createQuery("SELECT e FROM Employee e WHERE e.address.state IN ('ALMADA', 'SETÚBAL')", Employee.class)
                .getResultList();
    }

    /**
     * Esta sendo Criando Localmente a Query com o NOT IN
     * Me dê a lista de Employee com Endereço que NÃO vivem IN(.....)
     */
    public Collection<Employee> filterEmployeeByStateNotIn() {
        return entityManager.createQuery("SELECT e FROM Employee e WHERE e.address.state NOT IN ('ALMADA', 'SETÚBAL')",
                Employee.class).getResultList();
    }

    /**
     * Esta sendo Criando Localmente a Query com o IS EMPTY
     * Me dê a lista de Employee com e seu Subordinados que NÃO TEM VALORES
     * NULOS(.....)
     */
    public Collection<Employee> getManagers() {
        return entityManager.createQuery("SELECT e FROM Employee e WHERE e.subordinates IS NOT EMPTY", Employee.class)
                .getResultList();
    }

    /**
     * Esta sendo Criando Localmente a Query com o MEMBER OF , ou seja ENTIDADE com
     * relacionamento de Class
     * Member OF tem que receber um PARAMENTRO que é um Atributo do tipo
     * RELACIONAMENTO. :project MEMBER OF e.projects
     */
    public Collection<Employee> getEmployeesByProject(Project project) {
        return entityManager.createQuery("SELECT e FROM Employee e WHERE :project MEMBER OF e.projects", Employee.class)
                .setParameter("project", project).getResultList();
    }

    /**
     * Esta sendo Criando Localmente a Query com o AND, ANY , ALL
     * o AND é para LIGAÇÃO entre 2 condições Ex:e.basicSalary < ALL(SELECT
     * s.basicSalary FROM e.suborddinates s )
     * o FROM aqui substitui do JOIN.
     * o ALL vai pegar TUDO que tem na SubQuery e comparar com a VAR e.basicSalary
     * o ANY vai pegar ALGUNS e comparar 1 por 1 com a Var e.basicSalary
     */
    public Collection<Employee> getAllLowerPaidManagers() {
        return entityManager.createQuery(
                "SELECT e FROM Employee e WHERE e.subordinates IS NOT EMPTY AND e.basicSalary < ALL(SELECT s.basicSalary FROM e.subordinates s )",
                Employee.class).getResultList();
    }

    /**
     * Esta sendo Criando Localmente a Query com ORDER BY UP or DESC
     * tem a ver com a Ordem, crescente ou descrecente,
     * OBS: UP é o default, n precisa por
     */
    public Collection<Employee> getEmployeesByProjectOrdeBy(Project project) {
        return entityManager.createQuery(
                "SELECT e FROM Employee e WHERE :project MEMBER OF e.projects ORDER BY e.department.departmentName DESC",
                Employee.class)
                .setParameter("project", project).getResultList();
    }

    /**
     * Esta sendo Criando Localmente a Query SUM() são postos ANTES do FROM
     * SUM é uma Função que execulta uma SOMA
     */
    public Collection<Object[]> getTotalEmployeeSalariesByDept() {
        TypedQuery<Object[]> query = entityManager.createQuery(
                "SELECT d.departmentName, SUM(e.basicSalary) FROM Department d JOIN d.employees e GROUP BY d.departmentName",
                Object[].class);
        return query.getResultList();
    }

    /**
     * Esta sendo Criando Localmente a Query AVERAGE() são postos ANTES do FROM
     * SUM é uma Função que execulta uma SOMA
     */
    public Collection<Object[]> getAverageEmployeeByDepart() {
        TypedQuery<Object[]> query = entityManager.createQuery(
                "SELECT d.departmentName AVG(e.basicSalary) FROM Department d JOIN d.employees e WHERE e.subordinates IS EMPTY GROUP BY d.departmentName",
                Object[].class);
        return query.getResultList();
    }

    /**
     * Esta sendo Criando Localmente a Query COUNT() são postos ANTES do FROM
     * SUM é uma Função que execulta um contagem em todos os Employee
     */
    public Collection<Object[]> countEmployeeByDept() {
        return entityManager.createQuery(
                "SELECT d.departmentName COUNT(e) FROM Department d JOIN d.employees e GROUP BY d.departmentName ",
                Object[].class).getResultList();
    }

    /**
     * Esta sendo Criando Localmente a Query MAX() or MIN() são postos ANTES do FROM
     * SUM é uma Função que execulta um contagem em todos os Employee com MAIORES
     * salarios e com os MENORES salarios
     */
    public Collection<Object[]> countEmployeeLowersByDeptMAX() {
        return entityManager.createQuery(
                "SELECT d.departmentName MAX(e.basicSalary) FROM Department d JOIN d.employees e GROUP BY d.departmentName ",
                Object[].class).getResultList();
    }

    public Collection<Object[]> countEmployeeLowersByDeptMIN() {
        return entityManager.createQuery(
                "SELECT d.departmentName MIN(e.basicSalary) FROM Department d JOIN d.employees e GROUP BY d.departmentName ",
                Object[].class).getResultList();
    }

    /**
     * Esta sendo Criando Localmente a Query AGGREGATE function HAVING, que receberá
     * um paramentro e lá no retorno poderemos usar uma FUNÇÃO AVG, MAX, MIN ETC
     * depois do FROM, desta forma SOMENTE 1 resultado é agregado
     * Ex: HAVING AVG(e.basicSalary) > :minimumThreshold
     */
    public Collection<Object[]> getAverageEmployeeByDepartFuncHAVING(BigDecimal minimumThreshold) {
        TypedQuery<Object[]> query = entityManager.createQuery(
                "SELECT d.departmentName AVG(e.basicSalary) FROM Department d JOIN d.employees e WHERE e.subordinates IS EMPTY GROUP BY d.departmentName HAVING AVG(e.basicSalary) > :minimumThreshold",
                Object[].class).setParameter("minimumThreshold", minimumThreshold);
        return query.getResultList();
    }

    /** Native Query, usamos uma query nativa da DB */
    @SuppressWarnings("unchecked")
    public Collection<Department>getDepartmentNativeQuery(){
        return entityManager.createNativeQuery("SELECT * FROM Department", Department.class).getResultList();
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

package com.pedantic.service;

import javax.annotation.sql.DataSourceDefinition;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import com.pedantic.entities.Department;
import com.pedantic.entities.Employee;
import com.pedantic.entities.ParkingSpace;

@DataSourceDefinition(name = "java:app/Payroll/MyDS", className = "org.apache.derby.jdbc.ClientDriver", url = "jdbc:derby://localhost:1527/payroll", user = "appuser", password = "password")
@Stateless
public class PersistenceService {

    @Inject
    EntityManager entityManager;

    @Inject
    QueryService queryService;

    /** Exemplo de persistence na DB */

    public void saveDepartment(Department department) {
        entityManager.persist(department);

    }

    /* FindById */
    public Department findByIdDepartment(Long id) {
        return entityManager.find(Department.class, id);
    }

    public void removePakingSpace(Long employeeId) { /**
                                                      * 1º Buscar o Employee pelo ID
                                                      * 2º Busca o ParkingSpace do Employee que fou selecionado pelo ID
                                                      * 3º Por ter uma relação OneToOne com parkingSpace temos que tb
                                                      * Setar a tabela
                                                      * antes de ParkingSpace antes de remover o parkingSpace, pois
                                                      * senão ficará um Id
                                                      * de uma transação que não existe.
                                                      */
        Employee employee = queryService.findEmployeeById(employeeId);
        ParkingSpace parkingSpace = employee.getParkingSpace();
        employee.setParkingSpace(null);
        entityManager.remove(parkingSpace);

    }

    /*
     * @OneToOne(mappedBy = "employee", fetch = FetchType.LAZY, cascade =
     * CascadeType.PERSIST), por causa deste CasdadeType.PERSIST, ja não preciso me
     * preocupar com a relação da DB Employee com ParkingSpace
     */
    public void saveEmployeeWithParkingSpace(Employee employee, ParkingSpace parkingSpace) {
        employee.setParkingSpace(parkingSpace);
        entityManager.persist(employee);
    }

    public void updateDepartment(Department department) {
        entityManager.merge(department);
    }

    /**
     * Se não existe um Employee ele Persiste ou seja SALVA,
     * Caso ja exista, ele faz o MERGE ou seja o Update
     */
    public void saveEmployee(Employee employee) {
        if (employee.getId() == null) {
            entityManager.persist(employee);
        } else {
            entityManager.merge(employee);

        }
    }

}

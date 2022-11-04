package com.pedantic.entities;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
@NamedQuery(name = Department.FIND_BY_ID, query = "select d from Department d where d.id = :id and d.userEmail = :email")
@NamedQuery(name = Department.FIND_BY_NAME, query = "select d from Department d where d.departmentName = :name and d.userEmail = :email")
@NamedQuery(name = Department.LIST_DEPARTMENTS, query = "select d from Department d where  d.userEmail = :email")
@Access(AccessType.FIELD)
public class Department extends AbstractEntity {

    public static final String FIND_BY_ID = "Department.findById";
    public static final String FIND_BY_NAME = "Department.findByName";
    public static final String LIST_DEPARTMENTS = "Department.listDepartments";

    @NotEmpty(message = "Department name must be set") /** @NotEmpty é FieldAcess é um Provider de acesso ao campo */

    private String departmentName;

    @OneToMany(mappedBy = "department") /**
                                         * Lá em Employee temos s anotação @ManyToOne onde estou enviado Muitos
                                         * Employees, para Department e eles tem de ser armazenados em uma Collection,
                                         * array ou Set ou Map
                                         * Para Cada Employee la na DB teremos uma foreingKey associada na Tabela Department
                                         */
                                         /**
                                          * A LIST ordenará os dados pela PrimaKey
                                          */
    private List<Employee> employees = new ArrayList<>();
    

    @Transient /**
                * @Transient,Quando esta ENTITY for MAPEADA, diga ao CDI p/ Não MAPEAR este
                *                   campo, será um Objeto Java Local desta class e não Um
                *                   Atributo da Entidade a ser Mapeada,
                *                   Este no Scopo em Provaider or RunTIME não vai existir
                */
    private String departmentCode;







    public String getDepartmentCode() {
        return departmentCode;
    }

    public void setDepartmentCode(String departmentCode) {
        this.departmentCode = departmentCode;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public List<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }
}

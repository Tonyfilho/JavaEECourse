package com.pedantic.entities;

import java.time.LocalDate;
import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

@Entity
public class Project extends AbstractEntity {
    private String projectName;
    private LocalDate projecStartDate;
    private LocalDate projecEnddate;

    @ManyToMany() /**
                   * Nesta Relação, tenho Muitas Instancias de Employee sendo Recebidas aqui nesta
                   * Entidade, e por sua vez, Employee irá tb receber muitas Instancias da
                   * Entidade Project.
                   * NOTE: mappedBy = "employees" , estou dizendo que a Entidade PROJECT é o OWNER
                   * do relacionamento, é conceito de JOINTABLEs
                   */
      /* Iremos customizar os nomes da tabela com relação com as anotações seguintes */            
    @JoinTable(name = "PROJ_EMPLOYEES",
     joinColumns = @JoinColumn(name = "PROJ_ID"),
     inverseJoinColumns = @JoinColumn(name = "EMP_ID"))
    private Collection<Employee> employees;

    

    /**************************************GEts and SETs******************************** */
    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public LocalDate getProjecStartDate() {
        return projecStartDate;
    }

    public void setProjecStartDate(LocalDate projecStartDate) {
        this.projecStartDate = projecStartDate;
    }

    public LocalDate getProjecEnddate() {
        return projecEnddate;
    }

    public void setProjecEnddate(LocalDate projecEnddate) {
        this.projecEnddate = projecEnddate;
    }

    public Collection<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(Collection<Employee> employees) {
        this.employees = employees;
    }

}

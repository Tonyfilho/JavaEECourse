/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pedantic.entities;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import javax.json.bind.annotation.JsonbDateFormat;
import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.PastOrPresent;

import javafx.scene.control.PasswordField;

/**
 * @author Seeraj
 */
@Entity
/**Criando NamedQuery para PakingSpace employee relacionamento OneToOne LazyLoad */
@NamedQuery(name = Employee.GET_ALL_PARKING_SPACE, query = "select e.getParkingSpace.parkingLotNumber from Employee e")

/**Criando NamedQuery para employee name and Salary
 * Select: Expression e.fullName, e.basicSalary, isto será uma Coleção Array  de  Objeto, por serem Tipos diferentes, 1º string o 2º BigDecimal
 */
@NamedQuery(name = Employee.EMPLOYEE_PROJECTION, query = "select e.fullName, e.basicSalary from Employee e")



@NamedQuery(name = Employee.FIND_BY_ID, query = "select e from Employee e where e.id = :id and e.userEmail = :email")
@NamedQuery(name = Employee.FIND_BY_NAME, query = "select e from Employee e where e.fullName = :name and e.userEmail = :email")
@NamedQuery(name = Employee.LIST_EMPLOYEES, query = "select  e from Employee e where e.userEmail = :email order by e.fullName")
@NamedQuery(name = Employee.FIND_PAST_PAYSLIP_BY_ID, query = "select p from Employee e join e.pastPayslips p where e.id = :employeeId and e.userEmail =:email and p.id =:payslipId and p.userEmail = :email")
@NamedQuery(name = Employee.GET_PAST_PAYSLIPS, query = "select p from Employee e inner join e.pastPayslips p where e.id = :employeeId and e.userEmail=:email")
@Table(name = "Employee", schema = "HR") /** A Table @notation para Mapear a relação da Tabela da DB com a sua ENTITY */
public class Employee extends AbstractEntity {

    public static final String FIND_BY_ID = "Employee.findById";
    public static final String FIND_BY_NAME = "Employee.findByName";
    public static final String LIST_EMPLOYEES = "Employee.listEmployees";
    public static final String FIND_PAST_PAYSLIP_BY_ID = "Employee.findPastPayslipById";
    public static final String GET_PAST_PAYSLIPS = "Employee.getPastPayslips";
    /**Criando NamedQuery para PakingSpace employee relacionamento OneToOne LazyLoad */
    public static final String GET_ALL_PARKING_SPACE = "Employee.getAllParkingSpaces";
    /**Criando NamedQuery para employee name and Salary */
    public static final String EMPLOYEE_PROJECTION = "Employee.nameAndSalaryProjection";



    @NotEmpty(message = "Name cannot be empty")
    @Basic /**
            * Esta anotação para ser Mapeada na DB como do tipo BASIC, Ex: Tipo: Long,
            * String, Int , ela é OPCIONAL pois já é Default
            */
    private String fullName;

    @Past(message = "Date of birth must be in the past")
    @JsonbDateFormat(value = "yyyy-MM-dd")
    private LocalDate dateOfBirth; // yyyy-MM-dd

    @NotNull(message = "Basic salary must be set")
    private BigDecimal basicSalary;

    @NotNull(message = "Hired date must be set")
    @JsonbDateFormat(value = "yyyy-MM-dd")
    @PastOrPresent(message = "Hired date must be in the past or present")
    private LocalDate hiredDate;

    @ManyToOne
    private Employee reportsTo;

    @OneToMany
    private Set<Employee> subordinates = new HashSet<>();

    @Enumerated(EnumType.STRING)
    private EmploymentType employmentType;

    @Embedded
    private Address address;

    @ElementCollection /*
                        * Esta é a forma de Map uma collection em uma Entidade, com isto é criado uma
                        * segunda tabela a primarykey de Employee será uma referencia do  objeto em forma de foreingKey.
                        */
     @CollectionTable(name = "QUALIFICATION", joinColumns =  @JoinColumn(name = "EMP_ID "))/* Custamizando a Collection table com nomes da tabela */
    private Collection<Qualifications> qualifications = new ArrayList<>();
   
    private int age;

    @OneToMany /**
                * 1 instancia de Employee, irá receber Muitas Instancias de Allowance.
                * Lá em Allowance NÃO temos anotação @ManyToOne na Entidade Allowance onde é
                * UNIDIRECIONAL.
                * OBs: os Dados tem de ser armazenados em uma Collection, array ou Set ou Map
                * na DB teremos uma foreingKey de Department associada na Tabela Employee
                * Department
                */
    private Set<Allowance> employeeAllowances = new HashSet<>();

    @OneToOne /**
               * 1 Instancia de Employee tem relação com 1 instancia de Payslip, por Somente
               * ter aqui a @notação, esta relação será INIDIRECIONAL, teremos uma ForeingKey
               * the Allowance na tabela Employee
               */
    @JoinColumn(name = "CURRENT_PAYSLIP_ID") /**
                                              * Na Table Employee teremos uma Unique Constraint, ou seja Cada vez que a
                                              * Tabela PAYSLIP criar 1 item, teremos 1 Registro dentro de
                                              * Employee.CurrentPayslip,
                                              */
    private Payslip currentPayslip ;

    @OneToOne(mappedBy = "employee", fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST , CascadeType.REMOVE}) /**
                                                              * 1 Instancia de Employee tem relação com 1 instancia de
                                                              * Payslip, mas agora
                                                              * será Bidirecional, temos uma Foreingkey de ParkingSpace
                                                              * em Employee E uma
                                                              * ForeingKey de Employee em ParkingSpace, pois lá na
                                                              * entidade ParkingSpace
                                                              * teremos a @notação @OneToOne TB.
                                                              * fetch = FetchType.LAZY estou dizendo q o FETCH é LAZY,
                                                              * só irá buscar quando houver um Resquest
                                                              * este já o DEFAULT, quando não é informado.
                                                              * cascade = CascadeType.PERSIST que dizer, q tudo neste relacionamento OnetoOne será cascateado
                                                              * para ParkingSpace, este o conceito de  CASCADE
                                                              */
    private ParkingSpace parkingSpace;

    @OneToMany // todas as relações existem o conceito de CASCADE
    private Collection<Payslip> pastPayslips = new ArrayList<>();


    @ElementCollection /**Todo Map ja recebe por DEFAULT esta @anotação, mesmo que não declarada */
    @CollectionTable(name = "EMP_PHONE_NUMBERS")
    @MapKeyColumn(name = "PHONE_TYPE")
    @Column(name = "PHONE_NUMBER")
    @MapKeyEnumerated(EnumType.STRING)
    private Map<PhoneType,String> employeePhoneMumbers = new HashMap<>(); /**O Map precisa ter 2 argumentos, a KEY e Objeto */



    @ManyToOne /**
                * Esta @anotação diz: Envio Muitas instancia de Employee serão enviados
                * colocará tudo em uma unica
                * instancias de Department
                */
    @JoinColumn(name = "DEPT_ID") /**
                                   * a @notação vai Juntar as Colunas da PrimaryKey de Employee com ForeingKey de
                                   * Department com nome DEPT_ID
                                   */
    private Department department;

    @ManyToMany(mappedBy = "employees") /**
                                         * Nesta Relação, tenho Muitas Instancias de Employee sendo Recebidas aqui nesta
                                         * Entidade, e por sua vez, Employee irá tb receber muitas Instancias da
                                         * Entidade Project.
                                         * NOTE: mappedBy = "employees" , estou dizendo que a Entidade PROJECT é o OWNER
                                         * do relacionamento, é conceito de JOINTABLEs
                                         */
    private Collection<Project> projects = new ArrayList<>();

    @Lob
    private byte[] picture;

    @PrePersist
    private void init() {
        this.age = Period.between(dateOfBirth, LocalDate.now()).getYears();
    }

    /***************************************** GETs and SETs *************** */


    

    
    public Map<PhoneType, String> getEmployeePhoneMumbers() {
        return employeePhoneMumbers;
    }
    
    public void setEmployeePhoneMumbers(Map<PhoneType, String> employeePhoneMumbers) {
        this.employeePhoneMumbers = employeePhoneMumbers;
    }

    public ParkingSpace getParkingSpace() {
        return parkingSpace;
    }

    public Collection<Project> getProjects() {
        return projects;
    }

    public void setProjects(Collection<Project> projects) {
        this.projects = projects;
    }

    public void setParkingSpace(ParkingSpace parkingSpace) {
        this.parkingSpace = parkingSpace;
    }

    public Employee getReportsTo() {
        return reportsTo;
    }

    public void setReportsTo(Employee reportsTo) {
        this.reportsTo = reportsTo;
    }

    public Set<Employee> getSubordinates() {
        return subordinates;
    }

    public void setSubordinates(Set<Employee> subordinates) {
        this.subordinates = subordinates;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public int getAge() {
        return age;
    }

    public EmploymentType getEmploymentType() {
        return employmentType;
    }

    public void setEmploymentType(EmploymentType employmentType) {
        this.employmentType = employmentType;
    }

    public byte[] getPicture() {
        return picture;
    }

    public void setPicture(byte[] picture) {
        this.picture = picture;
    }

    public Payslip getCurrentPayslip() {
        return currentPayslip;
    }

    public void setCurrentPayslip(Payslip currentPayslip) {
        this.currentPayslip = currentPayslip;
    }

    public Collection<Payslip> getPastPayslips() {
        return pastPayslips;
    }

    public void setPastPayslips(Collection<Payslip> pastPayslips) {
        this.pastPayslips = pastPayslips;
    }

    public LocalDate getHiredDate() {
        return hiredDate;
    }

    public void setHiredDate(LocalDate hiredDate) {
        this.hiredDate = hiredDate;
    }

    public Set<Allowance> getEmployeeAllowances() {
        return employeeAllowances;
    }

    public void setEmployeeAllowances(Set<Allowance> employeeAllowances) {
        this.employeeAllowances = employeeAllowances;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public BigDecimal getBasicSalary() {
        return basicSalary;
    }

    public void setBasicSalary(BigDecimal basicSalary) {
        this.basicSalary = basicSalary;
    }
}

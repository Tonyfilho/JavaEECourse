package com.pedantic.entities;

import java.math.BigDecimal;

/**Iremos usar COMBINE TYPE EXPRESSION em forma de ARRAY e não desta forma
 * @NamedQuery(name = Employee.EMPLOYEE_PROJECTION, query = "select e.fullName, e.basicSalary from Employee e")
 * 1ª Criaremos um Objeto e passaremos dentro Select dando um NEW da Instancia
 */
public class EmployeeDetails {
    private String fullName;
    private BigDecimal basicSalary;
    private String depaString;





   

    public EmployeeDetails() {
    }


    public EmployeeDetails(String fullName, BigDecimal basicSalary, String depaString) {
        this.fullName = fullName;
        this.basicSalary = basicSalary;
        this.depaString = depaString;
    }

    
    /***********************************GETs and SETs************************* */
    public String getFullName() {
        return fullName;
    }
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
    public BigDecimal getBasicSalary() {
        return basicSalary;
    }
    public void setBasicSalary(BigDecimal basicSalary) {
        this.basicSalary = basicSalary;
    }
    public String getDepaString() {
        return depaString;
    }
    public void setDepaString(String depaString) {
        this.depaString = depaString;
    }

    
}

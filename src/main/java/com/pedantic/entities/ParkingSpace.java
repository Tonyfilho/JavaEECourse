package com.pedantic.entities;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@Entity
public class ParkingSpace extends AbstractEntity {

    private String parkingLotNumber;

    @OneToOne /**
               * Aqui temos uma RELAÇÃO BiDirecional, com a Entidade Employee e ParkingSpace,
               * na TABLE teremos 1 foreingKey de Employee dentro da TABLE ParkingSpace E uma
               * foreing de ParkingSpace dentro da TABLE de Employee
               */
    @JoinColumn(name = "EMPLOYEE_ID") /**
                                       * AO usar aqui o @JoinColums e na Entidade Employee e @OneToOne(mappedBy =
                                       * "employee"), a entidade Employee passa a ser Dona do Relacionamento, Ou seja
                                       * Foreingkey de Employee será a mesma PrimaryKey de PackSpace.
                                       */
    private Employee employee;

    public String getParkingLotNumber() {
        return parkingLotNumber;
    }

    public void setParkingLotNumber(String parkingLotNumber) {
        this.parkingLotNumber = parkingLotNumber;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

}

package com.pedantic.resources;


import javafx.scene.media.MediaPlayer;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import com.pedantic.entities.Employee;
import com.pedantic.service.PersistenceService;
import com.pedantic.service.QueryService;

import java.net.URI;
import java.util.Collection;

@Path("employees") //api/v1/employees/*
@Produces("application/json")
@Consumes("application/json")
public class EmployeeResource {


    @Context
    private UriInfo uriInfo;

    //employees GET List of employees
    //employees/{id} GET
    //employees POST - employees/98

    @Inject
    QueryService queryService;

    @Inject
    PersistenceService persistenceService;

//    GET /api/v1/employees/employees HTTP/1.1
//    Host www.ourdomain.com
//    User-Agent: Java/1.8.0_151
//    Content-Type: text/plain;charset=utf-8
//    Accept: application/json

    @GET //api/v1/employees GET Request
    @Path("employees") //api/v1/employees/employees
//    @Produces("application/xml")
    public Response getEmployees() {

//        Collection<Employee> employees = new ArrayList<>();
//
//        Employee employee = new Employee();
//        employee.setFullName("John Mahama");
//        employee.setSocialSecurityNumber("SSF12343");
//        employee.setDateOfBirth(LocalDate.of(1986, Month.APRIL, 10));
//        employee.setBasicSalary(new BigDecimal(60909));
//        employee.setHiredDate(LocalDate.of(2018, Month.JANUARY, 24));
//
//
//        Employee employee1 = new Employee();
//        employee1.setFullName("Donald Trump");
//        employee1.setSocialSecurityNumber("SKJBHJSBDKJ");
//        employee1.setDateOfBirth(LocalDate.of(1900, Month.JULY, 31));
//        employee1.setBasicSalary(new BigDecimal(250000));
//        employee1.setHiredDate(LocalDate.of(2016, Month.NOVEMBER, 7));
//
//        employees.add(employee);
//        employees.add(employee1);

        return Response.ok(queryService.getEmployees()).status(Response.Status.OK).build();

//        return employees;
    }

    @GET
    @Path("employees/{id: \\d+}") //api/v1/employees/employee/1  GET Method {username: }@{domain: }.{company}
    public Response getEmployeeById(@PathParam("id") @DefaultValue("0") Long id) {

        return Response.ok(queryService.findEmployeeById(id)).status(Response.Status.OK).build();
    }

    @GET
    @Path("id") //?id=27 /id?id=95
    public Employee findEmployeeById(@QueryParam("id") @DefaultValue("0") Long id) {
        return queryService.findEmployeeById(id);

    }


    @POST //api/v1/employees POST Request
    @Path("employees") //api/v1/employees/new - POST Request
//    @Consumes("application/xml")
    public Response createEmployee(Employee employee) {
        persistenceService.saveEmployee(employee, employee.getParkingSpace());

        URI uri = uriInfo.getAbsolutePathBuilder().path(employee.getId().toString()).build();
        return Response.created(uri).status(Response.Status.CREATED).build();
    }
}

package com.pedantic.config;

import java.util.HashMap;
import java.util.Map;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;


@Provider  /**Diz para JAXRS que esta class tem q ser injetado no runtime como Provedor de Serviço, neste caso como Exception Error.
* Quer dizer que: A qualquer momento que houver uma Exception o JAXRS irá invocar esta  class ConstraintViolationExcepyionMapper e passar
a Excessão para o metodo toResponse().
*/
public class ConstraintViolationExcepyionMapper implements ExceptionMapper<ConstraintViolationException> {


    /**Este é um metodo Generico de exception */
    @Override
    public Response toResponse(ConstraintViolationException arg0) {
        final Map<String, String> constraintViolations = new HashMap<>();
        for(ConstraintViolation<?> cv: arg0.getConstraintViolations()) {
            String path = cv.getPropertyPath().toString();
            constraintViolations.put(path, cv.getMessage());

        }
        return Response.status(Response.Status.PRECONDITION_FAILED).entity(constraintViolations).build();
    }

    
   
}

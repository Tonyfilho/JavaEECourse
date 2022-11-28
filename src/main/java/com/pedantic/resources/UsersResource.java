package com.pedantic.resources;

import javax.ws.rs.BeanParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.CookieParam;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;

import com.pedantic.entities.ApplicationUser;

@Path("users")
@Consumes("appication/json")
@Produces("appication/json")
public class UsersResource {

          
    @POST //api/v1/users/form
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Path("form")
    public Response createNewUser(@FormParam("email") String email, @FormParam("password") String password, @HeaderParam("Referer") String referer) {


        return null;
    }


    @POST
    @Path("map")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public Response createNewUser(MultivaluedMap<String, String> formMap, @Context HttpHeaders httpHeaders) {

//        httpHeaders.getHeaderString("Referer");

        httpHeaders.getRequestHeader("Referer").get(0);

        for (String h : httpHeaders.getRequestHeaders().keySet()) {
            System.out.println("Header key set " + h);
        }

        String email = formMap.getFirst("email");
        String password = formMap.getFirst("password");

        return null;
    }

    @POST
    @Path("bean")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public Response createNewUser(@BeanParam ApplicationUser applicationUser, @CookieParam("user") String user) { /*
                                                                                                                 * Quando usamos no metodo esta notação @BeanParam, 
                                                                                                                 * estou falando para JAVRS que quero q ele START 
                                                                                                                 * a aplicação com os VALORES existentes no FORM do front
                                                                                                                 * @BeanParam ApplicationUser applicationUser
                                                                                                                 * @BeanParam va VIEW é a melhor forma de consumir FORM OBJECT em JaxRs.
                                                                                                                 * Lá na Entidade é necessrio ter a @notação  @FormParam("email")
                                                                                                                 * @CookieParam("user") String user Cookie Params, são enviados pela
                                                                                                                 * VIEW e usando em nosso Metodos, só precisar passar uma String
                                                                                                                 * para um variavel
                                                                                                                 *  */
  
        return null;
    }


    //    GET /api/v1/employees/employees HTTP/1.1
//    Host www.ourdomain.com
//    User-Agent: Java/1.8.0_151
//    Content-Type: text/plain;charset=utf-8
//    Accept: application/json;q=.7, application/xml
    @GET
    @Path("{id}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response getUserById(@PathParam("id") Long id) {


        return null;
    } 

    
}

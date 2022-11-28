package com.pedantic.entities;

import javax.persistence.Entity;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import javax.ws.rs.FormParam;

@Entity
public class ApplicationUser extends AbstractEntity{


    @NotEmpty(message = "Email must be ser set")
    @Email(message = "The Email must be  in the  form user@domain.com")
    @FormParam("email")  /**Esta Anotação @FormParam("email"), junto com @BeanParam com 
                         *esta sendo usada em no Metodo Response 
                         *createNewUser(@BeanParam ApplicationUser applicationUser, @CookieParam("user") String user)
                         *@BeanParam va VIEW é a melhor forma de consumir FORM OBJECT em JaxRs
                           */
    private String email;
    
    
    
    
    @NotEmpty(message = "Password must be ser set")
    @Size(min= 8 , max = 20 , message = "Password must have min 8 caracters")
    @Pattern(regexp = "", message = "Password must have Pattern designer xxxxx")
    @FormParam("password")
    private String password;


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }



    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

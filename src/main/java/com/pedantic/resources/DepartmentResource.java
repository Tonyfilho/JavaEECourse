package com.pedantic.resources;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.xml.ws.Response;

/**
 * Ex: de Server Context Negociation
 * Nesta class é somente um Ex: de prioridade no tido de Arquivo que virá pelo
 * HEADER, se é Json ou Xml, ou Text. sendo que QS=0.9 é um tipo de ORDEM de
 * prioridade
 */
@Path("departments")
public class DepartmentResource {

    @GET
    @Produces({ "application/json; qs=0.9", "application/xml; qs=0.7" }) /**
                                                                          * QS é a uma LISTA de Prioridade vinda do
                                                                          * Cliente, vinda no Header, sendo q 1 é Hight priority
                                                                          * e 0 é low priority.
                                                                          *Aqui neste contexto o Json terá a prioridade, o default é 1, não sendo
                                                                          * Informado a priority , entra o Default
                                                                          */
    public Response gerDepartment() {

        return null; /** É um Dummy para o TIPO de arquivos recebidos pelo Header */
    }

}

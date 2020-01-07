package br.com.alura.resources;

import java.util.List;
import java.util.logging.Logger;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import br.com.alura.business.AgendamentoEmailBusiness;
import br.com.alura.entity.AgendamentoEmail;
import br.com.alura.exception.BusinessException;

//Acessar esse resource pela uri
@Path("/agendamentoemail")
public class AgendamentoEmailResource {

	private static Logger logger = Logger.getLogger(AgendamentoEmailResource.class.getName());

	// Utilizando CDI
	@Inject
	private AgendamentoEmailBusiness agendamentoEmailBusiness;

	// MOSTRAR RESPONDER REQUISIÇÃO
	@GET
	// Passando a resposta para formato JSON
	@Produces(MediaType.APPLICATION_JSON)
	public Response listarAgendamentoEmail() {
		List<AgendamentoEmail> emails = agendamentoEmailBusiness.listarAgendamentosEmail();
		return Response.ok(emails).build();

	}

	// RECEBENDO UM JSON, e configurar no postman um acesso post ao resource.
	// Misturar com lógica de negócios é uma má prática.
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Response salvarAgendamentoEmail(AgendamentoEmail agendamentoEmail) throws BusinessException {

		agendamentoEmailBusiness.salvarAgendamentoEmail(agendamentoEmail);

		return Response.status(201).build();
	}
}

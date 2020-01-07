package br.com.alura;

import java.util.stream.Collectors;

import javax.validation.ConstraintViolationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

import br.com.alura.dto.MensagemErroDto;

/**
 * 
 * @author lhsousa: Bean Validation é um tipo de exceção que utilizo uma
 *         exception mapper, para ser uma exceção que emite uma resposta na
 *         minha tela.
 *
 */
public class ConstraintValidationMapper implements ExceptionMapper<ConstraintViolationException> {

	@Override
	public Response toResponse(ConstraintViolationException e) {

		// Percorre as mensagens do bean validation
		return Response.status(Response.Status.BAD_REQUEST)
				.entity(MensagemErroDto.build(e.getConstraintViolations().stream()
						.map(constraintViolation -> constraintViolation.getMessage()).collect(Collectors.toList())))
				.build();
	}

}

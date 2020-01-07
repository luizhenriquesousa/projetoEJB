package br.com.alura.mdb;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.inject.Inject;
import javax.jms.JMSException;
import javax.jms.MessageListener;

import br.com.alura.business.AgendamentoEmailBusiness;
import br.com.alura.entity.AgendamentoEmail;
import br.com.alura.interceptor.Logger;

@Logger
@MessageDriven(activationConfig = {
		// JNDI são os propertys
		@ActivationConfigProperty(propertyName = "destinationLookup", propertyValue = "java:/jms/queue/EmailQueue"),
		@ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue") })
public class EmailMDB implements MessageListener {
	@Inject
	private AgendamentoEmailBusiness agendamentoEmailBusiness;

	@Override
	public void onMessage(javax.jms.Message message) {

		try {

			// agendamento da mensagem, o rollback dele delvolve para uma outra fila de erro
			AgendamentoEmail agendamentoEmail = message.getBody(AgendamentoEmail.class);
			agendamentoEmailBusiness.enviarEmail(agendamentoEmail);

		} catch (JMSException e) {
			throw new RuntimeException(e);
		}
	}
}

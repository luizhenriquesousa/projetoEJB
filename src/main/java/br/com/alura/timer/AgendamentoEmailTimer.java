package br.com.alura.timer;

import java.util.List;

import javax.annotation.Resource;
import javax.ejb.Schedule;
import javax.ejb.Singleton;
import javax.inject.Inject;
import javax.jms.JMSConnectionFactory;
import javax.jms.JMSContext;
import javax.jms.Queue;

import br.com.alura.business.AgendamentoEmailBusiness;
import br.com.alura.entity.AgendamentoEmail;

//Não deixa que uma nova task se reinicie
@Singleton
public class AgendamentoEmailTimer {

	@Inject
	private AgendamentoEmailBusiness agendamentoEmailBusiness;

	// Para enviar mensagens uso JMSContext
	@Inject
	@JMSConnectionFactory("java:jboss/DefaultJMSConnectionFactory")
	private JMSContext context;

	@Resource(mappedName = "java:/jms/queue/EmailQueue")
	private Queue queue;

	@Schedule(hour = "*", minute = "*")
	public void enviarEmailsAgendados() {

		// Utilizando java mail no wildfly, dentro do foreach está pronto para ir para a
		// fila e os status
		List<AgendamentoEmail> agendamentoEmails = agendamentoEmailBusiness.listarAgendamentosEmail();
		agendamentoEmails.stream().forEach(agendamentoEmail -> {
			context.createProducer().send(queue, agendamentoEmail);
			// aqui marcam as mensagens enviadas
			agendamentoEmailBusiness.marcarEnviadas(agendamentoEmail);
		});
		// file na fila jms
	}

}

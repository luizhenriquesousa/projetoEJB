package br.com.alura.dao;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import br.com.alura.entity.AgendamentoEmail;

/**
 * 
 * @author lhsousa
 *
 */

// Class EJB
@Stateless
public class AgendamentoEmailDao {

	// Contexto de persistência
	@PersistenceContext
	private EntityManager entityManager;

	public List<AgendamentoEmail> listarAgendamentosEmail() {
		return entityManager.createQuery("select a from AgendamentoEmail", AgendamentoEmail.class).getResultList();
	}

	public void salvarAgendamentoEmail(AgendamentoEmail agendamentoEmail) {
		entityManager.persist(agendamentoEmail);
	}

	/**
	 * 
	 * @param agendamentoEmail
	 *            realiza atualização de status de envio de email
	 */
	public void atualizarAgendamentoEmail(AgendamentoEmail agendamentoEmail) {
		entityManager.merge(agendamentoEmail);
	}

	public List<AgendamentoEmail> listarAgendamentosEmailPorEmail(String email) {
		Query query = entityManager.createQuery(
				"select a from AgendamentoEmail a where a.email =:email and a.enviado = false", AgendamentoEmail.class);
		query.setParameter("email", email);

		return query.getResultList();
	}

	public List<AgendamentoEmail> listarAgendamentosEmailNaoEnviados() {
		Query query = entityManager.createQuery(
				"select a from AgendamentoEmail a where a.email =:email and a.enviado = false", AgendamentoEmail.class);

		return query.getResultList();
	}
}

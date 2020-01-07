package br.com.alura.interceptor;

import java.util.logging.Logger;

import javax.annotation.Priority;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;
import javax.validation.ConstraintViolationException;

@Interceptor
// Prioridade
@Priority(1)
@br.com.alura.interceptor.Logger
public class LoggerInterceptor {

	@AroundInvoke
	public Object treatException(InvocationContext context) throws Exception {
		// Logs
		Logger logger = Logger.getLogger(context.getTarget().getClass().getName());
		try {
			// Interceptor genérico
			return context.proceed();
		} catch (Exception e) {
			if (e.getCause() instanceof ConstraintViolationException) {
				logger.info(e.getMessage());
			} else {
				logger.severe(e.getMessage());
			}
			throw e;
		}
	}

}

package io.helidon.examples.quickstart.mp;
import org.eclipse.microprofile.opentracing.Traced;

import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class MesssageProvider {

	@Traced
	String getMessage() {
		return getMessage2();
	}

	@Traced
	String getMessage2() {
		return "welcome to helidon";
	}

	
}
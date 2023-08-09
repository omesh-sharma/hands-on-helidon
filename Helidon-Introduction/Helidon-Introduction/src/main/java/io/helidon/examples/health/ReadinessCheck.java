package io.helidon.examples.health;

import java.time.Duration;
import java.util.concurrent.atomic.AtomicLong;

import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.HealthCheckResponse;
import org.eclipse.microprofile.health.Readiness;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.context.Initialized;
import jakarta.enterprise.event.Observes;

@Readiness
@ApplicationScoped
public class ReadinessCheck implements HealthCheck {

	private final AtomicLong readyTime = new AtomicLong(0);

	public void onStartUp(@Observes @Initialized(ApplicationScoped.class) Object init) {
		readyTime.set(System.currentTimeMillis());
	}

	private boolean isReady() {
		return Duration.ofMillis(System.currentTimeMillis() - readyTime.get()).getSeconds() >= 10;
	}

	@Override
	public HealthCheckResponse call() {
		// TODO Auto-generated method stub

		return HealthCheckResponse.named("readiness-check").status(isReady()).withData("ready", readyTime.get())
				.build();
	}

}
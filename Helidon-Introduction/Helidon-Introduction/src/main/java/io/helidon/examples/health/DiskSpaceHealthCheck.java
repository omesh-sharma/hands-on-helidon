package io.helidon.examples.health;

import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.HealthCheckResponse;
import org.eclipse.microprofile.health.Liveness;

import io.helidon.health.common.BuiltInHealthCheck;
import jakarta.enterprise.context.ApplicationScoped;

@Liveness
@ApplicationScoped
@BuiltInHealthCheck
public class DiskSpaceHealthCheck implements HealthCheck {

	@Override
	public HealthCheckResponse call() {
		// TODO Auto-generated method stub
		return HealthCheckResponse.named("disk -info")
				.withData("processors", Runtime.getRuntime().availableProcessors())
				.withData("free memory", Runtime.getRuntime().freeMemory())
				.withData("free memory", Runtime.getRuntime().totalMemory()).build();
	}

}
package io.helidon.examples.health;


import java.time.Duration;
import java.util.concurrent.atomic.AtomicLong;

import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.HealthCheckResponse;
import org.eclipse.microprofile.health.Startup;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.context.Initialized;
import jakarta.enterprise.event.Observes;
import jakarta.ws.rs.core.Application;

@Startup
@ApplicationScoped
public class StartedCheck implements HealthCheck {

	public StartedCheck() {
		// TODO Auto-generated constructor stub
	}
	
	private final AtomicLong readyTime=new AtomicLong(0);
	
	public void onStartup(@Observes @Initialized(ApplicationScoped.class) Object init)
	{
		
		readyTime.set(System.currentTimeMillis());
		
	}
	
	private boolean isStarted() {
		return Duration.ofMillis(System.currentTimeMillis() - readyTime.get()).getSeconds() > 15;
	}
	
	@Override
	public HealthCheckResponse call() {
		// TODO Auto-generated method stub
		
//		return HealthCheckResponse.named("start-up-check").status(false).build();
		return HealthCheckResponse.named("start-up-check").status(isStarted()).withData("time taken to be ready: ", readyTime.get()).build();
	}

}

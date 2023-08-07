package io.helidon.examples.quickstart.mp;

import jakarta.enterprise.context.RequestScoped;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("/greet")
@RequestScoped
public class MyResource {

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public String getDefaultMessage() {
		return ("World");
	}

}

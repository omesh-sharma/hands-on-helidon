package io.helidon.examples.quickstart.mp;

import java.util.List;
import java.util.concurrent.ExecutionException;

import org.eclipse.microprofile.faulttolerance.Fallback;
import org.eclipse.microprofile.metrics.MetricUnits;
import org.eclipse.microprofile.metrics.annotation.Counted;
import org.eclipse.microprofile.metrics.annotation.Metered;
import org.eclipse.microprofile.metrics.annotation.Timed;
import org.eclipse.microprofile.opentracing.Traced;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.helidon.examples.pojo.Book;
import io.helidon.media.jsonp.JsonpSupport;
import io.helidon.webclient.WebClient;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;

@RequestScoped
@Path("/publishers")
public class PublisherResource {
	
	@Inject
	MesssageProvider provider;

	@GET
	@Fallback(fallbackMethod = "myFallBackMethod", applyOn= ExecutionException.class)
	@Counted(name = "isbn-hit", description = "cunting num of requests", absolute = true)
	@Metered(name = "isbn-meter", absolute = true, unit = MetricUnits.PER_SECOND)
	@Timed(name = "isbn-Timer", absolute = true, unit = MetricUnits.MILLISECONDS)
	//@io.micrometer.core.annotation.Counted(value = "micro-counted")
	public JsonArray getData() throws Exception{
		JsonArray array = null;
		WebClient client = WebClient.builder().baseUri("http://localhost:8081/books")
				.addMediaSupport(JsonpSupport.create()).build();
		try {
			array = client.get().request(JsonArray.class).get();
			
			TypeReference<List<Book>> typeReference = new TypeReference<List<Book>>()
			{
				
			};
			ObjectMapper mapper = new ObjectMapper();
			List<Book> books = mapper.readValue(array.toString(), typeReference);
			books.forEach((b) -> System.out.println(b));
			
			System.out.println(provider.getMessage());

			
			System.out.println(provider.getMessage2());
			
			throw new RuntimeException();
			
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return array;
		
	}

	JsonArray myFallBackMethod()
	{
		JsonArray value = Json.createArrayBuilder()
				.add(Json.createObjectBuilder().add("bookName", "abcd").add("isbn", 10).add("price", 123)).build();
		return value;
	}
	
	@Traced
	String getMessage() {
		return getMessage2();
	}

	@Traced
	String getMessage2() {
		return "welcome to helidon";
	}


}
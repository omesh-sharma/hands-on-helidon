package io.helidon.examples.quickstart.mp;

import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.concurrent.ExecutionException;

import org.eclipse.microprofile.faulttolerance.Fallback;
import org.eclipse.microprofile.faulttolerance.Retry;
import org.eclipse.microprofile.faulttolerance.Timeout;
import org.eclipse.microprofile.metrics.MetricUnits;
import org.eclipse.microprofile.metrics.annotation.Counted;
import org.eclipse.microprofile.metrics.annotation.Metered;
import org.eclipse.microprofile.metrics.annotation.Timed;
import org.eclipse.microprofile.opentracing.Traced;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
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
	// @Fallback(fallbackMethod = "myFallbackMethod", applyOn =
	// ExecutionException.class)
	// @Retry(maxRetries = 3, delay = 10000, delayUnit = ChronoUnit.MILLIS, maxDuration = 1800000, durationUnit = ChronoUnit.MILLIS)
	@Timeout(value = 10000, unit = ChronoUnit.MILLIS)
	public JsonArray getData() throws Exception {
			System.out.println("******trying*********");
			JsonArray array = null;
			WebClient client = WebClient.builder().baseUri("http://localhost:8081/books")
					.addMediaSupport(JsonpSupport.create()).build();

			try {
				synchronized (this) {
					wait(8000);
				}

			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
	try {
				array = client.get().request(JsonArray.class).get();
				TypeReference<List<Book>> typeReference = new TypeReference<List<Book>>() {
				};
				ObjectMapper mapper = new ObjectMapper();
				List<Book> books = mapper.readValue(array.toString(), typeReference);
				books.forEach((b) -> System.out.println(b));
				System.out.println(provider.getMessage());
				System.out.println(provider.getMessage2());

				throw new RuntimeException();
			}
//				catch (InterruptedException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			} catch (ExecutionException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			} 
			catch (JsonMappingException e) {
//				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (JsonProcessingException e) {
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
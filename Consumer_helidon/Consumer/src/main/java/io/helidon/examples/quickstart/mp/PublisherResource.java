package io.helidon.examples.quickstart.mp;

import java.util.List;
import java.util.concurrent.ExecutionException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.helidon.examples.pojo.Book;
import io.helidon.media.jsonp.JsonpSupport;
import io.helidon.webclient.WebClient;
import jakarta.enterprise.context.RequestScoped;
import jakarta.json.JsonArray;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;

@RequestScoped
@Path("/publishers")
public class PublisherResource {

	@GET
	public JsonArray getData() {
		JsonArray array = null;
		WebClient client = WebClient.builder().baseUri("http://localhost:8080/books")
				.addMediaSupport(JsonpSupport.create()).build();
		try {
			array = client.get().request(JsonArray.class).get();
			
			TypeReference<List<Book>> typeReference = new TypeReference<List<Book>>()
			{
				
			};
			ObjectMapper mapper = new ObjectMapper();
			List<Book> books = mapper.readValue(array.toString(), typeReference);
			books.forEach((b) -> System.out.println(b));
			
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

}
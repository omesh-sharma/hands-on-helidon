package io.helidon.examples.controllers;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.QueryParam;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.enterprise.context.RequestScoped;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DefaultValue;
import jakarta.ws.rs.FormParam;
import jakarta.ws.rs.core.*;

import java.util.*;

import io.helidon.examples.pojo.Book;
import jakarta.ws.rs.core.*;

@Path("/books")
@RequestScoped

public class BookResources {

//	@GET
//	public Book readBook() {
//		return new Book(12, "Helidon 2", 12001);
//	}
	

	@GET
	public java.util.List<Book> readListOfBooks() {
		return Arrays.asList(new Book(1, "helidon 3", 24343), new Book(1, "omesj123", 4343));
	}
	
	@GET
	@Path("/name")
	public Book readBook(@DefaultValue("green")  @QueryParam("name") String name) {
		return new Book(100, name , 23232);
	}
	
	
	public java.util.List<Book> readBooks() {
		return Arrays.asList(new Book(1, "helidon 3", 24343), new Book(1, "omesj123", 4343));
	}
	
	
	
	@GET
	@Path("/isbn/{isbn}")
	public Response readBook(@PathParam("isbn") int isbn) {
		if(isbn > 0){
			
			//return new Book(isbn, "Helidon 2", 12001);
			
			//Or
			
			return Response.status(Response.Status.OK).entity(new Book(isbn, "Helidon 2", 12001)).build();

		}
		return Response.status(Response.Status.NO_CONTENT).build();
		
	}
	
	@GET
	@Path("/isbn/{isbn}/{name}")
	public Book readBook(@PathParam("isbn") int isbn, @PathParam("name") String name) {
		return new Book(isbn, name, 12001);
	}
	
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Response postBook(Book book) {
		
		if(book.getIsbn() > 0){
			System.out.println("receieved" + book);
			book.setBookName(book.getBookName().toUpperCase());
			return Response.status(Response.Status.CREATED).entity(book).build();
		}
		
		//return book;
		
		//or
		
		return Response.status(Response.Status.CONFLICT).build();
		
	}
	
	@POST
	@Consumes("application/x-www-form-urlencoded")
	public Response postBook(@FormParam("name")String name, @FormParam("isbn")int isbn, @FormParam("price")long price) {
		Book book = new Book(isbn, name, price);
		if(isbn > 0) {
			return Response.status(Response.Status.CREATED).entity(book).build();
		}
		return Response.status(Response.Status.CONFLICT).build();
	}
	
}

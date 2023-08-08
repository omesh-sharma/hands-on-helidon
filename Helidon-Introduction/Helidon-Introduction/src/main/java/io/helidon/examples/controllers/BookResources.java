package io.helidon.examples.controllers;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.QueryParam;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.DefaultValue;
import jakarta.ws.rs.FormParam;
import jakarta.ws.rs.core.*;

import java.util.*;

import io.helidon.examples.pojo.Book;


@Path("/books")
@RequestScoped

public class BookResources {

//	@GET
//	public Book readBook() {
//		return new Book(12, "Helidon 2", 12001);
//	}
	
	@Inject
	BookRepository repository;	
	
//	@GET
//	public java.util.List<Book> readListOfBooks() {
//		return Arrays.asList(new Book(1, "helidon 3", 24343), new Book(1, "omesj123", 4343));
//	}
	
//	@GET
//	@Path("/name")
//	public Book readBook(@DefaultValue("green")  @QueryParam("name") String name) {
//		return new Book(100, name , 23232);
//	}
	
	
	
	@GET
	@Path("/name")
	public Response readBook(@DefaultValue("green")  @QueryParam("name") String name) {

		List<Book> books = repository.findAllBooksByName(name);
		
		if (books.size()> 0) {
			return Response.status(Response.Status.OK).entity(books).build();
		}
		
		return Response.status(Response.Status.NO_CONTENT).build();
	}
	
	
//	@PUT
//	@Path("/{isbn}/{price}")
//	public Response updateBookPrice(@PathParam("price") int price) {
//		return Response.status(Response.Status.NO_CONTENT).build();
//	}
//
//	@PUT
//	@Path("/{isbn}/{name}")
//	public Response updateBookName(@PathParam("isbn") String isbn) {
//		return Response.status(Response.Status.NO_CONTENT).build();
//	}
//
//	@DELETE
//	@Path("/{isbn}")
//	public Response deleteBook(@PathParam("isbn") int isbn) {
//		return Response.status(Response.Status.NO_CONTENT).build();
//
//	}
//	
	
	@PUT
	@Path("/{isbn}/{price}")
	public Response updateBookPrice(@PathParam("isbn") int isbn, @PathParam("price") long price) {
	int success = repository.updateBookPrice(isbn,price);
	if(success == 0) {
	return Response.status(Response.Status.OK).build();
	}else {
	return Response.status(Response.Status.EXPECTATION_FAILED).build();
	}
	}

	@DELETE
	@Path("/{isbn}")
	public Response deleteBook(@PathParam("isbn") int isbn) {
	int success = repository.deleteBook(isbn);
	if(success == 0) {
	return Response.status(Response.Status.OK).build();
	}else {
	return Response.status(Response.Status.EXPECTATION_FAILED).build();
	}
	}
	
//	@DELETE
//	@Path("/{deletename}")
//	public Response deleteBookByName(@QueryParam("deletename") String name) {
//
//		Boolean res =  repository.deleteBookByName(name);
//		
//		if (res == true) {
//			return Response.status(Response.Status.OK).build();
//		}
//		
//		return Response.status(Response.Status.NO_CONTENT).build();
//	}
	
	
//	public java.util.List<Book> readBooks() {
//		
//		return Arrays.asList(new Book(1, "helidon 3", 24343), new Book(1, "omesj123", 4343));
//	}
	
	
	@GET
	public Response readBooks() {
		
		List<Book> books=repository.findAllBooks();
		return Response.status(Response.Status.OK).entity(books).build();
		
		///return Arrays.asList(new Book(1, "helidon 3", 24343), new Book(1, "omesj123", 4343));
	}
	
	
	@GET
	@Path("/isbn/{isbn}")
	public Response readBook(@PathParam("isbn") int isbn) {
		
		Book book=repository.findBookByISBN(isbn);
		
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
		
		Book book_saved = repository.createBook(book);
		
		if(book != null){
			System.out.println("receieved" + book);
			//book.setBookName(book.getBookName().toUpperCase());
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

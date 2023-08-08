package io.helidon.examples.controllers;

import java.util.List;

import org.yaml.snakeyaml.constructor.DuplicateKeyException;

import jakarta.persistence.Query;

import io.helidon.examples.pojo.Book;



import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.Path;

@ApplicationScoped
public class BookRepository {
		
		@PersistenceContext
		EntityManager entityManager;
		
		public Book findBookByISBN(int isbn) {
			Book book = entityManager.find(Book.class, isbn);
			
			if(book != null) {
				return book;
			}
			return new Book();
				
		}
		
		
		@Transactional
		public Book createBook(Book book) {
			Book book2 = null;
			try {
				entityManager.persist(book);
			}
			catch (DuplicateKeyException e)
			{
			// Todo : handle exception
			e.printStackTrace();
			}
			
			book2 = book;
			return book2;
		}
		
	public List<Book>findAllBooks(){
		Query query_findAll=entityManager.createQuery("FROM io.helidon.examples.pojo.Book b");
		return query_findAll.getResultList();
	}
	
	
	public List<Book> findAllBooksByName(String bookname){
		Query Query_findAll = entityManager.createQuery("FROM io.helidon.examples.pojo.Book b where b.bookName=:name");
		Query_findAll.setParameter("name", bookname);
		return Query_findAll.getResultList();
	}
	
	public List<Book> findAllBooksByISBN(int isbn) {
		Query query_findAll = entityManager.createQuery("FROM io.helidon.examples.pojo.Book b where b.isbn=:isbn");
		query_findAll.setParameter("isbn", isbn);
		return query_findAll.getResultList();
		}
	
//	@Transactional
//	public Boolean deleteBookByName(String bookName)
//	{	
//		System.out.println("$$$$$$$$$$$$\n\n" +bookName);
//		
//		List<Book> booksFound = findAllBooksByName(bookName);
//		if(booksFound.size() > 0)
//		{
////			Query send_delete_request = entityManager.createQuery("delete from io.helidon.examples.pojo.Book b where b.bookName=:name");
////			send_delete_request.setParameter("name", bookName);
////			send_delete_request.executeUpdate();
//			entityManager.remove(booksFound.get(0));
//			return true;
//		}
//		return false;
//	}
	
	@Transactional
	public int updateBookPrice(int isbn,long price) {
	try {
	List<Book> books = findAllBooksByISBN(isbn);
	books.get(0).setPrice(price);
	//persist is not required as set will do the update
	//entityManager.persist(books.get(0));
	}catch(DuplicateKeyException exp) {
	exp.printStackTrace();
	return 1;
	}
	return 0;
	}
	
	@Transactional
	public int deleteBook(int isbn) {
	try {
	List<Book> books = findAllBooksByISBN(isbn);
	entityManager.remove(books.get(0));
	}catch(DuplicateKeyException exp) {
	exp.printStackTrace();
	return 1;
	}
	return 0;
	}
}

package io.helidon.examples.pojo;

public class Book {

	private int isbn;
	private String bookName;
	private long price;

	public Book() {
		// TODO Auto-generated constructor stub
	}

	public Book(int isbn, String bookName, long price) {
		super();
		this.isbn = isbn;
		this.bookName = bookName;
		this.price = price;
	}

	public int getIsbn() {
		return isbn;
	}

	public void setIsbn(int isbn) {
		this.isbn = isbn;
	}

	public String getBookName() {
		return bookName;
	}

	public void setBookName(String bookName) {
		this.bookName = bookName;
	}

	public long getPrice() {
		return price;
	}

	public void setPrice(long price) {
		this.price = price;
	}

	@Override
	public String toString() {
		return "Book [isbn=" + isbn + ", bookName=" + bookName + ", price=" + price + "]";
	}

}

package lecture8;

@Serialize(as = "bk")
@Deserialize(as = "bk")

public class Book {
	private String name;
	private String author;
	private double price;
	
	public Book() {
		super();
	}
	public Book(String name, String author, double price) {
		super();
		this.name = name;
		this.author = author;
		this.price = price;
	}
	
	@Serialize(as = "name")
	public String getName() {
		return name;
	}
	@Deserialize(as = "name")
	public void setName(String name) {
		this.name = name;
	}
	@Serialize(as = "author")
	public String getAuthor() {
		return author;
	}
	@Deserialize(as = "author")
	public void setAuthor(String author) {
		this.author = author;
	}

	@Serialize(as = "price")
	public double getPrice() {
		return price;
	}
	
	@Deserialize(as = "price")
	public void setPrice(double price) {
		this.price = price;
	}
	
	@Override
	public String toString() {
		return "Book [name=" + name + ", author=" + author + ", price=" + price + "]";
	}

}

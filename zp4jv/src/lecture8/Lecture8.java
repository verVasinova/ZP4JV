package lecture8;


import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class Lecture8 {

	public static void main(String[] args) throws Exception {
		List<Class<?>> clazzes = new ArrayList<>(Arrays.asList(Employee.class, Book.class));
		Serializer s = new Serializer(new FileOutputStream("em.xml"));
		Deserializer d = new Deserializer(clazzes, new FileInputStream("em.xml"));
		
		Employee e = new Employee("Tomas Pech", 45, 7000);
		s.serialize(e);
		
		Book b = new Book("Harry Potter", "J.K. Rowling", 300);
		s.setOut(new FileOutputStream("book.xml"));
		s.serialize(b);
		
		Employee i = (Employee) d.deserialize();
		System.out.println(i.toString());
		d.setIs(new FileInputStream("book.xml"));
		Book bk = (Book) d.deserialize();
		System.out.println(bk.toString());
		
	}

}

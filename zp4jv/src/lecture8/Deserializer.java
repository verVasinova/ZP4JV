package lecture8;

import java.io.InputStream;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

public class Deserializer {
	private List<Class<?>> clazzes;
	private InputStream is;

	public Deserializer(List<Class<?>> clazzes, InputStream in) {
		this.clazzes = clazzes;
		this.is = in;
	}

	public void setIs(InputStream is) {
		this.is = is;
	}

	Object deserialize() throws Exception {
		DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory
				.newInstance();
		DocumentBuilder documentBuilder = documentBuilderFactory
				.newDocumentBuilder();

		Document doc = documentBuilder.parse(is);
		Object result;
		Element root = doc.getDocumentElement();
		Class<?> clazz = getDeserializeOfClass(root.getNodeName());
		if(clazz != null){
		result = clazz.newInstance();
		
		for (int i = 0; i < root.getChildNodes().getLength(); i++) {
			Node node = root.getChildNodes().item(i);

			for (Method m : getDeserializeMethods(clazz)) {
				if (m.getAnnotation(Deserialize.class).as().equals(node.getNodeName())){
					if (!(m.getParameterTypes().length > 1))
						m.invoke(result, getTypeOfArgument(node.getTextContent()));
					else
						throw new Exception("Metoda ma vic parametru!");
				}
			}
		}
		}
		else
			throw new Exception("Zadna trida tuto anotaci neobsahuje");

		return result;

	}
	
	private Class<?> getDeserializeOfClass(String s) {
		for(Class<?> c : clazzes){
			if( c.getAnnotation(Deserialize.class).as().equalsIgnoreCase(s)){
				return c;		  
			}
		}
		return null;
	}
	
	private List<Method> getDeserializeMethods(Class<?> clazz){
		List<Method> methods = new ArrayList<>();
		
		for(Method m :  clazz.getDeclaredMethods()){
			if(m.getAnnotation(Deserialize.class) != null){
				methods.add(m);
			}
				
		}
		return methods;
	}

	private Object getTypeOfArgument(String arg) {
		if (isInteger(arg))
			return Integer.valueOf(arg);
		else if (isFloat(arg))
			return Double.valueOf(arg);
		return arg;
	}

	private boolean isInteger(String s) {
		return s.matches("[-+]?\\d+");
	}

	private boolean isFloat(String s) {
		return s.matches("[+-]?\\d*\\.?\\d*");
	}

}

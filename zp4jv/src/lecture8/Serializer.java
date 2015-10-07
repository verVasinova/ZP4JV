package lecture8;

import java.io.OutputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class Serializer {	
	private OutputStream out;

	public Serializer(OutputStream out) {
		super();
		this.out = out;
	}
	
	public void setOut(OutputStream out) {
		this.out = out;
	}
	
	void serialize(Object obj) throws Exception{
		
		DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory
				.newInstance();
		DocumentBuilder documentBuilder = documentBuilderFactory
				.newDocumentBuilder();
		Document doc = documentBuilder.newDocument();

		Class<?> clazz = obj.getClass();
		Method[] methods = clazz.getDeclaredMethods();
		Annotation classAn = clazz.getAnnotation(Serialize.class);
		
		if (classAn != null) {
			Element rootElement = doc.createElement(((Serialize) classAn).as());
			doc.appendChild(rootElement);

			for (Method m : methods) {
				Annotation methodAn = m.getAnnotation(Serialize.class);
				if (methodAn != null && m.getReturnType()!= null) {
					Element methodNode = doc.createElement(((Serialize) methodAn).as());
					methodNode.setTextContent(m.invoke(obj).toString());
					rootElement.appendChild(methodNode);
				}
			}
		}
		else
			throw new Exception("Nelze serializovat!");
		
		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		Transformer transformer = transformerFactory.newTransformer();
		transformer.setOutputProperty(OutputKeys.INDENT, "yes");

		DOMSource source = new DOMSource(doc);
		StreamResult result = new StreamResult(out);
		transformer.transform(source, result);
		
	}

	

	

}

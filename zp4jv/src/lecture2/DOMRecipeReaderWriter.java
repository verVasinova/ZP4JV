package lecture2;

import java.io.InputStream;
import java.io.OutputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

public class DOMRecipeReaderWriter implements RecipeReaderWriter {

	@Override
	public Recipe loadRecipe(InputStream input) throws Exception {

		DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory
				.newInstance();
		DocumentBuilder documentBuilder = documentBuilderFactory
				.newDocumentBuilder();
		Recipe recept = new Recipe();

		Document doc = documentBuilder.parse(input);

		Element root = doc.getDocumentElement();
		recept.setServings(Integer.valueOf(root.getAttribute("servings")));

		for (int i = 0; i < root.getChildNodes().getLength(); i++) {
			Node node = root.getChildNodes().item(i);

			switch (node.getNodeName()) {
			case "name":
				recept.setName(node.getTextContent());
				break;

			case "cooktime":
				recept.setCooktime(Integer.valueOf(node.getTextContent()));
				break;

			case "directions":
				for (int j = 1; j < node.getChildNodes().getLength(); j += 2) {
					Node child = node.getChildNodes().item(j);
					recept.addStep(child.getTextContent());
				}
				break;

			case "ingredients":
				for (int k = 1; k < node.getChildNodes().getLength(); k += 2) {
					Node child = node.getChildNodes().item(k);
					int amount = Integer.valueOf(child.getAttributes()
							.getNamedItem("amount").getNodeValue());
					String unit = child.getAttributes().getNamedItem("unit")
							.getNodeValue();
					recept.addIngredient(amount, unit, child.getTextContent());
				}
			}
		}

		return recept;
	}

	@Override
	public void storeRecipe(OutputStream output, Recipe recept)
			throws Exception {
		// vytvori objekty pro sestaveni dokumentu
		DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory
				.newInstance();
		DocumentBuilder documentBuilder = documentBuilderFactory
				.newDocumentBuilder();
		
		// vytvori prazdny dokument
		Document doc = documentBuilder.newDocument();
		
		Element root = doc.createElement("recipe");
		root.setAttribute("servings", Integer.toString(recept.getServings()));
		doc.appendChild(root);
		
		Element name = doc.createElement("name");
		name.setTextContent(recept.getName());
		root.appendChild(name);
		
		Element cooktime = doc.createElement("cooktime");
		cooktime.setTextContent(Integer.toString(recept.getCooktime()));
		root.appendChild(cooktime);
		
		Element ingredients = doc.createElement("ingredients");
		
		for(Ingredient i : recept.getIngredients()){
			Element ingredient = doc.createElement("ingredient");
			ingredient.setAttribute("amount", Integer.toString(i.getAmount()));
			ingredient.setAttribute("unit", i.getUnit());
			ingredient.setTextContent(i.getIngredient());
			
			ingredients.appendChild(ingredient);
		}
		
		root.appendChild(ingredients);
		
		Element directions = doc.createElement("directions");
		
		for(String s : recept.getDirections()){
			Element step = doc.createElement("step");
			step.setTextContent(s);
			directions.appendChild(step);
		}
		
		root.appendChild(directions);
		
		// pro vystup se pouzije transformace
		TransformerFactory transformerFactory = TransformerFactory
				.newInstance();
		Transformer transformer = transformerFactory.newTransformer();
		transformer.setOutputProperty(OutputKeys.INDENT, "yes");

		DOMSource source = new DOMSource(doc);
		StreamResult result = new StreamResult(output);

		transformer.transform(source, result);
	}

}
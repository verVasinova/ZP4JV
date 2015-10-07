package lecture1;

import java.io.InputStream;
import java.io.OutputStream;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.XMLStreamWriter;

public class StAXRecipeReaderWriter implements RecipeReaderWriter {

	@Override
	public Recipe loadRecipe(InputStream input) throws Exception {

		XMLInputFactory xmlInputFactory = XMLInputFactory.newInstance();
		XMLStreamReader reader = xmlInputFactory.createXMLStreamReader(input);

		Recipe recept = new Recipe();
		int amount;
		String unit;

		while (reader.hasNext()) {
			switch (reader.next()) {
			case XMLStreamReader.START_ELEMENT:
				switch (reader.getName().toString()) {
				case "recipe":
					recept.setServings(Integer.valueOf(reader
							.getAttributeValue(0)));
					break;

				case "name":
					reader.next();
					recept.setName(reader.getText());
					break;

				case "cooktime":
					reader.next();
					recept.setCooktime(Integer.valueOf(reader.getText()));
					break;

				case "ingredient":
					amount = Integer.valueOf(reader.getAttributeValue(0));
					unit = reader.getAttributeValue(1);
					reader.next();
					recept.addIngredient(amount, unit, reader.getText());
					break;

				case "step":
					reader.next();
					recept.addStep(reader.getText());
					break;

				}

				break;

			case XMLStreamReader.END_ELEMENT:
				//reader.next();
				break;

			case XMLStreamReader.CHARACTERS:
				// System.out.println("Text: " + reader.getText());
				break;

			default:
				break;
			}
		}

		return recept;
	}

	@Override
	public void storeRecipe(OutputStream output, Recipe recept)
			throws Exception {

		XMLOutputFactory xmlOutputFactory = XMLOutputFactory.newInstance();
		XMLStreamWriter xmlWriter = xmlOutputFactory
				.createXMLStreamWriter(output);

		xmlWriter.writeStartDocument();

		xmlWriter.writeStartElement("recipe");
		xmlWriter.writeAttribute("servings",
				Integer.toString(recept.getServings()));

		xmlWriter.writeStartElement("name");
		xmlWriter.writeCharacters(recept.getName());
		xmlWriter.writeEndElement();

		xmlWriter.writeStartElement("cooktime");
		xmlWriter.writeCharacters(Integer.toString(recept.getCooktime()));
		xmlWriter.writeEndElement();

		xmlWriter.writeStartElement("ingredients");

		for (Ingredient i : recept.getIngredients()) {
			xmlWriter.writeStartElement("ingredient");
			xmlWriter.writeAttribute("amount", Integer.toString(i.getAmount()));
			xmlWriter.writeAttribute("unit", i.getUnit());
			xmlWriter.writeCharacters(i.getIngredient());
			xmlWriter.writeEndElement();
		}
		xmlWriter.writeEndElement();

		xmlWriter.writeStartElement("directions");

		for (String s : recept.getDirections()) {
			xmlWriter.writeStartElement("step");
			xmlWriter.writeCharacters(s);
			xmlWriter.writeEndElement();
		}
		xmlWriter.writeEndElement();

		xmlWriter.writeEndElement();

		xmlWriter.writeEndDocument();

	}

}

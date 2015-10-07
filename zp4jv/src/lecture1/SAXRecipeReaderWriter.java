package lecture1;

import java.io.InputStream;
import java.io.OutputStream;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.Attributes;

public class SAXRecipeReaderWriter implements RecipeReaderWriter {
	
	@Override
	public Recipe loadRecipe(InputStream input) throws Exception {
		SAXParserFactory parserFactory = SAXParserFactory.newInstance();
		SAXParser parser = parserFactory.newSAXParser();

		Recipe recept = new Recipe();
		parser.parse(input, new DefaultHandler() {
			int amount;
			String unit;
			String element;

			@Override
			public void startElement(String uri, String localName,
					String qName, Attributes attributes) throws SAXException {
				element = qName;
				if (element.equals("ingredient")){
					unit = attributes.getValue("unit");
					amount = Integer.valueOf(attributes.getValue("amount"));
				}

				else if (element.equals("recipe")) {
					recept.setServings(Integer.parseInt(attributes
							.getValue("servings")));
				}
				
			}
			
			public void endElement(String uri, String localName, String qName)
					throws SAXException {
				element = "";
			}

			public void characters(char[] ch, int start, int length)
					throws SAXException {
				String text = new String(ch, start, length);
				
				if(element.equals("name")){
					recept.setName(text);
					
				}

				else if(element.equals("ingredient")){
					recept.addIngredient(amount, unit, new String(ch, start, length));
				}
				
				else if(element.equals("step")){
					recept.addStep(new String(ch, start, length));
				}
				
				else if(element.equals("cooktime")){
					recept.setCooktime(Integer.valueOf(text));
				}
	

			}

		});

		return recept;
	}

	@Override
	public void storeRecipe(OutputStream output, Recipe recept)
			throws Exception {
		throw new SAXWriterException("SAX - nelze zapisovat");

	}

}

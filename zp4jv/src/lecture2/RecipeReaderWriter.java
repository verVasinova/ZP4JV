package lecture2;

import java.io.InputStream;
import java.io.OutputStream;

public interface RecipeReaderWriter {
	/**
     * Nacte ze streamu XML soubor a dle nej vytvori prislusny objekt reprezentujici recept
     */
    public Recipe loadRecipe(InputStream input) throws Exception;

    /**
     * Ulozi do prislusneho streamu XML soubor predstavujici dany recept
     */
    public void storeRecipe(OutputStream output, Recipe recept) throws Exception;

}
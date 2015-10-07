package lecture3;


import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;










import javax.swing.DefaultListModel;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.KeyStroke;



public class MainForm extends JFrame {
	private static final long serialVersionUID = 1L;
	
	private static Map<String, Recipe> recipes = new HashMap<>();

	private JPanel mainPanel;
	private JMenuBar mainMenu;
	private JMenu menuFile;
	private JMenu menuRecipe;
	
	private DefaultListModel<String> listModel;
	private JList<String> mainList;
	
	private File dir;

	public MainForm() throws IOException, Exception {
		super();
		this.setTitle("Recepty");

		// po uzavreni okna, budou prostredky s nim spojene odstraneny
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);

		// vytvori panel do ktereho budou vkladany dalsi komponenty
		mainPanel = new JPanel();
		mainPanel.setLayout(new GridLayout());

		initializeMenuBar();

		this.setJMenuBar(mainMenu);
		
		
		listModel = new DefaultListModel<>();
		mainList = new JList<>(listModel);
		
		this.setLayout(new GridLayout());
		this.add(mainList);

		// nastaveni preferovane velikosti
		this.setPreferredSize(new Dimension(400, 500));

		this.pack();
	}

	private void initializeMenuBar() {
		mainMenu = new JMenuBar();

		menuFile = new JMenu("Soubor");
		menuFile.setMnemonic(KeyEvent.VK_S);
		
		JMenuItem menuItemOpen = new JMenuItem("Naèíst");
		menuItemOpen.addActionListener(new OpenL());
		menuItemOpen.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, ActionEvent.CTRL_MASK));
		
		JMenuItem menuItemSave = new JMenuItem("Uložit");
		menuItemSave.addActionListener(new SaveL());
		menuItemSave.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_U, ActionEvent.CTRL_MASK));
		
		JMenuItem menuItemExit = new JMenuItem("Konec");
		menuItemExit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_K, ActionEvent.CTRL_MASK));

		menuItemExit.addActionListener((ActionEvent e) -> {
			// ukonceni prace s oknem
				saveRecipes();
				setVisible(false);
				dispose();
			});

		menuFile.add(menuItemOpen);
		menuFile.add(menuItemSave);
		menuFile.add(menuItemExit);

		mainMenu.add(menuFile);
		
		
		menuRecipe = new JMenu("Recept");
		menuRecipe.setMnemonic(KeyEvent.VK_R);
		
		JMenuItem menuItemAddRecipe = new JMenuItem("Pøidat");
		menuItemAddRecipe.addActionListener(new AddRecipeAction());
		menuItemAddRecipe.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P, ActionEvent.CTRL_MASK));
		
		JMenuItem menuItemEditRecipe = new JMenuItem("Upravit");
		menuItemEditRecipe.addActionListener(new EditRecipeAction());
		menuItemEditRecipe.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Z, ActionEvent.CTRL_MASK));
		
		JMenuItem menuItemDeleteRecipe = new JMenuItem("Smazat");
		menuItemDeleteRecipe.addActionListener(new DeleteRecipeAction());
		menuItemDeleteRecipe.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.CTRL_MASK));
		menuRecipe.add(menuItemAddRecipe);
		menuRecipe.add(menuItemEditRecipe);
		menuRecipe.add(menuItemDeleteRecipe);
		
		mainMenu.add(menuRecipe);
		
	}
	
	
	private void loadRecipes(File dir){
		File[] xmlFiles = null;
		DOMRecipeReaderWriter dom = new DOMRecipeReaderWriter();


		xmlFiles = dir.listFiles(new FilenameFilter() {

				@Override
				public boolean accept(File folder, String name) {
					return name.toLowerCase().endsWith(".xml");
				}
			});

		Recipe rcp;
		System.out.println(xmlFiles.length);
		for (File f : xmlFiles) {
			
			try {
				rcp = dom.loadRecipe(new FileInputStream(f));
				recipes.put(rcp.getName(), rcp);
				listModel.addElement(rcp.getName());
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}

		}

	}
	
	private void saveRecipes(){
		DOMRecipeReaderWriter dom = new DOMRecipeReaderWriter();
		int i = 1;

		for(Recipe r : recipes.values()){
			try {
				System.out.println(dir + "\\recipe"+i+".xml");
				dom.storeRecipe(new FileOutputStream(dir + "\\recept"+ i +".xml"), r);
				i++;
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
	}
	
	private class AddRecipeAction implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			AddEditDialog dlg = new AddEditDialog(MainForm.this, null );
			dlg.setModal(true);
			dlg.setVisible(true);
			dlg.dispose();
			if (dlg.getResult() != null){
				listModel.addElement(dlg.getResult().getName());
				recipes.put(dlg.getResult().getName(), dlg.getResult());
			}
				
		}
	}
	
	private class EditRecipeAction implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			
			if (mainList.getSelectedIndex() != -1) {
				String name = listModel.get(mainList.getSelectedIndex());
				AddEditDialog dlg = new AddEditDialog(MainForm.this, recipes.get(name));
				dlg.setModal(true);
				dlg.setVisible(true);
				dlg.dispose();
				listModel.set(mainList.getSelectedIndex(), dlg.getResult().getName());
				recipes.remove(name);
				recipes.put(dlg.getResult().getName(), dlg.getResult());
			}
				
		}
	}
	
	private class DeleteRecipeAction implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			if (mainList.getSelectedIndex() != -1) {
				DeleteDialog dlg = new DeleteDialog(MainForm.this);
				dlg.setModal(true);
				dlg.setVisible(true);
				dlg.dispose();
				if (dlg.getResult()){
					String name = listModel.get(mainList.getSelectedIndex());
					listModel.remove(mainList.getSelectedIndex());
					recipes.remove(name);
					
				}
					
			}
		}
	}
	
	class OpenL implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			JFileChooser c = new JFileChooser();
			c.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
			int rVal = c.showOpenDialog(MainForm.this);

			if (rVal == JFileChooser.APPROVE_OPTION) {
				dir = c.getCurrentDirectory();
				System.out.println(dir);
				loadRecipes(dir);

			}
		}
	}
	
	class SaveL implements ActionListener {
	    public void actionPerformed(ActionEvent e) {
	      JFileChooser c = new JFileChooser();
	      c.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

	      int rVal = c.showSaveDialog(MainForm.this);
	      if (rVal == JFileChooser.APPROVE_OPTION) {
	        dir = c.getCurrentDirectory();
	        saveRecipes();
	        
	      }
	    }
	  }

	
}

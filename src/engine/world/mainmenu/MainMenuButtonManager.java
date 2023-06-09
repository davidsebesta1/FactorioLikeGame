package engine.world.mainmenu;

import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.util.ArrayList;

import engine.Game;
import engine.world.mainmenu.loadworldmenu.LoadGameButton;
import engine.world.mainmenu.newworldmenu.NewGameButton;
import engine.world.mainmenu.newworldmenu.NewStandardGameButton;
import engine.world.mainmenu.quitmenu.CancelQuitButton;
import engine.world.mainmenu.quitmenu.FinalizeQuitButton;
import engine.world.mainmenu.quitmenu.QuitGameButton;
import math.Vector2;

/**
 * A manager of main menu buttons, provides functionality and rendering of it.
 * @author David Šebesta
 *
 */
public class MainMenuButtonManager {

	private ArrayList<Button> buttons;

	private ArrayList<Button> mainButtons;
	private ArrayList<Button> newSaveButtons;
	private ArrayList<Button> loadSaveButtons;
	private ArrayList<Button> quitButtons;

	public static final int MENU_MAIN = 0;
	public static final int MENU_NEWSAVE = 1;
	public static final int MENU_LOADSAVE = 2;
	public static final int MENU_QUITGAME = 3;

	/**
	 * A class constructor
	 */
	public MainMenuButtonManager() {
		buttons = new ArrayList<>();
		mainButtons = new ArrayList<>();
		newSaveButtons = new ArrayList<>();
		loadSaveButtons = new ArrayList<>();
		quitButtons = new ArrayList<>();

		//Main menu buttons
		NewGameButton btn1 = new NewGameButton(this, new Vector2(Game.getInstance().getResolution().getX() / 2
				- 75, Game.getInstance().getResolution().getY() / 2 - 50), new Vector2(150, 75), "New Game");
		registerButton(btn1);
		mainButtons.add(btn1);

		LoadGameButton btn2 = new LoadGameButton(this, new Vector2(Game.getInstance().getResolution().getX() / 2
				- 75, Game.getInstance().getResolution().getY() / 2 + 50), new Vector2(150, 75), "Load Game");
		registerButton(btn2);
		mainButtons.add(btn2);

		QuitGameButton btn3 = new QuitGameButton(this, new Vector2(Game.getInstance().getResolution().getX() / 2
				- 75, Game.getInstance().getResolution().getY() / 2 + 150), new Vector2(150, 75), "Quit");
		registerButton(btn3);
		mainButtons.add(btn3);

		//Quit menu buttons
		CancelQuitButton btnQ1 = new CancelQuitButton(this, new Vector2(Game.getInstance().getResolution().getX() / 2
				- 75, Game.getInstance().getResolution().getY() / 2 - 50), new Vector2(150, 75), "Cancel");
		registerButton(btnQ1);
		quitButtons.add(btnQ1);

		FinalizeQuitButton btnQ2 = new FinalizeQuitButton(this, new Vector2(Game.getInstance().getResolution().getX()
				/ 2 - 75, Game.getInstance().getResolution().getY() / 2 + 50), new Vector2(150, 75), "Quit");
		registerButton(btnQ2);
		quitButtons.add(btnQ2);
		
		//New game button
		NewStandardGameButton btnNW1 = new NewStandardGameButton(this, new Vector2(Game.getInstance().getResolution().getX()
				/ 2 - 75, Game.getInstance().getResolution().getY() / 2 + 50), new Vector2(150, 75), "Standard Game");
		registerButton(btnNW1);
		newSaveButtons.add(btnNW1);
		
		
		showMenu(0);
	}

	/**
	 * Register button, only registered buttons are being drawn
	 * @param button
	 */
	private void registerButton(Button button) {
		if (buttons.contains(button))
			return;

		buttons.add(button);
	}

	/**
	 * Draw method for Graphics2D object
	 * @param g2d
	 * @see Graphics2D
	 */
	public void draw(Graphics2D g2d) {
		for (Button button : buttons) {
			if (!button.isVisible()) {
				continue;
			}

			if (button.getTexture() != null) {
				g2d.drawImage(button.getTexture().getImage(), null, (int) button.getLocation().getX(), (int) button.getLocation().getY());
			}

			if (!button.getText().equals("")) {
				g2d.setColor(Color.DARK_GRAY);
				g2d.fillRect((int) button.getLocation().getX(), (int) button.getLocation().getY(), (int) button.getSize().getX(), (int) button.getSize().getY());

				g2d.setColor(Color.WHITE);

				FontMetrics fontMetrics = g2d.getFontMetrics();
				int textWidth = fontMetrics.stringWidth(button.getText());
				int textHeight = fontMetrics.getHeight();

				int centerX = (int) (button.getLocation().getX() + button.getSize().getX() / 2 - textWidth / 2);
				int centerY = (int) (button.getLocation().getY() + button.getSize().getY() / 2 + textHeight / 2);

				g2d.drawString(button.getText(), centerX, centerY - 2);
			}
		}
	}

	/**
	 * Switches between drawn menu to specified one
	 * @param index
	 */
	public void showMenu(int index) {
		if (index < 0 || index > 3)
			return;

		switch (index) {
		case 0:
			showMainMenu();
			break;
		case 1:
			showNewSaveMenu();
			break;
		case 2:
			showLoadSaveMenu();
			break;
		case 3:
			showQuitMenu();
			break;
		}
	}

	/**
	 * Hides all of the buttons
	 */
	private void hideAllButtons() {
		for (Button button : buttons) {
			button.setVisible(false);
		}
	}

	/**
	 * Shows only buttons assigned to new save
	 */
	private void showNewSaveMenu() {
		hideAllButtons();
		for (Button button : newSaveButtons) {
			button.setVisible(true);
		}
	}

	/**
	 * Shows all buttons assigned to load save (note that loading saves doesnt work yet)
	 */
	private void showLoadSaveMenu() {
		hideAllButtons();
		for (Button button : loadSaveButtons) {
			button.setVisible(true);
		}
	}

	/**
	 * Shows all buttons that are assigned to quitting the game
	 */
	private void showQuitMenu() {
		hideAllButtons();
		for (Button button : quitButtons) {
			button.setVisible(true);
		}
	}

	/**
	 * Shows the buttons of the main menu itself
	 */
	private void showMainMenu() {
		hideAllButtons();
		for (Button button : mainButtons) {
			button.setVisible(true);
		}
	}

	/**
	 * Returns all buttons
	 * @return List of all buttons in main menu
	 */
	public ArrayList<Button> getButtons() {
		return buttons;
	}
}

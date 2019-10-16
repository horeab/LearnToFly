package libgdx.game.lib.learntofly.states;

import libgdx.game.Game;
import libgdx.game.lib.learntofly.handlers.GameStateManager;
import libgdx.game.lib.learntofly.to.GameInfo;
import libgdx.game.lib.learntofly.util.Utils;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;

public class GameFinishedScreen extends GameState {

	public GameFinishedScreen(GameStateManager gameStateManager, GameInfo gameInfo) {
		super(gameStateManager, gameInfo);
		gameInfoManager.setGameFinishedScreen(false);
		stage.addActor(createLayout());
	}

	private Table createLayout() {
		Table allTable = libgdxControlUtils.createAllScreenTable(gameInfo.getSelectedStage());

		Label congratulationsLabel = c.label(getLabel("congratulations"));
		congratulationsLabel.setFontScale(libgdxControlUtils.getFontScale());
		Label finishedLabel = c.label(getLabel("finished_game", gameInfo.getCurrentDay()));
		finishedLabel.setFontScale(libgdxControlUtils.getFontScale());
		Label optionsLabel = c.label(getLabel("confirm_msg"));
		optionsLabel.setAlignment(Align.center);
		optionsLabel.setFontScale(libgdxControlUtils.getFontScale() / 1.3f);
		Table labelTable = new Table(skin);

		float labelTopPad = valueForScaledHeight(5);
		labelTable.add(congratulationsLabel).padTop(labelTopPad).row();
		labelTable.add(finishedLabel).padTop(labelTopPad).row();
		labelTable.add(optionsLabel).padTop(labelTopPad);

		Table buttonsTable = new Table(skin);
		Table continueTable = new Table(skin);
		Table newGameTable = new Table(skin);

		buttonsTable.add(continueTable).row();
		buttonsTable.add(newGameTable).padTop(labelTopPad);

		allTable.add(labelTable).height(valueForScaledHeight(40)).width(displayWidth).row();
		allTable.add(buttonsTable).height(valueForScaledHeight(60)).width(displayWidth);

		continueTable.add(createContinueBtn()).width(Utils.getValueForPercent(displayWidth, 50)).height(getBtnHeight());
		newGameTable.add(createNewGameBtn()).width(Utils.getValueForPercent(displayWidth, 50)).height(getBtnHeight());
		return allTable;
	}

	private float getBtnHeight() {
		return valueForScaledHeight(20);
	}

	private float valueForScaledHeight(int percent) {
		return Utils.getValueForPercent(displayHeight, percent);
	}

	private TextButton createNewGameBtn() {
		TextButton newGame = new TextButton(getLabel("start_a_new_game"), skin);
		newGame.getStyle().font = Game.getInstance().getFontManager().getFont();
		newGame.getLabel().setFontScale(libgdxControlUtils.getFontScale());
		newGame.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				startStageScreen();
			}
		});
		return newGame;
	}

	private TextButton createContinueBtn() {
		TextButton continueBtn = new TextButton(getLabel("continue_this_game"), skin);
		continueBtn.getStyle().font = Game.getInstance().getFontManager().getFont();
		continueBtn.getLabel().setFontScale(libgdxControlUtils.getFontScale());
		continueBtn.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				if (gameInfo.gameExistsInMemory()) {
					gameStateManager.setUpgradeShopState(gameInfo);
				}
				else {
					startStageScreen();
				}
			}
		});
		return continueBtn;
	}

	void initNewGame() {
		gameInfoManager.resetAll();
		achievementsManager.resetAll();
		gameInfo = gameInfoManager.createGameInfo();
	}

	void startStageScreen() {
		initNewGame();
		gameStateManager.setStageState(gameInfo, StageScreen.StageScreenType.START_GAME);
	}
}

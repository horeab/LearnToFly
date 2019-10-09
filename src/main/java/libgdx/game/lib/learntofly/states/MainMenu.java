package libgdx.game.lib.learntofly.states;

import libgdx.game.lib.learntofly.handlers.GameStateManager;
import libgdx.game.lib.learntofly.main.Game;
import libgdx.game.lib.learntofly.to.GameInfo;
import libgdx.game.lib.learntofly.util.Resource;
import libgdx.game.lib.learntofly.util.Utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.badlogic.gdx.utils.Align;

public class MainMenu extends GameState {

	Dialog dialog;

	public MainMenu(GameStateManager gameStateManager, GameInfo gameInfo) {
		super(gameStateManager, gameInfo);
		initEnterGameStuff();
		stage.addActor(createLayout());
	}

	private void initEnterGameStuff() {
		this.gameInfo = gameInfoManager.createGameInfo();
	}

	private Table createLayout() {
		Table allTable = libgdxControlUtils.createAllScreenTable(gameInfo.getSelectedStage());
		Image background_second = c.image("background_second");

		Table controlsTable = libgdxControlUtils.createAllScreenTable(gameInfo.getSelectedStage(), false);
		Table soundTable = new Table(skin);
		soundTable.add(createSoundBtn())
				.width(getSoundMusicBtnSide())
				.height(getSoundMusicBtnSide())
				.padLeft(Utils.getValueForPercent(displayWidth, 80))
				.padTop(valueForScaledHeight(45) * libgdxControlUtils.getWidthDisplayRatio());
		soundTable.add(createMusicBtn())
				.width(getSoundMusicBtnSide())
				.height(getSoundMusicBtnSide())
				.padTop(valueForScaledHeight(45) * libgdxControlUtils.getWidthDisplayRatio());

		Table titleTable = new Table(skin);
		SpriteDrawable titleDr = Utils.getDrawable(assetManager, Resource.getTitleResource(game.getLanguage()));
		titleTable.setBackground(titleDr);

		Table buttonsTable = new Table(skin);
		Table continueTable = new Table(skin);
		Table newGameTable = new Table(skin);
		Table likeBtnTable = new Table(skin);

		int titleTableTopPad = 0;
		int soundTableTopPad = 0;
		float likeButtonTopPad = valueForScaledHeight(-20);
		float padBottomForBackground = valueForScaledHeight(80);
		if (!gameInfo.gameExistsInMemory()) {
			titleTableTopPad = 10;
			soundTableTopPad = 10;
			padBottomForBackground = valueForScaledHeight(70);
			likeButtonTopPad = -(getBtnHeight() - valueForScaledHeight(-10));
		}
		buttonsTable.add(continueTable)
				.height(valueForScaledHeight(30))
				.row();
		buttonsTable.add(newGameTable)
				.height(valueForScaledHeight(30))
				.padTop(-valueForScaledHeight(5))
				.row();

		controlsTable.add(soundTable)
				.height(valueForScaledHeight(5))
				.padTop(valueForScaledHeight(soundTableTopPad))
				.width(displayWidth).row();
		controlsTable.add(titleTable)
				.height(titleDr.getSprite().getHeight() / 2f)
				.width(titleDr.getSprite().getWidth() / 2f)
				.padTop(valueForScaledHeight(titleTableTopPad))
				.row();
		controlsTable.add(buttonsTable)
				.height(valueForScaledHeight(65))
				.width(displayWidth);

		continueTable.add(createContinueBtn()).width(getBtnWidth()).height(getBtnHeight());
		if (gameInfo.gameExistsInMemory()) {
			newGameTable.add(createNewGameBtn()).width(getBtnWidth()).height(getBtnHeight());
		}
		allTable.add(background_second).width(background_second.getWidth()).height(background_second.getHeight()).padBottom(padBottomForBackground).align(Align.bottom);
		allTable.add(controlsTable).padLeft(-background_second.getWidth());
		return allTable;
	}

	private Table createSoundBtn() {
		final Table soundBtn = c.table(Resource.sound_on);
		updateSoundOrMusicButton(soundBtn, preferencesManager.isSoundOn());
		soundBtn.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				preferencesManager.setSoundOn(!preferencesManager.isSoundOn());
				updateSoundOrMusicButton(soundBtn, preferencesManager.isSoundOn());
			}
		});
		return soundBtn;
	}

	void updateSoundOrMusicButton(Table soundOrMusicButton, boolean isOn) {
		for (Actor actor : soundOrMusicButton.getChildren()) {
			soundOrMusicButton.removeActor(actor);
		}
		Table backgroundTable = new Table(skin);
		if (!isOn) {
			Table overTable = new Table(skin);
			overTable.setBackground(Utils.getDrawable(assetManager, Resource.sound_music_disabled));
			backgroundTable.add(overTable)
					.width(getSoundMusicBtnSide() / 2)
					.height(getSoundMusicBtnSide() / 2);
		}
		soundOrMusicButton.add(backgroundTable);
	}

	private Table createMusicBtn() {
		final Table musicBtn = c.table(Resource.music_on);
		updateSoundOrMusicButton(musicBtn, preferencesManager.isMusicOn());
		musicBtn.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				preferencesManager.setMusicOn(!preferencesManager.isMusicOn());
				updateSoundOrMusicButton(musicBtn, preferencesManager.isMusicOn());
				gameStateManager.getOverallState().processMusic();
			}
		});
		return musicBtn;
	}

	private TextButton createNewGameBtn() {
		TextButton newGame = c.textButton(getLabel("new_game"), "green");
		newGame.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				dialog = startNewGameDialog().show(stage);
				dialog.setWidth(displayWidth / 1.09f);
				dialog.setHeight(displayHeight / 1.9f);
				dialog.padBottom(20);
			}
		});
		return newGame;
	}

	private TextButton createContinueBtn() {
		String playBtnText = gameInfo.gameExistsInMemory() ? getLabel("continue") : getLabel("play");
		TextButton continueBtn = c.textButton(playBtnText, "green");
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

	Dialog startNewGameDialog() {
		Dialog dialogToReturn = new Dialog("", skin, "dialog") {
			@Override
			protected void result(final Object obj) {
				if (Boolean.TRUE.equals(obj)) {
					startStageScreen();
				}
				dialog = null;
			}
		};
		TextButton notBtn = c.textButton(getLabel("no"), "default");
		TextButton yesBtn = c.textButton(getLabel("yes"), "default");
		dialogToReturn.getButtonTable().add(notBtn).width(getDialogBtnWidth()).height(getDialogBtnHeight());
		dialogToReturn.getButtonTable().add(yesBtn).width(getDialogBtnWidth()).height(getDialogBtnHeight());
		dialogToReturn.setObject(notBtn, false);
		dialogToReturn.setObject(yesBtn, true);
		Label label = new Label(getLabel("newgame_confirm"), skin);
		label.setAlignment(Align.center);
		dialogToReturn.text(label);

		return dialogToReturn;
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

	public boolean isDialogIsShown() {
		return dialog != null;
	}

	public void closeShownDialog() {
		if (dialog != null) {
			dialog.hide();
			dialog = null;
		}
	}

	private float getExtraBtnWidth() {
		return valueForScaledHeight(80);
	}

	private float getExtraBtnHeight() {
		return valueForScaledHeight(30);
	}

	private float getSoundMusicBtnSide() {
		return valueForScaledHeight(25) * libgdxControlUtils.getWidthDisplayRatio();
	}

	private float getBtnWidth() {
		return valueForScaledHeight(55);
	}

	private float getBtnHeight() {
		return valueForScaledHeight(20);
	}

	private float getDialogBtnWidth() {
		return valueForScaledHeight(25);
	}

	private float getDialogBtnHeight() {
		return valueForScaledHeight(15);
	}

	private float valueForScaledHeight(int percent) {
		return Utils.getValueForPercent(displayHeight, percent);
	}
}

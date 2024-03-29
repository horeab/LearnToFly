package libgdx.game.lib.learntofly.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.badlogic.gdx.utils.Align;

import libgdx.controls.label.MyWrappedLabel;
import libgdx.controls.label.MyWrappedLabelConfigBuilder;
import libgdx.controls.popup.MyPopup;
import libgdx.game.Game;
import libgdx.game.lib.learntofly.handlers.GameStateManager;
import libgdx.game.lib.learntofly.to.GameInfo;
import libgdx.game.lib.learntofly.util.LearnToFlyGameLabel;
import libgdx.game.lib.learntofly.util.Resource;
import libgdx.game.lib.learntofly.util.Utils;
import libgdx.resources.dimen.MainDimen;
import libgdx.utils.ScreenDimensionsManager;
import libgdx.utils.model.FontColor;
import libgdx.utils.model.FontConfig;

public class MainMenu extends GameState {

    public MainMenu(GameStateManager gameStateManager, GameInfo gameInfo) {
        super(gameStateManager, gameInfo);
        initEnterGameStuff();
    }

    @Override
    public void buildStage() {
        addActor(createLayout());
    }

    private void initEnterGameStuff() {
        this.gameInfo = gameInfoManager.createGameInfo();
    }

    private Table createLayout() {
        Table allTable = libgdxControlUtils.createAllScreenTable(gameInfo.getSelectedStage());
        allTable.setFillParent(true);

        Table controlsTable = libgdxControlUtils.createAllScreenTable(gameInfo.getSelectedStage(), false);
        Table soundTable = new Table(skin);
        soundTable.add(createSoundBtn())
                .width(getSoundMusicBtnSide())
                .height(getSoundMusicBtnSide())
                .padLeft(ScreenDimensionsManager.getScreenWidthValue(80))
                .padTop(valueForScaledHeight(45) * libgdxControlUtils.getWidthDisplayRatio());
        soundTable.add(createMusicBtn())
                .width(getSoundMusicBtnSide())
                .height(getSoundMusicBtnSide())
                .padTop(valueForScaledHeight(45) * libgdxControlUtils.getWidthDisplayRatio());

        Table titleTable = new Table(skin);
        MyWrappedLabel titleLabel = new MyWrappedLabel(new MyWrappedLabelConfigBuilder().setFontConfig(new FontConfig(
                FontColor.WHITE.getColor(),
                FontColor.BLACK.getColor(),
                FontConfig.FONT_SIZE * 6,
                8f)).setText(Game.getInstance().getAppInfoService().getAppName()).build());
        titleTable.add(titleLabel);

        Table buttonsTable = new Table(skin);
        Table continueTable = new Table(skin);
        Table newGameTable = new Table(skin);

        int titleTableTopPad = 0;
        int soundTableTopPad = 0;
        float padBottomForBackground = valueForScaledHeight(80);
        if (!gameInfo.gameExistsInMemory()) {
            titleTableTopPad = 10;
            soundTableTopPad = 10;
            padBottomForBackground = valueForScaledHeight(70);
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
                .width(ScreenDimensionsManager.getScreenWidth()).row();
        controlsTable.add(titleTable)
                .padTop(valueForScaledHeight(titleTableTopPad))
                .row();
        controlsTable.add(buttonsTable)
                .height(valueForScaledHeight(65))
                .width(ScreenDimensionsManager.getScreenWidth());

        continueTable.add(createContinueBtn()).width(getBtnWidth()).height(getBtnHeight());
        if (gameInfo.gameExistsInMemory()) {
            newGameTable.add(createNewGameBtn()).width(getBtnWidth()).height(getBtnHeight());
        }
        Image background_second = c.image("background_second");
        float backgroundSecondDimen = MainDimen.horizontal_general_margin.getDimen() * 25;
        allTable.add(background_second).width(backgroundSecondDimen).height(backgroundSecondDimen).padBottom(padBottomForBackground).align(Align.bottom);
        allTable.add(controlsTable).padLeft(-backgroundSecondDimen);
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
            overTable.setBackground(Utils.getDrawable(Resource.sound_music_disabled));
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

    @Override
    public void onBackKeyPress() {
        Gdx.app.exit();
    }

    private TextButton createNewGameBtn() {
        TextButton newGame = c.textButton(LearnToFlyGameLabel.l_new_game.getText(), "green");
        newGame.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                MyPopup myPopup = new MyPopup(getAbstractScreen()) {
                    @Override
                    protected void addButtons() {
                        TextButton notBtn = c.textButton(LearnToFlyGameLabel.l_no.getText(), "default");
                        TextButton yesBtn = c.textButton(LearnToFlyGameLabel.l_yes.getText(), "default");
                        notBtn.setWidth(ScreenDimensionsManager.getScreenWidthValue(20));
                        yesBtn.setWidth(ScreenDimensionsManager.getScreenWidthValue(20));
                        yesBtn.addListener(new ClickListener() {
                            @Override
                            public void clicked(InputEvent event, float x, float y) {
                                startStageScreen();
                                hide();
                            }
                        });
                        notBtn.addListener(new ClickListener() {
                            @Override
                            public void clicked(InputEvent event, float x, float y) {
                                hide();
                            }
                        });
                        getButtonTable().add(notBtn).padBottom(MainDimen.vertical_general_margin.getDimen()).width(notBtn.getWidth()).height(notBtn.getHeight() * 1.05f).row();
                        getButtonTable().add(yesBtn).padBottom(MainDimen.vertical_general_margin.getDimen()).width(yesBtn.getWidth()).height(yesBtn.getHeight() * 1.05f).row();
                    }

                    @Override
                    protected String getLabelText() {
                        return LearnToFlyGameLabel.l_newgame_confirm.getText();
                    }
                };
                myPopup.addToPopupManager();
            }
        });
        return newGame;
    }

    private TextButton createContinueBtn() {
        String playBtnText = gameInfo.gameExistsInMemory() ? LearnToFlyGameLabel.l_continue.getText() : LearnToFlyGameLabel.l_play.getText();
        TextButton continueBtn = c.textButton(playBtnText, "green");
        continueBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (gameInfo.gameExistsInMemory()) {
                    gameStateManager.setUpgradeShopState(gameInfo);
                } else {
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

    @Override
    public void dispose() {
        super.dispose();
    }

    void startStageScreen() {
        initNewGame();
        gameStateManager.setStageState(gameInfo, StageScreen.StageScreenType.START_GAME);
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
        return Utils.getValueForPercent(ScreenDimensionsManager.getScreenHeight(), percent);
    }
}

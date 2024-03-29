package libgdx.game.lib.learntofly.states;

import libgdx.controls.label.MyWrappedLabel;
import libgdx.game.Game;
import libgdx.game.lib.learntofly.handlers.GameStateManager;
import libgdx.game.lib.learntofly.to.GameInfo;
import libgdx.game.lib.learntofly.util.LearnToFlyGameLabel;
import libgdx.game.lib.learntofly.util.Utils;
import libgdx.utils.ScreenDimensionsManager;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;

public class GameFinishedScreen extends GameState {

    public GameFinishedScreen(GameStateManager gameStateManager, GameInfo gameInfo) {
        super(gameStateManager, gameInfo);
    }

    @Override
    public void buildStage() {
        gameInfoManager.setGameFinishedScreen(false);
        addActor(createLayout());
    }

    @Override
    public void onBackKeyPress() {
        gameStateManager.setUpgradeShopState(gameInfo);
    }

    private Table createLayout() {
        Table allTable = libgdxControlUtils.createAllScreenTable(gameInfo.getSelectedStage());
        allTable.setFillParent(true);
        MyWrappedLabel congratulationsLabel = c.label(LearnToFlyGameLabel.l_congratulations.getText());
        congratulationsLabel.setFontScale(libgdxControlUtils.getFontScale());
        MyWrappedLabel finishedLabel = c.label(LearnToFlyGameLabel.l_finished_game.getText(gameInfo.getCurrentDay()));
        finishedLabel.setFontScale(libgdxControlUtils.getFontScale());
        MyWrappedLabel optionsLabel = c.label(LearnToFlyGameLabel.l_confirm_msg.getText());
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

        allTable.add(labelTable).height(valueForScaledHeight(40)).width(ScreenDimensionsManager.getScreenWidth()).row();
        allTable.add(buttonsTable).height(valueForScaledHeight(60)).width(ScreenDimensionsManager.getScreenWidth());

        continueTable.add(createContinueBtn()).width(Utils.getValueForPercent(ScreenDimensionsManager.getScreenWidth(), 50)).height(getBtnHeight());
        newGameTable.add(createNewGameBtn()).width(Utils.getValueForPercent(ScreenDimensionsManager.getScreenWidth(), 50)).height(getBtnHeight());
        return allTable;
    }

    private float getBtnHeight() {
        return valueForScaledHeight(20);
    }

    private float valueForScaledHeight(int percent) {
        return Utils.getValueForPercent(ScreenDimensionsManager.getScreenHeight(), percent);
    }

    private TextButton createNewGameBtn() {
        TextButton newGame = new TextButton(LearnToFlyGameLabel.l_start_a_new_game.getText(), skin);
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
        TextButton continueBtn = new TextButton(LearnToFlyGameLabel.l_continue_this_game.getText(), skin);
        continueBtn.getStyle().font = Game.getInstance().getFontManager().getFont();
        continueBtn.getLabel().setFontScale(libgdxControlUtils.getFontScale());
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

    void startStageScreen() {
        initNewGame();
        gameStateManager.setStageState(gameInfo, StageScreen.StageScreenType.START_GAME);
    }
}

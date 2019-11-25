package libgdx.game.lib.learntofly.states;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;

import libgdx.controls.label.MyWrappedLabel;
import libgdx.game.lib.learntofly.handlers.GameStateManager;
import libgdx.game.lib.learntofly.main.Game;
import libgdx.game.lib.learntofly.to.GameInfo;
import libgdx.game.lib.learntofly.util.LearnToFlyGameLabel;
import libgdx.game.lib.learntofly.util.Resource;
import libgdx.game.lib.learntofly.util.Utils;
import libgdx.utils.ScreenDimensionsManager;

public class TutorialScreen extends GameState {

    public enum TutorialScreenType {
        START_GAME,
        ROCKET
    }

    TutorialScreenType tutorialScreenType;

    public TutorialScreen(GameStateManager gameStateManager, GameInfo gameInfo, TutorialScreenType tutorialScreenType) {
        super(gameStateManager, gameInfo);
        this.tutorialScreenType = tutorialScreenType;
    }

    @Override
    public void buildStage() {
        addActor(createLayout());
    }

    @Override
    public void onBackKeyPress() {
        tutorialScreenBackBtnClick();
    }

    private void tutorialScreenBackBtnClick() {
        if (((TutorialScreen) this).getTutorialScreenType() == TutorialScreen.TutorialScreenType.START_GAME) {
            gameStateManager.setMainMenuState();
        } else if (((TutorialScreen) this).getTutorialScreenType() == TutorialScreen.TutorialScreenType.ROCKET) {
            gameStateManager.setUpgradeShopState(gameInfo);
        }
    }

    private Table createLayout() {
        Table allTable = libgdxControlUtils.createAllScreenTable(gameInfo.getSelectedStage(), false);
        Table imageTable = new Table(skin);
        allTable.setFillParent(true);
        imageTable.setBackground(Utils.getDrawable(getResource()));
        imageTable.setTouchable(Touchable.enabled);
        imageTable.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                processNextScreen();
            }
        });
        MyWrappedLabel instructionsLabel = c.label(getInstructions());
        instructionsLabel.setFontScale(libgdxControlUtils.getFontScale());
        instructionsLabel.setAlignment(Align.topLeft);
        imageTable
                .add(instructionsLabel)
                .height(ScreenDimensionsManager.getScreenHeight())
                .width(ScreenDimensionsManager.getScreenWidth())
                .padLeft(getPadLeft())
                .padTop(getPadTop());
        allTable
                .add(imageTable)
                .height(ScreenDimensionsManager.getScreenHeight())
                .width(ScreenDimensionsManager.getScreenWidth());
        return allTable;
    }

    void processNextScreen() {
        if (tutorialScreenType == TutorialScreenType.ROCKET) {
            gameInfoManager.setDisplayRocketTutorial(false);
        }
        gameStateManager.setPlayState(gameInfo);
    }

    public TutorialScreenType getTutorialScreenType() {
        return tutorialScreenType;
    }

    private Resource getResource() {
        Resource resource = null;
        switch (tutorialScreenType) {
            case START_GAME:
                resource = Game.isDesktopGameMode() ? Resource.start_tutorial_desk : Resource.start_tutorial_mob;
                break;
            case ROCKET:
                resource = Game.isDesktopGameMode() ? Resource.rocket_tutorial_desk : Resource.rocket_tutorial_mob;
                break;
            default:
                break;
        }
        return resource;
    }

    private String getInstructions() {
        String instructions = null;
        switch (tutorialScreenType) {
            case START_GAME:
                instructions = LearnToFlyGameLabel.l_start_tutorial_mobile.getText();
                break;
            case ROCKET:
                instructions = LearnToFlyGameLabel.l_rocket_tutorial_mobile.getText();
                break;
            default:
                break;
        }
        return instructions;
    }

    private float getPadLeft() {
        float padLeft = 0;
        switch (tutorialScreenType) {
            case START_GAME:
                padLeft = ScreenDimensionsManager.getScreenWidthValue(10);
                break;
            case ROCKET:
                padLeft = ScreenDimensionsManager.getScreenWidthValue(10);
                break;
            default:
                break;
        }
        return padLeft;
    }

    private float getPadTop() {
        float padTop = 0;
        switch (tutorialScreenType) {
            case START_GAME:
                padTop = Utils.getValueForPercent(60, Game.getHeight());
                break;
            case ROCKET:
                padTop = Utils.getValueForPercent(60, Game.getHeight());
                break;
            default:
                break;
        }
        return padTop;
    }
}

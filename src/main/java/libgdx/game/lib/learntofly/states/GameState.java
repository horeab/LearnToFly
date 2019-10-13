package libgdx.game.lib.learntofly.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FillViewport;

import libgdx.game.lib.learntofly.handlers.GameStateManager;
import libgdx.game.lib.learntofly.main.Game;
import libgdx.game.lib.learntofly.storemanagers.AchievementsManager;
import libgdx.game.lib.learntofly.storemanagers.GameInfoManager;
import libgdx.game.lib.learntofly.storemanagers.PreferencesManager;
import libgdx.game.lib.learntofly.to.GameInfo;
import libgdx.game.lib.learntofly.util.LibgdxControlCreator;
import libgdx.game.lib.learntofly.util.LibgdxControlUtils;

public abstract class GameState {

    protected GameStateManager gameStateManager;
    protected Game game;
    protected Stage stage;
    protected LibgdxControlUtils libgdxControlUtils;
    protected LibgdxControlCreator c;
    protected GameInfoManager gameInfoManager;
    protected PreferencesManager preferencesManager;
    protected AchievementsManager achievementsManager;

    protected OrthographicCamera cam;
    protected OrthographicCamera hudCam;

    protected Skin skin;

    protected GameInfo gameInfo;

    protected float displayWidth;
    protected float displayHeight;

    TextButton backButton;

    float scale;

    protected GameState(GameStateManager gameStateManager, GameInfo gameInfo) {
        this.gameStateManager = gameStateManager;
        this.gameInfo = gameInfo;

        initFields();

        Gdx.input.setInputProcessor(stage);
        game.setBackKeyPressed(Gdx.input.isKeyPressed(Keys.BACK));
        createBackButton();
    }

    private void createBackButton() {
        backButton = new TextButton(getLabel("back"), skin, "green");
        backButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                processBackKeyPressed();
            }
        });
        backButton.getLabel().setFontScale(libgdxControlUtils.getFontScale());
    }

    private void initFields() {
        OverallState overallState = gameStateManager.getOverallState();
        libgdxControlUtils = overallState.getLibgdxControlUtils();
        scale = libgdxControlUtils.getHeightDisplayRatio();
        skin = overallState.getSkin();
        gameInfoManager = overallState.getGameInfoManager();
        preferencesManager = overallState.getPreferencesManager();
        achievementsManager = overallState.getAchievementsManager();
        c = new LibgdxControlCreator(skin, libgdxControlUtils);
        displayWidth = Game.getWidth();
        displayHeight = Game.getHeight();
        stage = new Stage(new FillViewport(Game.getWidth(), Game.getHeight()));
        game = gameStateManager.getGame();
        cam = game.getCam();
        hudCam = game.getHudCam();
    }

    public Stage getStage() {
        return stage;
    }

    public void dispose() {
        stage.dispose();
    }

    public void render() {
        Gdx.gl.glClearColor(0f, 0f, 0f, 1);
        Gdx.gl20.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.getViewport().apply();
        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();

    }

    public void handleInput() {
        if (Gdx.input.isKeyPressed(Keys.BACK) && !game.isBackKeyPressed()) {
            game.setBackKeyPressed(true);
            processBackKeyPressed();
        } else {
            game.setBackKeyPressed(Gdx.input.isKeyPressed(Keys.BACK));
        }
    }

    void processBackKeyPressed() {
        if (this instanceof ScrollShopMenu) {
            gameStateManager.setUpgradeShopState(gameInfo);
        } else if (this instanceof Play) {
            gameStateManager.setUpgradeShopState(gameInfo);
        } else if (this instanceof UpgradeMenu) {
            gameStateManager.setMainMenuState();
        } else if (this instanceof AchievementsScreen) {
            gameStateManager.setUpgradeShopState(gameInfo);
        } else if (this instanceof TutorialScreen) {
            tutorialScreenBackBtnClick();
        } else if (this instanceof StageScreen) {
            stageScreenBackBtnClick();
        } else if (this instanceof GameFinishedScreen) {
            gameStateManager.setUpgradeShopState(gameInfo);
        } else if (this instanceof MainMenu) {
            if (((MainMenu) this).isDialogIsShown()) {
                ((MainMenu) this).closeShownDialog();
            } else {
                Gdx.app.exit();
            }
        }
        System.gc();
    }

    private void stageScreenBackBtnClick() {
        if (((StageScreen) this).getStageScreenType() == StageScreen.StageScreenType.START_GAME) {
            gameStateManager.setMainMenuState();
        } else if (((StageScreen) this).getStageScreenType() == StageScreen.StageScreenType.IN_GAME
                || ((StageScreen) this).getStageScreenType() == StageScreen.StageScreenType.FROM_LABEL_CLICK) {
            gameStateManager.setUpgradeShopState(gameInfo);
        }
    }

    private void tutorialScreenBackBtnClick() {
        if (((TutorialScreen) this).getTutorialScreenType() == TutorialScreen.TutorialScreenType.START_GAME) {
            gameStateManager.setMainMenuState();
        } else if (((TutorialScreen) this).getTutorialScreenType() == TutorialScreen.TutorialScreenType.ROCKET) {
            gameStateManager.setUpgradeShopState(gameInfo);
        }
    }

    public void resize(int width, int height) {
        stage.getViewport().update(width, height);
    }

    public void update(float dt) {
        handleInput();
    }

    public static String getLabel(String key) {
        return OverallState.getLabelsResource().get(key);
    }

    public static String getLabel(String key, Object... params) {
        for (int i = 0; i < params.length; i++) {
            if (params[i] instanceof Integer) {
                params[i] = String.valueOf(params[i]);
            }
        }
        return OverallState.getLabelsResource().format(key, params);
    }

}

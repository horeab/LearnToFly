package libgdx.game.lib.learntofly.states;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import libgdx.game.lib.learntofly.handlers.GameStateManager;
import libgdx.game.lib.learntofly.main.Game;
import libgdx.game.lib.learntofly.storemanagers.AchievementsManager;
import libgdx.game.lib.learntofly.storemanagers.GameInfoManager;
import libgdx.game.lib.learntofly.storemanagers.PreferencesManager;
import libgdx.game.lib.learntofly.to.GameInfo;
import libgdx.game.lib.learntofly.util.LearnToFlyGameLabel;
import libgdx.game.lib.learntofly.util.LibgdxControlCreator;
import libgdx.game.lib.learntofly.util.LibgdxControlUtils;
import libgdx.screen.AbstractScreen;

public abstract class GameState extends AbstractScreen {

    protected GameStateManager gameStateManager;
    protected Game game;
    protected LibgdxControlUtils libgdxControlUtils;
    protected LibgdxControlCreator c;
    protected GameInfoManager gameInfoManager;
    protected PreferencesManager preferencesManager;
    protected AchievementsManager achievementsManager;

    protected OrthographicCamera cam;
    protected OrthographicCamera hudCam;

    protected Skin skin;

    protected GameInfo gameInfo;

    TextButton backButton;

    float scale;

    protected GameState(GameStateManager gameStateManager, GameInfo gameInfo) {
        super();
        this.gameStateManager = gameStateManager;
        this.gameInfo = gameInfo;

        initFields();
        createBackButton();
    }

    private void createBackButton() {
        backButton = c.textButton(LearnToFlyGameLabel.l_back.getText(), "green");
        backButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                onBackKeyPress();
            }
        });
        backButton.getLabel().setFontScale(libgdxControlUtils.getFontScale());
    }

    protected void initFields() {
        super.initFields();
        OverallState overallState = gameStateManager.getOverallState();
        gameInfoManager = overallState.getGameInfoManager();
        libgdxControlUtils = overallState.getLibgdxControlUtils();
        scale = libgdxControlUtils.getHeightDisplayRatio();
        skin = overallState.getSkin();
        preferencesManager = overallState.getPreferencesManager();
        achievementsManager = overallState.getAchievementsManager();
        c = new LibgdxControlCreator(skin, libgdxControlUtils);
        game = gameStateManager.getGame();
        cam = game.getCam();
        hudCam = game.getHudCam();
    }

    public void update(float dt) {
    }

}

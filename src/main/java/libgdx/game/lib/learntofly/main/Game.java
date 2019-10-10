package libgdx.game.lib.learntofly.main;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;

import libgdx.constants.Language;
import libgdx.controls.labelimage.InventoryTableBuilderCreator;
import libgdx.controls.popup.RatingService;
import libgdx.game.GameId;
import libgdx.game.MainDependencyManager;
import libgdx.game.SubGameDependencyManager;
import libgdx.game.external.AppInfoService;
import libgdx.game.external.LoginService;
import libgdx.game.lib.learntofly.handlers.BBInput;
import libgdx.game.lib.learntofly.handlers.GameStateManager;
import libgdx.game.lib.learntofly.states.MainMenu;
import libgdx.game.lib.learntofly.states.Play;
import libgdx.game.lib.learntofly.to.GameInfo;
import libgdx.game.lib.learntofly.util.FacebookLinkHandler;
import libgdx.game.lib.learntofly.util.GameMode;
import libgdx.game.lib.learntofly.util.Utils;
import libgdx.login.GuestUserLoginService;
import libgdx.resources.FontManager;
import libgdx.resources.ResourceService;
import libgdx.screen.AbstractScreen;
import libgdx.screen.AbstractScreenManager;
import libgdx.transactions.TransactionsService;
import libgdx.utils.startgame.test.DefaultAppInfoService;
import libgdx.utils.startgame.test.DefaultBillingService;
import libgdx.utils.startgame.test.DefaultFacebookService;

public class Game extends libgdx.game.Game {

    public static final int STANDARD_WIDTH = 400;
    public static final int STANDARD_HEIGHT = 240;

    private static Game instance;

    private static Float displayHeight;
    private static Float displayWidth;

    private boolean backKeyPressed;

    private OrthographicCamera cam;
    private OrthographicCamera hudCam;

    private FontManager fontManager;
    private GameStateManager gameStateManager;

    private float accumulator;

    private Language language;
    private static GameMode gameMode;

    public Game(Language language, GameMode gameMode) {
        super(new DefaultFacebookService(), new DefaultBillingService(), new DefaultAppInfoService() {
            @Override
            public String getGameIdPrefix() {
                return GameIdEnum.learntofly.name();
            }
        }, new LearnToFlyMainDependencyManager());
        instance = this;
        displayHeight = null;
        displayWidth = null;
        this.language = language;
        Game.gameMode = gameMode;
        fontManager = new
                FontManager() {
                    @Override
                    protected String getGameAllFontChars() {
                        return "";
                    }
                };
    }

    @Override
    protected void displayScreenAfterAssetsLoad() {
        //		 gameStateManager.pushState(new AchievementsScreen(gameStateManager, new GameInfo(3, 1, 23, 12, 12, 6, 6, 6)));
//		 gameStateManager.pushState(new Play(gameStateManager, new GameInfo(3, 1, 23,   9, 9, 5, 5, 5)));
//		 gameStateManager.pushState(new Play(gameStateManager, new GameInfo(1, 1, 1, 1, 12, 6, 1, 1)));
//		 gameStateManager.pushState(new Play(gameStateManager, new GameInfo(1, 1, 1, 12, 0, 0, 0, 0)));
//		 gameStateManager.pushState(new UpgradeMenu(gameStateManager, new GameInfo(3, 4, 10000, 7, 2, 2, 6, 2)));
//		 gameStateManager.pushState(new ScrollShopMenu(gameStateManager, GliderLevel.SHOP_ID, new GameInfo(3, 1, 21113, 3, 2, 1, 0, 0)));
//		 gameStateManager.pushState(new TutorialScreen(gameStateManager, new GameInfo(3, 1, 23, 3, 2, 0, 5, 2), TutorialScreenType.ROCKET));
//		 gameStateManager.pushState(new StageScreen(gameStateManager, new GameInfo(0, 1, 0, 0, 0, 0, 0, 0), StageScreenType.START_GAME));
//		 gameStateManager.pushState(new GameFinishedScreen(gameStateManager, new GameInfo(3, 1, 23, 12,12, 6, 6, 6)));
		 gameStateManager.pushState(new MainMenu(gameStateManager, null));
    }

    public OrthographicCamera getCam() {
        return cam;
    }

    public static Game getInstance() {
        return instance;
    }

    public OrthographicCamera getHudCam() {
        return hudCam;
    }

    public FontManager getFontManager() {
        return fontManager;
    }

    @Override
    public void create() {
        super.create();
        updateWidthHeight();

        BBInput.gameHeight = (int) getHeight();

        cam = new OrthographicCamera();
        cam.setToOrtho(false, getWidth(), getHeight());
        hudCam = new OrthographicCamera();
        hudCam.setToOrtho(false, getWidth(), getHeight());
    }

    public void updateWidthHeight() {
        if (displayHeight == null || displayWidth == null) {
            updateDimensions();
        }
    }

    @Override
    public void executeAfterAssetsLoaded() {
        gameStateManager = new GameStateManager(this);
        loginService = new GuestUserLoginService();
        super.executeAfterAssetsLoaded();
    }

    private void updateDimensions() {
        int gdxHeight = getHeightForRatio();
        int gdxWidth = getWidthForRatio();
        boolean heightLarger = gdxHeight > gdxWidth;
        float ratio = heightLarger ? (float) gdxWidth / (float) gdxHeight : (float) gdxHeight / (float) gdxWidth;
        displayWidth = heightLarger ? ratio * gdxHeight : gdxWidth;
        displayHeight = heightLarger ? gdxHeight : ratio * gdxWidth;
    }

    private int getHeightForRatio() {
        float ratio = getGdxRatio();
        return ratio > Utils.STANDARD_RATIO ? (int) (ratio * STANDARD_WIDTH) : STANDARD_HEIGHT;
    }

    private int getWidthForRatio() {
        float ratio = getGdxRatio();
        return (int) (ratio > Utils.STANDARD_RATIO ? STANDARD_WIDTH : STANDARD_HEIGHT / ratio);
    }

    private float getGdxRatio() {
        return (float) getGdxHeight() / getGdxWidth();
    }

    private int getGdxHeight() {
        return Gdx.graphics.getHeight();
    }

    private int getGdxWidth() {
        return Gdx.graphics.getWidth();
    }

    public static float getHeight() {
        return displayHeight;
    }

    public static float getWidth() {
        return displayWidth;
    }

    @Override
    public void dispose() {
        super.dispose();
    }

    @Override
    public void render() {
        super.render();
        if (gameStateManager != null) {
            doPhysicsStep(Gdx.graphics.getDeltaTime());
        }
    }

    @Override
    public void pause() {
        super.pause();
    }

    @Override
    public void resize(int width, int height) {
        if (gameStateManager != null) {
            gameStateManager.resize(width, height);
        }
    }

    @Override
    public void resume() {
        super.resume();
    }

    public boolean isBackKeyPressed() {
        return backKeyPressed;
    }

    public void setBackKeyPressed(boolean backKeyPressed) {
        this.backKeyPressed = backKeyPressed;
    }

    private void doPhysicsStep(float deltaTime) {
        float dt = 1f / 68f;
        // fixed time step
        // max frame time to avoid spiral of death (on slow devices)
        float frameTime = deltaTime;
        accumulator += frameTime;
        while (accumulator >= dt) {
            accumulator -= dt;
            updating(dt);
        }
    }

    private void updating(float delta) {
        gameStateManager.update(delta);
        gameStateManager.render();
    }

    public Language getLanguage() {
        return language;
    }

    public static boolean isDesktopGameMode() {
        return gameMode == GameMode.DESKTOP;
    }

}

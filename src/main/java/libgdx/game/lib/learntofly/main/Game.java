package libgdx.game.lib.learntofly.main;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;

import libgdx.game.lib.learntofly.handlers.BBInput;
import libgdx.game.lib.learntofly.handlers.GameStateManager;
import libgdx.game.lib.learntofly.util.FacebookLinkHandler;
import libgdx.game.lib.learntofly.util.GameMode;
import libgdx.game.lib.learntofly.util.Language;
import libgdx.game.lib.learntofly.util.Utils;

public class Game extends com.badlogic.gdx.Game {

	public static final int STANDARD_WIDTH = 400;
	public static final int STANDARD_HEIGHT = 240;

	private static Float displayHeight;
	private static Float displayWidth;

	private boolean backKeyPressed;

	private OrthographicCamera cam;
	private OrthographicCamera hudCam;

	private GameStateManager gameStateManager;

	private float accumulator;

	private FacebookLinkHandler facebookLinkHandler;
	private Language language;
	private static GameMode gameMode;

	public Game(FacebookLinkHandler facebookLinkHandler, Language language, GameMode gameMode) {
		super();
		displayHeight = null;
		displayWidth = null;
		this.facebookLinkHandler = facebookLinkHandler;
		this.language = language;
		Game.gameMode = gameMode;
	}

	public OrthographicCamera getCam() {
		return cam;
	}

	public OrthographicCamera getHudCam() {
		return hudCam;
	}

	@Override
	public void create() {
		updateWidthHeight();

		BBInput.gameHeight = (int) getHeight();

		cam = new OrthographicCamera();
		cam.setToOrtho(false, getWidth(), getHeight());
		hudCam = new OrthographicCamera();
		hudCam.setToOrtho(false, getWidth(), getHeight());
		gameStateManager = new GameStateManager(this);
	}

	public void updateWidthHeight() {
		if (displayHeight == null || displayWidth == null) {
			updateDimensions();
		}
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
		doPhysicsStep(Gdx.graphics.getDeltaTime());
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

	public FacebookLinkHandler getFacebookLinkHandler() {
		return facebookLinkHandler;
	}

	public Language getLanguage() {
		return language;
	}

	public static boolean isDesktopGameMode() {
		return gameMode == GameMode.DESKTOP;
	}

}

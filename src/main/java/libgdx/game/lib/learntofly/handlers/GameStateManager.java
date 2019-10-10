package libgdx.game.lib.learntofly.handlers;

import java.util.Stack;

import libgdx.game.lib.learntofly.main.Game;
import libgdx.game.lib.learntofly.states.AchievementsScreen;
import libgdx.game.lib.learntofly.states.GameFinishedScreen;
import libgdx.game.lib.learntofly.states.GameState;
import libgdx.game.lib.learntofly.states.MainMenu;
import libgdx.game.lib.learntofly.states.OverallState;
import libgdx.game.lib.learntofly.states.Play;
import libgdx.game.lib.learntofly.states.ScrollShopMenu;
import libgdx.game.lib.learntofly.states.StageScreen;
import libgdx.game.lib.learntofly.states.TutorialScreen;
import libgdx.game.lib.learntofly.states.UpgradeMenu;
import libgdx.game.lib.learntofly.to.GameInfo;

public class GameStateManager {

	private Game game;

	private Stack<GameState> gameStates;
	private OverallState overallState;

	public static final int PLAY = 23231;
	public static final int MAIN_MENU = 23232;
	public static final int UPGRADE_MENU = 23233;
	public static final int SCROLLSHOP_MENU = 23234;

	public GameStateManager(Game game) {
		this.game = game;
		gameStates = new Stack<GameState>();
		overallState = new OverallState(game.getLanguage());
	}

	public Game getGame() {
		return game;
	}

	public void update(float dt) {
		gameStates.peek().update(dt);
	}

	public void render() {
		gameStates.peek().render();
	}

	public void resize(int width, int height) {
		gameStates.peek().resize(width, height);
	}

	public void setScrollShopState(int shopId, GameInfo gameInfo) {
		setState(new ScrollShopMenu(this, shopId, gameInfo));
	}

	public void setUpgradeShopState(GameInfo gameInfo) {
		setState(new UpgradeMenu(this, gameInfo));
	}

	public void setPlayState(GameInfo gameInfo) {
		setState(new Play(this, gameInfo));
	}

	public void setAchievementsState(GameInfo gameInfo) {
		setState(new AchievementsScreen(this, gameInfo));
	}

	public void setTutorialState(GameInfo gameInfo, TutorialScreen.TutorialScreenType tutorialScreenType) {
		setState(new TutorialScreen(this, gameInfo, tutorialScreenType));
	}

	public void setStageState(GameInfo gameInfo, StageScreen.StageScreenType stageScreenType) {
		setState(new StageScreen(this, gameInfo, stageScreenType));
	}

	public void setGameFinishedState(GameInfo gameInfo) {
		setState(new GameFinishedScreen(this, gameInfo));
	}

	public void setMainMenuState() {
		setState(new MainMenu(this, null));
	}

	private void setState(GameState gameState) {
		popState();
		System.gc();
		pushState(gameState);
	}

	public void pushState(GameState gameState) {
		gameStates.push(gameState);
	}

	private void popState() {
		GameState gameState = gameStates.pop();
		gameState.dispose();
	}

	public OverallState getOverallState() {
		return overallState;
	}
}

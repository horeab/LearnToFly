package libgdx.game.lib.learntofly.handlers;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Input.Keys;

import libgdx.game.lib.learntofly.main.Game;

public class BBInput {

	public static int gameHeight;

	public static boolean topRightScreen;
	public static boolean bottomRightScreen;
	public static boolean leftScreen;
	public static boolean wholeScreen;

	private static boolean isTopRightScreenPressed() {
		return topRightScreen;
	}

	private static boolean isBottomRightScreenPressed() {
		return bottomRightScreen;
	}

	private static boolean isLeftScreenPressed() {
		return leftScreen;
	}

	public static boolean isRocketPressed() {
		return Game.isDesktopGameMode() ? Gdx.input.isKeyPressed(Keys.CONTROL_LEFT) || Gdx.input.isKeyPressed(Keys.CONTROL_RIGHT) : isLeftScreenPressed();
	}

	public static boolean isGoDownPressed() {
		return Game.isDesktopGameMode() ? Gdx.input.isKeyPressed(Keys.RIGHT) : isBottomRightScreenPressed();
	}

	public static boolean isGoUpPressed() {
		return Game.isDesktopGameMode() ? Gdx.input.isKeyPressed(Keys.LEFT) : isTopRightScreenPressed();
	}

	public static boolean isWholeScreenPressed() {
		return wholeScreen || Gdx.input.isKeyPressed(Keys.ESCAPE);
	}

}

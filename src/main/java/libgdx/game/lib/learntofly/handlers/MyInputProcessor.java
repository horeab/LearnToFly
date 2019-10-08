package libgdx.game.lib.learntofly.handlers;

import java.util.Stack;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;

import libgdx.game.lib.learntofly.main.Game;
import libgdx.game.lib.learntofly.util.Point;

public class MyInputProcessor extends InputAdapter {

	private float displayHeight;
	private float displayWidth;
	private Stack<Point> lastTwoDraggedTouchedPoints = new Stack<Point>();

	public MyInputProcessor() {
		super();
		this.displayHeight = Game.getHeight();
		this.displayWidth = Game.getWidth();
	}

	@Override
	public boolean touchDown(int x, int y, int pointer, int button) {
		setKeyPressed(getRealXDependantOnRatio(x), getRealYDependantOnRatio(y), true);
		return true;
	}

	@Override
	public boolean touchUp(int x, int y, int pointer, int button) {
		setKeyPressed(getRealXDependantOnRatio(x), getRealYDependantOnRatio(y), false);
		return true;
	}

	@Override
	public boolean touchDragged(int x, int y, int pointer) {
		Point currentPoint = new Point(getRealXDependantOnRatio(x), getRealYDependantOnRatio(y));
		lastTwoDraggedTouchedPoints.add(currentPoint);
		if (lastTwoDraggedTouchedPoints.size() > 2) {
			lastTwoDraggedTouchedPoints.remove(lastTwoDraggedTouchedPoints.firstElement());
		}
		if (lastTwoDraggedTouchedPoints.size() == 2) {
			if (lastTwoDraggedTouchedPoints.firstElement().x <
					lastTwoDraggedTouchedPoints.lastElement().x &&
					pointIsOnNeutralArea(lastTwoDraggedTouchedPoints.lastElement())) {
				BBInput.leftScreen = false;
			}
			if (lastTwoDraggedTouchedPoints.firstElement().x >
					lastTwoDraggedTouchedPoints.lastElement().x &&
					pointIsOnNeutralArea(lastTwoDraggedTouchedPoints.lastElement())) {
				BBInput.topRightScreen = false;
				BBInput.bottomRightScreen = false;
			}
		}
		return true;
	};

	public void dispose() {
		resetControls();
	}

	private void setKeyPressed(float x, float y, boolean value) {
		Point point = new Point(x, y);
		if (!pointIsOnLeftScreen(point)) {
			if (pointIsOnTopRightScreen(point)) {
				BBInput.topRightScreen = value;
			} else {
				BBInput.bottomRightScreen = value;
			}
		} else {
			BBInput.leftScreen = value;
		}
		BBInput.wholeScreen = value;
	}

	private boolean pointIsOnNeutralArea(Point point) {
		return point.getX() > (int) displayWidth / 3 && point.getX() < displayWidth / 2;
	}

	private boolean pointIsOnLeftScreen(Point point) {
		return point.getX() < (int) displayWidth / 3;
	}

	private boolean pointIsOnTopRightScreen(Point point) {
		return point.getX() > displayWidth / 2 && point.getY() < displayHeight / 1.2f;
	}

	private void resetControls() {
		BBInput.topRightScreen = false;
		BBInput.bottomRightScreen = false;
		BBInput.leftScreen = false;
		BBInput.wholeScreen = false;
	}

	private float getRealYDependantOnRatio(int value) {
		return getRealValueDependantOnRatio(value, Game.getHeight(), Gdx.graphics.getHeight());
	}

	private float getRealXDependantOnRatio(int value) {
		return getRealValueDependantOnRatio(value, Game.getWidth(), Gdx.graphics.getWidth());
	}

	private float getRealValueDependantOnRatio(int value, float virtualDim, int realDim) {
		float ratio = virtualDim / realDim;
		return value * ratio;
	}
}

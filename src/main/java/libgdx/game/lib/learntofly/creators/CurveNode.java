package libgdx.game.lib.learntofly.creators;

import com.badlogic.gdx.math.Vector2;

public class CurveNode
{
	private Vector2 position;
	private Vector2 control;

	public CurveNode(Vector2 position, Vector2 control) {
		this.position = position;
		this.control = control;
	}

	public Vector2 getPosition() {
		return position;
	}

	public void setPosition(Vector2 position) {
		this.position = position;
	}

	public Vector2 getControl() {
		return control;
	}

	public void setControl(Vector2 control) {
		this.control = control;
	}

}
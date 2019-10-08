package libgdx.game.lib.learntofly.creators;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

public class BezierCurveCreator {

	private List<CurveNode> nodes = new ArrayList<CurveNode>();

	public void addNode(int positionX, int positionY, int controlX, int controlY)
	{
		Vector2 position = new Vector2(positionX, positionY);
		Vector2 control = new Vector2(controlX, controlY);

		CurveNode newNode = new CurveNode(position, control);

		nodes.add(newNode);
	}

	private Vector2 quadraticBezier(Vector2 x1, Vector2 x2, Vector2 controlPoint, float t)
	{
		float x = (1 - t) * (1 - t) * x1.x + (2 - 2 * t) * t * controlPoint.x + t * t * x2.x;
		float y = (1 - t) * (1 - t) * x1.y + (2 - 2 * t) * t * controlPoint.y + t * t * x2.y;

		return new Vector2(x, y);
	}

	public Array<Vector2> convertToPoints(int quality)
	{
		Array<Vector2> points = new Array<Vector2>();

		float precision = 1 / (float) quality;

		// Pass through all nodes to generate line segments
		for (int i = 0; i < nodes.size() - 1; i++)
		{
			CurveNode current = nodes.get(i);
			CurveNode next = nodes.get(i + 1);

			// Sample Bezier curve between two nodes
			// Number of steps is determined by quality parameter
			for (float step = 0; step < 1; step += precision)
			{
				Vector2 newPoint = quadraticBezier(current.getPosition(), next.getPosition(), current.getControl(), step);
				points.add(newPoint);
			}
		}
		return points;
	}
}

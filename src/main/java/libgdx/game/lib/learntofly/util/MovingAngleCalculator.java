package libgdx.game.lib.learntofly.util;

import com.badlogic.gdx.math.Vector2;

import libgdx.game.lib.learntofly.creators.RampCreator;

public class MovingAngleCalculator {

	public static final int RAMP_PLAYER_ANGLE_STARTS_CHANGING_X = RampCreator.START_RAMP_X + RampCreator.START_RAMP_X / 7;
	private Vector2 previousPlayerPoint;
	private Float previousAngle = null;

	public void setPreviousPlayerPoint(Vector2 previousPlayerPoint) {
		this.previousPlayerPoint = new Vector2(previousPlayerPoint);
	}

	public void setPreviousAngle(float previousAngle) {
		this.previousAngle = previousAngle;
	}

	public float getAngle(Vector2 currentPoint, float playerAcceleration) {
		if (playerHasReachedRampPointAngleChanges(currentPoint.x)) {
			float angleDiff = getAngleDiff(playerAcceleration);
			float currentAngle = (float) Math.toDegrees(Math.atan2(currentPoint.y - previousPlayerPoint.y, currentPoint.x - previousPlayerPoint.x));
			if (previousAngle == null) {
				return currentAngle;
			}

			Float angle = null;
			if (currentAngle - previousAngle > angleDiff || currentAngle - previousAngle < -angleDiff) {
				angle = currentAngle > previousAngle ? previousAngle + angleDiff : previousAngle - angleDiff;
			}

			return angle != null ? angle : currentAngle;
		}
		return 0;
	}

	private float getAngleDiff(float playerAcceleration) {
		// the smaller the factor the smaller the angle change impact
		float factor = 0.005f;
		return Math.abs(playerAcceleration * 10) * factor;
	}

	public static boolean playerHasReachedRampPointAngleChanges(float currentPointX) {
		return currentPointX > Utils.scaleDownPPM(RAMP_PLAYER_ANGLE_STARTS_CHANGING_X);
	}

	public static boolean playerIsOnDownwardsRamp(float currentPointX) {
		return currentPointX > Utils.scaleDownPPM(RampCreator.START_RAMP_X + 10);
	}

	public static boolean playerLeftRamp(float currentPointX) {
		return Utils.scaleDownPPM(RampCreator.END_RAMP_X) < currentPointX;
	}

	public static boolean playerJustBeforeLeavingRamp(float currentPointX) {
		return Utils.scaleDownPPM(RampCreator.END_RAMP_X) < currentPointX + Utils.scaleDownPPM(RampCreator.END_RAMP_X / 3);
	}

}

package libgdx.game.lib.learntofly.entities.player.properties;

import libgdx.game.lib.learntofly.entities.player.Player;
import libgdx.game.lib.learntofly.handlers.MyContactListener;

public class PlayerProperties {

	private PlayerAttributesProcessor playerAttributesProcessor;
	private PlayerOnRampProcessor playerOnRampProcessor;

	public PlayerProperties(Player player, MyContactListener contactListener) {
		playerOnRampProcessor = new PlayerOnRampProcessor(player.getBody());
		playerAttributesProcessor = new PlayerAttributesProcessor(player.getBody(), contactListener);
	}

	public float getMovingAngle() {
		return playerAttributesProcessor.getMovingAngle();
	}

	public float getXDisplaySpeed() {
		return playerAttributesProcessor.getXDisplaySpeed();
	}

	public float getYDisplaySpeed() {
		return playerAttributesProcessor.getYDisplaySpeed();
	}

	public float getDisplayAcceleration() {
		return playerAttributesProcessor.getDisplayAcceleration();
	}

	public int getDisplayAltitude() {
		return playerAttributesProcessor.getDisplayAltitude();
	}

	public int getDisplayDistance() {
		return playerAttributesProcessor.getDisplayDistance();
	}

	public void setPreviousPlayerPoint() {
		playerAttributesProcessor.setPreviousPlayerPoint();
	}

	public void setPreviousMovingAngle(float previousAngle) {
		playerAttributesProcessor.setPreviousMovingAngle(previousAngle);
	}

	public void setGravity(float val) {
		if (hasLeftRamp()) {
			playerAttributesProcessor.setGravity(val);
		}
	}

	public boolean isPlayerOnGround() {
		return playerAttributesProcessor.isPlayerOnGround();
	}

	public void applyAirResistance(float airResistanceFactor, float floatingLevel) {
		if (hasLeftRamp()) {
			playerAttributesProcessor.applyAirResistance(playerAttributesProcessor.getMovingAngle(), airResistanceFactor, floatingLevel);
		}
	}

	public boolean isPlayerGoingBackwards() {
		return playerAttributesProcessor.isPlayerGoingBackwards() && hasReachedRampPointAngleChanges();
	}

	public boolean hasLeftRamp() {
		return playerOnRampProcessor.hasLeftRamp();
	}

	public int getAltitudeLevelForBackgroundTexture(int levelLimit, float camY) {
		return (int) (camY / levelLimit);
	}

	public boolean hasReachedRampPointAngleChanges() {
		return playerOnRampProcessor.hasReachedRampPointAngleChanges();
	}

	public boolean isOnDownwardsRamp() {
		return playerOnRampProcessor.hasReachedDownwardsRamp();
	}
}

package libgdx.game.lib.learntofly.entities.player.properties;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;

import libgdx.game.lib.learntofly.util.MovingAngleCalculator;

class PlayerOnRampProcessor {

	private Body playerBody;

	public PlayerOnRampProcessor(Body playerBody) {
		this.playerBody = playerBody;
	}

	public boolean hasLeftRamp() {
		return MovingAngleCalculator.playerLeftRamp(getPosition().x);
	}

	private Vector2 getPosition() {
		return playerBody.getPosition();
	}

	public boolean hasReachedRampPointAngleChanges() {
		return MovingAngleCalculator.playerHasReachedRampPointAngleChanges(getPosition().x);
	}

	public boolean hasReachedDownwardsRamp() {
		return MovingAngleCalculator.playerIsOnDownwardsRamp(getPosition().x);
	}

}

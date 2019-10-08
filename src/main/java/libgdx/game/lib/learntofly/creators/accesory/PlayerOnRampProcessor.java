package libgdx.game.lib.learntofly.creators.accesory;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;

import libgdx.game.lib.learntofly.entities.player.Player;
import libgdx.game.lib.learntofly.util.MovingAngleCalculator;

public class PlayerOnRampProcessor {

	private static final float WALKING_SPEED = 0.6f;

	private boolean alreadyAcceleratedApplyStartingAccelerationOnRamp;
	private boolean alreadyAcceleratedapplyStartingAccelerationAfterLeftRamp;

	private Body sledBody;
	private float playerSpeedOnRamp;
	private float playerSpeedAfterLeftRamp;
	private PlayerTextureProcessor playerTextureProcessor;
	private Player player;

	public PlayerOnRampProcessor(Body sledBody, float playerSpeedOnRamp, float playerSpeedAfterLeftRamp, Player player, AssetManager assetManager) {
		super();
		this.sledBody = sledBody;
		this.playerSpeedOnRamp = playerSpeedOnRamp;
		this.playerSpeedAfterLeftRamp = playerSpeedAfterLeftRamp;
		this.player = player;
		playerTextureProcessor = new PlayerTextureProcessor(player, assetManager);
	}

	public void processPlayerOnRamp() {
		applyStartingAccelerationOnRamp(player.isPlayerOnGround(), playerSpeedOnRamp);
		applyStartingAccelerationAfterLeftRamp(playerSpeedAfterLeftRamp);
		if (hasReachedRampPointAngleChanges()) {
			playerTextureProcessor.processPlayerGoesDownRamp();
		}
	}

	private void applyStartingAccelerationOnRamp(boolean isPlayerIsOnGround, float startingPlayerAcceleration) {
		if (hasReachedRampPointAngleChanges()) {
			if (hasReachedDownwardsRamp()
					&&
					!alreadyAcceleratedApplyStartingAccelerationOnRamp
					&&
					isPlayerIsOnGround) {
				alreadyAcceleratedApplyStartingAccelerationOnRamp = true;
				getBody().applyForceToCenter(startingPlayerAcceleration, -startingPlayerAcceleration, true);
			}
		} else {
			getBody().applyForceToCenter(WALKING_SPEED, -WALKING_SPEED, true);
		}
	}

	private void applyStartingAccelerationAfterLeftRamp(float playerSpeedAfterLeftRamp) {
		if (MovingAngleCalculator.playerJustBeforeLeavingRamp(getPosition().x)
				&&
				!alreadyAcceleratedapplyStartingAccelerationAfterLeftRamp) {
			alreadyAcceleratedapplyStartingAccelerationAfterLeftRamp = true;
			getBody().applyForceToCenter(playerSpeedAfterLeftRamp, 0, true);
		}
	}

	private Vector2 getPosition() {
		return getBody().getPosition();
	}

	private boolean hasReachedRampPointAngleChanges() {
		return MovingAngleCalculator.playerHasReachedRampPointAngleChanges(getPosition().x);
	}

	private boolean hasReachedDownwardsRamp() {
		return MovingAngleCalculator.playerIsOnDownwardsRamp(getPosition().x);
	}

	private Body getBody() {
		return sledBody != null ? sledBody : player.getBody();
	}

}

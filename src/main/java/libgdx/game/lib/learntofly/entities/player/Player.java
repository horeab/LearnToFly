package libgdx.game.lib.learntofly.entities.player;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;

import libgdx.game.lib.learntofly.entities.player.properties.PlayerProperties;
import libgdx.game.lib.learntofly.handlers.MyContactListener;
import libgdx.game.lib.learntofly.util.B2DSprites;
import libgdx.game.lib.learntofly.util.Resource;
import libgdx.game.lib.learntofly.util.Utils;

public class Player extends B2DSprites {

	public static final float CRITICAL_UP_ANGLE = 30f;
	public static final float PUSH_UP_CRITICAL_UP_ANGLE = 50f;
	public static final int PLAYER_WALKING_TILE_WIDTH = 103;
	public static final int PLAYER_SLIDING_TILE_WIDTH = 126;
	private static final int PLAYER_TILE_HEIGHT = 118;

	private PlayerProperties playerProperties;

	public Player(Body body, MyContactListener contactListener, AssetManager manager) {
		super(body);
		playerProperties = new PlayerProperties(this, contactListener);
		setAnimation(Utils.getTexture(manager, Resource.peng_walking), PLAYER_WALKING_TILE_WIDTH);
	}

	public void setAnimation(Texture texture, int tileWitdth) {
		texture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		TextureRegion[] sprites = TextureRegion.split(texture, tileWitdth, PLAYER_TILE_HEIGHT)[0];
		setAnimation(sprites, 1 / 12f);
	}

	public void pushUp() {
		// if (playerLeftRamp) {
		float movingAngle = getMovingAngle();
		boolean thereIsAccelerationOrAngleIsSmall = getDisplayAcceleration() > 10 || movingAngle < CRITICAL_UP_ANGLE - CRITICAL_UP_ANGLE / 2;
		if (movingAngle < PUSH_UP_CRITICAL_UP_ANGLE && thereIsAccelerationOrAngleIsSmall) {
			float baseYValue = 1.5f;
			float xSpeed = getXDisplaySpeed() / 15f;

			float coefBasedOnAngle = 40f * (movingAngle / (CRITICAL_UP_ANGLE - CRITICAL_UP_ANGLE / 1.5f));
			float baseCoef = getBaseCoef(getXDisplaySpeed());
			coefBasedOnAngle = coefBasedOnAngle > baseCoef ? baseCoef : coefBasedOnAngle;
			float xValue = xSpeed * coefBasedOnAngle;
			// Y Value is calculated based on the X speed
			float yValue = xSpeed * baseYValue;

			// if player is below the critical angle the X resistance is smaller
			if (movingAngle < CRITICAL_UP_ANGLE) {
				xValue = xValue / 2f;
			}
			// if player is going downwards there is now X resistance
			if (movingAngle <= 0 || xValue < 0) {
				xValue = 0;
			}
			getBody().applyForceToCenter(-Math.abs(xValue), yValue, true);
		}
		// }
	}

	private float getBaseCoef(float xSpeed) {
		float coef = 1f;
		if (xSpeed < 50) {
			coef = 3f;
		} else if (xSpeed < 80) {
			coef = 2.5f;
		} else if (xSpeed < 130) {
			coef = 2f;
		} else if (xSpeed < 180) {
			coef = 1.5f;
		}
		return coef;
	}

	public void pushDown() {
		float baseYValue = 2.5f;
		getBody().applyForceToCenter(0, -baseYValue, true);
	}

	public void thrust(float thrustPower, float topSpeedAllowed) {
		if (getDisplayAcceleration() < topSpeedAllowed) {
			getBody().applyForceToCenter(thrustPower, 0, true);
		}
	}

	public float getMovingAngle() {
		return playerProperties.getMovingAngle();
	}

	public float getDisplayAcceleration() {
		return playerProperties.getDisplayAcceleration();
	}

	public float getXDisplaySpeed() {
		return playerProperties.getXDisplaySpeed();
	}

	public int getDisplayAltitude() {
		return playerProperties.getDisplayAltitude();
	}

	public int getDisplayDistance() {
		return playerProperties.getDisplayDistance();
	}

	public void setPreviousPlayerPoint() {
		playerProperties.setPreviousPlayerPoint();
	}

	public void setPreviousMovingAngle(float previousAngle) {
		playerProperties.setPreviousMovingAngle(previousAngle);
	}

	public void setGravity(float val) {
		playerProperties.setGravity(val);
	}

	public void applyAirResistance(float airResistanceFactor, float floatingLevel) {
		playerProperties.applyAirResistance(airResistanceFactor, floatingLevel);
	}

	public boolean isPlayerGoingBackwards() {
		return playerProperties.isPlayerGoingBackwards();
	}

	public boolean hasLeftRamp() {
		return playerProperties.hasLeftRamp();
	}

	public int getAltitudeLevelForBackgroundTexture(int levelLimit, float camY) {
		return playerProperties.getAltitudeLevelForBackgroundTexture(levelLimit, camY);
	}

	public boolean hasReachedRampPointAngleChanges() {
		return playerProperties.hasReachedRampPointAngleChanges();
	}

	public boolean isOnDownwardsRamp() {
		return playerProperties.isOnDownwardsRamp();
	}

	public boolean isPlayerOnGround() {
		return playerProperties.isPlayerOnGround();
	}
}

package libgdx.game.lib.learntofly.creators.accesory;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.joints.DistanceJointDef;

import libgdx.game.lib.learntofly.util.MovingAngleCalculator;

import static libgdx.game.lib.learntofly.handlers.B2DVars.*;
import static libgdx.game.lib.learntofly.util.Utils.*;

public abstract class BodyAccesoryCreator extends AccesoryCreator {

	protected Body accesoryBody;
	protected MovingAngleCalculator movinAngleCalculator;

	public BodyAccesoryCreator(World world, Body playerBody, Integer accesoryLevel, String userData) {
		super(world, playerBody, accesoryLevel, userData);
		movinAngleCalculator = new MovingAngleCalculator();
	}

	@Override
	public void createAccesory() {
		accesoryBody = createAccesory(playerBody);

		DistanceJointDef playerAccesoryJoint = new DistanceJointDef();
		playerAccesoryJoint.length = 0;
		playerAccesoryJoint.bodyA = playerBody;
		playerAccesoryJoint.bodyB = accesoryBody;
		playerAccesoryJoint.localAnchorA.set(0, 0);
		playerAccesoryJoint.localAnchorB.set(0, 0);

		this.playerAccesoryJoint = world.createJoint(playerAccesoryJoint);
	}

	@Override
	public Vector2 getAccesoryPosition() {
		return accesoryBody.getPosition();
	}

	public void setPreviousPlayerPoint() {
		movinAngleCalculator.setPreviousPlayerPoint(accesoryBody.getPosition());
	}

	public float getMovingAngle(int startingPlayerAcceleration) {
		return movinAngleCalculator.getAngle(accesoryBody.getPosition(), startingPlayerAcceleration);
	}

	private Body createAccesory(Body playerBody) {
		CircleShape accesoryShape = new CircleShape();
		accesoryShape.setRadius(scaleDownPPM(1));
		FixtureDef fixtureDef = worldBodyCreator.createFixtureDefAsSensor(accesoryShape, BIT_BUTTON, BIT_BUTTON);

		Body body = worldBodyCreator.createBody(BodyType.DynamicBody, playerBody.getPosition().x, playerBody.getPosition().y);
		body.createFixture(fixtureDef).setUserData(userData);
		body.setGravityScale(0.5f);
		return body;
	}

	@Override
	public void removeAccesory() {
		if (playerAccesoryJoint != null) {
			accesoryBody.setGravityScale(3f);
			world.destroyJoint(playerAccesoryJoint);
			playerAccesoryJoint = null;
		}
	}

	public Body getAccesoryBody() {
		return accesoryBody;
	}

}

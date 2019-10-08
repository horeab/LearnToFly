package libgdx.game.lib.learntofly.creators.accesory;

import static libgdx.game.lib.learntofly.handlers.B2DVars.*;
import static libgdx.game.lib.learntofly.util.Utils.*;


import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

public abstract class SensorAccesoryCreator extends AccesoryCreator {

	private Fixture fixture;

	public SensorAccesoryCreator(World world, Body playerBody, Integer accesoryLevel, String userData) {
		super(world, playerBody, accesoryLevel, userData);
	}

	@Override
	public void createAccesory() {
		CircleShape accesoryShape = new CircleShape();
		accesoryShape.setRadius(scaleDownPPM(0));
		FixtureDef fixtureDef = worldBodyCreator.createFixtureDefAsSensor(accesoryShape, BIT_BUTTON, BIT_BUTTON);
		fixture = playerBody.createFixture(fixtureDef);
		fixture.setUserData(userData);
	}

	@Override
	public Vector2 getAccesoryPosition() {
		return playerBody.getPosition();
	}

	@Override
	public void removeAccesory() {
		if (fixture != null) {
			playerBody.destroyFixture(fixture);
			fixture = null;
		}
	}

	public Fixture getAccesoryFixture() {
		return fixture;
	}

}

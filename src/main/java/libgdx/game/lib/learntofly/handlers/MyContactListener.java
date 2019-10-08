package libgdx.game.lib.learntofly.handlers;

import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;

public class MyContactListener implements ContactListener {

	private int numFootContacts;
	private Array<Body> bodiesToRemove = new Array<Body>();

	@Override
	public void beginContact(Contact c) {
		Fixture fa = c.getFixtureA();
		Fixture fb = c.getFixtureB();
		if (isPlayer(fa, fb)) {
			numFootContacts++;
		}
	}

	private boolean isPlayer(Fixture fa, Fixture fb) {
		return (fa.getUserData() != null && fa.getUserData().equals("player"))
				|| (fb.getUserData() != null && fb.getUserData().equals("player"));
	}

	@Override
	public void endContact(Contact c) {
		Fixture fa = c.getFixtureA();
		Fixture fb = c.getFixtureB();
		if (isPlayer(fa, fb)) {
			numFootContacts--;
		}
	}

	@Override
	public void postSolve(Contact c, ContactImpulse ci) {
	}

	@Override
	public void preSolve(Contact c, Manifold m) {
	}

	public boolean isPlayerIsOnGround() {
		return numFootContacts > 0;
	}

	public Array<Body> getBodiesToRemove() {
		return bodiesToRemove;
	}

}

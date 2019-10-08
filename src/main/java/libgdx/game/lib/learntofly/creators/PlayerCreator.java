package libgdx.game.lib.learntofly.creators;

import static libgdx.game.lib.learntofly.handlers.B2DVars.*;
import static libgdx.game.lib.learntofly.util.Utils.*;

import libgdx.game.lib.learntofly.entities.player.Player;
import libgdx.game.lib.learntofly.handlers.B2DVars;
import libgdx.game.lib.learntofly.handlers.MyContactListener;
import libgdx.game.lib.learntofly.util.Utils.*;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;

public class PlayerCreator {

	private static final int MASS_KG = 2;

	private WorldBodyCreator worldBodyCreator;

	public PlayerCreator(World world) {
		worldBodyCreator = new WorldBodyCreator(world);
	}

	public Player createPlayer(int rampHeight, MyContactListener contactListener, AssetManager manager) {
		CircleShape playerShape = new CircleShape();
		playerShape.setRadius(scaleDownPPM(PLAYER_RADIUS));
		Body playerBody = worldBodyCreator.createBody(BodyType.DynamicBody, B2DVars.PLAYER_RADIUS, rampHeight);
		FixtureDef playerFixtureDef = worldBodyCreator.createFixtureDef(playerShape, BIT_BUTTON, BIT_WALL);
		playerFixtureDef.friction = 0;
		playerBody.createFixture(playerFixtureDef).setUserData("player");
		playerBody.setFixedRotation(false);
		playerShape.dispose();

		MassData md = playerBody.getMassData();
		md.mass = MASS_KG;
		md.center.set(new Vector2(0, 0));
		playerBody.setMassData(md);
		Player player = new Player(playerBody, contactListener, manager);
		player.setPreviousPlayerPoint();
		return player;
	}

}

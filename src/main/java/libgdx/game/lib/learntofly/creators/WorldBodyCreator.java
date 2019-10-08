package libgdx.game.lib.learntofly.creators;


import static libgdx.game.lib.learntofly.util.Utils.*;

import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;

public class WorldBodyCreator {

    private World world;

    public WorldBodyCreator(World world) {
        this.world = world;
    }

    public Body createBody(BodyType bodyType, float horizPos, float vertPos) {
        BodyDef bodyDef = createBodyDef(bodyType, horizPos, vertPos);
        Body body = world.createBody(bodyDef);
        return body;
    }

    public FixtureDef createFixtureDef(Shape shape, short categBit, short allowedToColliedWith) {
        return createFixtureDef(shape, categBit, allowedToColliedWith, false);
    }

    public FixtureDef createFixtureDefAsSensor(Shape shape, short categBit, short allowedToColliedWith) {
        return createFixtureDef(shape, categBit, allowedToColliedWith, true);
    }

    private FixtureDef createFixtureDef(Shape shape, short categBit, short allowedToColliedWith, boolean isSensor) {
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.filter.categoryBits = categBit;
        fixtureDef.filter.maskBits = allowedToColliedWith;
        fixtureDef.isSensor = isSensor;
        return fixtureDef;
    }

    private BodyDef createBodyDef(BodyType bodyType, float horizPos, float vertPos) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(scaleDownPPM(horizPos), scaleDownPPM(vertPos));
        bodyDef.type = bodyType;
        bodyDef.angle = 0f * 3.14159f / 180f;
        return bodyDef;
    }

}

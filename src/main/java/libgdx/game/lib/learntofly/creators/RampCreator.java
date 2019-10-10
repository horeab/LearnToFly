package libgdx.game.lib.learntofly.creators;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.PolygonRegion;
import com.badlogic.gdx.graphics.g2d.PolygonSprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.EarClippingTriangulator;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ShortArray;

import java.util.ArrayList;
import java.util.List;

import static libgdx.game.lib.learntofly.handlers.B2DVars.*;
import static libgdx.game.lib.learntofly.util.Utils.*;

import libgdx.game.lib.learntofly.handlers.B2DVars;
import libgdx.game.lib.learntofly.util.Resource;
import libgdx.game.lib.learntofly.util.Utils;

public class RampCreator {

    private static final float RAMP_SURFACE_OFFSET = 7.00001f;
    public static final int GROUND_HEIGHT = (int) Utils.getValueForDisplayHeightPercent(5f);
    private static final int DEFAULT_RAMP_HEIGHT = (int) Utils.getValueForDisplayHeightPercent(5f * 2);

    public static final int START_RAMP_X = (int) (B2DVars.PLAYER_RADIUS * 1.5f);// 1.5
    public static final int END_RAMP_X = (int) (B2DVars.PLAYER_RADIUS * 19f);// 19

    public static final float RAMP_TEXTURE_SCALE = 0.5f;

    private WorldBodyCreator worldBodyCreator;

    private float upwardsRampLevel;
    private int rampBaseHeightLevel;
    private float downwardsRampLevel;

    private List<PolygonSprite> polygonSprites = new ArrayList<PolygonSprite>();

    private TextureRegion backgrL1;
    private TextureRegion backgrL3;
    private TextureRegion backgrL2;

    public RampCreator(World world, int currentStage, int rampBaseHeightLevel, float downwardsRampLevel, float upwardsRampLevel) {
        worldBodyCreator = new WorldBodyCreator(world);
        backgrL1 = new TextureRegion(Utils.getTextureWithFilter(Resource.getGameStageRampBackgroundResource(currentStage, 1)));
        backgrL2 = new TextureRegion(Utils.getTextureWithFilter(Resource.getGameStageRampBackgroundResource(currentStage, 2)));
        backgrL3 = new TextureRegion(Utils.getTextureWithFilter(Resource.getGameStageRampBackgroundResource(currentStage, 3)));
        this.downwardsRampLevel = downwardsRampLevel;
        this.upwardsRampLevel = upwardsRampLevel;
        this.rampBaseHeightLevel = rampBaseHeightLevel;
    }

    public void createRamps(Integer bestDinstance) {
        if (bestDinstance != null) {
            // createBestDistanceWall(bestDinstance);
        }
        // START HORIZONTAL RAMP
        createBindingPolygon(-40, START_RAMP_X, getDownwardsRampHeight(), true, 0, BIT_WALL, BIT_BUTTON, backgrL1, true);
        createBindingPolygon(-40, START_RAMP_X, getDownwardsRampHeight(), false, RAMP_SURFACE_OFFSET, BIT_BUTTON, BIT_WALL, backgrL3, true);
        // GROUND
        createBindingPolygon(0, (int) scaleUpPPM(5000), GROUND_HEIGHT, true, 0, BIT_WALL, BIT_BUTTON, backgrL1, false);
        // RAMP
        createCurve();

    }

    // 0 bottom left (horiz - vert)
    // 1 bottom right (h - v)
    // 2 top right (h - v)
    // 3 top left (horiz - vert)
    private void createCurve() {
        BezierCurveCreator bezierCurveCreator = new BezierCurveCreator();

        bezierCurveCreator.addNode(START_RAMP_X, (int) getDownwardsRampHeight(), 100, getBaseRampHeight());
        bezierCurveCreator.addNode(END_RAMP_X, (int) getUpwardsRampHeight(), 200, 25);

        Array<Vector2> points = bezierCurveCreator.convertToPoints(20);
        for (int i = 0; i < points.size; i++) {
            if (i == points.size - 1) {
                break;
            }
            Vector2 point1 = points.get(i);
            Vector2 point2 = points.get(i + 1);

            // actual ramp
            Vector2[] v = createRampVector(point1.x, point2.x, point1.y, point2.y, true, 0);
            createPolygon(v, "curve", BIT_WALL, BIT_BUTTON);
            polygonSprites.add(createPolySprite(v[0], v[1], v[2], v[3], backgrL1));

            // white ramp background
            v = createRampVector(point1.x, point2.x, point1.y, point2.y, false, RAMP_SURFACE_OFFSET);
            createPolygon(v, "curve", BIT_BUTTON, BIT_WALL);
            polygonSprites.add(createPolySprite(v[0], v[1], v[2], v[3], backgrL2));
        }
    }

    private Vector2[] createRampVector(float point1X, float point2X, float point1Y, float point2Y, boolean polyBaseIsGround, float yOffset) {
        Vector2[] v = new Vector2[4];
        v[0] = new Vector2(point1X, polyBaseIsGround ? 0 : point1Y - yOffset);
        v[1] = new Vector2(point2X, polyBaseIsGround ? 0 : point2Y - yOffset);
        v[2] = new Vector2(point2X, point2Y + yOffset);
        v[3] = new Vector2(point1X, point1Y + yOffset);
        return v;
    }

    private int getBaseRampHeight() {
        return (int) (Utils.getValueForDisplayHeightPercent(5f) * rampBaseHeightLevel);
    }

    public float getDownwardsRampHeight() {
        return DEFAULT_RAMP_HEIGHT * downwardsRampLevel;
    }

    private float getUpwardsRampHeight() {
        return DEFAULT_RAMP_HEIGHT * upwardsRampLevel;
    }

    public void createBackwardsWall(float x, float y) {
        createBindingPolygon(x - 40, x, y, true, 0, BIT_WALL, BIT_BUTTON, backgrL1, false);
    }

    @SuppressWarnings("unused")
    private void createBestDistanceWall(float x) {
        // createBindingPolygon(scaleUpPPM(x), scaleUpPPM(x + 0.5f), (int)
        // scaleUpPPM(50000), true, 0, BIT_WALL, BIT_WALL, transparentRed, true,
        // true);
    }

    // 0 bottom left (horiz - vert)
    // 1 bottom right (h - v)
    // 2 top right (h - v)
    // 3 top left (horiz - vert)
    private void createBindingPolygon(float leftPoint, float rightPoint, float height, boolean polyBaseIsGround, float yOffset, short categBit, short allowedToCollideWithBit,
                                      TextureRegion textureRegion, boolean addTexture) {
        Vector2[] rmp = new Vector2[4];
        rmp[0] = new Vector2(leftPoint, polyBaseIsGround ? 0 : height - yOffset);
        rmp[1] = new Vector2(rightPoint, polyBaseIsGround ? 0 : height - yOffset);
        rmp[2] = new Vector2(rightPoint, height + yOffset);
        rmp[3] = new Vector2(leftPoint, height + yOffset);
        createPolygon(rmp, "ramp", categBit, allowedToCollideWithBit);
        if (addTexture) {
            polygonSprites.add(createPolySprite(rmp[0], rmp[1], rmp[2], rmp[3], textureRegion));
        }
    }

    private PolygonSprite createPolySprite(Vector2 bottomLeft, Vector2 bottomRight, Vector2 topRight, Vector2 topLeft, TextureRegion textureRegion) {
        float scale = 2f;
        float[] fs = new float[]{
                bottomLeft.x * scale,
                bottomLeft.y * scale,
                bottomRight.x * scale,
                bottomRight.y * scale,
                topRight.x * scale,
                topRight.y * scale,
                topLeft.x * scale,
                topLeft.y * scale};
        fs = scaleUpFloatArray(fs);
        EarClippingTriangulator earClippingTriangulator = new EarClippingTriangulator();
        ShortArray sar = earClippingTriangulator.computeTriangles(fs);
        PolygonRegion polyReg = new PolygonRegion(textureRegion, fs, sar.toArray());
        PolygonSprite polygonSprite = new PolygonSprite(polyReg);
        return polygonSprite;
    }

    @SuppressWarnings("unused")
    private Vector2[] copyVectorArray(Vector2[] vectorArray) {
        Vector2[] result = new Vector2[4];
        for (int i = 0; i < vectorArray.length; i++) {
            result[i] = new Vector2(vectorArray[i].x, vectorArray[i].y);
        }
        return result;
    }

    private void createPolygon(Vector2[] vs, String userData, short categBit, short allowedToCollideWithBit) {
        PolygonShape shape = new PolygonShape();
        shape.set(Utils.scaleDownVectors(vs));
        Body body = worldBodyCreator.createBody(BodyType.StaticBody, 0, 0);

        Fixture fixture = body.createFixture(worldBodyCreator.createFixtureDef(shape, categBit, allowedToCollideWithBit));
        fixture.setUserData(userData);
        shape.dispose();
    }

    public List<PolygonSprite> getPolygonSprites() {
        return polygonSprites;
    }
}

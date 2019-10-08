package libgdx.game.lib.learntofly.entities.player.properties;

import static libgdx.game.lib.learntofly.util.Utils.*;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;

import libgdx.game.lib.learntofly.entities.player.Player;
import libgdx.game.lib.learntofly.handlers.MyContactListener;
import libgdx.game.lib.learntofly.util.MovingAngleCalculator;

class PlayerAttributesProcessor {

    private Vector2 previousPlayerPoint;

    private MovingAngleCalculator playerMovingAngleCalculator;
    private MyContactListener contactListener;

    private Body playerBody;

    public PlayerAttributesProcessor(Body playerBody, MyContactListener contactListener) {
        playerMovingAngleCalculator = new MovingAngleCalculator();
        this.playerBody = playerBody;
        this.contactListener = contactListener;
    }

    /**
     * <b>airResistanceFactor</b>: base is 1f.
     * <ul>
     * <li>smaller than 1f: air resistance reduced and more speed gained
     * <li>larger than 1f: air resistance amplified and less speed gained
     * <ul>
     */
    void applyAirResistance(float movingAngle, float airResistanceFactor, float floatingLevel) {
        float airRes = airResistanceFactor;
        float speedGained = airResistanceFactor > 1f ? 1f - (airResistanceFactor - 1f) : 1f + (1f - airResistanceFactor);
        // first(air resistance; big - big air resistance)
        // second (speed gained; big - big speed gained)
        float multiplyCoef = movingAngle > 0 ? 3f * airRes : 2f * speedGained;
        float angleResistance = movingAngle / 100f * multiplyCoef;
        if (!(isPlayerOnGround() && getDisplayAcceleration() == 0)
                &&
                movingAngle < Player.CRITICAL_UP_ANGLE
                &&
                !(getDisplayAcceleration() <= 3 && movingAngle > Player.CRITICAL_UP_ANGLE)) {
            float xForce = isPlayerOnGround() ? 100f : angleResistance;

            float accelRelated = getXDisplaySpeed() / 20f;
            xForce = accelRelated * xForce;

            // Gravity related
            xForce = movingAngle > 0 ? xForce + xForce * (1.1f - floatingLevel) : xForce;
            boolean ifYisSmallAndThereIsXresistance = xForce > 0 && getXDisplaySpeed() < 20;
            if (!isPlayerGoingBackwards() && !ifYisSmallAndThereIsXresistance) {
                playerBody.applyForceToCenter(-xForce, movingAngle > 0 ? -xForce / 2 : 0, true);
            } else if (ifYisSmallAndThereIsXresistance) {
                playerBody.applyForceToCenter(-xForce / 10, -xForce / 10, true);
            }
        }
        float yForce = movingAngle > Player.CRITICAL_UP_ANGLE ? angleResistance + angleResistance * (1.1f - floatingLevel) : 0f;
        playerBody.applyForceToCenter(0f, -yForce, true);
    }

    public void setPreviousPlayerPoint() {
        playerMovingAngleCalculator.setPreviousPlayerPoint(getPosition());
        this.previousPlayerPoint = new Vector2(getPosition());
    }

    public int getDisplayAcceleration() {
        return (int) (getXDisplaySpeed() + getYDisplaySpeed());
    }

    float getXDisplaySpeed() {
        return calcSpeed(getPosition().x, this.previousPlayerPoint.x);
    }

    private Vector2 getPosition() {
        return playerBody.getPosition();
    }

    private int calcSpeed(float currentCoord, float prevCoord) {
        return (int) (scaleUpPPM(currentCoord - prevCoord) * 10.0f);
    }

    float getYDisplaySpeed() {
        return calcSpeed(getPosition().y, this.previousPlayerPoint.y);
    }

    public int getDisplayAltitude() {
        return (int) (getPosition().y * 10.0f);
    }

    public int getDisplayDistance() {
        return (int) (getPosition().x);
    }

    public float getMovingAngle() {
        return playerMovingAngleCalculator.getAngle(getPosition(), getXDisplaySpeed());
    }

    void setGravity(float val) {
        playerBody.setGravityScale(val);
    }

    public void setPreviousMovingAngle(float previousAngle) {
        playerMovingAngleCalculator.setPreviousAngle(previousAngle);
    }

    boolean isPlayerGoingBackwards() {
        return previousPlayerPoint != null
                && (int) scaleUpPPM(previousPlayerPoint.x) > (int) scaleUpPPM(getPosition().x);
    }

    public boolean isPlayerOnGround() {
        return contactListener.isPlayerIsOnGround();
    }
}
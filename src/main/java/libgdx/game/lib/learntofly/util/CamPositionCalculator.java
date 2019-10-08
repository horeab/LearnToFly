package libgdx.game.lib.learntofly.util;

import libgdx.game.lib.learntofly.handlers.B2DVars;
import libgdx.game.lib.learntofly.main.Game;

import static libgdx.game.lib.learntofly.util.Utils.*;


public class CamPositionCalculator {

    private float displayHeight;
    private float displayWidth;

    private float xFirstScreenValueDeparture;
    private float yScreenValueDeparture;

    public CamPositionCalculator() {
        super();
        this.displayHeight = Game.getHeight();
        this.displayWidth = Game.getWidth();
    }

    public float xPosition(float xPlayerPos) {
        float newxPlayerPos = processPosition(xPlayerPos);
        float camXValue = displayWidth / 2;
        if (firstScreenDepartured(newxPlayerPos)) {
            camXValue = camXValue + (newxPlayerPos - xFirstScreenValueDeparture);
        } else {
            xFirstScreenValueDeparture = newxPlayerPos;
        }
        return camXValue;
    }

    public float yPosition(float yPlayerPos, float xPlayerPos) {
        float newYPlayerPos = processPosition(yPlayerPos);
        float newxPlayerPos = processPosition(xPlayerPos);
        float camYValue = displayHeight / 2;
        if (newYPlayerPos > displayHeight / 1.6f && firstScreenDepartured(newxPlayerPos)) {
            camYValue = camYValue + newYPlayerPos - yScreenValueDeparture;
        } else {
            yScreenValueDeparture = newYPlayerPos;
        }
        camYValue = camYValue < displayHeight / 2 ? displayHeight / 2 : camYValue;
        return camYValue;
    }

    private float processPosition(float position) {
        return scaleUpPPM(position + scaleDownPPM(B2DVars.PLAYER_RADIUS));
    }

    private boolean firstScreenDepartured(float xPlayerPos) {
        return xPlayerPos > displayWidth - (displayWidth / 2.5f);
    }
}

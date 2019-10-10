package libgdx.game.lib.learntofly.hud;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import libgdx.game.lib.learntofly.achievements.Achievement;
import libgdx.game.lib.learntofly.entities.player.Player;
import libgdx.game.lib.learntofly.entities.player.PlayerAttrs;
import libgdx.game.lib.learntofly.main.Game;
import libgdx.game.lib.learntofly.states.Play;
import libgdx.game.lib.learntofly.util.LibgdxControlUtils;
import libgdx.game.lib.learntofly.util.Resource;
import libgdx.game.lib.learntofly.util.Utils;

public class HUD {
    public static final int NUMBER_CHARACTER_WIDTH = 27;
    private static final int ALPHA_UPPER_CHARACTER_WIDTH = 33;
    private static final int ALPHA_LOWER_CHARACTER_WIDTH = 33;

    private static final float DEFAULT_SPEED_ANGLE = 90f;

    private static final int SPEEDOMETER_WIDTH = 70;
    private static final int SPEEDOMETER_HEIGHT = 70;
    private static final int SPEEDNEEDLE_WIDTH = 110;
    private static final int SPEEDNEEDLE_HEIGHT = 16;

    private Player player;
    private CreateFinishPopup createFinishPopup;
    private CreateAchievmentPopup createAchievmentPopup;

    private TextureRegion[] font;
    private TextureRegion[] greenNrfont;

    private TextureRegion fuelBarEmptyBackground;
    private TextureRegion speedometer;
    private TextureRegion speedNeedle;
    private Texture fuelBarFullTexture;

    private float displayWidth;
    private float displayHeight;

    public HUD(Player player, PlayerAttrs playerAttrs, LibgdxControlUtils libgdxControlUtils) {
        initFont();
        this.displayWidth = Game.getWidth();
        this.displayHeight = Game.getHeight();
        createFinishPopup = new CreateFinishPopup(font, playerAttrs);
        createAchievmentPopup = new CreateAchievmentPopup(font, libgdxControlUtils);
        fuelBarFullTexture = Utils.getTextureWithFilter(Resource.fuel_bar_full);
        fuelBarEmptyBackground = new TextureRegion(Utils.getTextureWithFilter(Resource.fuel_bar_empty), 0, 0, 8, SPEEDOMETER_HEIGHT);
        speedometer = new TextureRegion(Utils.getTexture(Resource.speedometer), 0, 0, SPEEDOMETER_WIDTH * 2, SPEEDOMETER_HEIGHT * 2);
        Texture speedNeedleTexture = Utils.getTexture(Resource.speed_needle);
        speedNeedle = new TextureRegion(speedNeedleTexture, 0, 0, SPEEDNEEDLE_WIDTH * 2, SPEEDNEEDLE_HEIGHT * 2);
        this.player = player;
    }

    private void initFont() {
        Texture nr = Utils.getTextureWithFilter(Resource.numbers);
        font = new TextureRegion[62];
        for (int i = 0; i < 10; i++) {
            font[i] = new TextureRegion(nr, i * NUMBER_CHARACTER_WIDTH, 0, NUMBER_CHARACTER_WIDTH, 40);
        }
        Texture upper = Utils.getTextureWithFilter(Resource.alphabet_upper);
        for (int i = 10; i < 36; i++) {
            font[i] = new TextureRegion(upper, (i - 10) * ALPHA_UPPER_CHARACTER_WIDTH, 0, ALPHA_UPPER_CHARACTER_WIDTH, 40);
        }
        Texture lower = Utils.getTextureWithFilter(Resource.alphabet_lower);
        for (int i = 36; i < 62; i++) {
            font[i] = new TextureRegion(lower, (i - 10 - 26) * ALPHA_LOWER_CHARACTER_WIDTH, 0, ALPHA_LOWER_CHARACTER_WIDTH, 40);
        }
        Texture greenNr = Utils.getTextureWithFilter(Resource.green_numbers);
        greenNrfont = new TextureRegion[10];
        for (int i = 0; i < 10; i++) {
            greenNrfont[i] = new TextureRegion(greenNr, i * NUMBER_CHARACTER_WIDTH, 0, NUMBER_CHARACTER_WIDTH, 40);
        }
    }

    public int getFuelbarXPos() {
        return (int) (displayWidth - getWidthPercent(5));
    }

    public static int getFuelbarYPos(float displayHeight) {
        return getHeightPercent(5, displayHeight);
    }

    public void render(SpriteBatch sb,
                       float availableFuel,
                       boolean hasRocket,
                       Play.GameRunState gameRunState,
                       Achievement achievement,
                       Integer stageJustUnlocked,
                       int displayAltitude,
                       int maxSpeedAllowed) {
        if (gameRunState == Play.GameRunState.RUN) {
            // Draw distance
            drawFont(sb, player.getDisplayDistance() + "", displayWidth / 2, getHeightPercent(85, displayHeight), font, getFontScale());
            if (hasRocket) {
                drawFuelBar(sb, availableFuel);
            }
            drawSpeedometer(sb, player.getXDisplaySpeed(), maxSpeedAllowed);
            createAchievmentPopup.drawAchievmentPopup(sb, achievement, stageJustUnlocked, displayAltitude);
        } else {
            createFinishPopup.drawFinishPopup(sb);
        }
    }

    private static float getFontScale() {
        return 0.5f;
    }

    @SuppressWarnings("unused")
    static float getPopupFontScale(float width, float height) {
        return 0.33f;
    }

    private void drawFuelBar(SpriteBatch sb, float availableFuel) {
        TextureRegion fuelBar = new TextureRegion(fuelBarFullTexture, 0, 0, getWidthPercent(2), (int) (availableFuel));
        sb.setColor(1.0f, 1.0f, 1.0f, 0.8f);
        sb.draw(fuelBarEmptyBackground, getFuelbarXPos(), getFuelbarYPos(displayHeight));
        sb.setColor(1.0f, 1.0f, 1.0f, 0.5f);
        sb.draw(fuelBar, getFuelbarXPos() + 0.7f, getFuelbarYPos(displayHeight));
        sb.setColor(1.0f, 1.0f, 1.0f, 1f);
    }

    private void drawSpeedometer(SpriteBatch sb, float speed, float speedAllowed) {
        sb.setColor(1.0f, 1.0f, 1.0f, 0.5f);
        sb.draw(speedometer,
                getFuelbarXPos() - SPEEDOMETER_WIDTH,
                getFuelbarYPos(displayHeight),
                SPEEDOMETER_WIDTH,// orgin
                SPEEDOMETER_WIDTH,// orgin
                SPEEDOMETER_WIDTH,
                SPEEDOMETER_WIDTH,
                1f,
                1f,
                0);
        float speedAllowedToDisplay = speedAllowed + (speedAllowed / 20) * 5;
        drawSpeedNr(sb, (int) speedAllowedToDisplay);
        float angle = speed;
        angle = angle > speedAllowedToDisplay ? speedAllowedToDisplay : angle;
        angle = angle < 0 ? 0 : angle;
        float percentSpeed = DEFAULT_SPEED_ANGLE / speedAllowedToDisplay;
        angle = percentSpeed * angle;
        sb.setColor(1.0f, 1.0f, 1.0f, 1f);
        sb.draw(speedNeedle,
                getFuelbarXPos() - SPEEDNEEDLE_WIDTH + SPEEDNEEDLE_WIDTH / 30f,
                getFuelbarYPos(displayHeight),
                SPEEDNEEDLE_WIDTH - SPEEDNEEDLE_WIDTH / 10f,// orgin
                SPEEDNEEDLE_HEIGHT / 2,// orgin
                SPEEDNEEDLE_WIDTH,
                SPEEDNEEDLE_HEIGHT,
                0.5f,
                0.5f,
                -angle);
    }

    private void drawSpeedNr(SpriteBatch sb, int speedAllowed) {
        float fontScale = 0.28f;
        // int halfSpeedAllowed = speedAllowed / 2;
        drawFont(sb,
                "0",
                getFuelbarXPos() - SPEEDOMETER_WIDTH / 1.05f,
                getFuelbarYPos(displayHeight) + SPEEDNEEDLE_HEIGHT / 5,
                greenNrfont,
                fontScale);
        drawFont(sb,
                speedAllowed + "",
                getFuelbarXPos() - getTextWidth(speedAllowed + "", fontScale) / 2 - SPEEDOMETER_WIDTH / 30,
                SPEEDOMETER_HEIGHT - SPEEDOMETER_WIDTH / 20,
                greenNrfont,
                fontScale);
    }

    public static int getHeightPercent(int percent, float displayHeight) {
        return (int) Utils.getValueForPercent(displayHeight, percent);
    }

    private int getWidthPercent(int percent) {
        return (int) Utils.getValueForPercent(displayWidth, percent);
    }

    static void drawFont(SpriteBatch sb, String s, float x, float y, TextureRegion[] font, float scale) {
        drawFont(sb, s, x, y, font, scale, 1f);
    }

    static void drawFont(SpriteBatch sb, String text, float x, float y, TextureRegion[] font, float scale, float alphaValue) {
        int index = 0;
        for (int i = 0; i < text.length(); i++) {

            char c = text.charAt(i);
            if (c >= '0' && c <= '9') {
                c -= '0';
                index = c;
            } else if (c >= 'A' && c <= 'Z') {
                c -= 'A';
                index = c + 10;
            } else if (c >= 'a' && c <= 'z') {
                c -= 'a';
                index = c + 10 + 26;
            } else if (c == ' ') {
                continue;
            }
            int nrOfSpaces = nrOfSpaces(text, i);
            float characterX = x + (i - nrOfSpaces) * (NUMBER_CHARACTER_WIDTH * (scale / 1.1f)) + nrOfSpaces * (NUMBER_CHARACTER_WIDTH * (scale / 1.6f));
            sb.setColor(1.0f, 1.0f, 1.0f, alphaValue);
            BitmapFont bitmapFont = Game.getInstance().getFontManager().getFont();
            sb.draw(font[index],
                    characterX,
                    y,
                    0,// origin
                    0,// origin
                    font[c].getRegionWidth(),
                    font[c].getRegionHeight(),
                    scale,// scale
                    scale,// scale
                    0);
            sb.setColor(1.0f, 1.0f, 1.0f, 1f);
            bitmapFont.draw(sb, text, x, y);
        }
    }

    public static float getTextWidth(String text, float scale) {
        int i = text.length() - 1;
        int nrOfSpaces = nrOfSpaces(text, i);
        float characterX = (i - nrOfSpaces) * (NUMBER_CHARACTER_WIDTH * (scale / 1.1f)) + nrOfSpaces * (NUMBER_CHARACTER_WIDTH * (scale / 1.6f));
        return characterX + NUMBER_CHARACTER_WIDTH;
    }

    private static int nrOfSpaces(String s, int currentPos) {
        int nrOfSpaces = 0;
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == ' ') {
                nrOfSpaces++;
            }
            if (i == currentPos) {
                break;
            }
        }
        return nrOfSpaces;
    }

    public CreateFinishPopup getCreateFinishPopup() {
        return createFinishPopup;
    }
}

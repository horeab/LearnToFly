package libgdx.game.lib.learntofly.hud;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
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
import libgdx.utils.model.FontColor;
import libgdx.utils.model.FontConfig;

public class HUD {

    private static final float DEFAULT_SPEED_ANGLE = 90f;

    private static final int SPEEDOMETER_WIDTH = 70;
    private static final int SPEEDOMETER_HEIGHT = 70;
    private static final int SPEEDNEEDLE_WIDTH = 110;
    private static final int SPEEDNEEDLE_HEIGHT = 16;

    private Player player;
    private CreateFinishPopup createFinishPopup;
    private CreateAchievementPopup createAchievmentPopup;

    private TextureRegion fuelBarEmptyBackground;
    private TextureRegion speedometer;
    private TextureRegion speedNeedle;
    private Texture fuelBarFullTexture;

    private float displayWidth;
    private float displayHeight;

    public HUD(Player player, PlayerAttrs playerAttrs, LibgdxControlUtils libgdxControlUtils) {
        this.displayWidth = Game.getWidth();
        this.displayHeight = Game.getHeight();
        createFinishPopup = new CreateFinishPopup(playerAttrs);
        createAchievmentPopup = new CreateAchievementPopup(libgdxControlUtils);
        fuelBarFullTexture = Utils.getTextureWithFilter(Resource.fuel_bar_full);
        fuelBarEmptyBackground = new TextureRegion(Utils.getTextureWithFilter(Resource.fuel_bar_empty), 0, 0, 8, SPEEDOMETER_HEIGHT);
        speedometer = new TextureRegion(Utils.getTexture(Resource.speedometer), 0, 0, SPEEDOMETER_WIDTH * 2, SPEEDOMETER_HEIGHT * 2);
        Texture speedNeedleTexture = Utils.getTexture(Resource.speed_needle);
        speedNeedle = new TextureRegion(speedNeedleTexture, 0, 0, SPEEDNEEDLE_WIDTH * 2, SPEEDNEEDLE_HEIGHT * 2);
        this.player = player;
    }

    public int getFuelbarXPos() {
        return (int) (displayWidth - getWidthPercent(4));
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
            drawFont(sb, player.getDisplayDistance() + "", displayWidth / 2, getHeightPercent(85, displayHeight),
                    new FontConfig(FontColor.WHITE.getColor(), FontColor.BLACK.getColor(), Math.round(Game.STANDARD_FONT_SIZE * 1.2f), 3f), 1f);
            if (hasRocket) {
                drawFuelBar(sb, availableFuel);
            }
            drawSpeedometer(sb, player.getXDisplaySpeed(), maxSpeedAllowed);
            createAchievmentPopup.drawAchievementPopup(sb, achievement, stageJustUnlocked, displayAltitude);
        } else {
            createFinishPopup.drawFinishPopup(sb);
        }
    }

    public static float getFontScale() {
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
        float fontScale = Game.STANDARD_FONT_SIZE / 1.5f;
        // int halfSpeedAllowed = speedAllowed / 2;
        FontConfig fontConfig = new FontConfig(FontColor.GREEN.getColor(), FontColor.BLACK.getColor(), Math.round(fontScale), 2f);
        drawFont(sb,
                "0",
                getFuelbarXPos() - SPEEDOMETER_WIDTH / 1.05f,
                getFuelbarYPos(displayHeight) + SPEEDNEEDLE_HEIGHT / 5,
                fontConfig, 1f);
        drawFont(sb,
                speedAllowed + "",
                getFuelbarXPos() - getTextWidth(speedAllowed + "", fontConfig),
                SPEEDOMETER_HEIGHT - SPEEDOMETER_WIDTH / 20,
                fontConfig,
                1f);
    }

    public static int getHeightPercent(int percent, float displayHeight) {
        return (int) Utils.getValueForPercent(displayHeight, percent);
    }

    private int getWidthPercent(int percent) {
        return (int) Utils.getValueForPercent(displayWidth, percent);
    }

    static void drawFont(SpriteBatch sb, String s, float x, float y, FontColor fontColor, int fontSize) {
        drawFont(sb, s, x, y, fontColor, fontSize, 1f);
    }

    static void drawFont(SpriteBatch sb, String text, float x, float y, FontColor fontColor, int fontSize, float alphaValue) {
        drawFont(sb, text, x, y, new FontConfig(fontColor.getColor(), fontColor.getColor(), fontSize, FontConfig.STANDARD_BORDER_WIDTH), alphaValue);
    }

    static void drawFont(SpriteBatch sb, String text, float x, float y, FontConfig fontConfig, float alphaValue) {
        y = y + Utils.getValueForDisplayHeightPercent(4.5f);
        BitmapFont bitmapFont = Game.getInstance().getFontManager().getFont(fontConfig);
        bitmapFont.getData().setScale(getFontScale());
        bitmapFont.draw(sb, text, x, y);
    }

    public static float getTextWidth(String text, FontConfig fontConfig) {
        return new GlyphLayout(libgdx.game.Game.getInstance().getFontManager().getFont(fontConfig), text).width;
    }

    public static float getTextWidth(String text) {
        return getTextWidth(text, new FontConfig());
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

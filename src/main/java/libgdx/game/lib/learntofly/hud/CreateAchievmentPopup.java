package libgdx.game.lib.learntofly.hud;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import libgdx.game.lib.learntofly.achievements.Achievement;
import libgdx.game.lib.learntofly.main.Game;
import libgdx.game.lib.learntofly.states.GameState;
import libgdx.game.lib.learntofly.states.StageScreen;
import libgdx.game.lib.learntofly.util.B2DSprites;
import libgdx.game.lib.learntofly.util.LearnToFlyGameLabel;
import libgdx.game.lib.learntofly.util.LibgdxControlUtils;
import libgdx.game.lib.learntofly.util.Resource;
import libgdx.game.lib.learntofly.util.Utils;
import libgdx.utils.ScreenDimensionsManager;
import libgdx.utils.model.FontColor;

public class CreateAchievmentPopup {

    private static final int ACIEVEMENT_TEXTURE_SIDE_SIZE = 32;
    private static final int POPUP_TIMEOUT_SECONDS = 2;

    private Texture achievmentPopupTexture;
    private TextureRegion coinTexture;
    private TextureRegion speedAchTexture;
    private TextureRegion distanceAchTexture;
    private TextureRegion durationAchTexture;
    private TextureRegion altitudeAchTexture;

    private float counter = 0f;
    private float popupAlphaValue = CreateFinishPopup.POPUP_ALPHA;
    private float textAlphaValue = 1f;
    private Achievement currentAchievement;
    private Integer stageJustUnlocked;
    private LibgdxControlUtils libgdxControlUtils;

    public CreateAchievmentPopup(LibgdxControlUtils libgdxControlUtils) {
        this.libgdxControlUtils = libgdxControlUtils;
        coinTexture = new TextureRegion(Utils.getTextureWithFilter(Resource.coin));
        speedAchTexture = new TextureRegion(Utils.getTextureWithFilter(Resource.speed_achievement));
        distanceAchTexture = new TextureRegion(Utils.getTextureWithFilter(Resource.distance_achievement));
        durationAchTexture = new TextureRegion(Utils.getTextureWithFilter(Resource.duration_achievement));
        altitudeAchTexture = new TextureRegion(Utils.getTextureWithFilter(Resource.altitude_achievement));
    }

    public void drawAchievmentPopup(SpriteBatch sb, Achievement achievement, Integer stageJustUnlocked, int displayAltitude) {
        if (currentAchievement == null) {
            currentAchievement = achievement;
            this.stageJustUnlocked = stageJustUnlocked;
        }
        // if a new achievement comes, replace the old one with the new one
        if (achievement != null) {
            currentAchievement = achievement;
            popupAlphaValue = CreateFinishPopup.POPUP_ALPHA;
            textAlphaValue = 1f;
            counter = 0f;
            if (stageJustUnlocked != null) {
                this.stageJustUnlocked = stageJustUnlocked;
            }
        }
        processFadeOut();
        if (popupAlphaValue >= 0f && currentAchievement != null) {
            TextureRegion achievementImage = getAchievementTexture(currentAchievement.getAchievementId());
            String text = currentAchievement.getAchievementLabel(currentAchievement.getValue());
            int popupWidth = (int) HUD.getTextWidth(text) + ACIEVEMENT_TEXTURE_SIDE_SIZE;
            achievmentPopupTexture = new Texture(CreateFinishPopup.getPixmapRoundedRectangle(Math.round(getMinPopupWidth(popupWidth) / 1.1f), getPopupHeight(), 12, Color.valueOf("339933")));
            float alphaValue = popupAlphaValue;
            if (playerIsCoveredByPopup(displayAltitude)) {
                alphaValue = 0.3f;
            }
            sb.setColor(1.0f, 1.0f, 1.0f, alphaValue);
            sb.draw(achievmentPopupTexture, popupX(displayAltitude), HUD.getFuelbarYPos(ScreenDimensionsManager.getScreenHeight()));
            drawAchievementImage(sb, displayAltitude, achievementImage);
            sb.setColor(1.0f, 1.0f, 1.0f, 1f);
            drawAchievementText(sb, HUD.getFuelbarYPos(ScreenDimensionsManager.getScreenHeight()), popupX(displayAltitude) + ACIEVEMENT_TEXTURE_SIDE_SIZE, text, currentAchievement.getReward());
            if (this.stageJustUnlocked != null) {
                drawUnlockedStagePopup(sb);
            }
        } else {
            this.stageJustUnlocked = null;
            currentAchievement = null;
        }
//        this.stageJustUnlocked = 1;
//        drawUnlockedStagePopup(sb);
    }

    private void drawUnlockedStagePopup(SpriteBatch sb) {
        String text1 = LearnToFlyGameLabel.l_next_stage_text1.getText();
        String text2 = LearnToFlyGameLabel.l_next_stage_text2.getText();
        if (stageJustUnlocked == StageScreen.NR_OF_STAGES + 1) {
            text1 = LearnToFlyGameLabel.l_play_congratulations.getText();
            text2 = LearnToFlyGameLabel.l_play_game_finished.getText();
        }
        int popupWidth = (int) HUD.getTextWidth(text1) + Math.round(ScreenDimensionsManager.getScreenWidthValue(5));
        achievmentPopupTexture = new Texture(CreateFinishPopup.getPixmapRoundedRectangle(getMinPopupWidth(popupWidth), getPopupHeight(), 12, Color.valueOf("FF9900")));
        sb.setColor(1.0f, 1.0f, 1.0f, popupAlphaValue);
        int height = (int) Utils.getValueForDisplayHeightPercent(80);
        float popupX = Utils.getValueForPercent(ScreenDimensionsManager.getScreenWidth(), 3);
        sb.draw(achievmentPopupTexture, popupX, height);
        sb.setColor(1.0f, 1.0f, 1.0f, 1f);
        float extraMargin = ScreenDimensionsManager.getScreenWidthValue(2);
        HUD.drawFont(sb,
                text1,
                popupX + extraMargin,
                height + Utils.getValueForDisplayHeightPercent(9),
                FontColor.BLACK,
                Game.STANDARD_FONT_SIZE,
                textAlphaValue);
        HUD.drawFont(sb,
                text2,
                popupX + extraMargin,
                height + Utils.getValueForDisplayHeightPercent(2),
                FontColor.BLACK,
                Game.STANDARD_FONT_SIZE,
                textAlphaValue);
    }

    private int getMinPopupWidth(int width) {
        float screenWidthValue = ScreenDimensionsManager.getScreenWidthValue(15);
        return width < screenWidthValue ? Math.round(screenWidthValue) : width;
    }

    private int getPopupHeight() {
        return (int) (ScreenDimensionsManager.getScreenHeightValue(8));
    }

    private void drawAchievementImage(SpriteBatch sb, int displayAltitude, TextureRegion achievementTexture) {
        float baseScale = 1f * libgdxControlUtils.getHeightDisplayRatio();
        sb.draw(achievementTexture,
                popupX(displayAltitude) + ACIEVEMENT_TEXTURE_SIDE_SIZE / 10,
                HUD.getFuelbarYPos(ScreenDimensionsManager.getScreenHeight()) + getPopupHeight() / 10,
                0,// orgin
                0,// orgin
                achievementTexture.getRegionWidth(),
                achievementTexture.getRegionHeight(),
                baseScale,
                baseScale,
                0);
    }

    private TextureRegion getAchievementTexture(String achievementId) {
        TextureRegion achievementTexture = null;
        if (achievementId == Achievement.ALTITUDE_ACHIEVEMENT) {
            achievementTexture = altitudeAchTexture;
        } else if (achievementId == Achievement.DISTANCE_ACHIEVEMENT) {
            achievementTexture = distanceAchTexture;
        } else if (achievementId == Achievement.DURATION_ACHIEVEMENT) {
            achievementTexture = durationAchTexture;
        } else if (achievementId == Achievement.SPEED_ACHIEVEMENT) {
            achievementTexture = speedAchTexture;
        }
        return achievementTexture;
    }

    private void drawAchievementText(SpriteBatch sb, float y, float labelX, String text, int cashReward) {
        String reward = LearnToFlyGameLabel.l_reward.getText(cashReward);
        float extraMargin = ScreenDimensionsManager.getScreenWidthValue(1);
        int standardFontSize = Math.round(Game.STANDARD_FONT_SIZE / 1.5f);
        HUD.drawFont(sb, text, labelX + extraMargin, y + Utils.getValueForDisplayHeightPercent(8), FontColor.BLACK, standardFontSize, textAlphaValue);
        HUD.drawFont(sb,
                reward,
                labelX + extraMargin,
                y + Utils.getValueForDisplayHeightPercent(1),
                FontColor.BLACK,
                standardFontSize,
                textAlphaValue);
        drawCoins(sb, y + Utils.getValueForDisplayHeightPercent(0), labelX + extraMargin + new GlyphLayout(libgdx.game.Game.getInstance().getFontManager().getFont(), LearnToFlyGameLabel.l_reward.getText("0")).width, cashReward);
    }

    private void drawCoins(SpriteBatch sb, float y, float x, int cash) {
        sb.setColor(1.0f, 1.0f, 1.0f, popupAlphaValue);
//        float margin = ScreenDimensionsManager.getScreenWidthValue(0.1f) * Integer.toString(cash).length();
//        float margin = 0;
        float margin = new GlyphLayout(libgdx.game.Game.getInstance().getFontManager().getFont(), Integer.toString(cash)).width;
        CreateFinishPopup.drawImage(sb, x + margin, y, coinTexture, B2DSprites.SPRITE_SCALE * 1.3f);
        sb.setColor(1.0f, 1.0f, 1.0f, 1f);
    }

    private void processFadeOut() {
        counter = counter + Gdx.graphics.getDeltaTime();
        if (counter > POPUP_TIMEOUT_SECONDS) {
            float decrement = 0.05f;
            popupAlphaValue = popupAlphaValue - decrement;
            textAlphaValue = textAlphaValue - decrement;
        }
    }

    private float popupX(int displayAltitude) {
        return (ScreenDimensionsManager.getScreenWidthValue(45) - achievmentPopupTexture.getWidth()) / getPopupXPos(displayAltitude);
    }

    private boolean playerIsCoveredByPopup(int displayAltitude) {
        return getPopupXPos(displayAltitude) == 10;
    }

    private int getPopupXPos(int displayAltitude) {
        // if altitude is under 7 the popup is pushed left
        return displayAltitude > 7 ? 2 : 10;
    }
}

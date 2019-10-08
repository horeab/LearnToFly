package libgdx.game.lib.learntofly.hud;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import libgdx.game.lib.learntofly.achievements.Achievement;
import libgdx.game.lib.learntofly.main.Game;
import libgdx.game.lib.learntofly.states.GameState;
import libgdx.game.lib.learntofly.states.StageScreen;
import libgdx.game.lib.learntofly.util.B2DSprites;
import libgdx.game.lib.learntofly.util.LibgdxControlUtils;
import libgdx.game.lib.learntofly.util.Resource;
import libgdx.game.lib.learntofly.util.Utils;

public class CreateAchievmentPopup {

	private static final int ACIEVEMENT_TEXTURE_SIDE_SIZE = 32;
	private static final int POPUP_TIMEOUT_SECONDS = 2;

	private float displayHeight;
	private float displayWidth;

	private Texture achievmentPopupTexture;
	private TextureRegion coinTexture;
	private TextureRegion speedAchTexture;
	private TextureRegion distanceAchTexture;
	private TextureRegion durationAchTexture;
	private TextureRegion altitudeAchTexture;

	private TextureRegion[] font;

	private float counter = 0f;
	private float popupAlphaValue = CreateFinishPopup.POPUP_ALPHA;
	private float textAlphaValue = 1f;
	private Achievement currentAchievement;
	private Integer stageJustUnlocked;
	private LibgdxControlUtils libgdxControlUtils;

	public CreateAchievmentPopup(TextureRegion[] font, AssetManager assetManager, LibgdxControlUtils libgdxControlUtils) {
		this.displayWidth = Game.getWidth();
		this.displayHeight = Game.getHeight();
		this.libgdxControlUtils = libgdxControlUtils;
		this.font = font;
		coinTexture = new TextureRegion(Utils.getTextureWithFilter(assetManager, Resource.coin));
		speedAchTexture = new TextureRegion(Utils.getTextureWithFilter(assetManager, Resource.speed_achievement));
		distanceAchTexture = new TextureRegion(Utils.getTextureWithFilter(assetManager, Resource.distance_achievement));
		durationAchTexture = new TextureRegion(Utils.getTextureWithFilter(assetManager, Resource.duration_achievement));
		altitudeAchTexture = new TextureRegion(Utils.getTextureWithFilter(assetManager, Resource.altitude_achievement));
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
			int popupWidth = (int) HUD.getTextWidth(text, HUD.getPopupFontScale(displayWidth, displayHeight)) + ACIEVEMENT_TEXTURE_SIDE_SIZE;
			achievmentPopupTexture = new Texture(CreateFinishPopup.getPixmapRoundedRectangle(popupWidth, getPopupHeight(), 12, Color.valueOf("339933")));
			float alphaValue = popupAlphaValue;
			if (playerIsCoveredByPopup(displayAltitude)) {
				alphaValue = 0.3f;
			}
			sb.setColor(1.0f, 1.0f, 1.0f, alphaValue);
			sb.draw(achievmentPopupTexture, popupX(displayAltitude), HUD.getFuelbarYPos(displayHeight));
			drawAchievementImage(sb, displayAltitude, achievementImage);
			sb.setColor(1.0f, 1.0f, 1.0f, 1f);
			drawAchievementText(sb, HUD.getFuelbarYPos(displayHeight), popupX(displayAltitude) + ACIEVEMENT_TEXTURE_SIDE_SIZE, text, currentAchievement.getReward());
			if (this.stageJustUnlocked != null) {
				drawUnlockedStagePopup(sb);
			}
		} else {
			this.stageJustUnlocked = null;
			currentAchievement = null;
		}
	}

	private void drawUnlockedStagePopup(SpriteBatch sb) {
		String text1 = GameState.getLabel("next_stage_text1");
		String text2 = GameState.getLabel("next_stage_text2");
		if (stageJustUnlocked == StageScreen.NR_OF_STAGES + 1) {
			text1 = GameState.getLabel("play_congratulations");
			text2 = GameState.getLabel("play_game_finished");
		}
		int text1Width = (int) HUD.getTextWidth(text1, HUD.getPopupFontScale(displayWidth, displayHeight));
		int text2Margin = (text1Width - (int) HUD.getTextWidth(text2, HUD.getPopupFontScale(displayWidth, displayHeight))) / 2;
		int popupWidth = (int) HUD.getTextWidth(text1, HUD.getPopupFontScale(displayWidth, displayHeight));
		achievmentPopupTexture = new Texture(CreateFinishPopup.getPixmapRoundedRectangle(popupWidth, getPopupHeight(), 12, Color.valueOf("FF9900")));
		sb.setColor(1.0f, 1.0f, 1.0f, popupAlphaValue);
		int height = (int) Utils.getValueForDisplayHeightPercent(80);
		float popupX = Utils.getValueForPercent(displayWidth, 3);
		sb.draw(achievmentPopupTexture, popupX, height);
		sb.setColor(1.0f, 1.0f, 1.0f, 1f);
		float extraMargin = HUD.NUMBER_CHARACTER_WIDTH / 2.5f;
		HUD.drawFont(sb,
				text1,
				popupX + extraMargin,
				height + Utils.getValueForDisplayHeightPercent(9),
				font,
				HUD.getPopupFontScale(displayWidth, displayHeight),
				textAlphaValue);
		HUD.drawFont(sb,
				text2,
				popupX + extraMargin + text2Margin,
				height + Utils.getValueForDisplayHeightPercent(2),
				font,
				HUD.getPopupFontScale(displayWidth, displayHeight),
				textAlphaValue);
	}

	private int getPopupHeight() {
		return (int) (displayHeight / 6f);
	}

	private void drawAchievementImage(SpriteBatch sb, int displayAltitude, TextureRegion achievementTexture) {
		float baseScale = 1f * libgdxControlUtils.getHeightDisplayRatio();
		sb.draw(achievementTexture,
				popupX(displayAltitude) + ACIEVEMENT_TEXTURE_SIDE_SIZE / 10,
				HUD.getFuelbarYPos(displayHeight) + getPopupHeight() / 10,
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
		int textWidth = (int) HUD.getTextWidth(text, HUD.getPopupFontScale(displayWidth, displayHeight));
		int rewardMargin = (textWidth - (int) HUD.getTextWidth(GameState.getLabel("reward", cashReward), HUD.getPopupFontScale(displayWidth, displayHeight))) / 2;
		float extraMargin = HUD.NUMBER_CHARACTER_WIDTH / 2.5f;
		HUD.drawFont(sb, text, labelX + extraMargin, y + Utils.getValueForDisplayHeightPercent(8), font, HUD.getPopupFontScale(displayWidth, displayHeight), textAlphaValue);
		HUD.drawFont(sb,
				GameState.getLabel("reward", cashReward),
				labelX + extraMargin + rewardMargin,
				y + Utils.getValueForDisplayHeightPercent(1),
				font,
				HUD.getPopupFontScale(displayWidth, displayHeight),
				textAlphaValue);
		drawCoins(sb, y + 2, labelX + extraMargin + rewardMargin + 50, cashReward);
	}

	private void drawCoins(SpriteBatch sb, float y, float x, int cash) {
		sb.setColor(1.0f, 1.0f, 1.0f, popupAlphaValue);
		CreateFinishPopup.drawImage(sb, x + (9 * Integer.toString(cash).length()), y, coinTexture, B2DSprites.SPRITE_SCALE * 1.3f);
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
		return (displayWidth - achievmentPopupTexture.getWidth()) / getPopupXPos(displayAltitude);
	}

	private boolean playerIsCoveredByPopup(int displayAltitude) {
		return getPopupXPos(displayAltitude) == 10;
	}

	private int getPopupXPos(int displayAltitude) {
		// if altitude is under 7 the popup is pushed left
		return displayAltitude > 7 ? 2 : 10;
	}
}

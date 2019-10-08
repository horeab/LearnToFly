package libgdx.game.lib.learntofly.hud;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import java.math.BigDecimal;

import libgdx.game.lib.learntofly.entities.player.PlayerAttrs;
import libgdx.game.lib.learntofly.main.Game;
import libgdx.game.lib.learntofly.states.GameState;
import libgdx.game.lib.learntofly.storemanagers.GameInfoManager;
import libgdx.game.lib.learntofly.util.B2DSprites;
import libgdx.game.lib.learntofly.util.Resource;
import libgdx.game.lib.learntofly.util.Utils;

public class CreateFinishPopup {

	public static final String BORDER_COLOR = "002E8A";
	public static final String POPUP_COLOR = "006BB2";
	public static final float POPUP_ALPHA = 0.8f;
	public static final float BORDER_ALPHA = 0.5f;
	public static final int BORDER_MARGIN = (int) Utils.getValueForDisplayHeightPercent(2);

	private int incrementStep = 10;
	private float distanceIncrementCounter = 0;
	private float altitudeIncrementCounter = 0;
	private float durationIncrementCounter = 0;
	private float speedIncrementCounter = 0;
	private boolean distanceShowEndInfo;
	private boolean altitudeShowEndInfo;
	private boolean durationShowEndInfo;
	private boolean speedShowEndInfo;
	private float distanceIncrementCounterDisplay = 0;
	private float altitudeIncrementCounterDisplay = 0;
	private float durationIncrementCounterDisplay = 0;
	private float speedIncrementCounterDisplay = 0;
	private int distanceMax;
	private int altitudeMax;
	private int durationMax;
	private int speedMax;

	private float displayHeight;
	private float displayWidth;
	private Texture finishPopupTexture;
	private Texture borderPopupTexture;
	private Texture incrementCounterFullTexture;
	private Texture incrementCounterRecordTexture;
	private TextureRegion incrementCounterEmpty;
	private TextureRegion incrementCounterBorder;
	private TextureRegion coinTexture;

	private GameInfoManager gameInfoManager;
	private PlayerAttrs playerAttrs;

	private TextureRegion[] font;

	public CreateFinishPopup(TextureRegion[] font, PlayerAttrs playerAttrs, AssetManager assetManager) {
		this.displayWidth = Game.getWidth();
		this.displayHeight = Game.getHeight();
		this.font = font;
		this.playerAttrs = playerAttrs;

		gameInfoManager = new GameInfoManager();
		initMaxFields();

		finishPopupTexture = new Texture(getPixmapRoundedRectangle(popupWidth(), (int) (displayHeight / 1.14f), 12, Color.valueOf(POPUP_COLOR)));
		borderPopupTexture = new Texture(getPixmapRoundedRectangle(popupWidth() + BORDER_MARGIN, (int) (displayHeight / 1.14f) + BORDER_MARGIN, 12, Color.valueOf(BORDER_COLOR)));
		incrementCounterFullTexture = Utils.getTextureWithFilter(assetManager, Resource.increment_full);
		incrementCounterRecordTexture = Utils.getTextureWithFilter(assetManager, Resource.increment_record);
		incrementCounterEmpty = new TextureRegion(Utils.getTextureWithFilter(assetManager, Resource.increment_empty), 0, 0, getMaxCounter(), 20);
		incrementCounterBorder = new TextureRegion(Utils.getTextureWithFilter(assetManager, Resource.increment_empty), 0, 0, getMaxCounter() + BORDER_MARGIN, 20 + BORDER_MARGIN);
		coinTexture = new TextureRegion(Utils.getTextureWithFilter(assetManager, Resource.coin));
	}

	private void initMaxFields() {
		distanceMax = gameInfoManager.getDistanceMaxLevel();
		altitudeMax = gameInfoManager.getAltitudeMaxLevel();
		durationMax = gameInfoManager.getDurationMaxLevel();
		speedMax = gameInfoManager.getSpeedMaxLevel();
	}

	private int popupWidth() {
		return (int) (displayWidth / 1.5f);
	}

	public void drawFinishPopup(SpriteBatch sb) {
		int distanceCurrent = playerAttrs.getDistance();
		int altitudeCurrent = playerAttrs.getAltitude();
		int durationCurrent = playerAttrs.getDuration();
		int speedCurrent = playerAttrs.getSpeed();

		float popupY = (displayHeight - finishPopupTexture.getHeight()) / 2;
		sb.setColor(1.0f, 1.0f, 1.0f, BORDER_ALPHA);
		sb.draw(borderPopupTexture, popupX(), popupY);
		sb.setColor(1.0f, 1.0f, 1.0f, POPUP_ALPHA);
		sb.draw(finishPopupTexture, popupX() + BORDER_MARGIN / 2, popupY + BORDER_MARGIN / 2);
		sb.setColor(1.0f, 1.0f, 1.0f, 1f);

		// bottom margin
		int startPercent = 60;
		// margin between rows
		int diffPercent = 13;

		drawCurrentDay(sb, popupY + getHeightPercent(startPercent + 15), getXIncrement());

		distanceShowEndInfo = true;
		altitudeShowEndInfo = true;
		durationShowEndInfo = true;
		speedShowEndInfo = true;
		int maxValueDistance = getMaxForValueCounter(distanceCurrent, distanceMax);
		int maxValueAltitude = getMaxForValueCounter(altitudeCurrent, altitudeMax);
		int maxValueDuration = getMaxForValueCounter(durationCurrent, durationMax);
		int maxValueSpeed = getMaxForValueCounter(speedCurrent, speedMax);
		float distanceStep = getStepForValueCounter(getMaxForValue(distanceCurrent, distanceMax));
		float altitudeStep = getStepForValueCounter(getMaxForValue(altitudeCurrent, altitudeMax));
		float durationStep = getStepForValueCounter(getMaxForValue(durationCurrent, durationMax));
		float speedStep = getStepForValueCounter(getMaxForValue(speedCurrent, speedMax));

		if (distanceIncrementCounterDisplay < maxValueDistance) {
			distanceIncrementCounter = distanceIncrementCounter + incrementStep;
			distanceIncrementCounterDisplay = distanceIncrementCounterDisplay + distanceStep;
			distanceIncrementCounterDisplay = distanceIncrementCounterDisplay > maxValueDistance ? maxValueDistance : distanceIncrementCounterDisplay;
			distanceShowEndInfo = false;
			altitudeShowEndInfo = false;
			durationShowEndInfo = false;
			speedShowEndInfo = false;
		} else if (altitudeIncrementCounterDisplay < maxValueAltitude) {
			altitudeIncrementCounter = altitudeIncrementCounter + incrementStep;
			altitudeIncrementCounterDisplay = altitudeIncrementCounterDisplay + altitudeStep;
			altitudeIncrementCounterDisplay = altitudeIncrementCounterDisplay > maxValueAltitude ? maxValueAltitude : altitudeIncrementCounterDisplay;
			altitudeShowEndInfo = false;
			durationShowEndInfo = false;
			speedShowEndInfo = false;
		} else if (durationIncrementCounterDisplay < maxValueDuration) {
			durationIncrementCounter = durationIncrementCounter + incrementStep;
			durationIncrementCounterDisplay = durationIncrementCounterDisplay + durationStep;
			durationIncrementCounterDisplay = durationIncrementCounterDisplay > maxValueDuration ? maxValueDuration : durationIncrementCounterDisplay;
			durationShowEndInfo = false;
			speedShowEndInfo = false;
		} else if (speedIncrementCounterDisplay < maxValueSpeed) {
			speedIncrementCounter = speedIncrementCounter + incrementStep;
			speedIncrementCounterDisplay = speedIncrementCounterDisplay + speedStep;
			speedIncrementCounterDisplay = speedIncrementCounterDisplay > maxValueSpeed ? maxValueSpeed : speedIncrementCounterDisplay;
			speedShowEndInfo = false;
		}
		drawAchievementIncrementBar(sb, popupY + getHeightPercent(startPercent), distanceIncrementCounter, distanceIncrementCounterDisplay, distanceShowEndInfo, distanceCurrent > distanceMax,
				playerAttrs.getDistanceCash(), GameState.getLabel("distance"));
		drawAchievementIncrementBar(sb, popupY + getHeightPercent(startPercent - diffPercent), altitudeIncrementCounter, altitudeIncrementCounterDisplay, altitudeShowEndInfo,
				altitudeCurrent > altitudeMax, playerAttrs.getAltitudeCash(), GameState.getLabel("altitude"));
		drawAchievementIncrementBar(sb, popupY + getHeightPercent(startPercent - diffPercent * 2), durationIncrementCounter, durationIncrementCounterDisplay, durationShowEndInfo,
				durationCurrent > durationMax, playerAttrs.getDurationCash(), GameState.getLabel("duration"));
		drawAchievementIncrementBar(sb, popupY + getHeightPercent(startPercent - diffPercent * 3), speedIncrementCounter, speedIncrementCounterDisplay, speedShowEndInfo,
				speedCurrent > speedMax, playerAttrs.getSpeedCash(), GameState.getLabel("speed"));

		if (areAllInfosShown()) {
			int yOffsetForTotal = 0;
			if (playerAttrs.getGainedAchievementsReward() > 0) {
				drawAchievementsCash(sb,
						popupY + getHeightPercent((int) (startPercent - diffPercent * 3.7f)),
						getXIncrement(),
						getXIncrement() + incrementCounterBorder.getRegionWidth() + getWidthPercent(1));
				yOffsetForTotal = -10;
			}
			drawTotal(sb,
					popupY + getHeightPercent(startPercent - diffPercent * 4) + yOffsetForTotal,
					getXIncrement(),
					getXIncrement() + incrementCounterBorder.getRegionWidth() + getWidthPercent(1),
					playerAttrs.getTotalCash());
		}
	}

	public void showAllInfos() {
		incrementStep = 10;
	}

	public boolean areAllInfosShown() {
		return distanceShowEndInfo && altitudeShowEndInfo && durationShowEndInfo && speedShowEndInfo;
	}

	private void drawAchievementIncrementBar(SpriteBatch sb, float y, float currentCounter, float currentCounterToDisplay, boolean showEndInfo, boolean isNewRecord, int cashWon, String counterLabel) {
		int xValue = getXIncrement();
		int counterXValue = xValue + getWidthPercent(26);
		int labelXValue = xValue + getWidthPercent(1);
		int cashXValue = xValue + incrementCounterBorder.getRegionWidth() + getWidthPercent(1);
		sb.draw(incrementCounterEmpty, xValue, y);
		sb.draw(incrementCounterBorder, xValue - BORDER_MARGIN / 2, y - BORDER_MARGIN / 2);

		float barWidth = currentCounter;
		int maxCounter = getMaxCounter();
		while (barWidth > maxCounter) {
			barWidth--;
		}
		TextureRegion bar = new TextureRegion(isNewRecord ? incrementCounterRecordTexture : incrementCounterFullTexture, 0, 0, (int) barWidth, 20);
		sb.draw(bar, xValue, y);
		// counter label
		float textY = y;
		HUD.drawFont(sb, counterLabel, labelXValue, textY, font, HUD.getPopupFontScale(displayWidth, displayHeight));
		// counter
		HUD.drawFont(sb, setFrontSpace(BigDecimal.valueOf(Math.ceil(currentCounterToDisplay)).setScale(0).toString()), counterXValue, textY, font, HUD.getPopupFontScale(displayWidth, displayHeight));
		if (showEndInfo) {
			// counter cash
			HUD.drawFont(sb, Integer.toString(cashWon), cashXValue, textY, font, HUD.getPopupFontScale(displayWidth, displayHeight));
			// coin image
			drawCoins(sb, textY, cashXValue, cashWon);
		}
	}

	public static void drawImage(SpriteBatch sb, float x, float y, TextureRegion texture, float scale) {
		sb.draw(texture,
				x,
				y,
				0,
				0,
				texture.getRegionWidth(),
				texture.getRegionHeight(),
				scale,
				scale,
				0);
	}

	private void drawCurrentDay(SpriteBatch sb, float y, float x) {
		HUD.drawFont(sb, GameState.getLabel("day", gameInfoManager.getCurrentDay()), x, y, font, HUD.getPopupFontScale(displayWidth, displayHeight));
	}

	private void drawAchievementsCash(SpriteBatch sb, float y, float labelX, float cashX) {
		HUD.drawFont(sb, GameState.getLabel("achievements_earnings"), labelX, y, font, HUD.getPopupFontScale(displayWidth, displayHeight) * 0.9f);
		HUD.drawFont(sb, playerAttrs.getGainedAchievementsReward() + "", cashX, y, font, HUD.getPopupFontScale(displayWidth, displayHeight));
		drawCoins(sb, y, cashX, playerAttrs.getGainedAchievementsReward());
	}

	private void drawTotal(SpriteBatch sb, float y, float labelX, float cashX, int total) {
		HUD.drawFont(sb, GameState.getLabel("total_earnings"), labelX, y, font, HUD.getPopupFontScale(displayWidth, displayHeight));
		HUD.drawFont(sb, total + "", cashX, y, font, HUD.getPopupFontScale(displayWidth, displayHeight));
		drawCoins(sb, y, cashX, total);
	}

	private void drawCoins(SpriteBatch sb, float y, float x, int cash) {
		drawImage(sb, x + (9 * Integer.toString(cash).length()), y, coinTexture, B2DSprites.SPRITE_SCALE * 1.3f);
	}

	private int getXIncrement() {
		return (int) (popupX() + getWidthPercent(5));
	}

	public static Pixmap getPixmapRoundedRectangle(int width, int height, int radius, Color color) {
		Pixmap pixmap = new Pixmap(width, height, Format.RGBA8888);
		pixmap.setColor(color);
		pixmap.fillRectangle(0, radius, pixmap.getWidth(), pixmap.getHeight() - 2 * radius);
		pixmap.fillRectangle(radius, 0, pixmap.getWidth() - 2 * radius, pixmap.getHeight());
		pixmap.fillCircle(radius, radius, radius);
		pixmap.fillCircle(radius, pixmap.getHeight() - radius, radius);
		pixmap.fillCircle(pixmap.getWidth() - radius, radius, radius);
		pixmap.fillCircle(pixmap.getWidth() - radius, pixmap.getHeight() - radius, radius);
		return pixmap;
	}

	private String setFrontSpace(String val) {
		String result = val;
		int length = 6;
		while (result.length() < length) {
			result = " " + result;
		}
		return result;
	}

	private float popupX() {
		return (displayWidth - finishPopupTexture.getWidth()) / 2;
	}

	private int getMaxForValueCounter(float current, float max) {
		int value = (int) current;
		if (max > current) {
			float ratio = current / max;
			value = (int) (max * ratio);
		}
		return value;
	}

	private int getMaxForValue(float current, float max) {
		return (int) (current > max ? current : max);
	}

	private float getStepForValueCounter(float maxValue) {
		int totalSteps = getMaxCounter() / incrementStep;
		float step = maxValue / totalSteps;
		return step;
	}

	private int getHeightPercent(int percent) {
		return (int) Utils.getValueForPercent(displayHeight, percent);
	}

	private int getWidthPercent(int percent) {
		return (int) Utils.getValueForPercent(displayWidth, percent);
	}

	private int getMaxCounter() {
		int val = getWidthPercent(40);// 160px
		while (val % incrementStep != 0) {
			val = val - 1;
		}
		return val;
	}
}

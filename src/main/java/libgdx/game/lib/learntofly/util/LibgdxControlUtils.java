package libgdx.game.lib.learntofly.util;

import libgdx.game.lib.learntofly.handlers.GameStateManager;
import libgdx.game.lib.learntofly.main.Game;
import libgdx.game.lib.learntofly.states.GameState;
import libgdx.game.lib.learntofly.states.StageScreen;
import libgdx.game.lib.learntofly.storemanagers.PreferencesManager;
import libgdx.game.lib.learntofly.to.GameInfo;
import libgdx.game.lib.learntofly.to.menu.HeaderInfo;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

public class LibgdxControlUtils {

	private Skin skin;
	private float displayWidth;
	private float displayHeight;
	private PreferencesManager preferencesManager;
	private AssetManager assetManager;

	public LibgdxControlUtils(Skin skin, PreferencesManager preferencesManager, AssetManager assetManager) {
		this.skin = skin;
		displayWidth = Game.getWidth();
		displayHeight = Game.getHeight();
		this.preferencesManager = preferencesManager;
		this.assetManager = assetManager;
	}

	public void playSound(Sound sound) {
		if (preferencesManager.isSoundOn()) {
			sound.play();
		}
	}

	public void loopSound(Sound sound) {
		if (preferencesManager.isSoundOn()) {
			sound.loop();
		}
	}

	public float getHeaderHeight() {
		return Utils.getValueForDisplayHeightPercent(10);
	}

	public float getSidePadding() {
		return Utils.getValueForPercent(displayWidth, 3);
	}

	public HeaderInfo createHeaderTable(int stage, int day, int cash, GameStateManager gameStateManager, GameInfo gameInfo) {
		Table headerTable = new Table(skin);
		headerTable.setBackground(LibgdxControlUtils.createColorTexture(Color.valueOf("ffffff"), 0.6f));
		Label stageLabel = new Label(getStageName(stage), skin);
		stageLabel.setFontScale(getFontScale());
		Label currentDayLabel = new Label(GameState.getLabel("day", day), skin);
		currentDayLabel.setFontScale(getFontScale());
		Label cashLabel = new Label(cash + "", skin);
		cashLabel.setFontScale(getFontScale());
		headerTable.add(currentDayLabel);
		headerTable.add().width(displayWidth / 4);
		headerTable.add(stageLabel);
		headerTable.add().width(displayWidth / 4);
		headerTable.add(cashLabel);
		headerTable.add(new Image(skin, "coin")).height(getHeaderHeight()).width(getHeaderHeight());
		stageLabelClick(stageLabel, gameStateManager, gameInfo);

		return new HeaderInfo(headerTable, currentDayLabel, stageLabel, cashLabel);
	}

	public float getFontScale() {
		return getHeightDisplayRatio();
	}

	public Table createAllScreenTable(Integer stageNr, boolean withStageBackground) {
		Table allTable = new Table(skin);
		allTable.setBounds(0, 0, displayWidth, displayHeight);
		if (withStageBackground) {
			allTable.setBackground(Utils.getDrawable(assetManager, Resource.getMenuStageBackgroundResource(stageNr)));
		}
		return allTable;
	}

	public Table createAllScreenTable(Integer stageNr) {
		return createAllScreenTable(stageNr, true);
	}

	public Table createContentTable() {
		Table allTable = new Table(skin);
		allTable.setBounds(0, 0, displayWidth, displayHeight - getHeaderHeight());
		return allTable;
	}

	private void stageLabelClick(Label stageLabel, final GameStateManager gameStateManager, final GameInfo gameInfo) {
		stageLabel.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				gameStateManager.setStageState(gameInfo, StageScreen.StageScreenType.FROM_LABEL_CLICK);
			}
		});
	}

	public static void refreshHeaderInfo(HeaderInfo headerInfo, int day, int cash) {
		headerInfo.getCurrentDay().setText(GameState.getLabel("day", day));
		headerInfo.getCash().setText(cash + "");
	}

	public static TextureRegionDrawable createColorTexture(Color color, float alpha) {
		Pixmap pm = new Pixmap(1, 1, Format.RGBA4444);
		pm.setColor(color.r, color.g, color.b, alpha);
		pm.fill();
		return new TextureRegionDrawable(new TextureRegion(new Texture(pm)));
	}

	public static String getStageName(int stageNr) {
		return GameState.getLabel("stage" + stageNr);
	}

	public float getHeightDisplayRatio() {
		float f = displayHeight / displayWidth > Utils.STANDARD_RATIO ? Game.getWidth() / Game.getWidth() : Game.getHeight() / Game.getHeight();
		return f / 2;
	}

	public float getWidthDisplayRatio() {
		float f = (displayWidth / displayHeight) / (Game.getWidth() / Game.getHeight());
		return f / 2;
	}
}

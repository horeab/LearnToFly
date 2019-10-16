package libgdx.game.lib.learntofly.states;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;

import java.util.ArrayList;
import java.util.List;

import libgdx.game.lib.learntofly.achievements.Achievement;
import libgdx.game.lib.learntofly.handlers.GameStateManager;
import libgdx.game.lib.learntofly.to.GameInfo;
import libgdx.game.lib.learntofly.util.LibgdxControlUtils;
import libgdx.game.lib.learntofly.util.Resource;
import libgdx.game.lib.learntofly.util.Utils;

public class StageScreen extends GameState {

	public enum StageScreenType {
		START_GAME,
		FROM_LABEL_CLICK,
		IN_GAME
	}

	public static final int NR_OF_STAGES = 4;

	StageScreenType stageScreenType;

	public StageScreen(GameStateManager gameStateManager, GameInfo gameInfo, StageScreenType stageScreenType) {
		super(gameStateManager, gameInfo);

		this.stageScreenType = stageScreenType;

		Table allTable = libgdxControlUtils.createAllScreenTable(gameInfo.getSelectedStage());

		Table btnsTable = new Table(skin);

		allTable
				.add(btnsTable)
				.width(displayWidth)
				.height(getBtnsTableHeight())
				.row();
		for (ImageButton stage : createStageBtns()) {
			btnsTable.add(stage)
					.width(getBtnSide())
					.height(getBtnHeight())
					.pad(displayWidth / 100f);
		}

		addFinishTable(allTable);
		stage.addActor(allTable);
	}

	private float getBtnHeight() {
		return getBtnSide() + Utils.getValueForPercent(displayWidth, 5);
	}

	private float getBtnSide() {
		return Utils.getValueForPercent(displayWidth, 22);
	}

	private void addFinishTable(Table allTable) {
		Table finishTable = new Table(skin);
		Image finishImage = new Image(Utils.getDrawable( Resource.finish));
		int finishTablePadTop = 20;
		finishTable
				.add(finishImage)
				.width(displayWidth / 7)
				.padTop(Utils.getValueForPercent(displayHeight, finishTablePadTop));
		Table labelTable = createLabelTable(
				achievementsManager.neededAchievementsToUnlock(5, Achievement.DISTANCE_ACHIEVEMENT),
				achievementsManager.neededAchievementsToUnlock(5, Achievement.ALTITUDE_ACHIEVEMENT),
				achievementsManager.neededAchievementsToUnlock(5, Achievement.DURATION_ACHIEVEMENT),
				achievementsManager.neededAchievementsToUnlock(5, Achievement.SPEED_ACHIEVEMENT)
				);
		finishTable
				.add(labelTable)
				.padTop(Utils.getValueForPercent(displayHeight, finishTablePadTop));
		allTable
				.add(finishTable)
				.width(displayWidth)
				.height(getBtnsTableHeight())
				.row();
	}

	private List<ImageButton> createStageBtns() {
		List<ImageButton> stageBtns = new ArrayList<ImageButton>();
		for (int i = 1; i <= NR_OF_STAGES; i++) {
			stageBtns.add(createStageBtn(i));
		}
		return stageBtns;
	}

	private ImageButton createStageBtn(final int stageNr) {
		ImageButton button = new ImageButton(skin);
		Table backgroundTable = new Table(skin);
		backgroundTable.setBackground(Utils.getDrawable( Resource.getMenuStageResource(stageNr)));
		Table stageImgTable = new Table(skin);
		button.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				stageBtnClick(stageNr);
			}
		});
		backgroundTable.add(stageImgTable).width(getBtnSide()).height(getBtnHeight());
		button.add(backgroundTable).fill();
		if (!achievementsManager.isStageNrUnlocked(stageNr)) {
			Image lockImage = new Image(Utils.getDrawable(
					Resource.lock));
			lockImage.scaleBy(-0.5f);
			stageImgTable.add(lockImage)
					.padLeft(displayWidth / 10)
					.padTop(Utils.getValueForPercent(displayWidth, -8))
					.padBottom(displayHeight / 40).row();
			Table labelTable = createLabelTable(
					achievementsManager.neededAchievementsToUnlock(stageNr, Achievement.DISTANCE_ACHIEVEMENT),
					achievementsManager.neededAchievementsToUnlock(stageNr, Achievement.ALTITUDE_ACHIEVEMENT),
					achievementsManager.neededAchievementsToUnlock(stageNr, Achievement.DURATION_ACHIEVEMENT),
					achievementsManager.neededAchievementsToUnlock(stageNr, Achievement.SPEED_ACHIEVEMENT)
					);
			stageImgTable.add(labelTable).width(getBtnSide()).padLeft(displayWidth / 10);
			button.setTouchable(Touchable.disabled);
			stageImgTable.setBackground(LibgdxControlUtils.createColorTexture(Color.WHITE,
					0.8f));
		} else {
			Label label = c.label(LibgdxControlUtils.getStageName(stageNr));
			label.setFontScale(libgdxControlUtils.getFontScale());
			Table labelBackgroundTable = new Table(skin);
			labelBackgroundTable.setBackground(LibgdxControlUtils.createColorTexture(Color.WHITE, 0.6f));
			labelBackgroundTable.add(label);
			stageImgTable.add(labelBackgroundTable).padTop(displayHeight / 10).width(getBtnSide()).row();
			if (gameInfo.getSelectedStage() == stageNr) {
				stageImgTable.setBackground(Utils.getDrawable( Resource.stage_play));
				stageImgTable.add().height(Utils.getValueForDisplayHeightPercent(43));
			}
		}
		return button;
	}

	private Table createLabelTable(int distanceAch, int altitudeAch, int durationAch, int speedAch) {
		Table achTable = new Table(skin);
		if (distanceAch > 0) {
			addToAchTable(distanceAch, Utils.getDrawable( Resource.distance_achievement), achTable);
		}
		if (altitudeAch > 0) {
			addToAchTable(altitudeAch, Utils.getDrawable( Resource.altitude_achievement), achTable);
		}
		if (durationAch > 0) {
			addToAchTable(durationAch, Utils.getDrawable( Resource.duration_achievement), achTable);
		}
		if (speedAch > 0) {
			addToAchTable(speedAch, Utils.getDrawable( Resource.speed_achievement), achTable);
		}
		return achTable;
	}

	private void addToAchTable(int nrValue, Drawable achImg, Table achTable) {
		Image achievImg = new Image(achImg);
		float imgScale = 0.25f;
		achievImg.setScale(imgScale);
		Label label = c.label("  " + nrValue);
		float fontScale = libgdxControlUtils.getFontScale() / 1.4f;
		label.setFontScale(fontScale);
		Label xlabel = c.label("x");
		xlabel.setFontScale(fontScale);
		int padTop = 1;
		achTable
				.add(label)
				.padTop(Utils.getValueForPercent(displayHeight, padTop));
		achTable
				.add(xlabel)
				.padTop(Utils.getValueForPercent(displayHeight, padTop));
		achTable
				.add(achievImg)
				.padTop(-Utils.getValueForPercent(displayHeight, 21 - padTop))
				.row();
	}

	void stageBtnClick(int stageNr) {
		if (stageScreenType == StageScreenType.IN_GAME
				|| stageScreenType == StageScreenType.FROM_LABEL_CLICK) {
			gameInfo.setSelectedStage(stageNr);
			if (gameInfoManager.displayRocketTutorial()) {
				gameStateManager.setTutorialState(gameInfo, TutorialScreen.TutorialScreenType.ROCKET);
			} else {
				gameStateManager.setPlayState(gameInfo);
			}
		} else if (stageScreenType == StageScreenType.START_GAME) {
			gameStateManager.setTutorialState(gameInfo, TutorialScreen.TutorialScreenType.START_GAME);
		}
		gameInfo = gameInfoManager.setSelectedStage(stageNr, gameInfo);
	}

	public StageScreenType getStageScreenType() {
		return stageScreenType;
	}

	private float getBtnsTableHeight() {
		return Utils.getValueForPercent(displayHeight, 33);
	}
}

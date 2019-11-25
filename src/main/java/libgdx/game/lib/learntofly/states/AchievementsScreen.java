package libgdx.game.lib.learntofly.states;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import libgdx.controls.label.MyWrappedLabel;
import libgdx.game.lib.learntofly.achievements.Achievement;
import libgdx.game.lib.learntofly.achievements.AltitudeAchievement;
import libgdx.game.lib.learntofly.achievements.DistanceAchievement;
import libgdx.game.lib.learntofly.achievements.DurationAchievement;
import libgdx.game.lib.learntofly.achievements.SpeedAchievement;
import libgdx.game.lib.learntofly.handlers.GameStateManager;
import libgdx.game.lib.learntofly.to.GameInfo;
import libgdx.game.lib.learntofly.to.menu.HeaderInfo;
import libgdx.game.lib.learntofly.util.LearnToFlyGameLabel;
import libgdx.game.lib.learntofly.util.Resource;
import libgdx.game.lib.learntofly.util.Utils;
import libgdx.resources.dimen.MainDimen;
import libgdx.utils.ScreenDimensionsManager;

import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane.ScrollPaneStyle;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.utils.Align;

public class AchievementsScreen extends GameState {

    private int scrollPanePositionInit = 0;
    private ScrollPane scrollPane;

    public AchievementsScreen(GameStateManager gameStateManager, GameInfo gameInfo) {
        super(gameStateManager, gameInfo);
    }

    @Override
    public void buildStage() {
        Table allTable = libgdxControlUtils.createAllScreenTable(gameInfo.getSelectedStage());
        allTable.setFillParent(true);
        Table contentTable = libgdxControlUtils.createContentTable();

        HeaderInfo headerInfo = libgdxControlUtils.createHeaderTable(gameInfo.getSelectedStage(), gameInfo.getCurrentDay(), gameInfo.getCash(), gameStateManager, gameInfo);
        allTable.add(headerInfo.getHeaderTable()).height(getHeaderHeight()).width(ScreenDimensionsManager.getScreenWidth());
        allTable.row();
        allTable.add(contentTable).height(ScreenDimensionsManager.getScreenHeight() - getHeaderHeight()).width(ScreenDimensionsManager.getScreenWidth());

        scrollPane = new ScrollPane(createAchievementsTable(), new ScrollPaneStyle());
        contentTable.add(scrollPane).width(ScreenDimensionsManager.getScreenWidth() / 1.3f);

        contentTable.add(backButton).align(Align.top).padTop(getAchievementsPadding()).width(getAchievementHeight()).height(getAchievementHeight());
        addActor(allTable);
    }

    @Override
    public void onBackKeyPress() {
        gameStateManager.setUpgradeShopState(gameInfo);
    }

    private List<Achievement> getAchievementsForLevel(int level) {
        List<Achievement> allAchievements = new ArrayList<Achievement>();
        allAchievements.addAll(Arrays.<Achievement>asList(DistanceAchievement.values()));
        allAchievements.addAll(Arrays.<Achievement>asList(AltitudeAchievement.values()));
        allAchievements.addAll(Arrays.<Achievement>asList(DurationAchievement.values()));
        allAchievements.addAll(Arrays.<Achievement>asList(SpeedAchievement.values()));
        List<Achievement> achievementsForLevel = new ArrayList<Achievement>();
        for (Achievement achievement : allAchievements) {
            if (achievement.toString().equals("L" + level)) {
                achievementsForLevel.add(achievement);
            }
        }
        return achievementsForLevel;
    }

    private Table createAchievementsTable() {
        Table allContainer = new Table(skin);
        float padding = getAchievementsPadding();
        allContainer.row().padBottom(padding).padTop(padding);
        List<Achievement> allAchievements = getAllAchievements();

        for (Achievement achievement : allAchievements) {
            allContainer.add(createAchievement(achievement))
                    .height(getAchievementHeight())
                    .width(getAchievementWidth());
            allContainer.row().padBottom(padding);
        }
        return allContainer;
    }

    private List<Achievement> getAllAchievements() {
        List<Achievement> allAchievements = new ArrayList<Achievement>();
        for (int i = 0; i < AltitudeAchievement.values().length; i++) {
            allAchievements.addAll(getAchievementsForLevel(i));
        }
        return allAchievements;
    }

    private int getFirstUnachievedAchievement() {
        int i = 0;
        for (Achievement achievement : getAllAchievements()) {
            if (!achievementsManager.isAchievementGained(achievement)) {
                break;
            }
            i++;
        }
        return i;
    }

    private Table createAchievement(Achievement achievement) {
        Table infoContainer = new Table(skin);
        MyWrappedLabel achievementLabel = c.label(achievement.getAchievementLabel(achievement.getValue()));
        achievementLabel.setAlignment(Align.center);
        achievementLabel.setFontScale(libgdxControlUtils.getFontScale());
        MyWrappedLabel rewardLabel = c.label(LearnToFlyGameLabel.l_reward.getText(achievement.getReward()));
        Table achievementLabelTable = new Table(skin);
        achievementLabelTable.add(achievementLabel).width(getAchievementInfoWidth()).padTop(getAchievementsPadding() / 2);
        Table rewardTable = new Table(skin);
        Image coinImg = c.image("coin");
        rewardTable.add(rewardLabel);
        float dimen = MainDimen.horizontal_general_margin.getDimen();
        float imgDimen = dimen * 5;
        rewardTable.add(coinImg).width(imgDimen).height(imgDimen).padTop(-dimen * 2.7f);

        infoContainer.add(achievementLabelTable).row();
        infoContainer.add(rewardTable);

        Table achievementContainer = new Table(skin);
        Drawable achDrawable = Utils.getDrawable(getResourceForAchType(achievement.getAchievementId()));
        Table achImg = new Table(skin);
        achImg.setBackground(achDrawable);
        achievementContainer.add(achImg)
                .width(getAchievementImgWidth() / 1.5f)
                .height(getAchievementImgWidth() / 1.5f);
        achievementContainer.add(infoContainer)
                .width(getAchievementInfoWidth())
                .height(getAchievementHeight());

        Drawable buttonPressedDrawable = skin.getDrawable("green_normal");
        if (!achievementsManager.isAchievementGained(achievement)) {
            buttonPressedDrawable = skin.getDrawable("grey_normal");
        }
        achievementContainer.setBackground(buttonPressedDrawable);

        return achievementContainer;
    }

    private Resource getResourceForAchType(String type) {
        Resource resource = null;
        if (type == Achievement.ALTITUDE_ACHIEVEMENT) {
            resource = Resource.altitude_achievement;
        } else if (type == Achievement.DISTANCE_ACHIEVEMENT) {
            resource = Resource.distance_achievement;
        } else if (type == Achievement.DURATION_ACHIEVEMENT) {
            resource = Resource.duration_achievement;
        } else if (type == Achievement.SPEED_ACHIEVEMENT) {
            resource = Resource.speed_achievement;
        }
        return resource;
    }

    private float getAchievementHeight() {
        return ScreenDimensionsManager.getScreenHeightValue(25);
    }

    private float getAchievementWidth() {
        return ScreenDimensionsManager.getScreenWidthValue(70);
    }

    private float getAchievementInfoWidth() {
        return Utils.getValueForPercent(getAchievementWidth(), 80);
    }

    private float getAchievementImgWidth() {
        return getAchievementWidth() - getAchievementInfoWidth();
    }

    private float getAchievementsPadding() {
        return getHeaderHeight() * 0.8f;
    }

    private float getHeaderHeight() {
        return libgdxControlUtils.getHeaderHeight();
    }

    public void update(float dt) {
        // scrollPanePositionInit needs to be used otherwise the scrollTo wont
        // work
        if (scrollPanePositionInit < 2) {
            int totalNrOfAchievements = AltitudeAchievement.values().length + DistanceAchievement.values().length + DurationAchievement.values().length + SpeedAchievement.values().length;
            int firstUnachievedAchievement = getFirstUnachievedAchievement();
            float achHeight = getAchievementHeight() + getAchievementsPadding();
            float totalHeight = totalNrOfAchievements * achHeight;
            float firstUnachievedAchievementHeight = firstUnachievedAchievement * achHeight;
            float y = totalHeight - firstUnachievedAchievementHeight - achHeight * 2;
            System.out.println(y);
            scrollPane.scrollTo(0, y, ScreenDimensionsManager.getScreenWidth(), ScreenDimensionsManager.getScreenHeight());
            scrollPanePositionInit++;
        }
    }
}

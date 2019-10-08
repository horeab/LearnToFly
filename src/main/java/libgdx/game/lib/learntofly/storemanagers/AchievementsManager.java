package libgdx.game.lib.learntofly.storemanagers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

import libgdx.game.lib.learntofly.achievements.Achievement;
import libgdx.game.lib.learntofly.achievements.AltitudeAchievement;
import libgdx.game.lib.learntofly.achievements.DistanceAchievement;
import libgdx.game.lib.learntofly.achievements.DurationAchievement;
import libgdx.game.lib.learntofly.achievements.SpeedAchievement;
import libgdx.game.lib.learntofly.states.StageScreen;

public class AchievementsManager {

	private Preferences prefs;

	public AchievementsManager() {
		prefs = Gdx.app.getPreferences("achievements");
		resetAll();
	}

	public List<Achievement> updateAchievementsAndGiveBackNewGainedAchievements(int distance, int altitude, int duration, int speed) {
		List<Achievement> list = new ArrayList<Achievement>();
		list.addAll(findGainedAchievements(altitude, Achievement.ALTITUDE_ACHIEVEMENT));
		list.addAll(findGainedAchievements(distance, Achievement.DISTANCE_ACHIEVEMENT));
		list.addAll(findGainedAchievements(duration, Achievement.DURATION_ACHIEVEMENT));
		list.addAll(findGainedAchievements(speed, Achievement.SPEED_ACHIEVEMENT));
		return list;
	}

	public boolean isAchievementGained(Achievement achievement) {
		return getAchievement(achievement) != null;
	}

	public boolean isStageNrUnlocked(int stageNr) {
		boolean isStageNrUnlocked = true;
		isStageNrUnlocked = neededAchievementsToUnlock(stageNr, Achievement.ALTITUDE_ACHIEVEMENT) == 0;
		isStageNrUnlocked &= neededAchievementsToUnlock(stageNr, Achievement.DISTANCE_ACHIEVEMENT) == 0;
		isStageNrUnlocked &= neededAchievementsToUnlock(stageNr, Achievement.DURATION_ACHIEVEMENT) == 0;
		isStageNrUnlocked &= neededAchievementsToUnlock(stageNr, Achievement.SPEED_ACHIEVEMENT) == 0;

		return isStageNrUnlocked;
	}

	public Integer stageJustUnlocked(String achievementId) {
		Integer stageJustUnlocked = null;
		int nrOfAchievementsGained = getNrOfAchievementsGained(getAllAchievementValuesForId(achievementId));
		boolean isStageJustUnlocked = false;
		if (nrOfAchievementsGained % StageScreen.NR_OF_STAGES == 0) {
			int stageThatCouldBeUnlocked = nrOfAchievementsGained / StageScreen.NR_OF_STAGES + 1;
			isStageJustUnlocked = isStageNrUnlocked(stageThatCouldBeUnlocked);
			stageJustUnlocked = isStageJustUnlocked ? stageThatCouldBeUnlocked : null;
		}
		return stageJustUnlocked;
	}

	public int neededAchievementsToUnlock(int stageNr, String achievementId) {
		int expectedNrOfAchievements = (stageNr - 1) * StageScreen.NR_OF_STAGES;
		List<Achievement> achievementsToProcess = getAllAchievementValuesForId(achievementId);
		int remainingAchToGain = expectedNrOfAchievements - getNrOfAchievementsGained(achievementsToProcess);
		return remainingAchToGain > 0 ? remainingAchToGain : 0;
	}

	private List<Achievement> getAllAchievementValuesForId(String achievementId) {
		if (Achievement.ALTITUDE_ACHIEVEMENT == achievementId) {
			return new ArrayList<Achievement>(Arrays.asList(AltitudeAchievement.values()));
		} else if (Achievement.DISTANCE_ACHIEVEMENT == achievementId) {
			return new ArrayList<Achievement>(Arrays.asList(DistanceAchievement.values()));
		} else if (Achievement.DURATION_ACHIEVEMENT == achievementId) {
			return new ArrayList<Achievement>(Arrays.asList(DurationAchievement.values()));
		} else if (Achievement.SPEED_ACHIEVEMENT == achievementId) {
			return new ArrayList<Achievement>(Arrays.asList(SpeedAchievement.values()));
		}
		return null;
	}

	private void putAchievement(Achievement achievement) {
		String formAchievementId = formAchievementId(achievement);
		prefs.putString(formAchievementId, formAchievementId);
		prefs.flush();
	}

	private String getAchievement(Achievement achievement) {
		String ach = prefs.getString(formAchievementId(achievement), null);
		return ach != null && !ach.equals("") ? ach : null;
	}

	private int getNrOfAchievementsGained(List<Achievement> values) {
		int nrOfAchievementsGained = 0;
		for (Achievement achievement : values) {
			if (isAchievementGained(achievement)) {
				nrOfAchievementsGained++;
			}
		}
		return nrOfAchievementsGained;
	}

	private List<Achievement> findGainedAchievements(int value, String achievementId) {
		List<Achievement> list = new ArrayList<Achievement>();
		for (Achievement achievement : getAllAchievementValuesForId(achievementId)) {
			if (achievement.getValue() <= value) {
				if (!isAchievementGained(achievement)) {
					putAchievement(achievement);
					list.add(achievement);
				}
			}
		}
		return list;
	}

	private String formAchievementId(Achievement achievement) {
		return achievement.getAchievementId() + achievement.toString();
	}

	public void resetAll() {
		resetAchievements(AltitudeAchievement.values());
		resetAchievements(DistanceAchievement.values());
		resetAchievements(DurationAchievement.values());
		resetAchievements(SpeedAchievement.values());
	}

	private void resetAchievements(Achievement[] values) {
		for (Achievement achievement : values) {
			String formAchievementId = formAchievementId(achievement);
			prefs.putString(formAchievementId, "");
			prefs.flush();
		}
	}

}

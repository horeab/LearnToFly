package libgdx.game.lib.learntofly.achievements;


import libgdx.game.lib.learntofly.states.GameState;
import libgdx.game.lib.learntofly.util.LearnToFlyGameLabel;

public enum DurationAchievement implements Achievement {

	L0(13, R_L0),
	L1(18, R_L1),
	L2(23, R_L2),
	L3(26, R_L3),
	// /////////
	L4(38, R_L4),
	L5(55, R_L5),
	L6(65, R_L6),
	L7(70, R_L7),
	// /////////
	L8(75, R_L8),
	L9(80, R_L9),
	L10(85, R_L10),
	L11(95, R_L11),
	// /////////
	L12(105, R_L12),
	L13(110, R_L13),
	L14(118, R_L14),
	L15(124, R_L15);

	private DurationAchievement(int value, int reward) {
		this.value = value;
		this.reward = reward;
	}

	private int value;
	private int reward;

	@Override
	public String getAchievementLabel(int value) {
		return LearnToFlyGameLabel.l_duration_achievement.getText( value);
	}

	@Override
	public int getValue() {
		return value;
	}

	@Override
	public int getReward() {
		return reward;
	}

	@Override
	public String getAchievementId() {
		return DURATION_ACHIEVEMENT;
	}

}

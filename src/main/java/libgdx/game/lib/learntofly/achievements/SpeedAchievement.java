package libgdx.game.lib.learntofly.achievements;


import libgdx.game.lib.learntofly.states.GameState;
import libgdx.game.lib.learntofly.util.LearnToFlyGameLabel;

public enum SpeedAchievement implements Achievement {

	L0(21, R_L0),
	L1(38, R_L1),
	L2(51, R_L2),
	L3(67, R_L3),
	// /////////
	L4(80, R_L4),
	L5(100, R_L5),
	L6(125, R_L6),
	L7(150, R_L7),
	// /////////
	L8(155, R_L8),
	L9(159, R_L9),
	L10(165, R_L10),
	L11(174, R_L11),
	// /////////
	L12(185, R_L12),
	L13(200, R_L13),
	L14(210, R_L14),
	L15(220, R_L15);

	private SpeedAchievement(int value, int reward) {
		this.value = value;
		this.reward = reward;
	}

	private int value;
	private int reward;

	@Override
	public String getAchievementLabel(int value) {
		return LearnToFlyGameLabel.l_speed_achievement.getText( value);
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
		return SPEED_ACHIEVEMENT;
	}

}

package libgdx.game.lib.learntofly.achievements;


import libgdx.game.lib.learntofly.states.GameState;

public enum DistanceAchievement implements Achievement {

	L0(15, R_L0),
	L1(25, R_L1),
	L2(43, R_L2),
	L3(53, R_L3),
	// /////////
	L4(65, R_L4),
	L5(150, R_L5),
	L6(280, R_L6),
	L7(330, R_L7),
	// /////////
	L8(370, R_L8),
	L9(400, R_L9),
	L10(430, R_L10),
	L11(465, R_L11),
	// /////////
	L12(550, R_L12),
	L13(600, R_L13),
	L14(680, R_L14),
	L15(740, R_L15);

	private DistanceAchievement(int value, int reward) {
		this.value = value;
		this.reward = reward;
	}

	private int value;
	private int reward;

	@Override
	public String getAchievementLabel(int value) {
		return GameState.getLabel("distance_achievement", value);
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
		return DISTANCE_ACHIEVEMENT;
	}

}

package libgdx.game.lib.learntofly.achievements;

public interface Achievement {

	// /REWARDS///
	static final int R_L0 = 100;
	static final int R_L1 = 150;
	static final int R_L2 = 200;
	static final int R_L3 = 450;
	// /////////
	static final int R_L4 = 500;
	static final int R_L5 = 700;
	static final int R_L6 = 1100;
	static final int R_L7 = 1300;
	// /////////
	static final int R_L8 = 1600;
	static final int R_L9 = 2000;
	static final int R_L10 = 2400;
	static final int R_L11 = 3900;
	// /////////
	static final int R_L12 = 4500;
	static final int R_L13 = 5000;
	static final int R_L14 = 5500;
	static final int R_L15 = 7000;

	static final String ALTITUDE_ACHIEVEMENT = "ALTITUDE_ACHIEVEMENT";
	static final String DISTANCE_ACHIEVEMENT = "DISTANCE_ACHIEVEMENT";
	static final String DURATION_ACHIEVEMENT = "DURATION_ACHIEVEMENT";
	static final String SPEED_ACHIEVEMENT = "SPEED_ACHIEVEMENT";

	public String getAchievementLabel(int value);

	public int getValue();

	public int getReward();

	public String getAchievementId();
}

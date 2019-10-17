package libgdx.game.lib.learntofly.levels;


import libgdx.game.lib.learntofly.states.GameState;
import libgdx.game.lib.learntofly.util.LearnToFlyGameLabel;

public enum RampLevel {

	// @formatter:off
	NO_LEVEL(0,  3.5f, 2,    1,    0),
	
	LEVEL1(  1,  4,    2,    2,    150),
	LEVEL2(  2,  4.5f, 2.5f, 3,    250),
	LEVEL3(  3,  5,    3,    4,    350),
	
	LEVEL4(  4,  5.5f, 3.4f, 4,    550),
	LEVEL5(  5,  5.8f, 3.7f, 4,    700),
	LEVEL6(  6,  6.1f, 4.0f, 4,    1100),
	
	LEVEL7(  7,  6.6f, 4.5f, 5,    2000),
	LEVEL8(  8,  7f,   4.9f, 5,    3000),
	LEVEL9(  9,  7.3f, 5.3f, 5,    4000),
	
	LEVEL10( 10, 7.7f, 5.5f, 6,    5000),
	LEVEL11( 11, 8,    5.7f, 6,    7000),
	LEVEL12( 12, 8.3f, 6,    7,    9000);
	// @formatter:on

	public static int SHOP_ID = 1;

	private int level;
	private float downwardsRampLevel;
	private float upwardsRampLevel;
	private int baseHeight;
	private int price;

	private RampLevel(int level, float downwardsRampLevel, float upwardsRampLevel, int baseHeight, int price) {
		this.price = price;
		this.level = level;
		this.downwardsRampLevel = downwardsRampLevel;
		this.upwardsRampLevel = upwardsRampLevel;
		this.baseHeight = baseHeight;
	}

	public static RampLevel getRamp(int level) {
		for (RampLevel rampLevel : values()) {
			if (rampLevel.level == level) {
				return rampLevel;
			}
		}
		if (level > LEVEL12.level) {
			return LEVEL12;
		}
		return NO_LEVEL;
	}

	public int getLevel() {
		return level;
	}

	public float getDownwardsRampLevel() {
		return downwardsRampLevel;
	}

	public float getUpwardsRampLevel() {
		return upwardsRampLevel;
	}

	public int getBaseHeight() {
		return baseHeight;
	}

	public int getPrice() {
		return price;
	}

	public static String getShopLabel() {
		return LearnToFlyGameLabel.l_ramp.getText();
	}

}

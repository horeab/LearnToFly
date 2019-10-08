package libgdx.game.lib.learntofly.levels;


import libgdx.game.lib.learntofly.states.GameState;
import libgdx.game.lib.learntofly.util.Utils;

public enum RocketLevel implements ShopLevel {

	NO_LEVEL(0, GameState.getLabel("rocket"), 1.3f, 50, 0),

	LEVEL1(1, GameState.getLabel("rocket1"), 1.3f, 80, 300),
	LEVEL2(2, GameState.getLabel("rocket2"), 1.5f, 100, 750),

	LEVEL3(3, GameState.getLabel("rocket3"), 1.8f, 140, 2500),
	LEVEL4(4, GameState.getLabel("rocket4"), 2.2f, 130, 7000),

	LEVEL5(5, GameState.getLabel("rocket5"), 2.9f, 140, 9500),

	LEVEL6(6, GameState.getLabel("rocket6"), 3.5f, 170, 16000);

	public static int SHOP_ID = 5;

	private int level;
	private String levelName;
	private float thrustLevel;
	private int topSpeedLevel;
	private int price;

	private RocketLevel(int level, String levelName, float thrustLevel, int topSpeedLevel, int price) {
		this.price = price;
		this.level = level;
		this.levelName = levelName;
		this.thrustLevel = thrustLevel;
		this.topSpeedLevel = topSpeedLevel;
	}

	@Override
	public ShopLevel getItem(int level) {
		for (RocketLevel rocketLevel : values()) {
			if (rocketLevel.level == level) {
				return rocketLevel;
			}
		}
		if (level > LEVEL6.level) {
			return LEVEL6;
		}
		return NO_LEVEL;
	}

	@Override
	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public float getThrustLevel() {
		return thrustLevel * ShopLevel.BASE;
	}

	public int getTopSpeedLevel() {
		return (int) (topSpeedLevel * ShopLevel.BASE);
	}

	@Override
	public String getLevelName() {
		return levelName;
	}

	@Override
	public String[] getAttrName() {
		return new String[] {
				GameState.getLabel("rocket_attr1"),
				GameState.getLabel("rocket_attr2") };
	}

	@Override
	public int[] getAttrLevel() {
		return new int[] {
				Utils.getLevelForAttr(LEVEL1.thrustLevel, LEVEL6.thrustLevel, this.thrustLevel),
				Utils.getLevelForAttr(LEVEL1.topSpeedLevel, LEVEL6.topSpeedLevel, this.topSpeedLevel) };
	}

	@Override
	public int getShopId() {
		return SHOP_ID;
	}

	@Override
	public int getPrice() {
		return price;
	}

}

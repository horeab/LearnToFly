package libgdx.game.lib.learntofly.levels;

import libgdx.game.lib.learntofly.states.GameState;
import libgdx.game.lib.learntofly.util.Utils;

public enum SledLevel implements ShopLevel {

	NO_LEVEL(0, GameState.getLabel("sled"), 20, 0, 0),

	LEVEL1(1, GameState.getLabel("sled1"), 80, 20, 125),
	LEVEL2(2, GameState.getLabel("sled2"), 140, 25, 180),

	LEVEL3(3, GameState.getLabel("sled3"), 230, 25, 850),
	LEVEL4(4, GameState.getLabel("sled4"), 340, 70, 2500),

	LEVEL5(5, GameState.getLabel("sled5"), 420, 100, 5000),

	LEVEL6(6, GameState.getLabel("sled6"), 500, 150, 10000);

	public static int SHOP_ID = 3;

	private int level;
	private String levelName;
	private int speedLevel = 0;
	private int takeOffLevel = 0;
	private int price;

	private SledLevel(int level, String levelName, int speedLevel, int takeOffLevel, int price) {
		this.price = price;
		this.level = level;
		this.levelName = levelName;
		this.speedLevel = speedLevel;
		this.takeOffLevel = takeOffLevel;
	}

	@Override
	public ShopLevel getItem(int level) {
		for (SledLevel sledLevel : values()) {
			if (sledLevel.level == level) {
				return sledLevel;
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

	public int getSpeedLevel() {
		return (int) (speedLevel * ShopLevel.BASE);
	}

	public void setSpeedLevel(int speedLevel) {
		this.speedLevel = speedLevel;
	}

	public int getTakeOffLevel() {
		return (int) (takeOffLevel * ShopLevel.BASE);
	}

	public void setTakeOffLevel(int takeOffLevel) {
		this.takeOffLevel = takeOffLevel;
	}

	@Override
	public String getLevelName() {
		return levelName;
	}

	@Override
	public String[] getAttrName() {
		return new String[] {
				GameState.getLabel("sled_attr1"),
				GameState.getLabel("sled_attr2") };
	}

	@Override
	public int[] getAttrLevel() {
		return new int[] {
				Utils.getLevelForAttr(LEVEL1.speedLevel, LEVEL6.speedLevel, this.speedLevel),
				Utils.getLevelForAttr(LEVEL1.takeOffLevel, LEVEL6.takeOffLevel, this.takeOffLevel) };
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

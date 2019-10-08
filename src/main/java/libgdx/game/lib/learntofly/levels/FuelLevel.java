package libgdx.game.lib.learntofly.levels;


import libgdx.game.lib.learntofly.states.GameState;

public enum FuelLevel {

	NO_LEVEL(0, 0.8f, 0),

	LEVEL1(1, 0.6f, 200),
	LEVEL2(2, 0.4f, 350),
	LEVEL3(3, 0.3f, 850),

	LEVEL4(4, 0.25f, 1200),
	LEVEL5(5, 0.2f, 1550),
	LEVEL6(6, 0.195f, 2450),

	LEVEL7(7, 0.19f, 3500),
	LEVEL8(8, 0.185f, 5000),
	LEVEL9(9, 0.18f, 7000),

	LEVEL10(10, 0.175f, 11000),
	LEVEL11(11, 0.17f, 14000),
	LEVEL12(12, 0.165f, 19000);

	public static int SHOP_ID = 2;

	private int level;
	private float fuelLevel;
	private int price;

	private FuelLevel(int level, float fuelLevel, int price) {
		this.price = price;
		this.level = level;
		this.fuelLevel = fuelLevel;
	}

	public static FuelLevel getFuel(int level) {
		for (FuelLevel fuelLevel : values()) {
			if (fuelLevel.level == level) {
				return fuelLevel;
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

	public float getFuelLevel() {
		return fuelLevel / ShopLevel.BASE;
	}

	public int getPrice() {
		return price;
	}

	public static String getShopLabel() {
		return GameState.getLabel("fuel");
	}

}

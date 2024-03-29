package libgdx.game.lib.learntofly.levels;

import libgdx.game.lib.learntofly.states.GameState;
import libgdx.game.lib.learntofly.util.LearnToFlyGameLabel;
import libgdx.game.lib.learntofly.util.Utils;

public enum GliderLevel implements ShopLevel {

	NO_LEVEL(0, LearnToFlyGameLabel.l_glider.getText(), 0.99f, 0),

	LEVEL1(1, LearnToFlyGameLabel.l_glider1.getText(), 0.9f, 150),
	LEVEL2(2, LearnToFlyGameLabel.l_glider2.getText(), 0.8f, 350),

	LEVEL3(3, LearnToFlyGameLabel.l_glider3.getText(), 0.7f, 800),
	LEVEL4(4, LearnToFlyGameLabel.l_glider4.getText(), 0.55f, 2500),

	LEVEL5(5, LearnToFlyGameLabel.l_glider5.getText(), 0.45f, 4000),

	LEVEL6(6, LearnToFlyGameLabel.l_glider6.getText(), 0.4f, 8000);

	public static int SHOP_ID = 4;

	private int level;
	private String levelName;
	private float floatingLevel;
	private int price;

	private GliderLevel(int level, String levelName, float floatingLevel, int price) {
		this.price = price;
		this.level = level;
		this.levelName = levelName;
		this.floatingLevel = floatingLevel;
	}

	@Override
	public ShopLevel getItem(int level) {
		for (GliderLevel gliderLevel : values()) {
			if (gliderLevel.level == level) {
				return gliderLevel;
			}
		}
		if (level > LEVEL6.level) {
			return LEVEL6;
		}
		return NO_LEVEL;
	}

	public float getFloatingLevel() {
		return floatingLevel / ShopLevel.BASE;
	}

	@Override
	public int getLevel() {
		return level;
	}

	@Override
	public String getLevelName() {
		return levelName;
	}

	@Override
	public int[] getAttrLevel() {
		return new int[] { Utils.getLevelForAttr(LEVEL1.floatingLevel, LEVEL6.floatingLevel, this.floatingLevel) };
	}

	@Override
	public String[] getAttrName() {
		return new String[] { LearnToFlyGameLabel.l_glider_attr1.getText() };
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

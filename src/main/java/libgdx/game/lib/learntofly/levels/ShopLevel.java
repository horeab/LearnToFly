package libgdx.game.lib.learntofly.levels;

public interface ShopLevel {

	public static int NR_OF_SHOP_ITEMS = 6;

	static float BASE = 1.13f;

	public int getShopId();

	public String getLevelName();

	public String[] getAttrName();

	public int[] getAttrLevel();

	public int getLevel();

	public ShopLevel getItem(int level);

	public int getPrice();

}

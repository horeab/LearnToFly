package libgdx.game.lib.learntofly.levels;


import libgdx.game.lib.learntofly.states.GameState;
import libgdx.game.lib.learntofly.util.LearnToFlyGameLabel;
import libgdx.game.lib.learntofly.util.Utils;

public enum RocketLevel implements ShopLevel {

    NO_LEVEL(0, LearnToFlyGameLabel.l_rocket.getText(), 1.3f, 50, 0),

    LEVEL1(1, LearnToFlyGameLabel.l_rocket1.getText(), 1.3f, 80, 300),
    LEVEL2(2, LearnToFlyGameLabel.l_rocket2.getText(), 1.5f, 100, 750),

    LEVEL3(3, LearnToFlyGameLabel.l_rocket3.getText(), 1.8f, 140, 2500),
    LEVEL4(4, LearnToFlyGameLabel.l_rocket4.getText(), 2.2f, 130, 7000),

    LEVEL5(5, LearnToFlyGameLabel.l_rocket5.getText(), 2.9f, 140, 9500),

    LEVEL6(6, LearnToFlyGameLabel.l_rocket6.getText(), 3.5f, 170, 16000);

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
        return new String[]{
                LearnToFlyGameLabel.l_rocket_attr1.getText(),
                LearnToFlyGameLabel.l_rocket_attr2.getText()};
    }

    @Override
    public int[] getAttrLevel() {
        return new int[]{
                Utils.getLevelForAttr(LEVEL1.thrustLevel, LEVEL6.thrustLevel, this.thrustLevel),
                Utils.getLevelForAttr(LEVEL1.topSpeedLevel, LEVEL6.topSpeedLevel, this.topSpeedLevel)};
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

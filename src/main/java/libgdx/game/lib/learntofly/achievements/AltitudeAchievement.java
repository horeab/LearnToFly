package libgdx.game.lib.learntofly.achievements;


import libgdx.game.lib.learntofly.states.GameState;
import libgdx.game.lib.learntofly.util.LearnToFlyGameLabel;

public enum AltitudeAchievement implements Achievement {

    L0(10, R_L0),
    L1(14, R_L1),
    L2(19, R_L2),
    L3(24, R_L3),
    // /////////
    L4(40, R_L4),
    L5(60, R_L5),
    L6(90, R_L6),
    L7(130, R_L7),
    // /////////
    L8(150, R_L8),
    L9(170, R_L9),
    L10(200, R_L10),
    L11(220, R_L11),
    // /////////
    L12(300, R_L12),
    L13(320, R_L13),
    L14(340, R_L14),
    L15(360, R_L15);

    private AltitudeAchievement(int value, int reward) {
        this.value = value;
        this.reward = reward;
    }

    private int value;
    private int reward;

    @Override
    public String getAchievementLabel(int value) {
        return LearnToFlyGameLabel.l_altitude_achievement.getText(value);
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
        return ALTITUDE_ACHIEVEMENT;
    }

}

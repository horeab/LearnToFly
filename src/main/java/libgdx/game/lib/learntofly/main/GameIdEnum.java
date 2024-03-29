package libgdx.game.lib.learntofly.main;

import libgdx.game.GameId;
import libgdx.game.SubGameDependencyManager;

public enum GameIdEnum implements GameId {

    learntofly(LearnToFlyDependencyManager.class),;

    private Class<? extends SubGameDependencyManager> dependencyManagerClass;

    GameIdEnum(Class<? extends SubGameDependencyManager> dependencyManagerClass) {
        this.dependencyManagerClass = dependencyManagerClass;
    }

    @Override
    public SubGameDependencyManager getDependencyManager() {
        try {
            return dependencyManagerClass.newInstance();
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }
}

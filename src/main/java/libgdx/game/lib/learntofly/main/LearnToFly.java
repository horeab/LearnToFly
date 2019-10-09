package libgdx.game.lib.learntofly.main;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

import libgdx.constants.Language;
import libgdx.game.lib.learntofly.util.GameMode;

public class LearnToFly {

    private static final int V_WIDTH = 800;
    private static final int V_HEIGHT = 480;

    // private static final int V_WIDTH = 1920;
    // private static final int V_HEIGHT = 1080;

    // private static final int V_WIDTH = 320;
    // private static final int V_HEIGHT = 240;

    public static void main(String[] args) {
        LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
        Game game = new Game(Language.en, GameMode.DESKTOP);
        cfg.width = V_WIDTH;
        cfg.height = V_HEIGHT;

        new LwjglApplication(game, cfg);
    }
}

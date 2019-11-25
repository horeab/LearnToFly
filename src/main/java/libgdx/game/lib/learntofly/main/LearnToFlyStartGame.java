package libgdx.game.lib.learntofly.main;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

import libgdx.constants.Language;
import libgdx.game.lib.learntofly.util.GameMode;

public class LearnToFlyStartGame {

    private static final Pair<Integer, Integer> q = new ImmutablePair<>(3436, 2125);
    private static final Pair<Integer, Integer> w = new ImmutablePair<>(1084, 610);
    private static final Pair<Integer, Integer> e = new ImmutablePair<>(789, 480);
    private static final Pair<Integer, Integer> r = new ImmutablePair<>(320, 240);
    private static final Pair<Integer, Integer> t = new ImmutablePair<>(853, 480);
    private static final Pair<Integer, Integer> s = new ImmutablePair<>(480, 853);
    private static final Pair<Integer, Integer> a = new ImmutablePair<>(853, 1480);

    public static void main(String[] args) {
        LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
        Game game = new Game(Language.en, GameMode.DESKTOP);
        Pair<Integer, Integer> V = s;
        cfg.height = V.getLeft();
        cfg.width = V.getRight();

        new LwjglApplication(game, cfg);
    }
}

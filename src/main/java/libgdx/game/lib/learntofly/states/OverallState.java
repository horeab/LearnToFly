package libgdx.game.lib.learntofly.states;

import libgdx.game.lib.learntofly.storemanagers.AchievementsManager;
import libgdx.game.lib.learntofly.storemanagers.GameInfoManager;
import libgdx.game.lib.learntofly.storemanagers.PreferencesManager;
import libgdx.game.lib.learntofly.util.Language;
import libgdx.game.lib.learntofly.util.LibgdxControlUtils;
import libgdx.game.lib.learntofly.util.Resource;
import libgdx.game.lib.learntofly.util.Utils;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.I18NBundle;

public class OverallState {

	private LibgdxControlUtils libgdxControlUtils;
	private GameInfoManager gameInfoManager;
	private PreferencesManager preferencesManager;
	private AchievementsManager achievementsManager;
	private AssetManager assetManager;
	private Skin skin;
	private static I18NBundle labelsResource;

	public OverallState(Language language) {
		setupStuff(language);
		initContent();
		processMusic();
	}

	private void setupStuff(Language language) {
		labelsResource = I18NBundle.createBundle(Gdx.files.internal("learntofly/resources/labels_" + language.toString()));
		Gdx.app.setLogLevel(Application.LOG_DEBUG);
		skin = new Skin(Gdx.files.internal("learntofly/resources/skin.json"), new TextureAtlas(Gdx.files.internal("learntofly/buttons/btn_texture.pack")));
		gameInfoManager = new GameInfoManager();
		preferencesManager = new PreferencesManager();
		achievementsManager = new AchievementsManager();
		assetManager = new AssetManager();
		libgdxControlUtils = new LibgdxControlUtils(skin, preferencesManager, assetManager);
		Gdx.input.setCatchBackKey(true);
	}

	private void initContent() {
		for (Resource resource : Resource.values()) {
			assetManager.load(resource.getPath(), resource.getClassType());
		}
		assetManager.finishLoading();
		skin.add("coin", Utils.getTextureWithFilter(assetManager, Resource.coin));
		skin.add("background_second", Utils.getTextureWithFilter(assetManager, Resource.background_second));
	}

	protected void processMusic() {
		for (Resource m : Resource.getMusic()) {
			if (preferencesManager.isMusicOn()) {
				Music music = Utils.getMusic(assetManager, m);
				music = Utils.getMusic(assetManager, Resource.sound_background);
				music.setLooping(true);
				music.setVolume(0.1f);
				music.play();
			} else {
				Utils.getMusic(assetManager, m).stop();
			}
		}
	}

	public void dispose() {
		skin.dispose();
		assetManager.dispose();
	}

	public LibgdxControlUtils getLibgdxControlUtils() {
		return libgdxControlUtils;
	}

	public GameInfoManager getGameInfoManager() {
		return gameInfoManager;
	}

	public PreferencesManager getPreferencesManager() {
		return preferencesManager;
	}

	public AchievementsManager getAchievementsManager() {
		return achievementsManager;
	}

	public AssetManager getAssetManager() {
		return assetManager;
	}

	public Skin getSkin() {
		return skin;
	}

	public static I18NBundle getLabelsResource() {
		return labelsResource;
	}

}

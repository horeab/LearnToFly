package libgdx.game.lib.learntofly.storemanagers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

public class PreferencesManager {

	private static final String SOUND_ON = "sound_on";
	private static final String MUSIC_ON = "music_on";

	private Preferences prefs;

	public PreferencesManager() {
		prefs = Gdx.app.getPreferences("preferencesManager");
	}

	public boolean isSoundOn() {
		// return true;
		// return false;
		return prefs.getBoolean(SOUND_ON, true);
	}

	public void setSoundOn(boolean isSoundOn) {
		putBoolean(isSoundOn, SOUND_ON);
	}

	public boolean isMusicOn() {
		// return true;
		// return false;
		return prefs.getBoolean(MUSIC_ON, true);
	}

	public void setMusicOn(boolean isMusicOn) {
		putBoolean(isMusicOn, MUSIC_ON);
	}

	private void putBoolean(boolean value, String key) {
		prefs.putBoolean(key, value);
		prefs.flush();
	}

}

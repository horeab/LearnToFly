package libgdx.game.lib.learntofly.util;

import java.util.ArrayList;
import java.util.List;

import libgdx.game.lib.learntofly.creators.RampCreator;
import libgdx.game.lib.learntofly.handlers.Background;
import libgdx.game.lib.learntofly.main.Game;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class BackgroundLoader {

	private static final int TEXTURE_HEIGHT = 240;
	private static final int TEXTURE_WIDTH = 320;
	private TextureRegion surface;
	private Texture bgs0;
	private Texture bgs1;
	private Texture bgs2;
	private Texture bgs3;
	private Texture bgs4;

	private float displayWidth;

	public BackgroundLoader(int currentStage, AssetManager assetManager) {
		this.surface = new TextureRegion(Utils.getTextureWithFilter(assetManager, Resource.getGameStageGroundBackgroundResource(currentStage)));
		this.bgs0 = Utils.getTexture(assetManager, Resource.getGameStageBackgroundResource(currentStage, 0));
		this.bgs1 = Utils.getTexture(assetManager, Resource.getGameStageBackgroundResource(currentStage, 1));
		this.bgs2 = Utils.getTexture(assetManager, Resource.getGameStageBackgroundResource(currentStage, 2));
		this.bgs3 = Utils.getTexture(assetManager, Resource.getGameStageBackgroundResource(currentStage, 3));
		this.bgs4 = Utils.getTexture(assetManager, Resource.getGameStageBackgroundResource(currentStage, 4));
		this.displayWidth = Game.getWidth();
	}

	private static final int NR_OF_BACKGROUND_ALTITUDE_FILES = 4;

	public List<Background> loadBackgroundForAltitudeLevel(int originalLevel, OrthographicCamera gameCam) {
		int level = originalLevel > NR_OF_BACKGROUND_ALTITUDE_FILES ? NR_OF_BACKGROUND_ALTITUDE_FILES : originalLevel;

		List<Background> allBackgrounds = new ArrayList<Background>();
		for (int i = -1; i <= 1; i++) {
			int bgsIndex = level == 0 && i == -1 ? 0 : level + i;
			// if level exceeds number of background files it takes the last
			// background file
			bgsIndex = level == NR_OF_BACKGROUND_ALTITUDE_FILES && i == 1 ? NR_OF_BACKGROUND_ALTITUDE_FILES : bgsIndex;
			bgsIndex = originalLevel > NR_OF_BACKGROUND_ALTITUDE_FILES ? NR_OF_BACKGROUND_ALTITUDE_FILES : bgsIndex;
			Texture bgs = getLevelTextureRegion(bgsIndex);
			TextureRegion skyBackground = new TextureRegion(bgs, 0, 0, TEXTURE_WIDTH, TEXTURE_HEIGHT);
			TextureRegion inFrontOfSky = new TextureRegion(bgs, 0, 240, TEXTURE_WIDTH, TEXTURE_HEIGHT);
			TextureRegion inFrontOfInFrontOfSky = new TextureRegion(bgs, 0, 480, TEXTURE_WIDTH, TEXTURE_HEIGHT);

			allBackgrounds.add(new Background(skyBackground, gameCam, 0f, "skyBackground", i, displayWidth));
			allBackgrounds.add(new Background(inFrontOfSky, gameCam, 0.1f, "inFrontOfSky", i, displayWidth));
			if (bgsIndex < 1) {
				allBackgrounds.add(new Background(inFrontOfInFrontOfSky, gameCam, 0.2f, "inFrontOfInFrontOfSky", i, displayWidth));
				allBackgrounds.add(new Background(surface, gameCam, 0.3f, "surface", i, null, (float) RampCreator.GROUND_HEIGHT - surface.getRegionHeight(),
						0.5f, displayWidth));
			}
		}

		return allBackgrounds;
	}

	private Texture getLevelTextureRegion(int level) {
		Texture toReturn = null;
		if (level == 0) {
			toReturn = bgs0;
		} else if (level == 1) {
			toReturn = bgs1;
		} else if (level == 2) {
			toReturn = bgs2;
		} else if (level == 3) {
			toReturn = bgs3;
		} else if (level == 4) {
			toReturn = bgs4;
		}
		return toReturn;
	}

}

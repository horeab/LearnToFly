package libgdx.game.lib.learntofly.util;

import static libgdx.game.lib.learntofly.handlers.B2DVars.*;

import libgdx.game.lib.learntofly.main.Game;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;

public class Utils {

	public final static float STANDARD_RATIO = Game.STANDARD_HEIGHT / Game.STANDARD_WIDTH;

	public static float scaleDownPPM(float val) {
		return val / WORLD_ACC;
	}

	public static float scaleUpPPM(float val) {
		float f = val * WORLD_ACC;
		// DecimalFormatSymbols symbols =
		// DecimalFormatSymbols.getInstance(Locale.US);
		// DecimalFormat df = new DecimalFormat("#.####", symbols);
		return f;
	}

	public static float[] scaleUpFloatArray(float[] array) {
		float[] scaledArray = new float[array.length];
		for (int i = 0; i < array.length; i++) {
			scaledArray[i] = scaleUpPPM(array[i]);
		}
		return scaledArray;
	}

	public static float getValueForPercent(float val, float percent) {
		return val * (percent / 100f);
	}

	public static float getValueForDisplayHeightPercent(float percent) {
		return getValueForPercent(Game.getHeight(), percent);
	}

	public static float getValueForRatio(float value) {
		float ratio = Game.getHeight() / Game.getWidth();
		return ratio > STANDARD_RATIO ? value : getValueForRatioWithoutIgnoreIfLarger(value);
	}

	public static float getValueForRatioWithoutIgnoreIfLarger(float value) {
		float ratio = Game.getHeight() / Game.getWidth();
		float ratioToProcess = ratio / STANDARD_RATIO;
		return value * ratioToProcess;
	}

	public static Vector2[] scaleDownVectors(Vector2[] vectors) {
		for (int i = 0; i < vectors.length; i++) {
			vectors[i] = scaleDownVector(vectors[i]);
		}
		return vectors;
	}

	public static Vector2 scaleDownVector(Vector2 vector) {
		return new Vector2(scaleDownPPM(vector.x), scaleDownPPM(vector.y));
	}

	public static int getLevelForAttr(float firstLevelAttr, float lastLevelAttr, float currentAttr) {
		return firstLevelAttr > lastLevelAttr ?
				getLevelForAttrFirstBiggerThanLast(firstLevelAttr, lastLevelAttr, currentAttr) :
				getLevelForAttrFirstSmallerThanLast(firstLevelAttr, lastLevelAttr, currentAttr);
	}

	private static int getLevelForAttrFirstBiggerThanLast(float firstLevelAttr, float lastLevelAttr, float currentAttr) {
		float diff = firstLevelAttr - lastLevelAttr;
		float diffBetweenMinAndMax = diff / 12f;
		float diffLevel = firstLevelAttr;
		int level = 0;
		while (diffLevel >= currentAttr) {
			diffLevel = diffLevel - diffBetweenMinAndMax;
			level++;
		}
		return level;
	}

	private static int getLevelForAttrFirstSmallerThanLast(float firstLevelAttr, float lastLevelAttr, float currentAttr) {
		float diff = lastLevelAttr - firstLevelAttr;
		float diffBetweenMinAndMax = diff / 12f;
		float diffLevel = firstLevelAttr;
		int level = 0;
		while (diffLevel <= currentAttr) {
			diffLevel = diffLevel + diffBetweenMinAndMax;
			level++;
		}
		return level;
	}

	public static SpriteDrawable getDrawable(AssetManager assetManager, Resource resource) {
		SpriteDrawable spriteDrawable = new SpriteDrawable(new Sprite(getTextureWithFilter(assetManager, resource)));
		return spriteDrawable;
	}

	public static Sound getSound(AssetManager assetManager, Resource resource) {
		return assetManager.get(resource.getPath(), Sound.class);
	}

	public static Music getMusic(AssetManager assetManager, Resource resource) {
		return assetManager.get(resource.getPath(), Music.class);
	}

	public static Texture getTexture(AssetManager manager, Resource resource) {
		return manager.get(resource.getPath(), Texture.class);
	}

	public static Texture getTextureWithFilter(AssetManager manager, Resource resource) {
		Texture texture = manager.get(resource.getPath(), Texture.class);
		texture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		return texture;

	}
}

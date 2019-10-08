package libgdx.game.lib.learntofly.creators.accesory;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;

import java.util.Arrays;

import libgdx.game.lib.learntofly.creators.TextureAnimation;
import libgdx.game.lib.learntofly.levels.GliderLevel;
import libgdx.game.lib.learntofly.util.Resource;
import libgdx.game.lib.learntofly.util.Utils;

public class GliderCreator extends SensorAccesoryCreator {

	public GliderCreator(World world, Body playerBody, Integer accesoryLevel, AssetManager assetManager) {
		super(world, playerBody, accesoryLevel, "glider");
		if (hasAccesory()) {
			Resource inGameShopResource = Resource.getInGameShopResource(GliderLevel.SHOP_ID, accesoryLevel);
			Texture textureWithFilter = Utils.getTextureWithFilter(assetManager, inGameShopResource);
			int width = textureWithFilter.getWidth();
			int height = textureWithFilter.getHeight();
			TextureAnimation gliderSingle = new TextureAnimation(inGameShopResource, "gliderSingle", Arrays.asList(0), width, height, assetManager);
			gliderSingle.setFixedAngle(90f);
			gliderSingle.setxOffset(-6);
			animations.put("gliderSingle", gliderSingle);
			TextureAnimation glider = new TextureAnimation(inGameShopResource, "glider", Arrays.asList(0), width, height, assetManager);
			animations.put("glider", glider);
			setAnimationToPlay("gliderSingle");
		}

	}
}

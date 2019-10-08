package libgdx.game.lib.learntofly.creators.accesory;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;

import java.util.Arrays;

import libgdx.game.lib.learntofly.creators.TextureAnimation;
import libgdx.game.lib.learntofly.levels.SledLevel;
import libgdx.game.lib.learntofly.util.Resource;
import libgdx.game.lib.learntofly.util.Utils;

public class SledCreator extends BodyAccesoryCreator {

	public SledCreator(World world, Body playerBody, Integer accesoryLevel, AssetManager assetManager) {
		super(world, playerBody, accesoryLevel, "sled");
		if (hasAccesory()) {
			Resource inGameShopResource = Resource.getInGameShopResource(SledLevel.SHOP_ID, accesoryLevel);
			Texture textureWithFilter = Utils.getTextureWithFilter(assetManager, inGameShopResource);
			int width = textureWithFilter.getWidth();
			int height = textureWithFilter.getHeight();
			TextureAnimation sled = new TextureAnimation(inGameShopResource, "sled", Arrays.asList(0), width, height, assetManager);
			animations.put("sled", sled);
			setAnimationToPlay("sled");
		}
	}
}

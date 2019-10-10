package libgdx.game.lib.learntofly.creators.accesory;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;

import java.util.Arrays;

import libgdx.game.lib.learntofly.creators.TextureAnimation;
import libgdx.game.lib.learntofly.levels.RocketLevel;
import libgdx.game.lib.learntofly.util.Resource;
import libgdx.game.lib.learntofly.util.Utils;

public class RocketCreator extends SensorAccesoryCreator {

    public RocketCreator(World world, Body playerBody, Integer accesoryLevel) {
        super(world, playerBody, accesoryLevel, "rocket");
        if (hasAccesory()) {
            Resource inGameShopResource = Resource.getInGameShopResource(RocketLevel.SHOP_ID, accesoryLevel);
            Texture textureWithFilter = Utils.getTextureWithFilter(inGameShopResource);
            int width = textureWithFilter.getWidth();
            int height = textureWithFilter.getHeight();
            TextureAnimation rocketVertical = new TextureAnimation(inGameShopResource, "rocketVertical", Arrays.asList(0), width, height
            );
            rocketVertical.setFixedAngle(90f);
            rocketVertical.setxOffset(-9);
            rocketVertical.setyOffset(6);
            animations.put("rocketVertical", rocketVertical);
            TextureAnimation rocketHorizontal = new TextureAnimation(inGameShopResource, "rocketHorizontal", Arrays.asList(0), width, height
            );
            animations.put("rocketHorizontal", rocketHorizontal);
            Resource rocketfire = Resource.getRocketFireResource(accesoryLevel);
            Texture fireTextureWithFilter = Utils.getTextureWithFilter(rocketfire);
            int fireWidth = fireTextureWithFilter.getWidth();
            int fireheight = fireTextureWithFilter.getHeight();
            TextureAnimation fire = new TextureAnimation(rocketfire, "rocketFire", Arrays.asList(0, 1), fireWidth / 2, fireheight);
            fire.setOneTimePlay(false);
            animations.put("rocketFire", fire);
            fire.setxOffset(rocketfire.getxOffset());
            setAnimationToPlay("rocketVertical");
        }
    }
}

package libgdx.game.lib.learntofly.util;

import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;

import java.util.ArrayList;
import java.util.List;

import libgdx.constants.Language;
import libgdx.game.lib.learntofly.levels.GliderLevel;
import libgdx.game.lib.learntofly.levels.RocketLevel;
import libgdx.game.lib.learntofly.levels.SledLevel;

public enum Resource {

    // @formatter:off

    //STAGE1///////////////////////////////////////////////////////////////////
    stage1_sky_bgs0("game/stages/stage1/sky_bgs0.png", Texture.class),
    stage1_sky_bgs1("game/stages/stage1/sky_bgs1.png", Texture.class),
    stage1_sky_bgs2("game/stages/stage1/sky_bgs2.png", Texture.class),
    stage1_sky_bgs3("game/stages/stage1/sky_bgs3.png", Texture.class),
    stage1_sky_bgs4("game/stages/stage1/sky_bgs4.png", Texture.class),
    stage1_ramp_backgr_l1("game/stages/stage1/ramp_backgr_l1.jpg", Texture.class),
    stage1_ramp_backgr_l2("game/stages/stage1/ramp_backgr_l2.jpg", Texture.class),
    stage1_ramp_backgr_l3("game/stages/stage1/ramp_backgr_l3.jpg", Texture.class),
    stage1_ground("game/stages/stage1/ground.png", Texture.class),
    //STAGE2///////////////////////////////////////////////////////////////////
    stage2_sky_bgs0("game/stages/stage2/sky_bgs0.png", Texture.class),
    stage2_sky_bgs1("game/stages/stage2/sky_bgs1.png", Texture.class),
    stage2_sky_bgs2("game/stages/stage2/sky_bgs2.png", Texture.class),
    stage2_sky_bgs3("game/stages/stage2/sky_bgs3.png", Texture.class),
    stage2_sky_bgs4("game/stages/stage2/sky_bgs4.png", Texture.class),
    stage2_ramp_backgr_l1("game/stages/stage2/ramp_backgr_l1.jpg", Texture.class),
    stage2_ramp_backgr_l2("game/stages/stage2/ramp_backgr_l2.jpg", Texture.class),
    stage2_ramp_backgr_l3("game/stages/stage2/ramp_backgr_l3.jpg", Texture.class),
    stage2_ground("game/stages/stage2/ground.png", Texture.class),
    //STAGE3///////////////////////////////////////////////////////////////////
    stage3_sky_bgs0("game/stages/stage3/sky_bgs0.png", Texture.class),
    stage3_sky_bgs1("game/stages/stage3/sky_bgs1.png", Texture.class),
    stage3_sky_bgs2("game/stages/stage3/sky_bgs2.png", Texture.class),
    stage3_sky_bgs3("game/stages/stage3/sky_bgs3.png", Texture.class),
    stage3_sky_bgs4("game/stages/stage3/sky_bgs4.png", Texture.class),
    stage3_ramp_backgr_l1("game/stages/stage3/ramp_backgr_l1.jpg", Texture.class),
    stage3_ramp_backgr_l2("game/stages/stage3/ramp_backgr_l2.jpg", Texture.class),
    stage3_ramp_backgr_l3("game/stages/stage3/ramp_backgr_l3.jpg", Texture.class),
    stage3_ground("game/stages/stage3/ground.png", Texture.class),
    //STAGE4///////////////////////////////////////////////////////////////////
    stage4_sky_bgs0("game/stages/stage4/sky_bgs0.png", Texture.class),
    stage4_sky_bgs1("game/stages/stage4/sky_bgs1.png", Texture.class),
    stage4_sky_bgs2("game/stages/stage4/sky_bgs2.png", Texture.class),
    stage4_sky_bgs3("game/stages/stage4/sky_bgs3.png", Texture.class),
    stage4_sky_bgs4("game/stages/stage4/sky_bgs4.png", Texture.class),
    stage4_ramp_backgr_l1("game/stages/stage4/ramp_backgr_l1.jpg", Texture.class),
    stage4_ramp_backgr_l2("game/stages/stage4/ramp_backgr_l2.jpg", Texture.class),
    stage4_ramp_backgr_l3("game/stages/stage4/ramp_backgr_l3.jpg", Texture.class),
    stage4_ground("game/stages/stage4/ground.png", Texture.class),

    //PENGUIN///////////////////////////////////////////////////////////////////
    peng_walking("game/penguin/peng_walking.png", Texture.class),
    peng_slide("game/penguin/peng_slide.png", Texture.class),

    //GLIDER///////////////////////////////////////////////////////////////////
    glider_1("game/glider/glider_1.png", Texture.class),
    glider_2("game/glider/glider_2.png", Texture.class),
    glider_3("game/glider/glider_3.png", Texture.class),
    glider_4("game/glider/glider_4.png", Texture.class),
    glider_5("game/glider/glider_5.png", Texture.class),
    glider_6("game/glider/glider_6.png", Texture.class),
    //ROCKET///////////////////////////////////////////////////////////////////
    rocket_1("game/rocket/rocket_1.png", Texture.class),
    rocket_2("game/rocket/rocket_2.png", Texture.class),
    rocket_3("game/rocket/rocket_3.png", Texture.class),
    rocket_4("game/rocket/rocket_4.png", Texture.class),
    rocket_5("game/rocket/rocket_5.png", Texture.class),
    rocket_6("game/rocket/rocket_6.png", Texture.class),
    rocketFire("game/rocket/rocketFire.png", Texture.class),
    rocketFire_5("game/rocket/rocketFire_5.png", Texture.class, -5),
    rocketFire_6("game/rocket/rocketFire_6.png", Texture.class, -5),
    //SLATE////////////////////////////////////////////////////////////////////
    sled_1("game/sled/sled_1.png", Texture.class),
    sled_2("game/sled/sled_2.png", Texture.class),
    sled_3("game/sled/sled_3.png", Texture.class),
    sled_4("game/sled/sled_4.png", Texture.class),
    sled_5("game/sled/sled_5.png", Texture.class),
    sled_6("game/sled/sled_6.png", Texture.class),
    ///////////////////////////////////////////////////////////////////////////

    //HUD//////////////////////////////////////////////////////////////////////
    fuel_bar_empty("game/hud/fuel_bar_empty_background.png", Texture.class),
    fuel_bar_full("game/hud/fuel_bar_full_background.png", Texture.class),
    increment_empty("game/hud/finish_popup_counter_empty_background.png", Texture.class),
    increment_full("game/hud/finish_popup_counter_full_background.png", Texture.class),
    increment_record("game/hud/finish_popup_counter_record_background.png", Texture.class),
    speedometer("game/hud/speedometer.png", Texture.class),
    speed_needle("game/hud/speed_needle.png", Texture.class),
    ///////////////////////////////////////////////////////////////////////////

    //MENU/////////////////////////////////////////////////////////////////////
    title_en("menu/title_en.png", Texture.class),
    title_de("menu/title_de.png", Texture.class),
    title_ro("menu/title_ro.png", Texture.class),
    background_all("menu/background_all.jpg", Texture.class),
    background_second("menu/background_second.png", Texture.class),
    facebook_like("menu/facebook_like.png", Texture.class),
    google_play("menu/google_play.png", Texture.class),
    coin("menu/coin.png", Texture.class),
    checked("menu/checked.png", Texture.class),
    checked_disabled("menu/checked_disabled.png", Texture.class),
    rocket_tutorial_mob("menu/tutorial/rocket_tutorial_mobile.png", Texture.class),
    start_tutorial_mob("menu/tutorial/start_tutorial_mobile.png", Texture.class),
    rocket_tutorial_desk("menu/tutorial/rocket_tutorial_desktop.png", Texture.class),
    start_tutorial_desk("menu/tutorial/start_tutorial_desktop.png", Texture.class),
    music_on("menu/sound/music_on.png", Texture.class),
    sound_on("menu/sound/sound_on.png", Texture.class),
    sound_music_disabled("menu/sound/disabled.png", Texture.class),

    //ACHIEVEMENTS/////////////////////////////////////////////////////////////////////
    achievement("menu/achievements/achievement.png", Texture.class),
    speed_achievement("menu/achievements/speed_achievement.png", Texture.class),
    duration_achievement("menu/achievements/duration_achievement.png", Texture.class),
    distance_achievement("menu/achievements/distance_achievement.png", Texture.class),
    altitude_achievement("menu/achievements/altitude_achievement.png", Texture.class),
    finish("menu/achievements/finish.png", Texture.class),

    //ALPHA NUMERIC CHARACTERS/////////////////////////////////////////////////
    green_numbers("resources/green_numbers.png", Texture.class),
    numbers("resources/numbers.png", Texture.class),
    alphabet_upper("resources/alphabet_upper.png", Texture.class),
    alphabet_lower("resources/alphabet_lower.png", Texture.class),
    ///////////////////////////////////////////////////////////////////////////

    //MENU STAGE///////////////////////////////////////////////////////////////
    stage_btn1("menu/stage/stage_btn1.png", Texture.class),
    stage_btn2("menu/stage/stage_btn2.png", Texture.class),
    stage_btn3("menu/stage/stage_btn3.png", Texture.class),
    stage_btn4("menu/stage/stage_btn4.png", Texture.class),
    background_stage1("menu/stage/background_1.jpg", Texture.class),
    background_stage2("menu/stage/background_2.jpg", Texture.class),
    background_stage3("menu/stage/background_3.jpg", Texture.class),
    background_stage4("menu/stage/background_4.jpg", Texture.class),
    lock("menu/stage/lock.png", Texture.class),
    stage_play("menu/stage/play.png", Texture.class),
    ///////////////////////////////////////////////////////////////////////////

    //UPGRADE_MENU/////////////////////////////////////////////////////////////
    sled_menu_1("menu/upgradeshop/sled/sled_1.png", Texture.class),
    sled_menu_2("menu/upgradeshop/sled/sled_2.png", Texture.class),
    sled_menu_3("menu/upgradeshop/sled/sled_3.png", Texture.class),
    sled_menu_4("menu/upgradeshop/sled/sled_4.png", Texture.class),
    sled_menu_5("menu/upgradeshop/sled/sled_5.png", Texture.class),
    sled_menu_6("menu/upgradeshop/sled/sled_6.png", Texture.class),
    glider_menu_1("menu/upgradeshop/glider/glider_1.png", Texture.class),
    glider_scroll_1("menu/upgradeshop/glider/glider_s_1.png", Texture.class),
    glider_menu_2("menu/upgradeshop/glider/glider_2.png", Texture.class),
    glider_scroll_2("menu/upgradeshop/glider/glider_s_2.png", Texture.class),
    glider_menu_3("menu/upgradeshop/glider/glider_3.png", Texture.class),
    glider_scroll_3("menu/upgradeshop/glider/glider_s_3.png", Texture.class),
    glider_menu_4("menu/upgradeshop/glider/glider_4.png", Texture.class),
    glider_menu_5("menu/upgradeshop/glider/glider_5.png", Texture.class),
    glider_scroll_5("menu/upgradeshop/glider/glider_s_5.png", Texture.class),
    glider_menu_6("menu/upgradeshop/glider/glider_6.png", Texture.class),
    glider_scroll_6("menu/upgradeshop/glider/glider_s_6.png", Texture.class),
    rocket_menu_1("menu/upgradeshop/rocket/rocket_1.png", Texture.class),
    rocket_menu_2("menu/upgradeshop/rocket/rocket_2.png", Texture.class),
    rocket_menu_3("menu/upgradeshop/rocket/rocket_3.png", Texture.class),
    rocket_menu_4("menu/upgradeshop/rocket/rocket_4.png", Texture.class),
    rocket_menu_5("menu/upgradeshop/rocket/rocket_5.png", Texture.class),
    rocket_menu_6("menu/upgradeshop/rocket/rocket_6.png", Texture.class),
    nothing_bought("menu/upgradeshop/nothing_bought.png", Texture.class),
    ///////////////////////////////////////////////////////////////////////////

    sound_rocket("sounds/rocket.wav", Sound.class),
    sound_bought("sounds/bought_cash.wav", Sound.class),
    sound_achiev("sounds/achievement.wav", Sound.class),
    sound_stage_unlocked("sounds/sound_stage_unlocked.wav", Sound.class),
    sound_coin("sounds/coin.wav", Sound.class),
    sound_background("sounds/background.mp3", Music.class);
    // @formatter:on

    private String path;
    private int xOffset;
    private Class<?> classType;

    private Resource(String path, Class<?> classType) {
        this.path = path;
        this.classType = classType;
        this.xOffset = 0;
    }

    private Resource(String path, Class<?> classType, int xOffset) {
        this(path, classType);
        this.xOffset = xOffset;
    }

    public Class<?> getClassType() {
        return classType;
    }

    public String getPath() {
        return "learntofly/" + path;
    }

    public static Resource getMenuStageBackgroundResource(Integer stageNr) {
        return stageNr == null ? Resource.background_all : getResourceByName("background_stage" + stageNr);
    }

    public static Resource getGameStageGroundBackgroundResource(int stageNr) {
        return getResourceByName("stage" + stageNr + "_ground");
    }

    public static Resource getGameStageRampBackgroundResource(int stageNr, int backgroundLevel) {
        return getResourceByName("stage" + stageNr + "_ramp_backgr_l" + backgroundLevel);
    }

    public static Resource getGameStageBackgroundResource(int stageNr, int backgroundLevel) {
        return getResourceByName("stage" + stageNr + "_sky_bgs" + backgroundLevel);
    }

    public static List<Resource> getMusic() {
        return getResourcesByClass(Music.class);
    }

    public static List<Resource> getSound() {
        return getResourcesByClass(Sound.class);
    }

    public static Resource getMenuStageResource(int level) {
        return getResourceByName("stage_btn" + level);
    }

    public static Resource getTitleResource(Language language) {
        return getResourceByName("title_" + language.toString());
    }

    public static Resource getMenuShopResourceForScrollShop(int shopId, int level) {
        Resource scrollResource = getShopResource(shopId, level, "_scroll_");
        if (scrollResource == null) {
            scrollResource = getMenuShopResource(shopId, level);
        }
        return scrollResource;
    }

    public static Resource getMenuShopResource(int shopId, int level) {
        return getShopResource(shopId, level, "_menu_");
    }

    public static Resource getInGameShopResource(int shopId, int level) {
        return getShopResource(shopId, level, "_");
    }

    private static Resource getShopResource(int shopId, int level, String bindingString) {
        String key = "";
        if (shopId == SledLevel.SHOP_ID) {
            key = "sled";
        } else if (shopId == GliderLevel.SHOP_ID) {
            key = "glider";
        } else if (shopId == RocketLevel.SHOP_ID) {
            key = "rocket";
        }
        return getResourceByName(key + bindingString + level);
    }

    public static Resource getRocketFireResource(int level) {
        Resource resourceByName = getResourceByName("rocketFire_" + level);
        return resourceByName == null ? rocketFire : resourceByName;
    }

    private static Resource getResourceByName(String name) {
        for (Resource value : values()) {
            if (value.toString().equals(name)) {
                return value;
            }
        }
        return null;
    }

    private static List<Resource> getResourcesByClass(Class<?> classType) {
        List<Resource> resources = new ArrayList<Resource>();
        for (Resource value : values()) {
            if (value.getClassType().equals(classType)) {
                resources.add(value);
            }
        }
        return resources;
    }

    public int getxOffset() {
        return xOffset;
    }

}

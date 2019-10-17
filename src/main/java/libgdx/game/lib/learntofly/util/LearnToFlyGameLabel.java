package libgdx.game.lib.learntofly.util;

import com.badlogic.gdx.physics.box2d.World;

import libgdx.game.Game;
import libgdx.game.lib.learntofly.states.OverallState;
import libgdx.game.lib.learntofly.states.Play;
import libgdx.resources.gamelabel.GameLabel;
import libgdx.resources.gamelabel.GameLabelUtils;
import libgdx.resources.properties.PropertiesUtils;

public enum LearnToFlyGameLabel implements GameLabel {

    ///////////////////General///////////////////////
    l_buy,
    l_play,
    l_day,
    l_reward,
    l_back,
    ///////////////////Play///////////////////////
    l_distance,
    l_altitude,
    l_duration,
    l_speed,
    l_achievements_earnings,
    l_total_earnings,
    l_next_stage_text1,
    l_next_stage_text2,
    l_play_congratulations,
    l_play_game_finished,
    ///////////////////Achievement///////////////////
    l_altitude_achievement,
    l_distance_achievement,
    l_duration_achievement,
    l_speed_achievement,
    ///////////////////MainMenu///////////////////////
    l_no,
    l_yes,
    l_continue,
    l_newgame_confirm,
    l_new_game,
    ///////////////////GameFinishedScreen///////////////////
    l_congratulations,
    l_finished_game,
    l_confirm_msg,
    l_start_a_new_game,
    l_continue_this_game,
    ///////////////////UpgradeMenu///////////////////
    l_next_level,
    ///////////////////StageName///////////////////
    l_stage1,
    l_stage2,
    l_stage3,
    l_stage4,
    ///////////////////Rocket///////////////////
    l_rocket,
    l_rocket1,
    l_rocket2,
    l_rocket3,
    l_rocket4,
    l_rocket5,
    l_rocket6,
    l_rocket_attr1,
    l_rocket_attr2,
    ///////////////////Sled///////////////////
    l_sled,
    l_sled1,
    l_sled2,
    l_sled3,
    l_sled4,
    l_sled5,
    l_sled6,
    l_sled_attr1,
    l_sled_attr2,
    ///////////////////Glider///////////////////
    l_glider,
    l_glider1,
    l_glider2,
    l_glider3,
    l_glider4,
    l_glider5,
    l_glider6,
    l_glider_attr1,
    ///////////////////Tutorial///////////////////
    l_rocket_tutorial_mobile,
    l_start_tutorial_mobile,
    ///////////////////Ramp///////////////////
    l_ramp,
    ///////////////////Fuel///////////////////
    l_fuel,;

    @Override
    public String getText(Object... params) {
        for (int i = 0; i < params.length; i++) {
            if (params[i] instanceof Integer) {
                params[i] = String.valueOf(params[i]);
            }
        }
        return OverallState.getLabelsResource().format(name(), params);
    }

    @Override
    public String getKey() {
        return name();
    }
}

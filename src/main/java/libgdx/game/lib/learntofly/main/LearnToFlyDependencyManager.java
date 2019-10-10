package libgdx.game.lib.learntofly.main;

import java.util.ArrayList;
import java.util.List;

import libgdx.game.SubGameDependencyManager;
import libgdx.game.lib.learntofly.util.Resource;
import libgdx.resources.IncrementingRes;

public class LearnToFlyDependencyManager extends SubGameDependencyManager {

    @Override
    public List<? extends IncrementingRes> getIncrementResList() {
        List<IncrementingRes> list = new ArrayList<>();
        return list;
    }

    @Override
    protected String allQuestionText() {
        return "";
    }

    @Override
    public Class<Resource> getSpecificResourceTypeEnum() {
        return Resource.class;
    }

}

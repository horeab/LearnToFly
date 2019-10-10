package libgdx.game.lib.learntofly.main;

import libgdx.controls.labelimage.InventoryTableBuilderCreator;
import libgdx.controls.popup.RatingPopup;
import libgdx.controls.popup.RatingService;
import libgdx.game.MainDependencyManager;
import libgdx.game.lib.learntofly.util.Resource;
import libgdx.resources.MainResource;
import libgdx.resources.ResourceService;
import libgdx.resources.gamelabel.MainGameLabel;
import libgdx.screen.AbstractScreen;
import libgdx.transactions.TransactionsService;

public class LearnToFlyMainDependencyManager extends MainDependencyManager<ScreenManager, AbstractScreen, MainGameLabel, Resource, GameIdEnum> {

    @Override
    public Class<Resource> getMainResourcesClass() {
        return Resource.class;
    }

    @Override
    public Class<GameIdEnum> getGameIdClass() {
        return GameIdEnum.class;
    }

    @Override
    public ResourceService createResourceService() {
        return new ResourceService() {

        };
    }

    @Override
    public Class<MainGameLabel> getGameLabelClass() {
        return MainGameLabel.class;
    }

    @Override
    public RatingService createRatingService(AbstractScreen abstractScreen) {
        return new RatingService(abstractScreen) {
            @Override
            protected RatingPopup createRatingPopup() {
                return null;
            }
        };
    }

    @Override
    public ScreenManager createScreenManager() {
        return new ScreenManager();
    }

    @Override
    public InventoryTableBuilderCreator createInventoryTableBuilderCreator() {
        throw new RuntimeException("Transactions not implemented for Game");
    }

    @Override
    public TransactionsService getTransactionsService() {
        throw new RuntimeException("Transactions not implemented for Game");
    }
}

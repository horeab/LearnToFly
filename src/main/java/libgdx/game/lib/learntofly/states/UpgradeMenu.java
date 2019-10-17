package libgdx.game.lib.learntofly.states;

import libgdx.game.Game;
import libgdx.game.lib.learntofly.handlers.GameStateManager;
import libgdx.game.lib.learntofly.levels.FuelLevel;
import libgdx.game.lib.learntofly.levels.GliderLevel;
import libgdx.game.lib.learntofly.levels.RampLevel;
import libgdx.game.lib.learntofly.levels.RocketLevel;
import libgdx.game.lib.learntofly.levels.ShopLevel;
import libgdx.game.lib.learntofly.levels.SledLevel;
import libgdx.game.lib.learntofly.to.GameInfo;
import libgdx.game.lib.learntofly.to.menu.HeaderInfo;
import libgdx.game.lib.learntofly.to.menu.IncrementShopInfo;
import libgdx.game.lib.learntofly.to.menu.UpgradeMenuScrollShopInfo;
import libgdx.game.lib.learntofly.util.LibgdxControlUtils;
import libgdx.game.lib.learntofly.util.Resource;
import libgdx.game.lib.learntofly.util.Utils;
import libgdx.resources.FontManager;
import libgdx.resources.dimen.MainDimen;
import libgdx.utils.ScreenDimensionsManager;
import libgdx.utils.model.FontColor;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton.ImageButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;

public class UpgradeMenu extends GameState {

    private Table shopsTable;
    private Table buttonsTable;
    private IncrementShopInfo rampLevelInfo = new IncrementShopInfo();
    private IncrementShopInfo fuelLevelInfo = new IncrementShopInfo();
    private UpgradeMenuScrollShopInfo upgradeMenuScrollShopInfo = new UpgradeMenuScrollShopInfo();
    private HeaderInfo headerInfo;

    private float buyBtnWidth;

    public UpgradeMenu(GameStateManager gameStateManager, GameInfo gameInfo) {
        super(gameStateManager, gameInfo);

        buyBtnWidth = Utils.getValueForPercent(getIncrementShopWidth(), 19);


    }

    @Override
    public void buildStage() {
        Table allTable = libgdxControlUtils.createAllScreenTable(gameInfo.getSelectedStage());
        allTable.setFillParent(true);
        Table contentTable = libgdxControlUtils.createContentTable();
        shopsTable = new Table(skin);
        buttonsTable = new Table(skin);

        addIncrementShop(RampLevel.SHOP_ID);
        refreshIncrementShop(rampLevelInfo, RampLevel.SHOP_ID);

        shopsTable.row().padBottom(getSidePadding());
        addShops(createShops());

        if (gameInfo.getRocketLevel() > 0) {
            shopsTable.row();
            addIncrementShop(FuelLevel.SHOP_ID);
            refreshIncrementShop(fuelLevelInfo, FuelLevel.SHOP_ID);
        }
        addButtons();

        headerInfo = libgdxControlUtils.createHeaderTable(gameInfo.getSelectedStage(), gameInfo.getCurrentDay(), gameInfo.getCash(), gameStateManager, gameInfo);
        contentTable.add(shopsTable)
                .padLeft(Utils.getValueForRatio(40))
                .width(getShopTableWidth())
                .height(getContentTableHeight());
        contentTable.add(buttonsTable)
                .width(getButtonsTableWidth())
                .height(getContentTableHeight());
        allTable.add(headerInfo.getHeaderTable())
                .height(getHeaderHeight())
                .width(ScreenDimensionsManager.getScreenWidth());
        allTable.row();
        allTable.add(contentTable)
                .height(ScreenDimensionsManager.getScreenHeight() - getHeaderHeight())
                .width(ScreenDimensionsManager.getScreenWidth());
        addActor(allTable);
    }

    @Override
    public void onBackKeyPress() {
        gameStateManager.setMainMenuState();
    }

    private void addButtons() {
        TextButton playButton = new TextButton(getLabel("play"), skin, "green");
        playButton.getStyle().font = Game.getInstance().getFontManager().getFont();
        playButton.getLabel().setFontScale(libgdxControlUtils.getFontScale());
        playButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                processPlay();
            }
        });
        ImageButton achievementButton = new ImageButton(skin, "green");
        Table achievementButtonTable = new Table(skin);
        SpriteDrawable drawable = Utils.getDrawable(Resource.achievement);
        achievementButtonTable.setBackground(drawable);
        achievementButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                gameStateManager.setAchievementsState(gameInfo);
            }
        });
        float achImgDimen = MainDimen.horizontal_general_margin.getDimen() * 3;
        achievementButton.add(achievementButtonTable).width(achImgDimen).height(achImgDimen);
        buttonsTable.add(playButton).width(getButtonSide()).height(getButtonSide()).padBottom(getSidePadding());
        buttonsTable.row();
        buttonsTable.add(achievementButton).width(getButtonSide()).height(getButtonSide()).padBottom(getSidePadding());
        buttonsTable.row();
        buttonsTable.add(backButton).width(getButtonSide()).height(getButtonSide());
    }

    void processPlay() {
        if (gameInfoManager.displayStageScreen()) {
            gameStateManager.setStageState(gameInfo, StageScreen.StageScreenType.IN_GAME);
        } else if (gameInfoManager.displayRocketTutorial()) {
            gameStateManager.setTutorialState(gameInfo, TutorialScreen.TutorialScreenType.ROCKET);
        } else {
            gameStateManager.setPlayState(gameInfo);
        }
    }

    private void addIncrementShop(int shopType) {
        shopsTable.add(createIncrementShopLabel(shopType))
                .height(ScreenDimensionsManager.getScreenHeightValue(10))
                .width(getIncrementShopWidth());
        shopsTable.row();

        shopsTable.row().padBottom(getSidePadding());
        shopsTable.add(createIncrementShop(shopType))
                .width(getIncrementShopWidth())
                .height(getIncrementShopHeight());
    }

    private void addShops(Table shop) {
        shopsTable.add(shop)
                .width(getShopWidth())
                .height(getHeaderHeight() * 3);
    }

    private Table createShops() {
        Table allShops = new Table(skin);
        for (int i = 0; i < 3; i++) {
            ImageButton shop = createShopButton(i);
            allShops.add(shop)
                    .height(getHeaderHeight() * 3)
                    .width(getShopWidth())
                    .pad(5);
        }
        return allShops;
    }

    private ImageButton createShopButton(int i) {
        final ShopLevel finalShopLevel = getShopLevel(i);

        float imgContainerHeight = getHeaderHeight() * 1.95f;

        Table imgContainer = new Table(skin);
        Table imgContainerLabelTable = new Table(skin);
        String labelText = finalShopLevel.getLevelName();
        Label imgLabel = c.label(labelText);
        imgLabel.setFontScale(libgdxControlUtils.getFontScale() / 1.5f);
        imgContainerLabelTable.add(imgLabel);
        Table imgContainerImgTable = new Table(skin);
        setShopImg(finalShopLevel, imgContainerImgTable);
        float labelHeight = imgContainerHeight / 4;
        imgContainer.add(imgContainerLabelTable)
                .padBottom(imgContainerHeight / 4)
                .padLeft(imgContainerHeight / 6)
                .height(labelHeight)
                .width(getShopWidth());
        imgContainer.row();
        Sprite sprite = Utils.getDrawable(getResourceForShop(finalShopLevel)).getSprite();
        float scalePercent = 65;
        imgContainer.add(imgContainerImgTable).width(Utils.getValueForPercent(sprite.getWidth(), scalePercent)).height(Utils.getValueForPercent(sprite.getHeight(), scalePercent));

        ImageButton shop = new ImageButton(skin);
        upgradeMenuScrollShopInfo.getShopNameLabels().add(imgLabel);
        upgradeMenuScrollShopInfo.getShopImgButtons().add(shop);
        refreshColorToShopLabel(finalShopLevel, shop);
        shop.add(imgContainer).width(getShopWidth()).height(imgContainerHeight);
        shop.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                gameStateManager.setScrollShopState(finalShopLevel.getShopId(), gameInfo);
            }
        });
        return shop;
    }

    private void refreshColorToShopLabel(ShopLevel shopLevel, ImageButton shopBtn) {
        if (shopLevel.getLevel() == ShopLevel.NR_OF_SHOP_ITEMS) {
            shopBtn.setStyle(skin.get("green", ImageButtonStyle.class));
        } else if (!ScrollShopMenu.affordNextLevelForShop(gameInfo.getCash(), shopLevel, gameInfoManager.getBoughtLevel(shopLevel.getShopId()))) {
            shopBtn.setStyle(skin.get("red", ImageButtonStyle.class));
        }
    }

    private void setShopImg(ShopLevel shopLevel, Table imgContainerImgTable) {
        imgContainerImgTable.setBackground(Utils.getDrawable(getResourceForShop(shopLevel)));
    }

    private Resource getResourceForShop(ShopLevel shopLevel) {
        Resource resource = Resource.nothing_bought;
        if (shopLevel.getLevel() != 0) {
            resource = Resource.getMenuShopResource(shopLevel.getShopId(), shopLevel.getLevel());
        }
        return resource;
    }

    private ShopLevel getShopLevel(int index) {
        ShopLevel shopLevel = null;
        if (index == 0) {
            shopLevel = GliderLevel.NO_LEVEL.getItem(gameInfo.getGliderLevel());
        } else if (index == 1) {
            shopLevel = SledLevel.NO_LEVEL.getItem(gameInfo.getSledLevel());
        } else {
            shopLevel = RocketLevel.NO_LEVEL.getItem(gameInfo.getRocketLevel());
        }
        return shopLevel;
    }

    private Table createIncrementShop(int shopType) {
        Table allContainer = new Table(skin);
        TextButton buyButton = new TextButton(getLabel("buy"), skin);
        buyButton.getStyle().font = Game.getInstance().getFontManager().getFont();
        buyButton.getLabel().setFontScale(FontManager.getSmallFontDim());
        buyButtonClick(buyButton, shopType);
        float levelContainerWidth = getIncrementShopWidth() - buyBtnWidth;
        Table levelContainerTable = new Table(skin);
        allContainer.add(levelContainerTable)
                .height(getIncrementShopHeight())
                .width(levelContainerWidth);
        float totalNrOfLevels = getTotalNrOfLevels(shopType);
        float levelWidth = levelContainerWidth / totalNrOfLevels;
        for (int i = 1; i <= totalNrOfLevels; i++) {
            Table level = new Table(skin);
            levelContainerTable.add(level)
                    .height(getIncrementShopHeight())
                    .width(levelWidth);
        }
        allContainer.add(buyButton)
                .width(buyBtnWidth)
                .height(getIncrementShopHeight());

        if (shopType == RampLevel.SHOP_ID) {
            rampLevelInfo.setContainer(levelContainerTable);
            rampLevelInfo.setBuyBtn(buyButton);
            refreshIncrementShop(rampLevelInfo, shopType);
        } else if (shopType == FuelLevel.SHOP_ID) {
            fuelLevelInfo.setContainer(levelContainerTable);
            fuelLevelInfo.setBuyBtn(buyButton);
            refreshIncrementShop(fuelLevelInfo, shopType);
        }

        return allContainer;
    }

    private float getTotalNrOfLevels(int shopId) {
        float totalNrOfLevels = -1;
        if (shopId == RampLevel.SHOP_ID) {
            totalNrOfLevels = RampLevel.values().length;
        } else if (shopId == FuelLevel.SHOP_ID) {
            totalNrOfLevels = FuelLevel.values().length;
        }
        return totalNrOfLevels - 1;
    }

    private int getNextLevelPrice(int shopId) {
        int price = -1;
        if (shopId == RampLevel.SHOP_ID) {
            price = RampLevel.getRamp(gameInfo.getRampLevel() + 1).getPrice();
        } else if (shopId == FuelLevel.SHOP_ID) {
            price = FuelLevel.getFuel(gameInfo.getFuelLevel() + 1).getPrice();
        }
        return price;
    }

    private String getShopLabel(int shopId) {
        String price = "";
        if (shopId == RampLevel.SHOP_ID) {
            price = RampLevel.getShopLabel();
        } else if (shopId == FuelLevel.SHOP_ID) {
            price = FuelLevel.getShopLabel();
        }
        return price;
    }

    private float getBoughtLevels(int shopType) {
        float boughtLevels = -1;
        if (shopType == RampLevel.SHOP_ID) {
            boughtLevels = gameInfo.getRampLevel();
        } else if (shopType == FuelLevel.SHOP_ID) {
            boughtLevels = gameInfo.getFuelLevel();
        }
        return boughtLevels;
    }

    private void buyButtonClick(TextButton buyButton, final int shopId) {
        buyButton.addListener(new ClickListener() {
            @SuppressWarnings("synthetic-access")
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (gameInfo.getLevelForShopId(shopId) < getTotalNrOfLevels(shopId)) {
                    gameInfoManager.updateCash(gameInfo, gameInfo.getCash() - getNextLevelPrice(shopId));
                    libgdxControlUtils.playSound(Utils.getSound(Resource.sound_bought));
                    gameInfoManager.incrementShopLevel(gameInfo, shopId);
                }
                refreshIncrementShop(rampLevelInfo, RampLevel.SHOP_ID);
                for (int i = 0; i < 3; i++) {
                    refreshColorToShopLabel(getShopLevel(i), upgradeMenuScrollShopInfo.getShopImgButtons().get(i));
                }
                refreshNextLevelPriceLabel(rampLevelInfo.getPriceLabel(), rampLevelInfo.getCoinImage(), RampLevel.SHOP_ID);

                if (gameInfo.getRocketLevel() > 0) {
                    refreshIncrementShop(fuelLevelInfo, FuelLevel.SHOP_ID);
                    refreshNextLevelPriceLabel(fuelLevelInfo.getPriceLabel(), fuelLevelInfo.getCoinImage(), FuelLevel.SHOP_ID);
                }

                LibgdxControlUtils.refreshHeaderInfo(headerInfo, gameInfo.getCurrentDay(), gameInfo.getCash());
            }
        });
    }

    @SuppressWarnings("rawtypes")
    private void refreshIncrementShop(IncrementShopInfo incrementShopInfo, int shopId) {
        float totalNrOfLevels = getTotalNrOfLevels(shopId);
        float boughtLevels = getBoughtLevels(shopId);
        Drawable notAttrDrawable = skin.getDrawable("grey_normal");
        Drawable yesAttrDrawable = skin.getDrawable("green_pressed");
        if (incrementShopInfo.getContainer() != null) {
            Array<Cell> levelTablesCells = incrementShopInfo.getContainer().getCells();
            for (int i = 0; i < totalNrOfLevels; i++) {
                ((Table) levelTablesCells.get(i).getActor()).setBackground(boughtLevels < i + 1 ? notAttrDrawable : yesAttrDrawable);
            }
        }
        if (gameInfo.getCash() < getNextLevelPrice(shopId)) {
            if (incrementShopInfo.getBuyBtn() != null) {
                disableButton(incrementShopInfo.getBuyBtn());
            }
        }
        if (shopIsMaxedOut(shopId) && incrementShopInfo.getBuyBtn() != null) {
            disableButton(incrementShopInfo.getBuyBtn());
        }
    }

    private void disableButton(TextButton btn) {
        btn.getStyle().fontColor = Color.GRAY;
        btn.setTouchable(Touchable.disabled);
    }

    private boolean shopIsMaxedOut(int shopId) {
        float totalNrOfLevels = getTotalNrOfLevels(shopId);
        return totalNrOfLevels == getBoughtLevels(shopId);
    }

    private Table createIncrementShopLabel(int shopId) {
        Table labelTable = new Table(skin);
        labelTable.setBackground(LibgdxControlUtils.createColorTexture(Color.valueOf("ffffff"), 0.3f));
        Label shopNameLabel = c.label(getShopLabel(shopId));
        shopNameLabel.setFontScale(libgdxControlUtils.getFontScale());
        Label nextLevelPriceLabel = c.label("");
        nextLevelPriceLabel.setAlignment(Align.right);
        nextLevelPriceLabel.setFontScale(libgdxControlUtils.getFontScale());
        float incrementShopWidth = getIncrementShopWidth() - buyBtnWidth;
        float labelWidth = Utils.getValueForPercent(incrementShopWidth, 45);
        labelTable.add(shopNameLabel)
                .height(Utils.getValueForRatio(50))
                .width(labelWidth)
                .padLeft(Utils.getValueForRatio(15));
        labelTable.add()
                .height(Utils.getValueForRatio(50))
                .width(Utils.getValueForRatio(40));
        labelTable.add(nextLevelPriceLabel)
                .height(Utils.getValueForRatio(50))
                .width(labelWidth - Utils.getValueForRatio(40));
        Image coinImg = new Image(skin, "coin");
        coinImg.setAlign(Align.bottomRight);
        float coinDimen = MainDimen.horizontal_general_margin.getDimen() * 2;
        labelTable.add(coinImg)
                .height(coinDimen)
                .width(coinDimen)
                .padTop(-Utils.getValueForDisplayHeightPercent(1));
        labelTable.add().width(buyBtnWidth);

        if (shopId == RampLevel.SHOP_ID) {
            rampLevelInfo.setPriceLabel(nextLevelPriceLabel);
            rampLevelInfo.setCoinImage(coinImg);
            refreshNextLevelPriceLabel(nextLevelPriceLabel, rampLevelInfo.getCoinImage(), shopId);
            refreshIncrementShop(rampLevelInfo, shopId);
        } else if (shopId == FuelLevel.SHOP_ID) {
            fuelLevelInfo.setPriceLabel(nextLevelPriceLabel);
            fuelLevelInfo.setCoinImage(coinImg);
            refreshNextLevelPriceLabel(nextLevelPriceLabel, fuelLevelInfo.getCoinImage(), shopId);
            refreshIncrementShop(fuelLevelInfo, shopId);
        }
        return labelTable;
    }

    private void refreshNextLevelPriceLabel(Label nextLevel, Image coin, int shopId) {
        int nextLevelPrice = getNextLevelPrice(shopId);
        nextLevel.setText(getLabel("next_level") + nextLevelPrice);
        if (gameInfo.getCash() < nextLevelPrice) {
            nextLevel.getStyle().font = Game.getInstance().getFontManager().getFont(FontColor.RED);
        }
        if (shopIsMaxedOut(shopId)) {
            nextLevel.setText("");
            coin.setVisible(false);
        }
    }

    private float getButtonSide() {
        return ScreenDimensionsManager.getScreenHeightValue(25);
    }

    private float getContentTableHeight() {
        return ScreenDimensionsManager.getScreenHeight() - getHeaderHeight();
    }

    private float getHeaderHeight() {
        return libgdxControlUtils.getHeaderHeight();
    }

    private float getIncrementShopHeight() {
        return getHeaderHeight() * 1.2f;
    }

    private float getIncrementShopWidth() {
        return getShopWidth() * 3 + getSidePadding() * 2;
    }

    private float getShopTableWidth() {
        return getIncrementShopWidth() + getSidePadding();
    }

    private float getButtonsTableWidth() {
        return Utils.getValueForRatio(ScreenDimensionsManager.getScreenWidth()) - getShopTableWidth();
    }

    private float getSidePadding() {
        return libgdxControlUtils.getSidePadding();
    }

    private float getShopWidth() {
        return Utils.getValueForRatio(Utils.getValueForPercent(ScreenDimensionsManager.getScreenWidth(), 20));
    }

}
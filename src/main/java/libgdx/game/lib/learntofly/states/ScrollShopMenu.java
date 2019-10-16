package libgdx.game.lib.learntofly.states;

import libgdx.game.Game;
import libgdx.game.lib.learntofly.handlers.GameStateManager;
import libgdx.game.lib.learntofly.levels.GliderLevel;
import libgdx.game.lib.learntofly.levels.RocketLevel;
import libgdx.game.lib.learntofly.levels.ShopLevel;
import libgdx.game.lib.learntofly.levels.SledLevel;
import libgdx.game.lib.learntofly.to.GameInfo;
import libgdx.game.lib.learntofly.to.menu.HeaderInfo;
import libgdx.game.lib.learntofly.to.menu.ScrollShopInfo;
import libgdx.game.lib.learntofly.util.LibgdxControlUtils;
import libgdx.game.lib.learntofly.util.Resource;
import libgdx.game.lib.learntofly.util.Utils;
import libgdx.utils.ScreenDimensionsManager;
import libgdx.utils.model.FontColor;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane.ScrollPaneStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.badlogic.gdx.utils.Align;

public class ScrollShopMenu extends GameState {

    private ScrollPane scrollPane;
    private HeaderInfo headerInfo;

    private ShopLevel currentShopLevel;
    private int scrollPanePositionInit = 0;

    private ScrollShopInfo scrollShopInfo = new ScrollShopInfo();

    public ScrollShopMenu(GameStateManager gameStateManager, int shopId, GameInfo gameInfo) {
        super(gameStateManager, gameInfo);
        // ////////FOR TESTING///////////////////////
        // this.gameInfo = gameInfoManager.createGameInfo();
        // this.gameInfo.setCash(55555);
        // ///////////////////////////////
        this.currentShopLevel = getShopLevel(shopId, gameInfo.getLevelForShopId(shopId));
    }

    @Override
    public void buildStage() {

        Table allTable = libgdxControlUtils.createAllScreenTable(gameInfo.getSelectedStage());
        Table contentTable = libgdxControlUtils.createContentTable();
        allTable.setFillParent(true);
        headerInfo = libgdxControlUtils.createHeaderTable(gameInfo.getSelectedStage(), gameInfo.getCurrentDay(), gameInfo.getCash(), gameStateManager, gameInfo);
        allTable.add(headerInfo.getHeaderTable()).height(getHeaderHeight()).width(ScreenDimensionsManager.getScreenWidth());
        allTable.row();
        allTable.add(contentTable).height(ScreenDimensionsManager.getScreenHeight() - getHeaderHeight()).width(ScreenDimensionsManager.getScreenWidth());

        ScrollPaneStyle scrollPaneStyle = new ScrollPaneStyle();
        scrollPane = new ScrollPane(createShopsTable(), scrollPaneStyle);
        contentTable.add(scrollPane).width(ScreenDimensionsManager.getScreenWidth() / 1.3f);

        contentTable.add(backButton).align(Align.top).width(getButtonSide()).height(getButtonSide()).padTop(getShopPadding()).padLeft(getShopPadding());

        addActor(allTable);
    }

    @Override
    public void onBackKeyPress() {
        gameStateManager.setUpgradeShopState(gameInfo);
    }

    private Table createShopsTable() {
        Table allContainer = new Table(skin);
        float padding = getShopPadding();
        allContainer.row().padBottom(padding).padTop(padding);
        for (int i = 1; i <= ShopLevel.NR_OF_SHOP_ITEMS; i++) {
            ShopLevel shopLevel = this.currentShopLevel.getItem(i);
            allContainer.add(createShop(shopLevel));
            allContainer.row().padBottom(padding);
        }
        refreshScrollShop();
        return allContainer;
    }

    private float getShopPadding() {
        return Utils.getValueForDisplayHeightPercent(5);
    }

    private Table createShop(ShopLevel shopLevel) {
        Table allContainer = new Table(skin);
        Table buyBtnTextContainer = new Table(skin);
        ImageButton buyButton = new ImageButton(skin);
        buyButton.add(buyBtnTextContainer);
        buyButtonClick(buyButton, shopLevel.getLevel());
        Table imgLevelContainer = new Table(skin);
        Drawable buttonPressedDrawable = skin.getDrawable("grey_right_border");
        imgLevelContainer.setBackground(buttonPressedDrawable);

        Table levelContainer = createLevelContainer(shopLevel.getAttrLevel(), shopLevel.getAttrName());

        imgLevelContainer.add(levelContainer)
                .height(getShopHeight())
                .width(getShopWidth() - getImgContainerWidth());

        Table imgContainer = createImageContainer(shopLevel);
        imgLevelContainer.add(imgContainer)
                .height(getShopHeight())
                .width(getImgContainerWidth());

        allContainer.add(imgLevelContainer)
                .height(getShopHeight())
                .width(getShopWidth());
        allContainer.add(buyButton)
                .height(getShopHeight())
                .width(getBuyBtnWidth());

        scrollShopInfo.getBuyBtns().add(buyButton);
        scrollShopInfo.getBuyBtnTextContainer().add(buyBtnTextContainer);

        return allContainer;
    }

    private void refreshScrollShop() {
        for (int i = 1; i <= ShopLevel.NR_OF_SHOP_ITEMS; i++) {
            ShopLevel shopLevel = this.currentShopLevel.getItem(i);
            ImageButton btn = (ImageButton) scrollShopInfo.getBuyBtns().get(i - 1);
            Table btnTable = scrollShopInfo.getBuyBtnTextContainer().get(i - 1);
            if (btnTable.getChildren().size > 0) {
                btnTable.getChildren().items[0].remove();
            }
            btn.setTouchable(Touchable.disabled);
            if (shopLevel.getLevel() <= getBoughtLevel()
                    && shopLevel.getLevel() != gameInfo.getLevelForShopId(currentShopLevel.getShopId())) {
                btnTable.setBackground(Utils.getDrawable(Resource.checked_disabled));
                btn.setTouchable(Touchable.enabled);
            } else if (shopLevel.getLevel() == gameInfo.getLevelForShopId(currentShopLevel.getShopId())) {
                SpriteDrawable drawable = Utils.getDrawable(Resource.checked);
                btnTable.setBackground(drawable);
            } else {
                Label label = c.label(getLabel("buy"));
                label.setFontScale(libgdxControlUtils.getFontScale());
                btnTable.add(label);
                if (shopLevel.getLevel() > getBoughtLevel() + 1) {
                    label.getStyle().font = Game.getInstance().getFontManager().getFont(FontColor.GRAY);
                } else {
                    if (!affordNextLevelForShop(gameInfo.getCash(), currentShopLevel, getBoughtLevel())) {
                        BitmapFont font = Game.getInstance().getFontManager().getFont(FontColor.RED);
                        label.getStyle().font = font;
                        scrollShopInfo.getPriceLabels().get(i - 1).getStyle().font = font;
                    } else {
                        btn.setTouchable(Touchable.enabled);
                    }
                }
            }
        }
    }

    static boolean affordNextLevelForShop(int currentCash, ShopLevel shopLevel, int boughtLevel) {
        return currentCash > shopLevel.getItem(boughtLevel + 1).getPrice();
    }

    private void buyButtonClick(ImageButton buyButton, final Integer selectedLevel) {
        buyButton.addListener(new ClickListener() {
            @SuppressWarnings("synthetic-access")
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (selectedLevel > getBoughtLevel()) {
                    incrementShop();
                } else {
                    selectPreviousItem(selectedLevel);
                }
                refreshScrollShop();
            }
        });
    }

    private void selectPreviousItem(int newLevel) {
        gameInfoManager.setShopLevel(gameInfo, currentShopLevel.getShopId(), newLevel);
        currentShopLevel = currentShopLevel.getItem(newLevel);
    }

    private int getBoughtLevel() {
        return gameInfoManager.getBoughtLevel(currentShopLevel.getShopId());
    }

    private void incrementShop() {
        if (currentShopLevel.getLevel() < getTotalNrOfLevels(currentShopLevel.getShopId())) {
            processDisplayRocketTutorial();
            gameInfoManager.incrementShopLevel(gameInfo, currentShopLevel.getShopId());
            currentShopLevel = currentShopLevel.getItem(currentShopLevel.getLevel() + 1);
            gameInfoManager.updateCash(gameInfo, gameInfo.getCash() - currentShopLevel.getPrice());
            libgdxControlUtils.playSound(Utils.getSound(Resource.sound_bought));
        }
        LibgdxControlUtils.refreshHeaderInfo(headerInfo, gameInfo.getCurrentDay(), gameInfo.getCash());
    }

    private void processDisplayRocketTutorial() {
        if (RocketLevel.SHOP_ID == currentShopLevel.getShopId() && currentShopLevel.getLevel() == 0) {
            gameInfoManager.setDisplayRocketTutorial(true);
        }
    }

    private float getTotalNrOfLevels(int shopType) {
        float totalNrOfLevels = -1;
        if (shopType == GliderLevel.SHOP_ID) {
            totalNrOfLevels = GliderLevel.values().length;
        } else if (shopType == SledLevel.SHOP_ID) {
            totalNrOfLevels = SledLevel.values().length;
        } else if (shopType == RocketLevel.SHOP_ID) {
            totalNrOfLevels = RocketLevel.values().length;
        }
        return totalNrOfLevels - 1;
    }

    private ShopLevel getShopLevel(int shopType, int selectedLevel) {
        ShopLevel shopLevel = null;
        if (shopType == GliderLevel.SHOP_ID) {
            shopLevel = GliderLevel.NO_LEVEL.getItem(selectedLevel);
        } else if (shopType == SledLevel.SHOP_ID) {
            shopLevel = SledLevel.NO_LEVEL.getItem(selectedLevel);
        } else if (shopType == RocketLevel.SHOP_ID) {
            shopLevel = RocketLevel.NO_LEVEL.getItem(selectedLevel);
        }
        return shopLevel;
    }

    private Table createLevelContainer(int[] attrValues, String[] attrLabels) {
        Drawable backgroundDrawable = skin.getDrawable("grey_left_border");
        Drawable notAttrDrawable = skin.getDrawable("grey_normal");
        Drawable yesAttrDrawable = skin.getDrawable("green_pressed");
        float levelContainerWidth = getShopWidth() - getImgContainerWidth();
        float padding = getLevelContainerLabelTableHeight() * 0.2f;
        float attrTableHeight = getLevelContainerLabelTableHeight() * 1.5f;
        Table levelContainer = new Table(skin);
        levelContainer.setBackground(backgroundDrawable);
        for (int i = 0; i < attrValues.length; i++) {
            Label levelLabel = c.label(attrLabels[i]);
            levelLabel.setFontScale(libgdxControlUtils.getFontScale() / 1.5f);
            Table labelTable = new Table(skin);
            labelTable.add(levelLabel);
            Table attrTable = new Table(skin);
            int nrOfCells = 12;
            for (int j = 1; j <= nrOfCells; j++) {
                Table attrCell = new Table(skin);
                attrCell.setBackground(attrValues[i] >= j ? yesAttrDrawable : notAttrDrawable);
                attrTable.add(attrCell)
                        .height(attrTableHeight)
                        .width((levelContainerWidth - padding * 8) / nrOfCells);
            }
            levelContainer.add(labelTable)
                    .height(getLevelContainerLabelTableHeight())
                    .width(levelContainerWidth).padLeft(padding).padRight(padding);
            levelContainer.row();
            levelContainer.add(attrTable)
                    .height(attrTableHeight)
                    .width(levelContainerWidth).padLeft(padding).padRight(padding).padBottom(padding);
            levelContainer.row();
        }
        return levelContainer;
    }

    private Table createImageContainer(ShopLevel shopLevel) {
        Table imgContainer = new Table(skin);
        Table imgContainerLabelTable = new Table(skin);
        Label imgLabel = c.label(shopLevel.getLevelName());
        imgLabel.setFontScale(libgdxControlUtils.getFontScale() / 1.5f);
        imgContainerLabelTable
                .add(imgLabel)
                .padTop(10);
        Table imgContainerImgTable = new Table(skin);
        Table imgContainerImgImgTable = new Table(skin);
        Table imgContainerImgPriceTable = new Table(skin);
        float imgContainerHeight = getShopHeight() - getImgContainerLabelTableHeight();

        setShopImg(shopLevel, imgContainerImgImgTable);
        Sprite sprite = Utils.getDrawable(Resource.getMenuShopResourceForScrollShop(shopLevel.getShopId(), shopLevel.getLevel())).getSprite();
        imgContainerImgTable.add(imgContainerImgImgTable)
                .width(Utils.getValueForPercent(sprite.getWidth(), 45))
                .height(Utils.getValueForPercent(sprite.getHeight(), 45))
                .padTop(10);
        imgContainerImgTable.row();

        Label priceLabel = c.label(Integer.toString(shopLevel.getPrice()));
        priceLabel.setFontScale(libgdxControlUtils.getFontScale());
        Image coinImg = new Image(skin, "coin");
        coinImg.setScale(0.52f);
        imgContainerImgPriceTable.add(priceLabel);
        float coinHeight = Utils.getValueForRatio(37);
        imgContainerImgPriceTable
                .add(coinImg)
                .padBottom(18)
                .height(coinHeight)
                .width(coinHeight);
        imgContainerImgTable
                .add(imgContainerImgPriceTable)
                .padTop(10)
                .padLeft(Utils.getValueForDisplayHeightPercent(10))
                .height(getImgContainerLabelTableHeight())
                .width(getImgContainerWidth());
        imgContainer
                .add(imgContainerLabelTable)
                .height(getImgContainerLabelTableHeight())
                .width(getImgContainerWidth());
        imgContainer.row();
        imgContainer
                .add(imgContainerImgTable)
                .height(imgContainerHeight)
                .width(getImgContainerWidth());

        scrollShopInfo.getPriceLabels().add(priceLabel);

        return imgContainer;
    }

    private void setShopImg(ShopLevel shopLevel, Table imgContainerImgImgTable) {
        Drawable drawable = Utils.getDrawable(Resource.getMenuShopResourceForScrollShop(shopLevel.getShopId(), shopLevel.getLevel()));
        imgContainerImgImgTable.setBackground(drawable);
    }

    private float getShopHeight() {
        return Utils.getValueForDisplayHeightPercent(45);
    }

    private float getShopWidth() {
        return Utils.getValueForRatio(Utils.getValueForPercent(ScreenDimensionsManager.getScreenWidth(), 50));
    }

    private float getImgContainerWidth() {
        return Utils.getValueForPercent(getShopWidth(), 48);
    }

    private float getBuyBtnWidth() {
        return Utils.getValueForRatio(Utils.getValueForPercent(ScreenDimensionsManager.getScreenWidth(), 15));
    }

    private float getImgContainerLabelTableHeight() {
        return Utils.getValueForPercent(getShopHeight(), 20);
    }

    private float getLevelContainerLabelTableHeight() {
        return Utils.getValueForPercent(getShopHeight(), 11);
    }

    private float getHeaderHeight() {
        return libgdxControlUtils.getHeaderHeight();
    }

    private float getButtonSide() {
        return Utils.getValueForDisplayHeightPercent(25);
    }

    @Override
    public void update(float dt) {
        super.update(dt);
        float shopY = (currentShopLevel.getLevel()) * getShopHeight() + (currentShopLevel.getLevel());
        float lastShopY = (ShopLevel.NR_OF_SHOP_ITEMS - 1) * (getShopHeight());

        // scrollPanePositionInit needs to be used otherwise the scrollTo wont
        // work
        if (scrollPanePositionInit < 2) {
            scrollPane.scrollTo(0, lastShopY - shopY, ScreenDimensionsManager.getScreenWidth(), ScreenDimensionsManager.getScreenHeight());
            scrollPanePositionInit++;
        }
    }

}

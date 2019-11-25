package libgdx.game.lib.learntofly.util;

import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;

import libgdx.controls.button.ButtonBuilder;
import libgdx.controls.button.MyButton;
import libgdx.controls.label.MyWrappedLabel;
import libgdx.controls.label.MyWrappedLabelConfigBuilder;
import libgdx.game.Game;
import libgdx.game.lib.learntofly.hud.HUD;
import libgdx.resources.FontManager;
import libgdx.utils.model.FontColor;
import libgdx.utils.model.FontConfig;

public class LibgdxControlCreator {

    private Skin skin;
    private LibgdxControlUtils libgdxControlUtils;

    public LibgdxControlCreator(Skin skin, LibgdxControlUtils libgdxControlUtils) {
        super();
        this.skin = skin;
        this.libgdxControlUtils = libgdxControlUtils;
    }

    public TextButton textButton(String text, String styleName) {
        return textButton(text, new FontConfig(FontConfig.FONT_SIZE * 2), styleName);
    }

    public MyButton textButton(String text, FontConfig fontConfig, String styleName) {
        MyButton btn = new ButtonBuilder().setDefaultButton().setText(text).build();
        btn.getStyle().font = Game.getInstance().getFontManager().getFont(fontConfig);
        btn.getStyle().font.getData().setScale(HUD.getFontScale());
        return btn;
    }

    public Table table(Resource resource) {
        Table table = new Table(skin);
        table.setBackground(Utils.getDrawable(resource));
        table.setTouchable(Touchable.enabled);
        return table;
    }

    public Image image(String name) {
        Image image = new Image(skin, name);
        image.setScale(FontManager.getNormalFontDim());
        return image;
    }

    public Image image(Drawable drawable) {
        Image image = new Image();
        image.setDrawable(drawable);
        image.setScale(libgdxControlUtils.getHeightDisplayRatio());
        return image;
    }

    public MyWrappedLabel label(String text) {
        return new MyWrappedLabel(new MyWrappedLabelConfigBuilder().setFontConfig(new FontConfig(
                FontColor.BLACK.getColor(),
                FontColor.BLACK.getColor(),
                FontConfig.FONT_SIZE * 2,
                0f)).setText(text).build());
    }
}

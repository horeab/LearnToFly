package libgdx.game.lib.learntofly.util;

import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;

import libgdx.game.Game;
import libgdx.game.lib.learntofly.hud.HUD;
import libgdx.resources.FontManager;
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

    public TextButton textButton(String text, FontConfig fontConfig, String styleName) {
        TextButton btn = new TextButton(text, skin, styleName);
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

    public Label label(String text) {
        Label label = new Label(text, skin);
        label.setFontScale(FontManager.getNormalFontDim());
        label.getStyle().font = Game.getInstance().getFontManager().getFont(new FontConfig(FontConfig.FONT_SIZE * 2));
        return label;
    }
}

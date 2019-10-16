package libgdx.game.lib.learntofly.util;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;

import libgdx.game.Game;
import libgdx.resources.FontManager;

public class LibgdxControlCreator {

    private Skin skin;
    private LibgdxControlUtils libgdxControlUtils;

    public LibgdxControlCreator(Skin skin, LibgdxControlUtils libgdxControlUtils) {
        super();
        this.skin = skin;
        this.libgdxControlUtils = libgdxControlUtils;
    }

    public TextButton textButton(String text, String styleName) {
        TextButton btn = new TextButton(text, skin, styleName);
        btn.getStyle().font = Game.getInstance().getFontManager().getFont();
        btn.getStyle().font.getData().setScale(libgdxControlUtils.getHeightDisplayRatio());
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
        image.setScale(libgdxControlUtils.getHeightDisplayRatio());
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
        label.getStyle().font = Game.getInstance().getFontManager().getFont();
        label.getStyle().font.getData().setScale(libgdxControlUtils.getHeightDisplayRatio());
        return label;
    }
}

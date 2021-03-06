package com.obstacleavoid.assets;

import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

public class AssetDescriptors {


    public static final AssetDescriptor<BitmapFont> FONT = new AssetDescriptor<BitmapFont>(AssetPaths.UI_FONT, BitmapFont.class);

    /*public static final AssetDescriptor<Texture> PLAYER = new AssetDescriptor<Texture>(AssetPaths.PLAYER, Texture.class);
    public static final AssetDescriptor<Texture> OBSTACLE = new AssetDescriptor<Texture>(AssetPaths.OBSTACLE, Texture.class);
    public static final AssetDescriptor<Texture> BACKGROUND = new AssetDescriptor<Texture>(AssetPaths.BACKGROUND, Texture.class);*/

    public static final AssetDescriptor<TextureAtlas> GAME_PLAY = new AssetDescriptor<TextureAtlas>(AssetPaths.GAME_PLAY, TextureAtlas.class);

    private AssetDescriptors() {
    }
}

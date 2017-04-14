package taylor.murias.managers;

import org.andengine.engine.Engine;
import org.andengine.engine.camera.Camera;
import org.andengine.extension.texturepacker.opengl.texture.util.texturepacker.TexturePack;
import org.andengine.extension.texturepacker.opengl.texture.util.texturepacker.TexturePackLoader;
import org.andengine.extension.texturepacker.opengl.texture.util.texturepacker.TexturePackTextureRegionLibrary;
import org.andengine.extension.texturepacker.opengl.texture.util.texturepacker.exception.TexturePackParseException;
import org.andengine.opengl.font.Font;
import org.andengine.opengl.font.FontFactory;
import org.andengine.opengl.texture.ITexture;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.texture.region.ITiledTextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import taylor.murias.assets.TurboSquaresTextures;
import taylor.murias.turbosquares.MainActivity;

import android.graphics.Color;

public class ResourceManager {
	
	/* Single class instance */
	private static final ResourceManager INSTANCE = new ResourceManager();
	
	/* Colours */
	public static final int RED = Color.rgb(255, 0, 0);
	public static final int GREEN = Color.rgb(0, 170, 0);
	public static final int BLUE = Color.rgb(30, 144, 255);
	
	/* Common objects */
	public Engine engine;
	public MainActivity activity;
	public Camera camera;
	public VertexBufferObjectManager vbom;
	
	/* Fonts */
	public Font titleFont, secondaryFont, arcadeFont, loadingFont;
	
	/* Texture packer stuff */
	public TexturePackTextureRegionLibrary texturePackLibrary;
	public TexturePack texturePack;
	
	/* Texture regions */
	public ITextureRegion backgroundRegion;  //Every scene
	public ITextureRegion thumbRegion;  //Splash scene
	public ITextureRegion startGameButtonRegion, optionsButtonRegion, highScoresButtonRegion, howToPlayButtonRegion;  //Main menu scene
	public ITextureRegion goodSquareRegion, badSquareRegion, resumeButtonRegion, quitButtonRegion, goToMenuButtonRegion;  //Game scene
	public ITiledTextureRegion easyButtonRegion, mediumButtonRegion, hardButtonRegion, onButtonRegion, offButtonRegion;  //Options scene
	
	public static void prepare(Engine engine, MainActivity activity, Camera camera, VertexBufferObjectManager vbom) {
		getInstance().engine = engine;
		getInstance().activity = activity;
		getInstance().camera = camera;
		getInstance().vbom = vbom;
	}
	
	public void loadGenericResources() {
		try {
			texturePack = new TexturePackLoader(activity.getTextureManager(), "gfx/")
					.loadFromAsset(activity.getAssets(), "turbo_squares_textures.xml");
			texturePack.loadTexture();
			texturePackLibrary = texturePack.getTexturePackTextureRegionLibrary();
		} catch (final TexturePackParseException e) {
			
		}
		backgroundRegion = texturePackLibrary.get(TurboSquaresTextures.TURBO_SQUARES_BACKGROUND_ID);
		
		FontFactory.setAssetBasePath("font/");
		final ITexture loadingFontTexture = new BitmapTextureAtlas(activity.getTextureManager(),
				256, 256, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		loadingFont = FontFactory.createStrokeFromAsset(activity.getFontManager(), loadingFontTexture,
				activity.getAssets(), "BUSTER.TTF", 60, true, RED, 2, RED);
		loadingFont.load();
	}
	
	public void loadSplashResources() {
		thumbRegion = texturePackLibrary.get(TurboSquaresTextures.THUMB_ID);
	}
	
	public void loadMenuResources() {
		startGameButtonRegion = texturePackLibrary.get(TurboSquaresTextures.START_GAME_BUTTON_ID);
		optionsButtonRegion = texturePackLibrary.get(TurboSquaresTextures.OPTIONS_BUTTON_ID);
		highScoresButtonRegion = texturePackLibrary.get(TurboSquaresTextures.HIGH_SCORES_BUTTON_ID);
		howToPlayButtonRegion = texturePackLibrary.get(TurboSquaresTextures.HOW_TO_PLAY_BUTTON_ID);
		
		FontFactory.setAssetBasePath("font/");
		final ITexture titleFontTexture = new BitmapTextureAtlas(activity.getTextureManager(),
				256, 256, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		titleFont = FontFactory.createStrokeFromAsset(activity.getFontManager(), titleFontTexture,
				activity.getAssets(), "BUSTER.TTF", 75, true, RED, 2, RED);
		titleFont.load();
	}
	
	public void loadGameResources() {
		goodSquareRegion = texturePackLibrary.get(TurboSquaresTextures.GOOD_SQUARE_ID);
		badSquareRegion = texturePackLibrary.get(TurboSquaresTextures.BAD_SQUARE_ID);
		goToMenuButtonRegion = texturePackLibrary.get(TurboSquaresTextures.GO_TO_MENU_BUTTON_ID);
		resumeButtonRegion = texturePackLibrary.get(TurboSquaresTextures.RESUME_BUTTON_ID);
		quitButtonRegion = texturePackLibrary.get(TurboSquaresTextures.QUIT_BUTTON_ID);
		
		FontFactory.setAssetBasePath("font/");
		final ITexture titleFontTexture = new BitmapTextureAtlas(activity.getTextureManager(),
				256, 256, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		titleFont = FontFactory.createStrokeFromAsset(activity.getFontManager(), titleFontTexture,
				activity.getAssets(), "BUSTER.TTF", 65, true, RED, 2, RED);
		final ITexture arcadeFontTexture = new BitmapTextureAtlas(activity.getTextureManager(),
				256, 256, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		arcadeFont = FontFactory.createStrokeFromAsset(activity.getFontManager(), arcadeFontTexture,
				activity.getAssets(), "ARCADE.TTF", 100, true, BLUE, 2, BLUE);
		titleFont.load();
		arcadeFont.load();
	}
	
	public void loadOptionsResources() {
		easyButtonRegion = texturePackLibrary.get(TurboSquaresTextures.TILED_EASY_BUTTON_ID, 2, 1);
		mediumButtonRegion = texturePackLibrary.get(TurboSquaresTextures.TILED_MEDIUM_BUTTON_ID, 2, 1);
		hardButtonRegion = texturePackLibrary.get(TurboSquaresTextures.TILED_HARD_BUTTON_ID, 2, 1);
		onButtonRegion = texturePackLibrary.get(TurboSquaresTextures.TILED_ON_BUTTON_ID, 2, 1);
		offButtonRegion = texturePackLibrary.get(TurboSquaresTextures.TILED_OFF_BUTTON_ID, 2, 1);
		
		FontFactory.setAssetBasePath("font/");
		final ITexture titleFontTexture = new BitmapTextureAtlas(activity.getTextureManager(),
				256, 256, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		titleFont = FontFactory.createStrokeFromAsset(activity.getFontManager(), titleFontTexture,
				activity.getAssets(), "BUSTER.TTF", 60, true, RED, 2, RED);
		final ITexture secondaryFontTexture = new BitmapTextureAtlas(activity.getTextureManager(),
				256, 256, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		secondaryFont = FontFactory.createStrokeFromAsset(activity.getFontManager(), secondaryFontTexture,
				activity.getAssets(), "BUSTER.TTF", 30, true, GREEN, 2, GREEN);
		titleFont.load();
		secondaryFont.load();
	}
	
	public void loadHighScoresResources() {
		FontFactory.setAssetBasePath("font/");
		final ITexture titleFontTexture = new BitmapTextureAtlas(activity.getTextureManager(),
				256, 256, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		titleFont = FontFactory.createStrokeFromAsset(activity.getFontManager(), titleFontTexture,
				activity.getAssets(), "BUSTER.TTF", 50, true, RED, 2, RED);
		final ITexture secondaryFontTexture = new BitmapTextureAtlas(activity.getTextureManager(),
				256, 256, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		secondaryFont = FontFactory.createStrokeFromAsset(activity.getFontManager(), secondaryFontTexture,
				activity.getAssets(), "BUSTER.TTF", 30, true, GREEN, 2, GREEN);
		final ITexture arcadeFontTexture = new BitmapTextureAtlas(activity.getTextureManager(),
				256, 256, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		arcadeFont = FontFactory.createStrokeFromAsset(activity.getFontManager(), arcadeFontTexture,
				activity.getAssets(), "ARCADE.TTF", 60, true, BLUE, 2, BLUE);
		titleFont.load();
		secondaryFont.load();
		arcadeFont.load();
	}
	
	public void loadHowToPlayResources() {
		FontFactory.setAssetBasePath("font/");
		final ITexture secondaryFontTexture = new BitmapTextureAtlas(activity.getTextureManager(),
				256, 256, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		secondaryFont = FontFactory.createStrokeFromAsset(activity.getFontManager(), secondaryFontTexture,
				activity.getAssets(), "BUSTER.TTF", 35, true, RED, 2, RED);
		secondaryFont.load();
	}
	
	public void unloadSplashScene() {
		thumbRegion = null;
	}
	
	public void unloadMenuTextures() {
		startGameButtonRegion = null;
		optionsButtonRegion = null;
		highScoresButtonRegion = null;
		howToPlayButtonRegion = null;
		titleFont.unload();
	}
	
	public void unloadGameTextures() {
		goodSquareRegion = null;
		badSquareRegion = null;
		goToMenuButtonRegion = null;
		resumeButtonRegion = null;
		quitButtonRegion = null;
		titleFont.unload();
		arcadeFont.unload();
	}
	
	public void unloadOptionsTextures() {
		titleFont.unload();
		secondaryFont.unload();
	}
	
	public void unloadHighScoresTextures() {
		titleFont.unload();
		secondaryFont.unload();
		arcadeFont.unload();
	}
	
	public void unloadHowToPlayTextures() {
		secondaryFont.unload();
	}
	
	public static ResourceManager getInstance() {
		return INSTANCE;
	}

}

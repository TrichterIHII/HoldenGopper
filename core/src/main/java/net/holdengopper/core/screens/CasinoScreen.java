package net.holdengopper.core.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.collision.BoundingBox;
import net.holdengopper.core.screens.menus.MenuScreen;
import net.mgsx.gltf.loaders.glb.GLBLoader;
import net.mgsx.gltf.scene3d.lights.DirectionalLightEx;
import net.mgsx.gltf.scene3d.scene.Scene;
import net.mgsx.gltf.scene3d.scene.SceneAsset;
import net.mgsx.gltf.scene3d.scene.SceneManager;
import net.mgsx.gltf.scene3d.shaders.PBRShaderConfig;
import net.mgsx.gltf.scene3d.shaders.PBRShaderProvider;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class CasinoScreen extends MenuScreen {

    private final PerspectiveCamera camera;
    private final SceneManager sceneManager;

    private final Vector3 dir = new Vector3();
    private final Vector3 up = new Vector3();
    private final Vector3 right = new Vector3();

    private final List<SceneAsset> sceneAssets = new ArrayList<>();
    private final List<Scene> scenes = new ArrayList<>();

    private float playerX;
    private float playerY;

    public float sensitivity = 0.2f;

    public CasinoScreen() {
        Gdx.gl.glEnable(GL20.GL_DEPTH_TEST);
        Gdx.input.setCursorCatched(true);

        camera = new PerspectiveCamera(67, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        camera.position.set(0f, 0f, 10f);
        camera.lookAt(0f, 0f, 0f);
        camera.near = 0.1f;
        camera.far = 1000000f;
        camera.update();

        playerX = camera.position.x;
        playerY = camera.position.y;

        sceneManager = new SceneManager();
        PBRShaderConfig config = new PBRShaderConfig();
        config.numBones = 128;
        sceneManager.setShaderProvider(new PBRShaderProvider(config));

        sceneManager.setCamera(camera);

        DirectionalLightEx light = new DirectionalLightEx();
        light.direction.set(-1f, -0.8f, -0.2f).nor();
        light.color.set(1f, 1f, 1f, 1f);
        light.intensity = 5f;
        sceneManager.environment.add(light);

        // WALLS
        loadModel("assets/textures/3d/casino_wall.glb", 0f, 0f, 0f, 1f);
        // ROULETTE
        loadModel("assets/textures/3d/roulette_table.glb", 200f, 40f, 160f, 5f);
        loadModel("assets/textures/3d/victorian_chair.glb", 295f, 0, 80f, 50f, 0f, 360f - 45f / 2f, 0f);
        loadModel("assets/textures/3d/victorian_chair.glb", 340f, 0, 80f, 50f, 0f, 355, 0f);
        loadModel("assets/textures/3d/victorian_chair.glb", 290f, 0, 220f, 50f, 0f, 180f, 0f);
        loadModel("assets/textures/3d/victorian_chair.glb", 340f, 0, 210f, 50f, 0f, 180f, 0f);
        // BLACKJACK
        loadModel("assets/textures/3d/black_jack_table.glb", 480f, 0f, 60f, 0.25f, 0f, 180f, 0f);
        loadModel("assets/textures/3d/heisenberg.glb", 480f, 0f, 30f, 40f, 360f - 90f, 0f, 0f);
        // CARPET
        SceneAsset carpet = loadAsset("assets/textures/3d/persian_carpet.glb");
        for (int i = 0; i < 11 * 100; i += 100) {
            for (float f = 0; f < 8 * 145.7f; f += 145.7f) {
                addInstance(carpet, i, 0f, f, 31f, 90f, 0f, 0f);
            }
        }
    }

    private void loadModel(String path, float x, float y, float z, float scale) {
        loadModel(path, x, y, z, scale, 0f, 0f, 0f);
    }

    private void loadModel(String path, float x, float y, float z, float scale, float rotX, float rotY, float rotZ) {
        SceneAsset asset = new GLBLoader().load(Gdx.files.internal(path));
        Scene scene = new Scene(asset.scene);
        scene.modelInstance.transform
                .setToTranslation(x, y, z)
                .scl(scale)
                .rotate(Vector3.X, rotX)
                .rotate(Vector3.Y, rotY)
                .rotate(Vector3.Z, rotZ);
        sceneManager.addScene(scene);
        sceneAssets.add(asset);
        scenes.add(scene);
    }

    private SceneAsset loadAsset(String path) {
        SceneAsset asset = new GLBLoader().load(Gdx.files.internal(path));
        sceneAssets.add(asset);
        return asset;
    }

    private void addInstance(@NotNull SceneAsset asset, float x, float y, float z, float scale, float rx, float ry, float rz) {
        Scene scene = new Scene(asset.scene);
        scene.modelInstance.transform
                .setToTranslation(x, y, z)
                .scl(scale)
                .rotate(Vector3.X, rx)
                .rotate(Vector3.Y, ry)
                .rotate(Vector3.Z, rz);

        sceneManager.addScene(scene);
        scenes.add(scene);
    }


    private final BoundingBox ROULETTE_HITBOX = new BoundingBox();
    private final BoundingBox BLACKJACK_HITBOX = new BoundingBox();



    private boolean isGameReachable() {

    }

    private void chooseGame() {

    }

    @Override
    public void render(float delta) {
        playerX = camera.position.x;
        playerY = camera.position.y;

        float speed = 100f * delta;

        float yaw = -Gdx.input.getDeltaX() * sensitivity;
        float pitch = -Gdx.input.getDeltaY() * sensitivity;
        camera.rotate(Vector3.Y, yaw);
        right.set(camera.direction).crs(Vector3.Y).nor();
        camera.rotate(right, pitch);
        camera.up.set(Vector3.Y);
        camera.update();

        dir.set(camera.direction).scl(speed);
        if (Gdx.input.isKeyPressed(Input.Keys.W)) camera.position.add(dir);
        if (Gdx.input.isKeyPressed(Input.Keys.S)) camera.position.sub(dir);

        right.set(camera.direction).crs(camera.up).nor().scl(speed);
        if (Gdx.input.isKeyPressed(Input.Keys.A)) camera.position.sub(right);
        if (Gdx.input.isKeyPressed(Input.Keys.D)) camera.position.add(right);

        up.set(camera.up).scl(speed);
        if (Gdx.input.isKeyPressed(Input.Keys.SPACE))      camera.position.add(up);
        if (Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT)) camera.position.sub(up);

        if (Gdx.input.isKeyPressed(Input.Keys.F) && isGameReachable())  chooseGame();

        if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) Gdx.app.exit();

        camera.update();

        Gdx.gl.glViewport(0, 0, Gdx.graphics.getBackBufferWidth(), Gdx.graphics.getBackBufferHeight());
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);

        sceneManager.update(delta);
        sceneManager.render();
    }

    @Override
    public void resize(int width, int height) {
        sceneManager.updateViewport(width, height);
    }

    @Override
    public void dispose() {
        super.dispose();
        for (SceneAsset asset : sceneAssets) asset.dispose();
    }

    @Override
    public void hide() {
        dispose();
        Gdx.input.setCursorCatched(false);
    }
}
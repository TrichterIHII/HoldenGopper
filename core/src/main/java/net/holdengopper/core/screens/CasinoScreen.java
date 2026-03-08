package net.holdengopper.core.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.math.Vector3;
import net.holdengopper.core.screens.menus.MenuScreen;
import net.mgsx.gltf.loaders.glb.GLBLoader;
import net.mgsx.gltf.scene3d.lights.DirectionalLightEx;
import net.mgsx.gltf.scene3d.scene.Scene;
import net.mgsx.gltf.scene3d.scene.SceneAsset;
import net.mgsx.gltf.scene3d.scene.SceneManager;

import java.util.ArrayList;
import java.util.List;

public class CasinoScreen extends MenuScreen {

    private final PerspectiveCamera camera;
    private final SceneManager sceneManager;

    private final Vector3 dir = new Vector3();
    private final Vector3 up = new Vector3();

    private final List<SceneAsset> sceneAssets = new ArrayList<>();
    private final List<Scene> scenes = new ArrayList<>();

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

        sceneManager = new SceneManager();
        sceneManager.setCamera(camera);

        DirectionalLightEx light = new DirectionalLightEx();
        light.direction.set(-1f, -0.8f, -0.2f).nor();
        light.color.set(1f, 1f, 1f, 1f);
        light.intensity = 5f;
        sceneManager.environment.add(light);

        loadModel("assets/textures/3d/casino_wall.glb", 0f, 0f, 0f, 1f);
        for (int i = 0; i < 11 * 100; i += 100) {
            for (float f = 0; f < 8 * 145.7f; f += 145.7f) {
                loadModel("assets/textures/3d/persian_carpet.glb", i, 0f, f, 31f, 90f, 0f, 0f);
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

    @Override
    public void render(float delta) {
        float speed = 100f * delta;

        float yaw = -Gdx.input.getDeltaX() * sensitivity;
        float pitch = -Gdx.input.getDeltaY() * sensitivity;
        camera.rotate(Vector3.Y, yaw);
        Vector3 right = new Vector3(camera.direction).crs(Vector3.Y).nor();
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
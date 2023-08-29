package org.yunghegel.gdx.utils.graphics.model.gltf;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import net.mgsx.gltf.loaders.gltf.GLTFLoader;
import net.mgsx.gltf.scene3d.scene.Scene;
import net.mgsx.gltf.scene3d.scene.SceneAsset;

public class GLTFSampleLoader {

    public static SceneAsset load(GLTFSamples sample, String subpath) {
        return new GLTFLoader().load(Gdx.files.internal(sample.path(subpath)));
    }

    public static SceneAsset load(GLTFSamples sample) {
        return new GLTFLoader().load(Gdx.files.internal(sample.path()));
    }

    private static ModelInstance load(SceneAsset sceneAsset) {
        return new Scene(sceneAsset.scene).modelInstance;
    }

    public static ModelInstance loadMI(GLTFSamples sample, String subpath) {
        return load(load(sample, subpath));
    }

    public static ModelInstance loadMI(GLTFSamples sample) {
        return load(load(sample));
    }

    public static SceneAsset loadPath(String path) {
        return new GLTFLoader().load(Gdx.files.internal(path));
    }

    public static ModelInstance loadMIPath(String path) {
        return load(loadPath(path));
    }






}

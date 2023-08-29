package org.yunghegel.gdx.utils.graphics.model;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Mesh;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.Renderable;
import com.badlogic.gdx.graphics.g3d.model.Node;
import com.badlogic.gdx.graphics.g3d.model.NodePart;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pool;
import org.yunghegel.gdx.utils.Pools;

import java.util.function.Consumer;

public class ModelUtils {

    public static void eachNodeRecusrsive(Iterable<Node> nodes, Consumer<Node> callback){
        for(Node node : nodes){
            callback.accept(node);
            if(node.hasChildren()) eachNodeRecusrsive(node.getChildren(), callback);
        }
    }

    public static void eachNodeRecusrsive(Node node, Consumer<Node> callback){
        callback.accept(node);
        if(node.hasChildren()) eachNodeRecusrsive(node.getChildren(), callback);
    }

    public static Array<NodePart> collectNodeParts(ModelInstance modelInstance) {
        Array<NodePart> results = new Array<NodePart>();
        eachNodeRecusrsive(modelInstance.nodes, node->results.addAll(node.parts));
        return results;
    }
    public static Array<Node> collectNodes(ModelInstance modelInstance) {
        Array<Node> results = new Array<Node>();
        eachNodeRecusrsive(modelInstance.nodes, node->results.add(node));
        return results;
    }

    public static void eachNodePartRecusrsive(Iterable<Node> nodes, Consumer<NodePart> callback) {
        eachNodeRecusrsive(nodes, node->{
            for(NodePart part : node.parts){
                callback.accept(part);
            }
        });
    }
    public static void eachNodePartRecusrsive(Node node, Consumer<NodePart> callback) {
        eachNodeRecusrsive(node, n->{
            for(NodePart part : n.parts){
                callback.accept(part);
            }
        });
    }

    private static final Array<Renderable> renderables = new Array<>();
    private static final Pool<Renderable> pool = new Pools.RenderablePool();
    private final static Vector3 tmpVec0 = new Vector3();

    /**
     * Gets the total bone count for the given model based on having
     * one renderable.
     *
     * @param model the model to count bones for
     * @return the bone count
     */
    public static int getBoneCount(Model model) {
        int numBones = 0;

        ModelInstance instance = new ModelInstance(model);
        instance.getRenderables(renderables, pool);

        // Bones appear to be copied to each NodePart
        // So we just count the first renderable that has bones
        // and break
        for (Renderable renderable : renderables) {
            if (renderable.bones != null) {
                numBones += renderable.bones.length;
                break;
            }
        }

        renderables.clear();
        pool.clear();

        return numBones;
    }

    public static boolean isVisible(final Camera cam, final ModelInstance modelInstance, Vector3 center, float radius) {
        tmpVec0.set(center).mul(modelInstance.transform);
        return cam.frustum.sphereInFrustum(tmpVec0, radius);
    }

    /**
     * Checks if visible to camera using boundsInFrustum and dimensions
     */
    public static boolean isVisible(final Camera cam, final ModelInstance modelInstance, Vector3 center, Vector3 dimensions) {
        modelInstance.transform.getTranslation(tmpVec0);
        tmpVec0.add(center);
        return cam.frustum.boundsInFrustum(tmpVec0, dimensions);
    }

    public static int getVerticesCount(Model model) {
        int vertices = 0;
        for (Mesh mesh : model.meshes) {
            vertices += mesh.getNumVertices();
        }
        return vertices;
    }

    /**
     * Get Indices count of a Model
     */
    public static float getIndicesCount(Model model) {
        int indices = 0;
        for (Mesh mesh : model.meshes) {
            indices += mesh.getNumIndices();
        }
        return indices;
    }


}

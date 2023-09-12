package org.yunghegel.gdx.utils.ui.widgets;

import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Tree;
import com.kotcrab.vis.ui.VisUI;
import com.kotcrab.vis.ui.widget.VisImageButton;
import com.kotcrab.vis.ui.widget.VisLabel;
import com.kotcrab.vis.ui.widget.VisTable;
import com.kotcrab.vis.ui.widget.VisTree;
import org.yunghegel.gdx.scenegraph.scene3d.graph.GameObject;
import org.yunghegel.gdx.scenegraph.scene3d.scene.SceneGraph;


public class SceneTree extends VisTable {

    private SceneGraph sceneGraph;
    private VisTree tree;

    public SceneTree(SceneGraph sceneGraph) {
        super();
        this.sceneGraph = sceneGraph;
        tree = new VisTree();
        TitleNode title = new TitleNode("Scene Graph");
        tree.add(title);
        GameObjectNode root = new GameObjectNode(sceneGraph.getRoot());


        traverse(sceneGraph.getRoot(),title);
        add(tree).grow();
    }

    void traverse(GameObject go, Tree.Node parent) {
        GameObjectNode node = new GameObjectNode(go);
        parent.add(node);
        if(go.getChildren()!=null) {
        for (GameObject child : go.getChildren()) {
            traverse(child,node);
        }
        }
    }

    private class GameObjectNodeTable extends VisTable {

        VisLabel name;
        VisImageButton visible = new VisImageButton("eye");

        public GameObjectNodeTable(GameObject go) {
            super();
            name = new VisLabel(go.name);
            add(name).growX().left();
            add(visible).right().size(20);
        }

    }
    private class GameObjectNode extends VisTree.Node<GameObjectNode, GameObject,GameObjectNodeTable> {

        public GameObjectNode(GameObject value) {
            super(new GameObjectNodeTable(value));
        }

    }

    private class TitleTable extends VisTable {
        public TitleTable(String title) {
            super();
            add(new VisLabel(title)).growX().left();
        }
    }


    private class TitleNode extends Tree.Node<VisTree.Node, String, TitleTable>
    {

        public TitleNode(String value) {
            super(new TitleTable(value));
        }

    }



}

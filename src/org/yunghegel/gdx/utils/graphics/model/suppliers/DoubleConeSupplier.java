package org.yunghegel.gdx.utils.graphics.model.suppliers;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Mesh;
import com.badlogic.gdx.graphics.VertexAttributes;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.utils.shapebuilders.ConeShapeBuilder;
import com.badlogic.gdx.math.Matrix4;
import org.yunghegel.gdx.utils.graphics.model.BuilderUtils;

public class DoubleConeSupplier extends InstanceSupplier{

    private int vertices;
    private float radius;
    private float height;
    private int divisions;

    public DoubleConeSupplier(float radius, float height, int vertices, int divisions, Color color){
        super(color);
        this.radius = radius;
        this.height = height;
        this.vertices = vertices;
        this.divisions = divisions;
    }

    public DoubleConeSupplier(float radius, float height, int vertices, int divisions){
        super(BuilderUtils.getRandomColor());
        this.radius = radius;
        this.height = height;
        this.vertices = vertices;
        this.divisions = divisions;
    }


    @Override
    public Model createModel() {

        modelBuilder.begin();

        b = modelBuilder.part("top_cone", primitiveType, attributes, mat);
        ConeShapeBuilder.build(b,radius, height/2, radius,divisions, 0, 360f,true);
        Model top = modelBuilder.end();

        modelBuilder.begin();
        b = modelBuilder.part("bottom_cone", primitiveType, attributes, mat);
        ConeShapeBuilder.build(b,radius, height/2, radius,divisions, 0, 360f,true);
        //flip so the cone is facing down


        Model bottom = modelBuilder.end();

        modelBuilder.begin();
        b = modelBuilder.part("double_cone", primitiveType, attributes, mat);
        Mesh topMesh = top.meshes.get(0);
        topMesh.transform(new Matrix4().setToTranslation(0,height/2,0));


        b.addMesh(top.meshes.get(0));
        Mesh bottomMesh = bottom.meshes.get(0);
        //rotate the bottom cone 180 degrees so it's facing up
        bottomMesh.transform(new Matrix4().setToRotation(1,0,0,180));
        b.addMesh(bottomMesh);

        Model doubleCone = modelBuilder.end();
        doubleCone.materials.first().set(mat);

        return doubleCone;
    }
}

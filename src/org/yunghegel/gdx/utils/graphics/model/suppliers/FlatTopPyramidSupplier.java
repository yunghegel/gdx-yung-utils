package org.yunghegel.gdx.utils.graphics.model.suppliers;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.VertexAttributes;
import com.badlogic.gdx.graphics.g3d.Model;
import org.yunghegel.gdx.utils.graphics.model.BuilderUtils;

public class FlatTopPyramidSupplier extends InstanceSupplier{

    float size, topScale;

    public FlatTopPyramidSupplier(float size, float topScale, Color color){
        super(color);
        this.size = size;
        this.topScale = topScale;
    }

    public FlatTopPyramidSupplier(float size, float topScale){
        this(size, topScale, BuilderUtils.getRandomColor());
    }

    @Override
    public Model createModel() {
        modelBuilder.begin();

        b = modelBuilder.part("disc", primitiveType, VertexAttributes.Usage.Position, mat);
        //top face is scaled down
        v(size,-size,-size);
       v(size,-size,size);
        v(-size,-size,size);
        v(-size,-size,-size);
        v(size * topScale,size,-size * topScale);
       v(size * topScale,size,size * topScale);
        v(-size * topScale,size,size * topScale);
        v(-size * topScale,size,-size * topScale);

        b.triangle((short) 0,(short) 2,(short) 1);
        b.triangle((short) 0,(short) 3,(short) 2);
        b.triangle((short) 4,(short) 6,(short) 5);
        b.triangle((short) 4,(short) 7,(short) 6);
        b.triangle((short) 0,(short) 5,(short) 1);
        b.triangle((short) 0,(short) 4,(short) 5);
        b.triangle((short) 1,(short) 6,(short) 2);
        b.triangle((short) 1,(short) 6,(short) 5);
        b.triangle((short) 2,(short) 3,(short) 7);
        b.triangle((short) 2,(short) 7,(short) 6);
        b.triangle((short) 3,(short) 0,(short) 4);
        b.triangle((short) 3,(short) 4,(short) 7);


        return modelBuilder.end();
    }




}

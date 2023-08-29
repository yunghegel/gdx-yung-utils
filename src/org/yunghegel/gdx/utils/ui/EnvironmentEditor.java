package org.yunghegel.gdx.utils.ui;

import com.badlogic.gdx.scenes.scene2d.ui.Cell;
import com.badlogic.gdx.utils.Align;
import com.kotcrab.vis.ui.widget.Separator;
import com.kotcrab.vis.ui.widget.VisLabel;
import com.kotcrab.vis.ui.widget.VisTable;
import com.kotcrab.vis.ui.widget.VisWindow;
import org.yunghegel.gdx.utils.UIUtil;
import org.yunghegel.gdx.utils.setup.EnvironmentPlus;
import org.yunghegel.gdx.utils.ui.widgets.VerticalTable;

public class EnvironmentEditor extends VerticalTable {

    private EnvironmentPlus environment;

    private FloatUI near;
    private FloatUI far;
    private FloatUI exponent;
    private Cell nearCell;
    private Cell farCell;
    private Cell exponentCell;

    private Vector4UI color;
    public String label;
    public VisLabel debugLabel;

    public EnvironmentEditor(String title, EnvironmentPlus environment) {
        super();
        this.environment = environment;

        initFogFields();
        align(Align.topLeft);


    }

    void initFogFields(){
        near = new FloatUI(getSkin(), 0, "Near", 0, 100, new FloatUI.FloatUIListener() {
            @Override
            public void onChange(float value) {
                environment.exponent = near.getValue();
                environment.setFogEquation();
                environment.fog(color.getValue(),near.getValue(),far.getValue(),exponent.getValue());
            }
        });

        far = new FloatUI(getSkin(), 0, "Far", 0, 100,(f)->{
            environment.far = far.getValue();
            environment.setFogEquation();
            environment.fog(color.getValue(),near.getValue(),far.getValue(),exponent.getValue());
        });

        exponent = new FloatUI(getSkin(), 0, "Exponent", 0, 100,(f)->{
            environment.exponent = exponent.getValue();
            environment.setFogEquation();
            environment.fog(color.getValue(),near.getValue(),far.getValue(),exponent.getValue());
        });

        VerticalTable fogTable = new VerticalTable();
        fogTable.setActors(near, far, exponent);
        addRow(fogTable);
        pad(2);


        color = new Vector4UI(getSkin(), environment.col);
        debugLabel = new VisLabel("debug");


        addRow(color);



        }


    @Override
    public void act(float delta) {
        super.act(delta);
        debugLabel.setText(getWidth() + " " + getHeight());
        float width = getWidth();
        float cellWidth = width / 3;



    }

    @Override
    public void layout() {
        super.layout();




    }
}


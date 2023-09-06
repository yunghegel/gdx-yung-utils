package org.yunghegel.gdx.utils.ui.widgets;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Align;
import com.kotcrab.vis.ui.layout.FlowGroup;
import com.kotcrab.vis.ui.widget.VisTable;
import com.kotcrab.vis.ui.widget.VisTextButton;
import com.kotcrab.vis.ui.widget.tabbedpane.Tab;
import com.kotcrab.vis.ui.widget.tabbedpane.TabbedPane;
import de.damios.guacamole.gdx.utils.FPSCounter;
import org.yunghegel.gdx.utils.system.Profiler;
import org.yunghegel.gdx.utils.ui.LoggerWidget;
import org.yunghegel.gdx.utils.ui.fields.LabeledFloatField;
import org.yunghegel.gdx.utils.ui.fields.LabeledIntField;
import org.yunghegel.gdx.utils.ui.fields.LabeledTextField;

public class ProfilerWidget extends TabPane {

    public LabeledFloatField fpsField, pastFrameTimeField;
    public LabeledIntField averageFpsField;
    public LabeledTextField maxMemoryField, availableMemoryField, usedMemoryField;
    public LabeledFloatField drawCallsField, renderCallsField, shaderSwitchesField, textureBindingsField, vertexCountField;
    public LabeledTextField javaUsedMemoryField, javaMaxMemoryField, javaAvailableMemoryField;
    public HorizontalTable glProfilerTable, glMemoryTable, javaMemoryTable, fpsTable;

    public FPSCounter fpsCounter;
    Profiler profiler;

    TabbedPane tabbedPane;

    class ProfilerTab extends Tab {

        String title;
        HorizontalTable table;

        public ProfilerTab(HorizontalTable table, String title) {
            super(false, false);
            this.table = table;
            this.title = title;
        }

        @Override
        public String getTabTitle() {
            return title;
        }

        @Override
        public Table getContentTable() {
            return table;
        }
    }

    public ProfilerWidget(Skin skin) {
        super(skin.get("default", TabPaneStyle.class));


        profiler = new Profiler();
        fpsCounter = new FPSCounter();

        align(Align.topLeft);
//        createFpsField();
//        createJavaMemoryFields();
//        createGLMemoryFields();
//        createGLProfilerFields();

        addPane("Console",new LoggerWidget());




    }

    @Override
    public void act(float delta) {
        super.act(delta);
        fpsCounter.update(delta);
        Profiler.glProfiler.reset();
    }

    private void createGLProfilerFields() {
        drawCallsField = new LabeledFloatField("Draw calls: ", 0f, () -> (float) Profiler.getDrawCalls());
        renderCallsField = new LabeledFloatField("Render calls: ", 0f, () -> (float) Profiler.getRenderCalls());
        shaderSwitchesField = new LabeledFloatField("Shader switches: ", 0f, () -> (float) Profiler.getShaderSwitches());
        textureBindingsField = new LabeledFloatField("Texture bindings: ", 0f, () -> (float) Profiler.getTextureBindings());
        vertexCountField = new LabeledFloatField("Vertex count: ", 0f, Profiler::getVertexCount);

        VisTable table = new VisTable();
        FlowGroup flowGroup = new FlowGroup(true,5);
        flowGroup.addActor(drawCallsField);
        flowGroup.addActor(renderCallsField);
        flowGroup.addActor(shaderSwitchesField);
        flowGroup.addActor(textureBindingsField);
        flowGroup.addActor(vertexCountField);


        table.add(flowGroup).growX().row();

        VisTextButton glProfilerButton = new VisTextButton("GL Rendering");
        addPane(glProfilerButton, flowGroup);

//        add(glProfilerTable).left().row();

    }

    private void createGLMemoryFields() {
        maxMemoryField = new LabeledTextField("GL max memory: ", "", Profiler::getGLMaxMemoryGB);
        availableMemoryField = new LabeledTextField("GL available memory: ", "", Profiler::getGLAvailableMemoryGB);
        usedMemoryField = new LabeledTextField("GL used memory: ", "", Profiler::getGLUsedMemoryGB);
        VisTable table = new VisTable();
        table.add(maxMemoryField);
        table.add(availableMemoryField);
        table.add(usedMemoryField);
        VisTextButton glMemoryButton = new VisTextButton("GL Memory");
        addPane(glMemoryButton, table);
    }

    private void createJavaMemoryFields() {
        javaUsedMemoryField = new LabeledTextField("Java used memory", "", Profiler::getJavaUsedMemoryGB);
        javaMaxMemoryField = new LabeledTextField("Java max memory", "", Profiler::getJavaMaxMemoryGB);
        javaAvailableMemoryField = new LabeledTextField("Java available memory", "", Profiler::getJavaAvailableMemoryGB);
        VisTable table = new VisTable();
        table.add(javaUsedMemoryField);
        table.add(javaMaxMemoryField);
        table.add(javaAvailableMemoryField);
        VisTextButton javaMemoryButton = new VisTextButton("Java Memory");
        addPane(javaMemoryButton, table);

//        add(javaMemoryTable).left().row();
    }

    private void createFpsField() {
        fpsField = new LabeledFloatField("FPS: ", 0f, () -> {
            int fps = fpsCounter.getFramesPerSecond();
            if (fps == -1) return (float) 0;
            return (float)fps;
        });
        averageFpsField = new LabeledIntField("Average FPS: ", 0, () -> {
           if (fpsCounter.getFramesPerSecond() == 0) return 1;
              return fpsCounter.getAverageFrameTime();
        });
        VisTable table = new VisTable();
        table.add(fpsField);
        table.add(averageFpsField);


        VisTextButton fpsButton = new VisTextButton("FPS");
        addPane(fpsButton, table);

//        add(fpsTable).left();
    }


}

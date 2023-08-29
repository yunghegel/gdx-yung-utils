package org.yunghegel.gdx.utils.ui;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import com.kotcrab.vis.ui.widget.VisTextButton;
import com.kotcrab.vis.ui.widget.VisWindow;

import javax.swing.event.ChangeEvent;
import java.util.HashMap;
import java.util.concurrent.Callable;
import java.util.function.Function;

public class ImS2D implements Disposable {

    private static Stage stage;
    static HashMap<Integer,VisWindow> windows = new HashMap<>();

    private static VisWindow currentWindow;

    private ImS2D() {
    }

    public static void init() {
        stage = new Stage();
    }

    @Override
    public void dispose() {
        stage.dispose();
    }

    public static void begin(){
        currentWindow = null;

    }

    public static void end(){
        render();


    }



    public static void window(String title){
       if(windows.containsKey(title.hashCode())) {
           currentWindow = windows.get(title.hashCode());
       } else {
           currentWindow = new VisWindow(title);
              windows.put(title.hashCode(),currentWindow);
       }

        stage.addActor(currentWindow);
    }

    public static void window(String title, float x, float y){
        currentWindow = new VisWindow(title);
        currentWindow.setPosition(x, y);
        stage.addActor(currentWindow);
    }


    public static void button(Callable callable){
        VisTextButton button = new VisTextButton("Button");
        button.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                try {
                    callable.call();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        if(!currentWindow.getChildren().contains(button,true))
            currentWindow.add(button);

    }

    public static void render(){
        stage.act();
        stage.draw();
    }

}

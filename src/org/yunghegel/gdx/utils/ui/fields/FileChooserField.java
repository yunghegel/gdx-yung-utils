package org.yunghegel.gdx.utils.ui.fields;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.kotcrab.vis.ui.widget.VisTable;
import com.kotcrab.vis.ui.widget.VisTextButton;
import com.kotcrab.vis.ui.widget.VisTextField;
import com.kotcrab.vis.ui.widget.file.FileChooser;
import com.kotcrab.vis.ui.widget.file.SingleFileChooserListener;

public class FileChooserField extends VisTable {

    public interface FileSelected {
        void selected(FileHandle fileHandle);
    }

    private int width = -1;

    private FileChooser.SelectionMode mode = FileChooser.SelectionMode.FILES;
    private FileSelected fileSelected;
    private final VisTextField textField;
    private final VisTextButton fcBtn;

    private String path;
    private FileHandle fileHandle;

    public FileChooserField(int width) {
        super();
        this.width = width;
        textField = new VisTextField();

        fcBtn = new VisTextButton("Import");

        setupUI();
        setupListeners();
    }

    public FileChooserField() {
        this(-1);
    }

    public void setEditable(boolean editable) {
        textField.setDisabled(!editable);
    }

    public String getPath() {
        return textField.getText();
    }

    public FileHandle getFile() {
        return this.fileHandle;
    }

    public void setCallback(FileSelected fileSelected) {
        this.fileSelected = fileSelected;
    }

    public void setFileMode(FileChooser.SelectionMode mode) {
        this.mode = mode;
    }

    public void clear() {
        textField.setText(". . .");
        fileHandle = null;
    }

    public void setText(String text) {
        textField.setText(text);
    }

    private void setupUI() {
        if (width <= 0) {
            add(textField).expandX().fillX().padRight(5);
            add(fcBtn).expandX().fillX().row();
        } else {
            add(textField).width(width * 0.75f).padRight(5);
            add(fcBtn).expandX().fillX();
        }
    }

    private void setupListeners() {

        fcBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                FileChooser fileChooser = new FileChooser(FileChooser.Mode.OPEN);

                fileChooser.setSelectionMode(mode);
                fileChooser.setMultiSelectionEnabled(false);
                getStage().addActor(fileChooser.fadeIn());
                fileChooser.setListener(new SingleFileChooserListener() {
                    @Override
                    protected void selected(FileHandle file) {
                        fileHandle = file;
                        path = file.path();

                        textField.setText(file.path());
                        if (fileSelected != null) {
                            fileSelected.selected(file);
                        }

                    }
                });

            }
        });

    }
}

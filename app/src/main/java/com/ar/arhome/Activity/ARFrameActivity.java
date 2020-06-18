package com.ar.arhome.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ar.arhome.R;
import com.ar.arhome.Util.ArScreenShot;
import com.google.ar.core.Anchor;
import com.google.ar.sceneform.AnchorNode;
import com.google.ar.sceneform.collision.Box;
import com.google.ar.sceneform.math.Vector3;
import com.google.ar.sceneform.rendering.ModelRenderable;
import com.google.ar.sceneform.rendering.ViewRenderable;
import com.google.ar.sceneform.ux.ArFragment;
import com.google.ar.sceneform.ux.TransformableNode;

public class ARFrameActivity extends AppCompatActivity implements View.OnClickListener {

    ArFragment arFragment;
    private ModelRenderable bearRenderable;
    private ModelRenderable catRenderable;
    private ModelRenderable barStoolRenderable;
    private ModelRenderable bedRenderable;
    private ModelRenderable sofaRenderable;
    private ModelRenderable toiletRenderable;
    private ModelRenderable traditionPaletteRenderable;

    ImageView bearPic, catPic, barStoolPic, bedPic, sofaPic, toiletPic, traditionPalettePic;
    ImageView btn_back, btn_screenShot, btn_help;
    View[] arrayView;

    int selected = 1;//default selected

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ar_frame);
        arFragment = (ArFragment)getSupportFragmentManager().findFragmentById(R.id.sceneform_fragment);
        // View
        bearPic = findViewById(R.id.bear);
        catPic = findViewById(R.id.cat);
        barStoolPic = findViewById(R.id.barstool);
        bedPic = findViewById(R.id.bed);
        sofaPic = findViewById(R.id.sofa);
        toiletPic = findViewById(R.id.toilet);
        traditionPalettePic = findViewById(R.id.traditionPalette);

        initFunctionBar();
        setArrayView();
        setClickListener();
        setupModel();

        arFragment.setOnTapArPlaneListener((hitResult, plane, motionEvent) -> {
            // when  user tap on plane,add model
            Anchor anchor = hitResult.createAnchor();
            AnchorNode anchorNode = new AnchorNode(anchor);
            anchorNode.setParent(arFragment.getArSceneView().getScene());
            createModel(anchorNode, selected);
        });

        // 设置默认待选的模型 2020.6.17 @房文宇
        Intent intent = getIntent();
        selected = intent.getIntExtra("ModelIndex",1);
    }

    private void initFunctionBar() {
        btn_back = (ImageView)findViewById(R.id.ar_back);
        btn_screenShot = (ImageView)findViewById(R.id.ar_screenshot);
        btn_help = (ImageView)findViewById(R.id.ar_help);

        // 返回
        btn_back.setOnClickListener((v)->{
            finish();
        });
        // 截屏
        btn_screenShot.setOnClickListener((v)->{
            ArScreenShot arScreenShot = new ArScreenShot(this, arFragment);
            arScreenShot.takePhoto();
        });
        // 帮助页面
        btn_help.setOnClickListener(v -> {
            Intent intent = new Intent(this, HelpActivity.class);
            startActivity(intent);
        });
    }

    private void setupModel() {
        ModelRenderable.builder()
                .setSource(this, R.raw.bear)
                .build().thenAccept(renderable -> bearRenderable = renderable)
                .exceptionally(
                        throwable -> {
                            Toast.makeText(this, "模型加载失败", Toast.LENGTH_SHORT).show();
                            return null;
                        });
        ModelRenderable.builder()
                .setSource(this, R.raw.cat)
                .build().thenAccept(renderable -> catRenderable = renderable)
                .exceptionally(
                        throwable -> {
                            Toast.makeText(this, "模型加载失败", Toast.LENGTH_SHORT).show();
                            return null;
                        });
        ModelRenderable.builder()
                .setSource(this, R.raw.barstool)
                .build().thenAccept(renderable -> barStoolRenderable = renderable)
                .exceptionally(
                        throwable -> {
                            Toast.makeText(this, "模型加载失败", Toast.LENGTH_SHORT).show();
                            return null;
                        });
        ModelRenderable.builder()
                .setSource(this, R.raw.bed)
                .build().thenAccept(renderable -> bedRenderable = renderable)
                .exceptionally(
                        throwable -> {
                            Toast.makeText(this, "模型加载失败", Toast.LENGTH_SHORT).show();
                            return null;
                        });
        ModelRenderable.builder()
                .setSource(this, R.raw.sofa)
                .build().thenAccept(renderable -> sofaRenderable = renderable)
                .exceptionally(
                        throwable -> {
                            Toast.makeText(this, "模型加载失败", Toast.LENGTH_SHORT).show();
                            return null;
                        });
        ModelRenderable.builder()
                .setSource(this, R.raw.toilet)
                .build().thenAccept(renderable -> toiletRenderable = renderable)
                .exceptionally(
                        throwable -> {
                            Toast.makeText(this, "模型加载失败", Toast.LENGTH_SHORT).show();
                            return null;
                        });
        ModelRenderable.builder()
                .setSource(this, R.raw.traditionpalette)
                .build().thenAccept(renderable -> traditionPaletteRenderable = renderable)
                .exceptionally(
                        throwable -> {
                            Toast.makeText(this, "模型加载失败", Toast.LENGTH_SHORT).show();
                            return null;
                        });
    }

    private void createModel(AnchorNode anchorNode, int selected) {
        String[] name = {"BEAR","CAT","BARSTOOL","BED","SOFA","TOILET","PALETTE"};

        if(selected == 6) {
            TransformableNode bear = new TransformableNode(arFragment.getTransformationSystem());
            bear.setParent(anchorNode);
            bear.setRenderable(bearRenderable);
            bear.select();
            addnNameView(anchorNode, bear ,"BEAR");

        }
        else if(selected == 7){
            TransformableNode cat = new TransformableNode(arFragment.getTransformationSystem());
            cat.setParent(anchorNode);
            cat.setRenderable(catRenderable);
            cat.select();
            addnNameView(anchorNode, cat ,"CAT");
        }
        else if(selected == 1){
            TransformableNode barStool = new TransformableNode(arFragment.getTransformationSystem());
            barStool.setParent(anchorNode);
            barStool.setRenderable(barStoolRenderable);
            barStool.select();
            addnNameView(anchorNode, barStool ,"BARSTOOL");
        }
        else if(selected == 2){
            TransformableNode bed = new TransformableNode(arFragment.getTransformationSystem());
            bed.setParent(anchorNode);
            bed.setRenderable(bedRenderable);
            bed.select();
            addnNameView(anchorNode, bed ,"BED");
        }
        else if(selected == 3){
            TransformableNode sofa = new TransformableNode(arFragment.getTransformationSystem());
            sofa.setParent(anchorNode);
            sofa.setRenderable(sofaRenderable);
            sofa.select();
            addnNameView(anchorNode, sofa ,"SOFA");
        }
        else if(selected == 4){
            TransformableNode toilet = new TransformableNode(arFragment.getTransformationSystem());
            toilet.setParent(anchorNode);
            toilet.setRenderable(toiletRenderable);
            toilet.select();
            addnNameView(anchorNode, toilet ,"TOILET");
        }
        else if(selected == 5){
            TransformableNode palette = new TransformableNode(arFragment.getTransformationSystem());
            palette.setParent(anchorNode);
            palette.setRenderable(traditionPaletteRenderable);
            palette.select();
            addnNameView(anchorNode, palette ,"PALETTE");
        }
    }

    private void addnNameView(AnchorNode anchorNode, TransformableNode model, String name) {
        Box box = (Box) model.getRenderable().getCollisionShape();

        ViewRenderable.builder().setView(this, R.layout.name_card)
                .build().thenAccept(viewRenderable -> {
            TransformableNode nameView = new TransformableNode(arFragment.getTransformationSystem());
            nameView.setLocalPosition(new Vector3(0f, box.getSize().y+0.1f, 0));
            nameView.setParent(anchorNode);
            nameView.setRenderable(viewRenderable);
            nameView.select();
            TextView tv_animalName =(TextView) viewRenderable.getView();
            tv_animalName.setText(name);
            tv_animalName.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    anchorNode.setParent(null);
                    showModelDeletedMsg();
                }
            });
        });
    }

    private void showModelDeletedMsg() {
        Toast.makeText(this, "删除模型成功",Toast.LENGTH_SHORT).show();
    }

    private void setClickListener() {
        for (View view : arrayView) {
            view.setOnClickListener(this);
        }
    }
    private void setArrayView() {
        arrayView = new View[]{bearPic, catPic, barStoolPic, bedPic, sofaPic, toiletPic, traditionPalettePic};
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.bear){
            selected = 6;
        }
        else if(v.getId() == R.id.cat){
            selected = 7;
        }
        else if(v.getId() == R.id.barstool){
            selected = 1;
        }
        else if(v.getId() == R.id.bed){
            selected = 2;
        }
        else if(v.getId() == R.id.sofa){
            selected = 3;
        }
        else if(v.getId() == R.id.toilet){
            selected = 4;
        }
        else if(v.getId() == R.id.traditionPalette){
            selected = 5;
        }
        setBackground(v.getId());
    }

    private void setBackground(int id) {
        for (View view : arrayView) {
            if (view.getId() == id) {
                view.setBackgroundColor(Color.parseColor("#80333639"));
            } else {
                view.setBackgroundColor(Color.TRANSPARENT);
            }
        }
    }
}

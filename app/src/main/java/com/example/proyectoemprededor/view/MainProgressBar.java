package com.example.proyectoemprededor.view;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.widget.ArrayAdapter;

import com.baoyachi.stepview.VerticalStepView;
import com.example.proyectoemprededor.R;
import com.example.proyectoemprededor.model.Global;

import java.util.ArrayList;
import java.util.List;


public class MainProgressBar extends AppCompatActivity {

    VerticalStepView verticalStepView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_state_progress_bar);

        verticalStepView = findViewById(R.id.verticalStepView);
        setStepView();
    }
    private void setStepView(){
        Global global = (Global)getApplicationContext();
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            verticalStepView.setStepsViewIndicatorComplectingPosition(source().size())
                    .reverseDraw(false)
                    .setTextSize(15)
                    .setStepViewTexts(source())
                    .setLinePaddingProportion(0.85f)
                    .setStepsViewIndicatorCompletedLineColor(getColor(R.color.white))
                    .setStepViewComplectedTextColor(getColor(R.color.white))
                    .setStepViewUnComplectedTextColor(getColor(R.color.black))
                    .setStepsViewIndicatorAttentionIcon(getDrawable(R.drawable.complted))
                    .setStepsViewIndicatorAttentionIcon(getDrawable(R.drawable.attention))
                    .setStepsViewIndicatorDefaultIcon(getDrawable(R.drawable.default_icon));
            //se progreso y que estado esta
            verticalStepView.setStepsViewIndicatorComplectingPosition(Integer.parseInt(global.getEstadoUnidad()));
        }
    }

    //se carga las unidades existentes
    private  List<String> source() {

        List<String> source = new ArrayList<>();
        source.add("Unidad 1");
        source.add("Unidad 2");
        source.add("Unidad 3");
        source.add("Unidad 4");
        source.add("Unidad 5");
        source.add("Unidad 6");
        source.add("Unidad 7");
        return  source;
    }
}
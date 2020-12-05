package com.example.atividaden2;



import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class FotoClass extends AppCompatActivity {
    static final int REQUEST_IMAGE_CAPTURE = 1;
    private ImageView imageView;
    String value ="";
    Integer contador1 = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fotolayout);
        Button btnSalvar = findViewById(R.id.btnsalvar);

        TextView nomepais = (TextView)findViewById(R.id.textView3);

        imageView = findViewById(R.id.fotoview);

        Bundle paisclick = getIntent().getExtras();



        if(paisclick != null){
            value = (String) paisclick.get("paisclicado");

        }
        nomepais.setText("Pais:"+ value);

        btnSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dispatchTakePictureIntent();
                contador1 +=1;
                SharedPreferences sharedPreferences = getSharedPreferences("pais", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString(value, contador1.toString() );
                editor.apply();
            }
        });


    }
    protected void onResume() {
        super.onResume();
        SharedPreferences sharedPreferences = getSharedPreferences("pais", Context.MODE_PRIVATE);
        String result = sharedPreferences.getString(value, "0");
        Toast.makeText(this,"Fotos tiradas no pais: "+result,Toast.LENGTH_LONG).show();

    }



    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            imageView.setImageBitmap(imageBitmap);
        }
    }
}

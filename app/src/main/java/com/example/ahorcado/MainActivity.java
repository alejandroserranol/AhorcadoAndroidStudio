package com.example.ahorcado;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    String palabraOculta = "CETYS";
    boolean partidaTerminada = false; //indica si la partida ha terminado
    int numeroDeFallos = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(savedInstanceState == null){
            getSupportFragmentManager().beginTransaction().add(R.id.ventanaJuego, new VentanaAhorcado()).commit();
        }
    }

    @Override
    protected void onStart(){
        super.onStart();
        palabraOculta = eligePalabra();
        String barras = "";
        for (int i=0; i<palabraOculta.length(); i++){
            barras += "_ ";
        }

        ((TextView) findViewById(R.id.palabraConGuiones)).setText(barras);

    }

    private String eligePalabra() {
        String[] listaPalabras = {"HOLA", "CETYS", "BORREGUITO", "BABYYODA", "MANDO"};
        Random aleatorio = new Random(); //Variable aleatoria va a elegir una palabra al azahar
        int posicion = aleatorio.nextInt(listaPalabras.length);
        return listaPalabras[posicion].toUpperCase();
    }

    public void botonPulsado(View vista){
        if(!partidaTerminada) {
            Button boton = findViewById(vista.getId());
            boton.setVisibility(View.INVISIBLE);
            chequeaLetra(boton.getText().toString());
        }
    }

    private void chequeaLetra(String letra){
        letra = letra.toUpperCase();
        ImageView imagenAhorcado = ((ImageView) findViewById(R.id.imagenAhorcado));
        TextView textoConGuiones = ((TextView) findViewById(R.id.palabraConGuiones));
        String palabraConGuiones = textoConGuiones.getText().toString();

        boolean acierto = false;

        if (palabraOculta.contains(letra)) {
            for (int i = 0; i < palabraOculta.length(); i++) {
                if (palabraOculta.charAt(i) == letra.charAt(0)) {
                    //quita el guion bajo la ley correspondiente
                    palabraConGuiones = palabraConGuiones.substring(0, 2 * i)
                            + letra
                            + palabraConGuiones.substring(2 * i + 1);
                    acierto = true;
                }
            }

            if (!palabraConGuiones.contains("_")) {
                imagenAhorcado.setImageResource(R.drawable.acertastetodo);
            }
        } else {
            numeroDeFallos++;
            if (numeroDeFallos == 6) {
                partidaTerminada = true;
            }
        }

        textoConGuiones.setText(palabraConGuiones);

        if(!acierto){
            numeroDeFallos++;
            switch (numeroDeFallos){
                case 0:
                    imagenAhorcado.setImageResource(R.drawable.ahorcado_0);
                    break;
                case 1:
                    imagenAhorcado.setImageResource(R.drawable.ahorcado_1);
                    break;
                case 2:
                    imagenAhorcado.setImageResource(R.drawable.ahorcado_2);
                    break;
                case 3:
                    imagenAhorcado.setImageResource(R.drawable.ahorcado_3);
                    break;
                case 4:
                    imagenAhorcado.setImageResource(R.drawable.ahorcado_4);
                    break;
                case 5:
                    imagenAhorcado.setImageResource(R.drawable.ahorcado_5);
                    break;
                default:
                    imagenAhorcado.setImageResource(R.drawable.ahorcado_fin);
                    break;
            }
        }

    }


}

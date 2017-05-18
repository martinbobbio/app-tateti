package bobbio.martin.a07_tateti;

import android.app.Activity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.Toast;

public class MainActivity extends Activity {

    private int jugadores;
    private int[] casilla;
    private Partida partida;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        casilla = new int[9];
        casilla[0] = R.id.a1;
        casilla[1] = R.id.a2;
        casilla[2] = R.id.a3;
        casilla[3] = R.id.b1;
        casilla[4] = R.id.b2;
        casilla[5] = R.id.b3;
        casilla[6] = R.id.c1;
        casilla[7] = R.id.c2;
        casilla[8] = R.id.c3;

    }

    public void play(View v){

        //Vacio las casillas
        ImageView imagen;
        for(int c:casilla){
            imagen = (ImageView)findViewById(c);
            imagen.setImageResource(R.drawable.casilla);
        }

        //Seteo los jugadores depende el boton que es apretado
        jugadores = 1;
        if(v.getId() == R.id.two_player){
            jugadores = 2;
        }

        //Chequeo la dificultad (0=facil 1=normal 2=imposible)
        RadioGroup dificultades = (RadioGroup)findViewById(R.id.radioGroup);
        int id = dificultades.getCheckedRadioButtonId();
        int dificultad = 0;
        if(id == R.id.normal){
            dificultad = 1;
        }else if(id == R.id.imposible){
            dificultad = 2;
        }

        //le paso la dificultad
        partida = new Partida(dificultad);
        //deshabilito botones
        ((Button)findViewById(R.id.one_player)).setEnabled(false);
        ((Button)findViewById(R.id.two_player)).setEnabled(false);
        ((RadioGroup)findViewById(R.id.radioGroup)).setAlpha(0);



    }

    public void touch(View v){

        //Si la partida no comenzo se sale del metodo
        if (partida == null){
            return;
        }

        //Me fijo que casilla es seleccionada de las 9
        int selected = 0;
        for (int i=0;i<casilla.length;i++){
            if(casilla[i] == v.getId()){
                selected = i;
                break;
            }
        }

        //Me fijo de no tocar la misma casilla
        if(!partida.check(selected)){
            return;
        }

        draw(selected);
        int resultado = partida.turn();
        if(resultado>0){
            end(resultado);
            return;
        }
        selected = partida.ia();

        //Hago que elija a una que no fue seleccionada
        while(!partida.check(selected)){
            selected = partida.ia();
        }

        draw(selected);
        resultado = partida.turn();
        if(resultado>0)
            end(resultado);

    }


    public void draw(int pos){
        ImageView imagen = (ImageView)findViewById(casilla[pos]);

        if(partida.jugador == 1){
            imagen.setImageResource(R.drawable.circulo);
        }else{
            imagen.setImageResource(R.drawable.aspa);
        }
    }

    private void end(int resultado){
        String message;

        if(resultado==1)
            message = "The circles wins!";
        else if(resultado==2)
            message = "The crosses wins!";
        else
            message = "Tie!";

        Toast msj = Toast.makeText(this,message,Toast.LENGTH_SHORT);
        msj.setGravity(Gravity.CENTER,0,250);
        msj.show();

        partida = null;

        ((Button)findViewById(R.id.one_player)).setEnabled(true);
        ((Button)findViewById(R.id.two_player)).setEnabled(true);
        ((RadioGroup)findViewById(R.id.radioGroup)).setAlpha(1);

    }
}

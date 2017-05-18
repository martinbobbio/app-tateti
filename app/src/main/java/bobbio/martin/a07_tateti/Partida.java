package bobbio.martin.a07_tateti;

import java.util.Random;

/**
 * Created by admin on 21/04/2017.
 */

public class Partida {


    public final int dificultad;
    public int jugador;
    private int[] ocupado;
    public final int[][] COMBINACIONES = {{0,1,2},{3,4,5},{6,7,8},{0,3,6},{1,4,7},{2,5,8},{0,4,8},{2,4,6}};

    public Partida(int dificultad) {
        this.dificultad = dificultad;
        jugador = 1;

        ocupado = new int[9];
        for (int i = 0; i < ocupado.length; i++) {
            ocupado[i] = 0;
        }
    }


    public int ia() {
        int casilla;
        casilla = ticTac(2);//Te devuelve la casilla que le falta al j2

        if(casilla!=-1)
            return casilla;
        if(dificultad>0){
            casilla = ticTac(1);//Te devuelve la casilla que le falta al j1
            if(casilla!=-1)
                return casilla;
        }

        if (dificultad==2){//Algoritmo para que siempre que pueda marque esquinas
            if(ocupado[0]==0)return 0;
            if(ocupado[2]==0)return 2;
            if(ocupado[6]==0)return 6;
            if(ocupado[8]==0)return 8;
        }


        Random rnd = new Random();
        casilla = rnd.nextInt(9);
        return casilla;
    }


    public int turn() {// EMPATE=3, GANA_J1=1, GANA_J2=2, SEGUIR_JUGANDO=0

        boolean empate = true;
        boolean ultMovimiento = true;

        for(int i=0;i<COMBINACIONES.length;i++){

            for(int pos:COMBINACIONES[i]){

                if(ocupado[pos]!=jugador)//Si las casillas no son iguales a jugador
                    ultMovimiento = false;

                if(ocupado[pos]==0)//Al recorrer los 2 for si hay una casilla vacia no hay empate
                    empate = false;


            }
            if(ultMovimiento)return jugador;//Si hay tateti te devuelve el jugador ganador
            ultMovimiento = true;
        }

        if(empate)//Va a dar empate si no hay ningun 0 en ocupado[]
            return 3;


        jugador++;

        if (jugador > 2)
            jugador = 1;

        return 0;
    }


    public boolean check(int casilla) {
        if (ocupado[casilla] != 0) {
            return false;
        } else {
            ocupado[casilla] = jugador;
        }
        return true;
    }


    public int ticTac(int jugador){//Metodo para saber cuando el jugador esta por hacer tateti

        int casilla = -1;
        int cont = 0;

        for(int i=0;i<COMBINACIONES.length;i++){

            for(int pos:COMBINACIONES[i]){

                if(ocupado[pos] == jugador) //Si falta una para el tateti
                    cont++;
                if(ocupado[pos]==0) //Guardo el campo que falta para hacer tateti
                    casilla = pos;
            }

            if(cont == 2 && casilla != -1)
                return casilla;

            casilla = -1;
            cont = 0;
        }


        return -1;
    }

}

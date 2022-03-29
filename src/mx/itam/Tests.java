package mx.itam;

import mx.itam.Clases.Jugador;
import mx.itam.Client.Cliente;
import mx.itam.Servidor.Deployer;

import java.util.ArrayList;
import java.util.Random;

public class Tests {
    private static final int numClientes = 5;
    private static ArrayList<Cliente> clientes = new ArrayList<Cliente>();

    public static void main(String[] args) {
        //Ejecuta el servidor
        Deployer deployer;

        //Crea N clientes
        String nomCliente = "Cliente ";
        for(int i = 0; i<numClientes; i++){
            Cliente temp = new Cliente(nomCliente+i);
            clientes.add(temp);
        }

        boolean ganoJugador = false;
        while(!ganoJugador){
            int ganador = ganadorRonda();
            clientes.get(ganador).ganaRonda();
        }
    }

    public static int ganadorRonda(){
        return randomNumber(clientes.size(), 0);
    }

    private static int randomNumber(int max, int min){
        Random random = new Random();

        int value = random.nextInt(max - min) + min;
        return  value;
    }
}

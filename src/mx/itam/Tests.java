package mx.itam;

import mx.itam.Clases.Jugador;
import mx.itam.Client.Cliente;
import mx.itam.Servidor.Deployer;

import javax.sound.midi.Soundbank;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Random;

public class Tests {
    private static final int numClientes = 1;
    private static final int numRondas = 10;
    private static ArrayList<Cliente> clientes = new ArrayList<Cliente>();

    public static void main(String[] args) throws InterruptedException {
        //Crea N clientes
        String nomCliente = "Cliente ";
        System.out.println("Se crean los clientes");
        for(int i = 0; i<numClientes; i++){
            Cliente temp = new Cliente(nomCliente + i);
            System.out.println("Cliente " + i + " creado");
            temp.start();
            clientes.add(temp);
        }

        System.out.println("Jugadores creados");

/*        for(int i = 0; i<numRondas;i++){
            int ganador = ganadorRonda();
            System.out.println("Ganador: " + ganador);
            clientes.get(ganador).ganaRonda();
            Thread.sleep(1000);
        }*/
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

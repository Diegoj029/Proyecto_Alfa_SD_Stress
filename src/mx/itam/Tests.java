package mx.itam;

import mx.itam.Clases.Jugador;
import mx.itam.Client.Cliente;
import mx.itam.Servidor.Deployer;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Random;

public class Tests {
    private static final int numClientes = 5;
    private static final int numRondas = 100;
    private static ArrayList<Cliente> clientes = new ArrayList<Cliente>();

    public static void main(String[] args) throws InterruptedException {
        //Crea N clientes
        creaClientes();

        for(int i = 0; i<numRondas;i++){
            int ganador = ganadorRonda();
            clientes.get(ganador).ganaRonda();
            Thread.sleep(1000);
        }
    }

    public static void creaClientes(){
        String nomCliente = "Cliente ";
        for(int i = 0; i<numClientes; i++){
            Cliente temp = new Cliente(nomCliente + i);
            System.out.println("Cliente " + i + " creado");
            clientes.add(temp);
        }

        System.out.println("Jugadores creados");
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

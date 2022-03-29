package mx.itam;

import mx.itam.Clases.Jugador;
import mx.itam.Client.Cliente;
import mx.itam.Servidor.Deployer;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Random;

public class Tests {
    private static final int numClientes = 5;
    private static final int numRondas = 1;
    private static ArrayList<Cliente> clientes = new ArrayList<Cliente>();

    public static void main(String[] args) {
        //Ejecuta el servidor
        try {
            Deployer deployer = new Deployer(5);
            System.out.println("Servidor creado");

            //Crea N clientes
            String nomCliente = "Cliente ";
            for(int i = 0; i<numClientes; i++){
                Cliente temp = new Cliente(nomCliente + i);
                System.out.println("Cliente " + i + " creado");
                clientes.add(temp);
            }

            for(int i = 0; i<numRondas;i++){
                deployer.empiezaRonda();
                boolean ganoJugador = false;
                while(!ganoJugador){
                    int ganador = ganadorRonda();
                    clientes.get(ganador).ganaRonda();
                    ganoJugador = deployer.getEncuentraGanador();
                }
            }

        } catch (RemoteException e) {
            e.printStackTrace();
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

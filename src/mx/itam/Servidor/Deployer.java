package mx.itam.Servidor;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;

public class Deployer {
    private static Servidor servidor;

    public Deployer(int puntuacionGanadora) throws RemoteException{
        String serverAddress = "localhost";
        System.setProperty("java.rmi.server.hostname",serverAddress);

        LocateRegistry.createRegistry(1099);

        String name= "Registro";
        int N = puntuacionGanadora;
        servidor = new Servidor(N);
        servidor.deploy(name);
        //System.out.println("Hola");
    }

    public boolean getEncuentraGanador(){
        return servidor.encuentraGanador;
    }

    public void empiezaRonda(){
        servidor.loopJuego();
    }
}
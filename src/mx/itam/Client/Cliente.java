package mx.itam.Client;

import mx.itam.Interfaces.Registro;
import mx.itam.Tablero.Tablero;

import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.net.*;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Random;

public class Cliente extends Thread {
    private static int portTCP;
    private static String nombreJugador;
    private static String IP;

    public Cliente(String nombreCliente){
        this.nombreJugador = nombreCliente;
        //RMI
        System.setProperty("java.security.policy", "src/mx/itam/Client/client.policy");
    }

    @Override
    public void run() {
        //En versiones recientes de la maquina virutal de java marca error
        /*if(System.getSecurityManager() == null) {
            System.setSecurityManager(new SecurityManager());
        }*/

        MulticastSocket socketUDP = null;
        String name = "Registro";
        try {
            Registry registry = LocateRegistry.getRegistry("localhost");
            Registro comp = (Registro) registry.lookup(name);
            String[] datosRegistro = comp.registro(nombreJugador).split(";");

            String IP = datosRegistro[0];
            int portTCP = Integer.parseInt(datosRegistro[1]);
            int portUDP = Integer.parseInt(datosRegistro[2]);
            String inetA = datosRegistro[3];

            //Se crea el Tablero
            //Tablero tab = new Tablero();
            this.conectar(IP, portTCP);

            //Se registra el jugador en el sevidor Multicast UDP
            InetAddress group = InetAddress.getByName(inetA); // destination multicast group
            socketUDP = new MulticastSocket(portUDP);
            socketUDP.joinGroup(group);

            //Espera los mensajes del servidor Multicast UDP y los envía al tablero
            while(true) {
                byte[] buffer = new byte[1000];
                DatagramPacket messageIn = new DatagramPacket(buffer, buffer.length);
                socketUDP.receive(messageIn);

                String[] mensaje = new String(messageIn.getData()).trim().split(";");
                int posMonstruo = Integer.parseInt(mensaje[0]);
                String nomGanador = mensaje[1];
                //System.out.println(posMonstruo + nomGanador);

                golpeaMonstruo(posMonstruo);
            }
        } catch (RemoteException | NotBoundException e) {
            System.out.println("Connect: " + e.getMessage());
        } catch (SocketException e) {
            System.out.println("Socket: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("IO: " + e.getMessage());
        } finally {
            if (socketUDP != null) socketUDP.close();
        }
    }

    private void conectar(String IP, int portTCP){
        this.portTCP = portTCP;
        this.IP = IP;
    }

    private void mensajeTCP(String mensaje) {
        Socket socket = null;
        try {
            socket = new Socket(IP, portTCP);
            //System.out.println(mensaje);
            DataOutputStream out = new DataOutputStream(socket.getOutputStream());
            byte[] data = mensaje.getBytes();
            out.writeInt(data.length);
            out.write(data);
        } catch (UnknownHostException e) {
            System.out.println("Sock:" + e.getMessage());
        } catch (EOFException e) {
            System.out.println("EOF:" + e.getMessage());
        } catch (IOException e) {
            System.out.println("IO: -" + e.getMessage());
        } finally {
            if (socket != null) try {
                socket.close();
            } catch (IOException e) {
                System.out.println("close:" + e.getMessage());
            }
        }
    }

    public void ganaRonda(){
        this.mensajeTCP(nombreJugador);
        System.out.println(nombreJugador + " Golepeo el monstruo");
    }

    private static int randomNumber(int max, int min){
        Random random = new Random();

        int value = random.nextInt(max - min) + min;
        return  value;
    }

    public void golpeaMonstruo(int posMonstruo){
        try {
            int golpeCasilla = 0;
            while (golpeCasilla != posMonstruo){
                Thread.sleep(10);
                golpeCasilla = randomNumber(9,1);
            }
            ganaRonda();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

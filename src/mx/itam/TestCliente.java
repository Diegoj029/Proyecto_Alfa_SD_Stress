package mx.itam;

import mx.itam.Client.Cliente;

public class TestCliente extends Thread{
    private Cliente cliente;

    public TestCliente(String nombreCliente){
        this.cliente = new Cliente(nombreCliente);
    }

    @Override
    public void run() {
        this.cliente.deploy();
    }

    public void ganaRonda(String jugadorGanador){
        this.cliente.ganaRonda(jugadorGanador);
    }
}

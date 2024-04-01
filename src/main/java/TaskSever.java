import java.io.*;
import java.net.*;
import java.util.List;
import java.util.ArrayList;

public class TaskSever {
    private static List<String> tareas = new ArrayList<String>();

    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(9090); // Creación del servidor en el puerto 9090

            while (true) {
                Socket socket = serverSocket.accept(); // Aceptación de conexiones entrantes

                // Configuración de flujos de entrada y salida
                BufferedReader entrada = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                PrintWriter salida = new PrintWriter(socket.getOutputStream(), true);

                // Lectura de la solicitud del cliente
                String solicitud = entrada.readLine();
                System.out.println("Solicitud recibida del cliente: " + solicitud);

                // Procesamiento de la solicitud
                String respuesta = procesarSolicitud(solicitud);

                // Envío de la respuesta al cliente
                salida.println(respuesta);

                // Cierre de la conexión
                socket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private static String procesarSolicitud(String solicitud) {
        StringBuilder respuesta = new StringBuilder();
        switch (solicitud) {
            case "LISTAR_TAREAS":
                respuesta.append("Lista de tareas:\n");
                for (String tarea : tareas) {
                    respuesta.append("- ").append(tarea).append("\n");
                }
                break;
            default:
                respuesta.append("Solicitud no válida");
                break;
        }
        return respuesta.toString();
    }

}

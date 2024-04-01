import java.io.*;
import java.net.*;

public class TaskClient {
    public static void main(String[] args) {
        try {
            Socket socket = new Socket("localhost", 9090); // Conexión al servidor en el puerto 9090

            // Configuración de flujos de entrada y salida
            BufferedReader entrada = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter salida = new PrintWriter(socket.getOutputStream(), true);

            // Envío de una solicitud al servidor
            salida.println("LISTAR_TAREAS");

            // Lectura de la respuesta del servidor
            String respuesta = entrada.readLine();
            System.out.println("Respuesta del servidor: " + respuesta);

            // Cierre de la conexión
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

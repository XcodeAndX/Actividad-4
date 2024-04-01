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
        String[] partes = solicitud.split(" ", 2); // Dividimos la solicitud en dos partes: operación y parámetros (si los hay)
        String operacion = partes[0];

        switch (operacion) {
            case "LISTAR_TAREAS":
                respuesta.append("Lista de tareas:\n");
                for (String tarea : tareas) {
                    respuesta.append("- ").append(tarea).append("\n");
                }
                break;
            case "AGREGAR_TAREA":
                if (partes.length == 2) {
                    String nuevaTarea = partes[1];
                    tareas.add(nuevaTarea);
                    respuesta.append("Tarea agregada con éxito.");
                } else {
                    respuesta.append("Formato de solicitud incorrecto.");
                }
                break;
            case "ACTUALIZAR_TAREA":
                if (partes.length == 2) {
                    int indice = Integer.parseInt(partes[1]);
                    if (indice >= 0 && indice < tareas.size()) {
                        String nuevaTarea = partes[1];
                        tareas.set(indice, nuevaTarea);
                        respuesta.append("Tarea actualizada con éxito.");
                    } else {
                        respuesta.append("Índice de tarea inválido.");
                    }
                } else {
                    respuesta.append("Formato de solicitud incorrecto.");
                }
                break;
            case "ELIMINAR_TAREA":
                if (partes.length == 2) {
                    int indice = Integer.parseInt(partes[1]);
                    if (indice >= 0 && indice < tareas.size()) {
                        tareas.remove(indice);
                        respuesta.append("Tarea eliminada con éxito.");
                    } else {
                        respuesta.append("Índice de tarea inválido.");
                    }
                } else {
                    respuesta.append("Formato de solicitud incorrecto.");
                }
                break;
            default:
                respuesta.append("Operación no válida.");
                break;
        }
        return respuesta.toString();
    }
}



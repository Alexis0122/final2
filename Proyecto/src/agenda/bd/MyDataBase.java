package agenda.bd;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;


public class MyDataBase {
    
    private String url = "jdbc:mysql://localhost/agenda";
    private String user = "root";
    private String password = "";
    private Connection con = null;

    public void conectar() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection(url, user, password);
            System.out.println("estas conectado");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void desconectar() {
        try {
            if (con != null) {
                con.close();
            }
            System.out.println("estas desconectado");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    
    public void actualizarAgenda(Agenda agenda) {
        conectar();

        String sql = "UPDATE agenda SET nombre = ?, apellido = ?, correo = ?, telefono = ?, empresa = ? WHERE id = ?";

        try {
            PreparedStatement preparedStatement = con.prepareStatement(sql);
            preparedStatement.setString(1, agenda.getNombre());
            preparedStatement.setString(2, agenda.getApellido());
            preparedStatement.setString(3, agenda.getCorreo());
            preparedStatement.setString(4, agenda.getTelefono());
            preparedStatement.setString(5, agenda.getEmpresa());
            preparedStatement.setInt(6, agenda.getId());

            int filasAfectadas = preparedStatement.executeUpdate();

            if (filasAfectadas > 0) {
                System.out.println("Registro actualizado correctamente");
            } else {
                System.out.println("No se pudo actualizar el registro");
            }

            preparedStatement.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            desconectar();
        }
        
        
    }
}

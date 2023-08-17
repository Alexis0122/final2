package agenda.bd;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;


public class MyDataBase {
    
    private final String url = "jdbc:mysql://localhost/agenda";
    private final String user = "root";
    private final String password = "";
    private Connection con = null;

    public void conectar() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection(url, user, password);
            System.out.println("estas conectado");

        } catch (ClassNotFoundException | SQLException e) {
        }
    }
    
     
    public void actualizarAgenda(Agenda agenda) {
    conectar();

    String sql = "UPDATE agenda SET nombre = ?, apellido = ?, correo = ?, telefono = ?, empresa = ?, id = ?";

    try {
        con.setAutoCommit(false);

        try (PreparedStatement preparedStatement = con.prepareStatement(sql)) {
            preparedStatement.setString(1, agenda.getNombre());
            preparedStatement.setString(2, agenda.getApellido());
            preparedStatement.setString(3, agenda.getCorreo());
            preparedStatement.setString(4, agenda.getTelefono());
            preparedStatement.setString(5, agenda.getEmpresa());
            preparedStatement.setInt(6, agenda.getId());

            int filasAfectadas = preparedStatement.executeUpdate();

            if (filasAfectadas > 0) {
                System.out.println("Registro actualizado correctamente");
                con.commit();
            } else {
                System.out.println("No se pudo actualizar el registro");
                con.rollback();
            }
        }
    } catch (SQLException e) {
        try {
            con.rollback();
        } catch (SQLException ex) {
        }
    } finally {
        desconectar();
    }  
}

    public void desconectar() {
        try {
            if (con != null) {
                con.close();
            }
            System.out.println("estas desconectado");
        } catch (SQLException e) {
        }

    }    
}

package cl.duoc.maestros.dto;

import java.sql.Date;

import cl.duoc.maestros.model.Usuarios.Usuario;

// Usuario sin la contraseña: nunca se devuelve al front
public record UsuarioResponse(
        Long id,
        String nombreUsuario,
        String rut,
        String primerNombre,
        String segundoNombre,
        String apePaterno,
        String apeMaterno,
        String telefono,
        String correo,
        Date fechaNacimiento) {

    public static UsuarioResponse de(Usuario u) {
        return new UsuarioResponse(u.getId(), u.getNombreUsuario(), u.getRut(),
                u.getPrimerNombre(), u.getSegundoNombre(), u.getApePaterno(), u.getApeMaterno(),
                u.getTelefono(), u.getCorreo(), u.getFechaNacimiento());
    }
}

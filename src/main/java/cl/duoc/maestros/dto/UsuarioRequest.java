package cl.duoc.maestros.dto;

import java.sql.Date;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

// Datos para crear o actualizar un usuario (la contraseña llega en texto y se guarda con hash BCrypt)
public record UsuarioRequest(
        @Size(max = 30, message = "El nombre de usuario admite hasta 30 caracteres") String nombreUsuario,
        @Size(max = 11, message = "El RUT admite hasta 11 caracteres") String rut,
        @NotBlank(message = "El primer nombre es obligatorio")
        @Size(max = 50, message = "El primer nombre admite hasta 50 caracteres") String primerNombre,
        @Size(max = 50, message = "El segundo nombre admite hasta 50 caracteres") String segundoNombre,
        @NotBlank(message = "El apellido paterno es obligatorio")
        @Size(max = 50, message = "El apellido paterno admite hasta 50 caracteres") String apePaterno,
        @Size(max = 50, message = "El apellido materno admite hasta 50 caracteres") String apeMaterno,
        @Size(max = 12, message = "El teléfono admite hasta 12 caracteres") String telefono,
        @Email(message = "El correo no es válido")
        @Size(max = 50, message = "El correo admite hasta 50 caracteres") String correo,
        // obligatoria al crear; al actualizar, si viene vacía se conserva la actual
        @Size(min = 6, max = 72, message = "La contraseña debe tener entre 6 y 72 caracteres") String contrasenia,
        Date fechaNacimiento) {
}

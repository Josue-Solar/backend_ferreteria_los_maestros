package cl.duoc.maestros.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

// Registro publico de un cliente nuevo (crea el usuario y su ficha de cliente)
public record RegistroRequest(
        @NotBlank(message = "El nombre de usuario es obligatorio")
        @Size(max = 30, message = "El nombre de usuario admite hasta 30 caracteres") String nombreUsuario,
        @Size(max = 11, message = "El RUT admite hasta 11 caracteres") String rut,
        @NotBlank(message = "El primer nombre es obligatorio")
        @Size(max = 50, message = "El primer nombre admite hasta 50 caracteres") String primerNombre,
        @Size(max = 50, message = "El segundo nombre admite hasta 50 caracteres") String segundoNombre,
        @NotBlank(message = "El apellido paterno es obligatorio")
        @Size(max = 50, message = "El apellido paterno admite hasta 50 caracteres") String apePaterno,
        @Size(max = 50, message = "El apellido materno admite hasta 50 caracteres") String apeMaterno,
        @Size(max = 12, message = "El teléfono admite hasta 12 caracteres") String telefono,
        @NotBlank(message = "El correo es obligatorio")
        @Email(message = "El correo no es válido")
        @Size(max = 50, message = "El correo admite hasta 50 caracteres") String correo,
        @NotBlank(message = "La contraseña es obligatoria")
        @Size(min = 6, max = 72, message = "La contraseña debe tener entre 6 y 72 caracteres") String contrasenia,
        java.sql.Date fechaNacimiento,
        @NotNull(message = "El tipo de cliente es obligatorio") Long idTipoCliente,
        @NotBlank(message = "La región es obligatoria")
        @Size(max = 50, message = "La región admite hasta 50 caracteres") String region,
        @NotBlank(message = "La comuna es obligatoria")
        @Size(max = 50, message = "La comuna admite hasta 50 caracteres") String comuna) {
}

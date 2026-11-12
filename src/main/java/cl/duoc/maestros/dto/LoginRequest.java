package cl.duoc.maestros.dto;

import jakarta.validation.constraints.NotBlank;

// "usuario" puede ser el correo o el nombre de usuario
public record LoginRequest(
        @NotBlank(message = "El usuario es obligatorio") String usuario,
        @NotBlank(message = "La contraseña es obligatoria") String contrasenia) {
}

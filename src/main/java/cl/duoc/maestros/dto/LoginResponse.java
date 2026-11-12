package cl.duoc.maestros.dto;
// Respuesta del login: token JWT y datos básicos del usuario
public record LoginResponse(
        String token,
        String tipo,
        long expiraEnMs,
        Long id,
        String nombreUsuario,
        String rol) {
}

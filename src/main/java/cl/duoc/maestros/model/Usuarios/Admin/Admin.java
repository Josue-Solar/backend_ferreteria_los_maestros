package cl.duoc.maestros.model.Usuarios.Admin;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data 
@AllArgsConstructor 
@NoArgsConstructor 
@Entity 
@Table(name = "admin")
public class Admin {
    @Id 
    @Column(name = "ID")
    private Long id;

    @Column(name = "id_ROL")
    private Long idRol;

    @Column(name = "id_CARGO")
    private Long idCargo;

    @Column(name = "id_SUCURSAL")
    private Long idSucursal;
}

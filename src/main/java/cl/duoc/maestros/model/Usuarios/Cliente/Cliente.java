package cl.duoc.maestros.model.Usuarios.Cliente;

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
@Table(name = "cliente")
public class Cliente {
    @Id
    @Column(name = "id")
    private Long id;

    @Column(name = "id_tipo_cliente")
    private Long idTipoCliente;

    @Column(name = "region")
    private String region;

    @Column(name = "comuna")
    private String comuna;
}

package cl.duoc.maestros.model.Pedidos;

import java.sql.Date;

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
@Table(name = "PEDIDO")
public class Pedido {
    @Id 
    @Column(name = "ID")
    private Long id;

    @Column(name = "id_CLIENTE")
    private Long idCliente;

    @Column(name = "TOTAL")
    private Long total;
    
    @Column(name = "FECHA")
    private Date fecha;
}

package cl.duoc.maestros.model.Productos;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor 
@NoArgsConstructor 
@Entity
@Table(name="producto")
public class Producto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "url_imagen")
    private String urlImagen;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "descripcion")
    private String descripcion;

    @Column(name = "detalle")
    private String detalle;

    @Column(name = "precio")
    private int precio;

    @Column(name = "id_CATEGORIA")
    private int id_CATEGORIA;

    @Column(name = "sku")
    private String sku;

    @Column(name = "marca")
    private String marca;

    @Column(name = "unidad")
    private String unidad;
    
    @Column(name = "stock")
    private int stock;

}

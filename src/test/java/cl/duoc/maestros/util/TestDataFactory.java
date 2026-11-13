package cl.duoc.maestros.util;

import java.sql.Date;
import java.util.Locale;

import cl.duoc.maestros.model.Pedidos.Pedido;
import cl.duoc.maestros.model.Productos.Producto;
import cl.duoc.maestros.model.Usuarios.Cliente.Cliente;
import net.datafaker.Faker;

//datos falsos con DataFaker
public class TestDataFactory {

    private static final Faker FAKER = new Faker(Locale.of("es"));

    private TestDataFactory() {
    }

    public static Producto producto() {
        Producto p = new Producto();
        p.setUrlImagen(FAKER.lorem().word() + ".jpg");
        p.setNombre(FAKER.commerce().productName());
        p.setDescripcion(FAKER.lorem().sentence());
        p.setDetalle(FAKER.lorem().sentence(10));
        p.setPrecio(FAKER.number().numberBetween(1000, 100000));
        p.setId_CATEGORIA(FAKER.number().numberBetween(1, 6));
        p.setSku("LM-" + FAKER.number().digits(4)); // 7 caracteres
        p.setMarca(FAKER.company().name());
        p.setUnidad("Unidad");
        p.setStock(FAKER.number().numberBetween(0, 100));
        return p;
    }

    public static Cliente cliente() {
        Cliente c = new Cliente();
        c.setId(FAKER.number().numberBetween(2L, 1000L));
        c.setIdTipoCliente(FAKER.number().numberBetween(1L, 4L));
        c.setRegion(FAKER.address().state());
        c.setComuna(FAKER.address().city());
        return c;
    }

    public static Pedido pedido() {
        Pedido p = new Pedido();
        p.setIdCliente(FAKER.number().numberBetween(2L, 1000L));
        p.setTotal(FAKER.number().numberBetween(5000L, 500000L));
        p.setFecha(new Date(System.currentTimeMillis()));
        return p;
    }
}
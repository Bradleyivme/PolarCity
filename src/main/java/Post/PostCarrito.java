
package Post;

public class PostCarrito {
    private String codigo;
    private String nombreProducto;
    private Integer precio;

    public PostCarrito(String codigo, String nombreProducto, Integer precio) {
        this.codigo = codigo;
        this.nombreProducto = nombreProducto;
        this.precio = precio;
    }

    public PostCarrito() {
    }
    
    
    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNombreProducto() {
        return nombreProducto;
    }

    public void setNombreProducto(String nombreProducto) {
        this.nombreProducto = nombreProducto;
    }

    public Integer getPrecio() {
        return precio;
    }

    public void setPrecio(Integer precio) {
        this.precio = precio;
    }
    
    
}

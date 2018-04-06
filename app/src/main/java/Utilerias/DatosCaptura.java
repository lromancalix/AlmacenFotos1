package Utilerias;

/**
 * Created by Deintec on 02/10/2016.
 */

public class DatosCaptura {

    private String descripcion;
    private String ruta;
    private String arrayBits;
    private String tipo;
    private String estatus;
    private String id;

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getRuta() {
        return ruta;
    }

    public void setRuta(String ruta) {
        this.ruta = ruta;
    }

    public String getArrayBits() {
        return arrayBits;
    }

    public void setArrayBits(String array) {
        this.arrayBits = array;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getEstatus() {
        return estatus;
    }

    public void setEstatus(String estatus) {
        this.estatus = estatus;
    }

    public void setID(String  Id){this.id = Id;}

    public String getId() {
        return id;
    }

}

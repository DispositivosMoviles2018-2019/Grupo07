package uce.optativa3.com.registrologin;

public class Estudiante{
    public Estudiante(String usuario, String clave, String nombre, String apellido, String email, String celular, String genero, String fecha_nacimiento, String asignaturas, String beca) {
        this.usuario = usuario;
        this.clave = clave;
        this.nombre = nombre;
        this.apellido = apellido;
        this.email = email;
        this.celular = celular;
        this.genero = genero;
        this.fecha_nacimiento = fecha_nacimiento;
        this.asignaturas = asignaturas;
        this.beca = beca;
    }

    private String usuario, clave, nombre, apellido, email, celular,genero,fecha_nacimiento,asignaturas,beca;

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public String getFecha_nacimiento() {
        return fecha_nacimiento;
    }

    public void setFecha_nacimiento(String fecha_nacimiento) {
        this.fecha_nacimiento = fecha_nacimiento;
    }

    public String getAsignaturas() {
        return asignaturas;
    }

    public void setAsignaturas(String asignaturas) {
        this.asignaturas = asignaturas;
    }

    public String getBeca() {
        return beca;
    }

    public void setBeca(String beca) {
        this.beca = beca;
    }

    @Override
    public String toString() {
        return "Estudiante{" +
                "usuario='" + usuario + '\'' +
                ", clave='" + clave + '\'' +
                ", nombre='" + nombre + '\'' +
                ", apellido='" + apellido + '\'' +
                ", email='" + email + '\'' +
                ", celular='" + celular + '\'' +
                ", genero='" + genero + '\'' +
                ", fecha_nacimiento='" + fecha_nacimiento + '\'' +
                ", asignaturas='" + asignaturas + '\'' +
                ", beca='" + beca + '\'' +
                '}';
    }
}

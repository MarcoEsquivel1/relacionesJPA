package models;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.Date;
import java.text.SimpleDateFormat;

@Entity
@Cacheable(false)
@Table(name = "pedido", schema = "pruebajpa")
public class PedidoEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private int id;
    @Basic
    @Column(name = "id_cliente")
    private int idCliente;
    @Basic
    @Column(name = "fecha")
    private Date fecha;
    @Basic
    @Column(name = "total")
    private BigDecimal total;
    @Basic
    @Column(name = "estado")
    private String estado;
    @ManyToOne
    @PrimaryKeyJoinColumn(name = "id_cliente", referencedColumnName = "id")
    private ClienteEntity clienteByIdCliente;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PedidoEntity that = (PedidoEntity) o;

        if (id != that.id) return false;
        if (idCliente != that.idCliente) return false;
        if (fecha != null ? !fecha.equals(that.fecha) : that.fecha != null) return false;
        if (total != null ? !total.equals(that.total) : that.total != null) return false;
        if (estado != null ? !estado.equals(that.estado) : that.estado != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + idCliente;
        result = 31 * result + (fecha != null ? fecha.hashCode() : 0);
        result = 31 * result + (total != null ? total.hashCode() : 0);
        result = 31 * result + (estado != null ? estado.hashCode() : 0);
        return result;
    }

    public ClienteEntity getCliente() {
        return clienteByIdCliente;
    }

    public void setCliente(ClienteEntity clienteByIdCliente) {
        this.clienteByIdCliente = clienteByIdCliente;
    }

    public String getFechaString() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(fecha);
    }
}

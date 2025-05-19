/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Builder;

import Modelo.Venta;
import java.sql.Timestamp;

public class VentaBuilder {
    private int id;
    private String cliente;
    private String vendedor;
    private double total;
    private Timestamp fecha;

    public VentaBuilder setCliente(String cliente) {
        this.cliente = cliente;
        return this;
    }

    public VentaBuilder setVendedor(String vendedor) {
        this.vendedor = vendedor;
        return this;
    }

    public VentaBuilder setTotal(double total) {
        this.total = total;
        return this;
    }

    public VentaBuilder setFecha(Timestamp fecha) {
        this.fecha = fecha;
        return this;
    }
    
    public Venta build() {
        return new Venta(0, cliente, vendedor, total, fecha);
    }
}

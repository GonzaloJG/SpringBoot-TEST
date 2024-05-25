package org.test.springboot.app.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Generated;
import lombok.NoArgsConstructor;
import org.test.springboot.app.exceptions.DineroInsuficienteException;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name="cuentas")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Cuenta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String persona;
    private BigDecimal saldo;

    public void debito(BigDecimal monto){
        BigDecimal nuevoSaldo = this.saldo.subtract(monto);
        if (nuevoSaldo.compareTo(BigDecimal.ZERO) < 0) {
            throw new DineroInsuficienteException("Dinero insuficiente en la cuenta.");
        }
        this.saldo = nuevoSaldo;
    }
    public void credito(BigDecimal monto){
        this.saldo = this.saldo.add(monto);
    }
}

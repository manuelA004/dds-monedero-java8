package dds.monedero.model;

import dds.monedero.exceptions.MaximaCantidadDepositosException;
import dds.monedero.exceptions.MaximoExtraccionDiarioException;
import dds.monedero.exceptions.MontoNegativoException;
import dds.monedero.exceptions.SaldoMenorException;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Cuenta {

  private double saldo = 0;
  private List<Movimiento> movimientos = new ArrayList<>();
  private Integer limiteDiario;

  public Cuenta() {
    saldo = 0;
    limiteDiario = 1000;
  }

  public Cuenta(double montoInicial, Integer limiteDiario) {
    this.saldo = montoInicial;
    this.limiteDiario = limiteDiario;
  }

  public void depositarDinero(double cuanto) {
    chequeaExcepcionesDeposito(cuanto);
    var movimiento = new Movimiento(LocalDate.now(), cuanto, true);
    agregarMovimiento(movimiento);
  }

  public void retirarDinero(double cuanto) {
    chequeaExcepcionesIngreso(cuanto);
    var movimiento = new Movimiento(LocalDate.now(), -cuanto, false);
    agregarMovimiento(movimiento);
  }

  public void agregarMovimiento(Movimiento movimiento) {
    movimientos.add(movimiento);
    saldo += movimiento.getMonto();
  }
  public void chequearMontoNegativo(double cuanto) {
    if (cuanto <= 0) {
      throw new MontoNegativoException(cuanto);
    }
  }

  public void chequeaExcepcionesDeposito(double cuanto) {
    chequearMontoNegativo(cuanto);

    if (movimientos.stream()
        .filter(movimiento -> movimiento.fueDepositado(LocalDate.now()))
        .count() >= 3) {
      throw new MaximaCantidadDepositosException(3);
    }

  }
  public void chequeaExcepcionesIngreso(double cuanto) {
    chequearMontoNegativo(cuanto);



    var montoExtraidoHoy = getMontoExtraidoA(LocalDate.now());
    var limite = limiteDiario - montoExtraidoHoy;
    if (cuanto > limite) {
      throw new MaximoExtraccionDiarioException(limiteDiario,limite);
    }
  }

  public double getMontoExtraidoA(LocalDate fecha) {
    return movimientos.stream()
        .filter(movimiento -> !movimiento.isDeposito() && movimiento.getFecha().equals(fecha))
        .mapToDouble(Movimiento::getMonto)
        .sum();
  }

  public List<Movimiento> getMovimientos() {
    return movimientos;
  }

  public void setMovimientos(List<Movimiento> movimientos) {
    this.movimientos = movimientos;
  }

  public double getSaldo() {
    return saldo;
  }

  public void setSaldo(double saldo) {
    this.saldo = saldo;
  }

}

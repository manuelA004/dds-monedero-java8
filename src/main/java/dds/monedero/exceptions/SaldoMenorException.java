package dds.monedero.exceptions;

public class SaldoMenorException extends RuntimeException {
  public SaldoMenorException(Double saldo) {
    super("No puede sacar mas de " + saldo + " $");
  }
}
package dds.monedero.exceptions;

public class MaximaCantidadDepositosException extends RuntimeException {

  public MaximaCantidadDepositosException(Integer cantidad) {
    super("Ya excedio los " + cantidad + " depositos diarios");
  }

}
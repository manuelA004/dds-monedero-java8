package dds.monedero.exceptions;

public class MaximoExtraccionDiarioException extends RuntimeException {
  public MaximoExtraccionDiarioException(Integer LimiteDiario, Double limite) {
    super( "No puede extraer mas de $ " + LimiteDiario + " diarios, " + "límite: " + limite);
  }
}
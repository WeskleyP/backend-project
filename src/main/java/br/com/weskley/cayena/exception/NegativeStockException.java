package br.com.weskley.cayena.exception;

public class NegativeStockException extends RuntimeException {

    public NegativeStockException(String message) {
        super(message);
    }
}

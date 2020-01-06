package ru.craftside.lunchvote.util.exception;

/**
 * Created at 06.01.2020
 *
 * @author Pavel Tolstenkov
 */
public class IllegalRequestDataException extends RuntimeException {

    public IllegalRequestDataException(String msg) {
        super(msg);
    }
}

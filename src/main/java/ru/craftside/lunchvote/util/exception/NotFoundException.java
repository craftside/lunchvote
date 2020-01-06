package ru.craftside.lunchvote.util.exception;

/**
 * Created at 06.01.2020
 *
 * @author Pavel Tolstenkov
 */
public class NotFoundException extends RuntimeException {

    public NotFoundException(String arg) {
        super(arg);
    }
}

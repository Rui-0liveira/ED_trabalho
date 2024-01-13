/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Exceptions;

/**
 *
 * @author 8210311 Daniela Moreira
 * @author 8210367 Orlando Pires
 */
public class ArrayException extends Exception {

    /**
     * Creates a new instance of <code>ArrayException</code> without detail
     * message.
     */
    public ArrayException() {
    }

    /**
     * Constructs an instance of <code>ArrayException</code> with the specified
     * detail message.
     *
     * @param msg the detail message.
     */
    public ArrayException(String msg) {
        super(msg);
    }
}

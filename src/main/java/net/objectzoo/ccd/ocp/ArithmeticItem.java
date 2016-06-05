package net.objectzoo.ccd.ocp;

/**
 * Überschrift:   DSA-Manager
 * Beschreibung:  dsaman-Projekt auf www.sourceforge.net
 * Copyright:     Copyright (c) 2002
 */

public interface ArithmeticItem
{

    public static final int TYPE_TERM = 1;
    public static final int TYPE_ATOMIC_VARIABLE = 2;
    public static final int TYPE_REPOSITORY_ITEM = 3;

    /**
     * Returns the resolved value of this ArithmeticItem
     *
     * @return the resolved value of this ArithmeticItem
     * @throws ArithmeticException  if it cannot be evaluated
     * @throws NullPointerException if a nullpointer has ocurred
     */
    public double resolve() throws ArithmeticException, NullPointerException;

    /**
     * Returns the resolved value of this ArithmeticItem for the given operation-type
     *
     * @param operationType the type of operation this Item is involved in
     * @return the resolved value of this ArithmeticItem
     * @throws ArithmeticException  if it cannot be evaluated
     * @throws NullPointerException if a nullpointer has ocurred
     */
    public double resolve(int operationType) throws ArithmeticException, NullPointerException;

}
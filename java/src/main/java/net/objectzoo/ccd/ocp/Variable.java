package net.objectzoo.ccd.ocp;

/**
 * Überschrift:   DSA-Manager
 * Beschreibung:  dsaman-Projekt auf www.sourceforge.net
 * Copyright:     Copyright (c) 2002
 */
public class Variable implements ArithmeticItem
{

    private static final int TYPE = TYPE_ATOMIC_VARIABLE;

    private String name = "";
    private double value;
    private boolean isNull;

    public Variable(Object object)
    {
        this.name = "";
        this.value = 0;
        this.isNull = true;
    }

    public Variable(double value)
    {
        this.name = "";
        this.value = value;
        this.isNull = false;
    }

    public Variable(String name, double value)
    {
        this.name = name;
        this.value = value;
        this.isNull = false;
    }

    public String getName()
    {
        return this.name;
    }

    public int getType()
    {
        return this.TYPE;
    }

    public double resolve() throws ArithmeticException, NullPointerException
    {
        return this.value;
    }

    public double resolve(int operationType) throws ArithmeticException, NullPointerException
    {
        if (this.isNull)
        {
            switch (operationType)
            {
                case LexicalAnalyzer.MULTIPLICATION:
                case LexicalAnalyzer.DIVISION:
                    return 1;
                case LexicalAnalyzer.SUM:
                case LexicalAnalyzer.SUBTRACTION:
                    return 0;
                default:
            }
        }
        return resolve();
    }

    public String toString()
    {
        return Double.toString(this.value);
    }

}
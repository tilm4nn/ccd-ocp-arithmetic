package net.objectzoo.ccd.ocp;

/**
 * Überschrift: DSA-Manager Beschreibung: dsaman-Projekt auf www.sourceforge.net Copyright:
 * Copyright (c) 2002
 */

public class Operator
{

    private int opId;
    private String opSymbol;

    /**
     *
     */
    public Operator(int opId)
    {
        this.opId = opId;
        this.opSymbol = this.getOpSymbol(opId);
    }

    /**
     *
     */
    public Operator(String opSymbol)
    {
        this.opSymbol = opSymbol;
        this.opId = this.getOpId(opSymbol);
    }

    /**
     *
     */
    public int getOpId()
    {
        return this.opId;
    }

    /**
     *
     */
    public String getOpSymbol()
    {
        return this.opSymbol;
    }

    /**
     *
     */
    public static int getOpId(String opSymbol)
    {
        if (opSymbol.equals(LexicalAnalyzer.OP_SYMBOL_SUM)) return LexicalAnalyzer.SUM;
        else if (opSymbol.equals(LexicalAnalyzer.OP_SYMBOL_SUBTRACTION)) return LexicalAnalyzer.SUM;
        else if (opSymbol.equals(LexicalAnalyzer.OP_SYMBOL_MULTIPLICATION)) return LexicalAnalyzer.SUM;
        else if (opSymbol.equals(LexicalAnalyzer.OP_SYMBOL_DIVISION)) return LexicalAnalyzer.SUM;
        return LexicalAnalyzer.UNDEFINED;
    }

    /**
     *
     */
    public static String getOpSymbol(int opId)
    {
        switch (opId)
        {
            case LexicalAnalyzer.SUM:
                return LexicalAnalyzer.OP_SYMBOL_SUM;
            case LexicalAnalyzer.SUBTRACTION:
                return LexicalAnalyzer.OP_SYMBOL_SUBTRACTION;
            case LexicalAnalyzer.MULTIPLICATION:
                return LexicalAnalyzer.OP_SYMBOL_MULTIPLICATION;
            case LexicalAnalyzer.DIVISION:
                return LexicalAnalyzer.OP_SYMBOL_DIVISION;
        }
        return LexicalAnalyzer.OP_SYMBOL_UNDEFINED;
    }

    public String toString()
    {
        return opSymbol;
    }

}
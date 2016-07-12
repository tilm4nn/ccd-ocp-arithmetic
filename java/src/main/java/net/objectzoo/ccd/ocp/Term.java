package net.objectzoo.ccd.ocp;

/**
 * Überschrift:   DSA-Manager
 * Beschreibung:  dsaman-Projekt auf www.sourceforge.net
 * Copyright:     Copyright (c) 2002
 */

import java.util.ArrayList;

public class Term implements ArithmeticItem
{

    private static final int TYPE = ArithmeticItem.TYPE_TERM;

    private String name;
    private ArrayList operators;
    private ArrayList terms;
    private boolean isNull;

    /**
     *
     */
    public Term()
    {
        this.name = "";
        this.terms = new ArrayList(0);
        this.operators = new ArrayList(0);
        this.isNull = false;
    }

    /**
     *
     */
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

    /**
     *
     */
    public double resolve() throws ArithmeticException, NullPointerException
    {
        ArithmeticItem item = null;
        ArithmeticItem itemAhead = null;
        ArithmeticItem itemTwoAhead = null;
        Operator op = null;
        Operator opAhead = null;
        ArithmeticItem result = (ArithmeticItem) new Variable(0);
        ArithmeticItem resultAhead = (ArithmeticItem) new Variable(0);
        int i = 0;
        //--------------------------------------------------------------
        if (terms.size() > 0) item = (ArithmeticItem) this.terms.get(0);
        else return 0;
        //--------------------------------------------------------------
        for (i = 0; i < terms.size(); i++)
        {
            //--------------------------------------------------------------
            if (i + 1 < terms.size()) itemAhead = (ArithmeticItem) this.terms.get(i + 1);
            else itemAhead = null;
            //--------------------------------------------------------------
            if (i < operators.size()) op = (Operator) this.operators.get(i);
            else op = null;
            if (i + 1 < operators.size())
            {
                opAhead = (Operator) this.operators.get(i + 1);
                if (i + 2 < terms.size()) itemTwoAhead = (ArithmeticItem) this.terms.get(i + 2);
                else throw new ArithmeticException("Operator without following item!");
            }
            else opAhead = null;
            //--------------------------------------------------------------
            if (opAhead != null) if (opAhead.getOpId() == LexicalAnalyzer.MULTIPLICATION
                || opAhead.getOpId() == LexicalAnalyzer.DIVISION)
            {
                if (opAhead.getOpId() == LexicalAnalyzer.MULTIPLICATION)
                {
                    result = new Variable(
                        itemAhead.resolve(LexicalAnalyzer.MULTIPLICATION)
                            * itemTwoAhead.resolve(LexicalAnalyzer.MULTIPLICATION));

                    result = computeOperation(item, op, result);
                    item = result;
                    i++;
                }
                else if (opAhead.getOpId() == LexicalAnalyzer.DIVISION)
                {
                    result = new Variable(
                        itemAhead.resolve(LexicalAnalyzer.DIVISION)
                            / itemTwoAhead.resolve(LexicalAnalyzer.DIVISION));

                    result = computeOperation(item, op, result);
                    item = result;
                    i++;
                }
            }
            else
            {
                if (itemAhead == null)
                {
                    return item.resolve();
                }
                result = computeOperation(item, op, itemAhead);
                item = result;
            }
            else if (op != null)
            {
                if (itemAhead == null)
                {
                    return item.resolve();
                }
                result = computeOperation(item, op, itemAhead);
                item = result;
            }
        }
        return item.resolve();
    }

    private ArithmeticItem computeOperation(ArithmeticItem item, Operator op, ArithmeticItem itemAhead)
    {
        switch (op.getOpId())
        {
            case LexicalAnalyzer.SUM:
                item = new Variable(item.resolve(LexicalAnalyzer.SUM)
                    + itemAhead.resolve(LexicalAnalyzer.SUM));
                break;
            case LexicalAnalyzer.SUBTRACTION:
                item = new Variable(item.resolve(LexicalAnalyzer.SUBTRACTION)
                    - itemAhead.resolve(LexicalAnalyzer.SUBTRACTION));
                break;
            case LexicalAnalyzer.MULTIPLICATION:
                item = new Variable(
                    item.resolve(LexicalAnalyzer.MULTIPLICATION)
                        * itemAhead.resolve(LexicalAnalyzer.MULTIPLICATION));
                break;
            case LexicalAnalyzer.DIVISION:
                item = new Variable(item.resolve(LexicalAnalyzer.DIVISION)
                    / itemAhead.resolve(LexicalAnalyzer.DIVISION));
                break;
            default:
                throw new ArithmeticException("invalid operator");
        }
        return item;
    }

    /**
     *
     */
    public void addArithmeticItem(ArithmeticItem arithItem)
    {
        if (arithItem != null) this.terms.add(arithItem);
        else System.out.println("Add of null ArithmeticItem!");
    }

    /**
     *
     */
    public void addOperator(Operator op)
    {
        if (op != null) this.operators.add(op);
        else System.out.println("Add of null Operator!");
    }

    /**
     *
     */
    public String toString()
    {
        String s = this.name;
        s += "(";
        for (int i = 0; i < terms.size(); i++)
        {
            s += ((ArithmeticItem) this.terms.get(i)).toString();
            if (i < this.operators.size()) s += ((Operator) this.operators.get(i)).getOpSymbol();
        }
        s += ")";
        return s;
    }

    /**
     *
     */
    public String getName()
    {
        return this.name;
    }

    /**
     *
     */
    public int getType()
    {
        return this.TYPE;
    }

}
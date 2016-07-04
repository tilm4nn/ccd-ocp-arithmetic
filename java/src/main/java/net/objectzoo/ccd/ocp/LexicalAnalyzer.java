package net.objectzoo.ccd.ocp;

/**
 * Überschrift:   DSA-Manager
 * Beschreibung:  dsaman-Projekt auf www.sourceforge.net
 * Copyright:     Copyright (c) 2002
 */

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LexicalAnalyzer
{

    public static final int SUM = 43;
    public static final int SUBTRACTION = 45;
    public static final int MULTIPLICATION = 42;
    public static final int DIVISION = 47;
    public static final int WORD = 512;
    public static final int NUMBER = 1024;
    public static final int QUOTED_STRING = 34;
    public static final int LEFT_PAREN = 40;
    public static final int RIGHT_PAREN = 41;
    public static final int EOF = 100;
    public static final int OPERATOR = 110;
    public static final int TERM = 120;
    public static final int PAREN = 130;
    public static final int UNDEFINED = 300;

    public static final String OP_SYMBOL_SUM = "+";
    public static final String OP_SYMBOL_SUBTRACTION = "-";
    public static final String OP_SYMBOL_MULTIPLICATION = "*";
    public static final String OP_SYMBOL_DIVISION = "/";
    public static final String OP_SYMBOL_UNDEFINED = "UNDEFINED";

    private static final String NUMBER_REGEX = "[0-9\\.]+";

    private static final String NAME_REGEX = "[a-zA-Z][a-zA-Z_0-9]+";
    private static final String PARENTHESIS_REGEX = "[()]";
    private static final String OPERATOR_REGEX = "[/*+-]";

    private int lastToken = 0;

    private double number;

    private String string;

    private Matcher matcher;

    Pattern pattern = Pattern.compile("\\s*(" + NUMBER_REGEX + ")|(" + NAME_REGEX + ")|(" + PARENTHESIS_REGEX
        + ")|(" + OPERATOR_REGEX + ")");

    public LexicalAnalyzer(String term)
    {
        matcher = pattern.matcher(term);
    }

    public String getString()
    {
        return string;
    }

    public double getNumber()
    {
        return number;
    }

    public int getTType()
    {
        return lastToken;
    }

    public int nextToken()
    {
        int token;

        if (matcher.find())
        {
            token = getToken(matcher);
        }
        else
        {
            token = EOF;
        }

        lastToken = token;
        return token;
    }

    private int getToken(Matcher matcher)
    {
        string = matcher.group(1);
        if (string != null)
        {
            number = Double.valueOf(string);
            return NUMBER;
        }

        string = matcher.group(2);
        if (string != null)
        {
            return WORD;
        }

        string = matcher.group(3);
        if (string != null)
        {
            switch (string)
            {
                case "(":
                    return LEFT_PAREN;
                case ")":
                    return RIGHT_PAREN;
                default:
                    throw new IllegalArgumentException("Unexpected token " + string);
            }
        }

        string = matcher.group(4);
        if (string != null)
        {
            switch (string)
            {
                case "+":
                    return SUM;
                case "-":
                    return SUBTRACTION;
                case "/":
                    return DIVISION;
                case "*":
                    return MULTIPLICATION;
                default:
                    throw new IllegalArgumentException("Unexpected token " + string);
            }
        }

        throw new IllegalArgumentException("Unexpected token " + string);
    }

}
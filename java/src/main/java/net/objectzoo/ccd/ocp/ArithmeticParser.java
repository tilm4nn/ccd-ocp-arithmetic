package net.objectzoo.ccd.ocp;

/**
 * Überschrift:   DSA-Manager
 * Beschreibung:  dsaman-Projekt auf www.sourceforge.net
 * Copyright:     Copyright (c) 2002
 */

import java.util.HashMap;

public class ArithmeticParser
{

    private HashMap arithmeticItemIndex;
    private HashMap overrideIndex;
    private LexicalAnalyzer lexer;
    private int token;
    private int lastToken;
    private int tokenType;
    private int lastTokenType;
    private int klammerAuf;
    private int klammerZu;

    public ArithmeticParser(HashMap arithmeticItemIndex)
    {
        this.arithmeticItemIndex = arithmeticItemIndex;
        this.overrideIndex = null;
        this.lexer = null;
        this.token = 0;
        this.lastToken = 0;
        this.tokenType = 0;
        this.lastTokenType = 0;
        this.klammerAuf = 0;
        this.klammerZu = 0;
    }

    public Term parse(String input, HashMap overrideIndex) throws ArithmeticException
    {
        this.overrideIndex = overrideIndex;
        return parse(input);
    }

    public Term parse(String input) throws ArithmeticException
    {
        Term term = null;
        lexer = new LexicalAnalyzer(input);
        term = term();
        if (klammerAuf != klammerZu)
            throw new ArithmeticException("parentheses mismatch");
        return term;
    }

    private Term term() throws ArithmeticException
    {
        Term term = new Term();
        ArithmeticItem arithItem = null;
        while (token != LexicalAnalyzer.EOF)
        {
            nextToken();
            switch (token)
            {
                case LexicalAnalyzer.WORD:
                    tokenType = LexicalAnalyzer.TERM;
                    if (this.overrideIndex == null)
                        arithItem = (ArithmeticItem) arithmeticItemIndex.get(lexer.getString());
                    else
                    {
                        arithItem = (ArithmeticItem) overrideIndex.get(lexer.getString());
                        if (arithItem == null)
                            arithItem = (ArithmeticItem) arithmeticItemIndex.get(lexer.getString());
                    }
                    if (arithItem != null)
                    {
                        term.addArithmeticItem(arithItem);
                    }
                    else
                        term.addArithmeticItem(new Variable(null));
                    //throw new ArithmeticException ( "variable name '"+ lexer.getString() + "' could not be resolved!" );
                    break;
                case LexicalAnalyzer.LEFT_PAREN:
                    tokenType = LexicalAnalyzer.LEFT_PAREN;
                    klammerAuf++;
                    term.addArithmeticItem((ArithmeticItem) term());
                    break;
                case LexicalAnalyzer.RIGHT_PAREN:
                    tokenType = LexicalAnalyzer.RIGHT_PAREN;
                    klammerZu++;
                    return term;
                case LexicalAnalyzer.SUM:
                    tokenType = LexicalAnalyzer.OPERATOR;
                    term.addOperator(new Operator(LexicalAnalyzer.SUM));
                    break;
                case LexicalAnalyzer.SUBTRACTION:
                    tokenType = LexicalAnalyzer.OPERATOR;
                    term.addOperator(new Operator(LexicalAnalyzer.SUBTRACTION));
                    break;
                case LexicalAnalyzer.MULTIPLICATION:
                    tokenType = LexicalAnalyzer.OPERATOR;
                    term.addOperator(new Operator(LexicalAnalyzer.MULTIPLICATION));
                    break;
                case LexicalAnalyzer.DIVISION:
                    tokenType = LexicalAnalyzer.OPERATOR;
                    term.addOperator(new Operator(LexicalAnalyzer.DIVISION));
                    break;
                case LexicalAnalyzer.NUMBER:
                    tokenType = LexicalAnalyzer.TERM;
                    arithItem = (ArithmeticItem) new Variable("", lexer.getNumber());
                    if (arithItem != null)
                        term.addArithmeticItem(arithItem);
                    break;
                case LexicalAnalyzer.EOF:
                    tokenType = LexicalAnalyzer.EOF;
                    return term;
                default:
                    throw new ArithmeticException("unexpected token " + tokenName(token));
            }
            checkTokenType();
        }
        return term;
    }

    private void nextToken() throws ArithmeticException
    {
        lastToken = token;
        lastTokenType = tokenType;
        token = lexer.nextToken();
        System.out.println(tokenName(token));
    }

    public void reset()
    {
        this.lexer = null;
        this.overrideIndex = null;
        this.token = 0;
        this.lastToken = 0;
        this.tokenType = 0;
        this.lastTokenType = 0;
        this.klammerAuf = 0;
        this.klammerZu = 0;
    }

    public void setArithmeticItemIndex(HashMap arithmeticItemIndex) throws IllegalArgumentException
    {
        if (arithmeticItemIndex != null)
            this.arithmeticItemIndex = arithmeticItemIndex;
        else
            throw new IllegalArgumentException("arithmetic item index is null!");
    }

    private void checkTokenType()
    {
        if (!(token == LexicalAnalyzer.EOF))
        {
            switch (lastTokenType)
            {
                case LexicalAnalyzer.LEFT_PAREN:
                    if (tokenType != LexicalAnalyzer.TERM && tokenType != LexicalAnalyzer.LEFT_PAREN)
                        throw new ArithmeticException("unexpected token " + tokenName(token));
                    break;
                case LexicalAnalyzer.OPERATOR:
                    if (tokenType != LexicalAnalyzer.TERM)
                        throw new ArithmeticException("unexpected token " + tokenName(token));
                    break;
                case LexicalAnalyzer.TERM:
                    if (tokenType != LexicalAnalyzer.OPERATOR && tokenType != LexicalAnalyzer.RIGHT_PAREN)
                        throw new ArithmeticException("unexpected token " + tokenName(token));
                    break;
                default:
            }
        }
    }

    private String tokenName(int t)
    {
        String tname = "";
        switch (t)
        {
            case LexicalAnalyzer.SUM:
                tname = "SUM: " + (char) lexer.getTType();
                break;
            case LexicalAnalyzer.SUBTRACTION:
                tname = "SUBTRACTION: " + (char) lexer.getTType();
                break;
            case LexicalAnalyzer.MULTIPLICATION:
                tname = "MULTIPLICATION: " + (char) lexer.getTType();
                break;
            case LexicalAnalyzer.DIVISION:
                tname = "DIVISION: " + (char) lexer.getTType();
                break;
            case LexicalAnalyzer.WORD:
                tname = "WORD: " + lexer.getString();
                break;
            case LexicalAnalyzer.NUMBER:
                tname = "NUMBER: " + lexer.getNumber();
                break;
            case LexicalAnalyzer.QUOTED_STRING:
                tname = "QUOTED_STRING";
                break;
            case LexicalAnalyzer.LEFT_PAREN:
                tname = "LEFT_PAREN: " + (char) lexer.getTType();
                break;
            case LexicalAnalyzer.RIGHT_PAREN:
                tname = "RIGHT_PAREN: " + (char) lexer.getTType();
                break;
            case LexicalAnalyzer.EOF:
                tname = "EOF";
                break;
            default:
                tname = "UNEXPECTED: " + (char) lexer.getTType();
        }
        return tname;
    }

}
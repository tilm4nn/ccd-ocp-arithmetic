package net.objectzoo.ccd.ocp;

/**
 * Überschrift:   DSA-Manager
 * Beschreibung:  dsaman-Projekt auf www.sourceforge.net
 * Copyright:     Copyright (c) 2002
 */

import java.util.HashMap;

public class TestContainer
{

    private HashMap repositoryItemIndex;
    private ArithmeticParser parser;
    private String usageText;
    private String defaultExpression;
    private String variableValueExpression;
    private String result;

    public TestContainer()
    {

        repositoryItemIndex = new HashMap();
        defaultExpression = "((VAR01+3-VAR03)*VAR06/(7.7+VAR10)/VAR05)/2.1"; // == 0,0141723356009070294784580498866213
        variableValueExpression = "";
        result = "";

        Variable var01 = new Variable("VAR01", 1.0);
        Variable var02 = new Variable("VAR02", 2.9);
        Variable var03 = new Variable("VAR03", 3.8);
        Variable var04 = new Variable("VAR04", 4.7);
        Variable var05 = new Variable("VAR05", 5.6);
        Variable var06 = new Variable("VAR06", 6.5);
        Variable var07 = new Variable("VAR07", 7.4);
        Variable var08 = new Variable("VAR08", 8.3);
        Variable var09 = new Variable("VAR09", 9.2);
        Variable var10 = new Variable("VAR10", 0.1);

        repositoryItemIndex.put(var01.getName(), var01);
        repositoryItemIndex.put(var02.getName(), var02);
        repositoryItemIndex.put(var03.getName(), var03);
        repositoryItemIndex.put(var04.getName(), var04);
        repositoryItemIndex.put(var05.getName(), var05);
        repositoryItemIndex.put(var06.getName(), var06);
        repositoryItemIndex.put(var07.getName(), var07);
        repositoryItemIndex.put(var08.getName(), var08);
        repositoryItemIndex.put(var09.getName(), var09);
        repositoryItemIndex.put(var10.getName(), var10);

        parser = new ArithmeticParser(repositoryItemIndex);

        this.usageText = "VAR01 = " + var01.resolve() + "\n";
        this.usageText += "VAR02 = " + var02.resolve() + "\n";
        this.usageText += "VAR03 = " + var03.resolve() + "\n";
        this.usageText += "VAR04 = " + var04.resolve() + "\n";
        this.usageText += "VAR05 = " + var05.resolve() + "\n";
        this.usageText += "VAR06 = " + var06.resolve() + "\n";
        this.usageText += "VAR07 = " + var07.resolve() + "\n";
        this.usageText += "VAR08 = " + var08.resolve() + "\n";
        this.usageText += "VAR09 = " + var09.resolve() + "\n";
        this.usageText += "VAR10 = " + var10.resolve() + "\n";
    }

    public String getUsageText()
    {
        return this.usageText;
    }

    public String getDefaultExpression()
    {
        return this.defaultExpression;
    }

    public String getResult()
    {
        return this.result;
    }

    public String getVariableValueExpression()
    {
        return this.variableValueExpression;
    }

    public void performParserTest(String input)
    {
        variableValueExpression = "";
        result = "";
        //parser.reset ();
        if (input.equals(""))
            return;
        try
        {
            Term term = new ArithmeticParser(repositoryItemIndex).parse(input);
            //Term term = StaticArithmeticParser.parse(input,repositoryItemIndex);
            //Term term = parser.parse(input);
            if (term != null)
            {
                double d = term.resolve();
                result = input + " = " + d;
                variableValueExpression = term.toString();
            }
            else
                result = "Parsing of " + input + " failed!";
        }
        catch (ArithmeticException e)
        {
            result = e.toString();
        }
    }
}
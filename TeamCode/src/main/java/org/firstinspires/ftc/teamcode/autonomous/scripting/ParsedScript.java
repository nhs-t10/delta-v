package org.firstinspires.ftc.teamcode.autonomous.scripting;

import android.renderscript.Script;
import android.renderscript.ScriptC;

import java.lang.reflect.Constructor;
import java.util.ArrayList;


public class ParsedScript {
    private ScriptStatement[] statements;

    public int length;

    private static ArrayList<Class> availableStatements = new ArrayList<>();
    private static ArrayList<Class> availableConstraints = new ArrayList<>();

    //MOVE F TIME 3000
    //MOVE B TIME 1000

    public ParsedScript(String script) {
        this(script.split("\n"));
    }
    public ParsedScript(String[] scriptLines) {
        this.statements = new ScriptStatement[scriptLines.length];
        this.length = scriptLines.length;
    
        for(int j = 0; j < scriptLines.length; j++) {
            String line = scriptLines[j];
            String[] tokenizedLine = line.split(" ");

            ArrayList<ScriptConstraint> constraints = new ArrayList<>();
            ScriptStatement lineStatement = null;

            for(Class state : availableStatements) {
                try {
                    if (((String) (state.getField("TOKEN").get(null))).contains(tokenizedLine[0])) {
                            Class[] cArgs = new Class[1];
                            cArgs[0] = String.class;
                            lineStatement = (ScriptStatement)state.getDeclaredConstructor(cArgs).newInstance(tokenizedLine[0] + " " + tokenizedLine[1]);
                        break;
                    }
                } catch(Exception e) {

                }
            }

            for(int i = 2; i + 1 < tokenizedLine.length; i += 2) {
                try {
                    String[] thisToken = {tokenizedLine[i], tokenizedLine[i + 1]};
                    for (Class constraint : availableConstraints) {
                        try {
                            if (((String) (constraint.getField("TOKEN").get(null))).contains(thisToken[0])) {
                                Class[] cArgs = new Class[1];
                                cArgs[0] = String.class;
                                constraints.add((ScriptConstraint)constraint.getDeclaredConstructor(cArgs).newInstance(thisToken[0] + " " + thisToken[1]));
                                break;
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                } catch(Exception e) {
                    e.printStackTrace();
                }
            }
            if(lineStatement != null) {
                lineStatement.setConstraints((ScriptConstraint[])constraints.toArray());

                statements[j] = lineStatement;
            }
        }
    }

    private void setInitialConstraintsAndStatements() {
        registerStatement(MovementScriptStatement.class);

        registerConstraint(TimeScriptConstraint.class);
    }

    private void registerStatement(Class statement) {

        if(statement.getSuperclass() != ScriptStatement.class) throw new IllegalArgumentException("Statements must inherit from ScriptStatement");


        int actuallyMatchedIndex = -1;

        int i = 0;
        for(Class sc : availableStatements) {
            if(sc.getName() == statement.getName()) actuallyMatchedIndex = i;
            i++;
        }
        try {
            if (i > 0) {
                availableStatements.set(i, statement);
            } else {
                availableStatements.add(statement);
            }
        } catch(Exception e) {}
    }
    private void registerConstraint(Class constraint) {
        if(constraint.getSuperclass() != ScriptConstraint.class) throw new IllegalArgumentException("Constraints must inherit from ScriptConstraint");

        int actuallyMatchedIndex = -1;

        int i = 0;
        for(Class sc : availableConstraints) {
            if(sc.getName() == constraint.getName()) actuallyMatchedIndex = i;
            i++;
        }
        try {
            if (i > 0) {
                availableConstraints.set(i, constraint);
            } else {
                availableConstraints.add(constraint);
            }
        } catch(Exception e) {}
    }

    public ParsedScript(ArrayList<String> scriptLines) {
        this(scriptLines.toArray(new String[0]));
    }

    public ScriptStatement getStatement(int i) {
        return statements[i];
    }
}
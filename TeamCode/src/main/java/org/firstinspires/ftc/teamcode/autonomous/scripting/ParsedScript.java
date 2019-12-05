package org.firstinspires.ftc.teamcode.autonomous.scripting;

import java.util.ArrayList;


public class ParsedScript {
    private ScriptStatement[] statements;

    public int length;

    private static ArrayList<ScriptStatement> availableStatements = new ArrayList<ScriptStatement>();
    private static ArrayList<ScriptConstraint> availableConstraints = new ArrayList<ScriptConstraint>();

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

            ArrayList<ScriptConstraint> constraints = new ArrayList<ScriptConstraint>();
            ScriptStatement lineStatement = null;

            for(ScriptStatement state : availableStatements) {
                try {
                    if (((String) (state.getClass().getField("TOKEN").get(null))).contains(tokenizedLine[0])) {
                            lineStatement = state.getClass().getDeclaredConstructor().newInstance(tokenizedLine[0] + " " + tokenizedLine[1]);
                        break;
                    }
                } catch(Exception e) {

                }
            }

            for(int i = 2; i + 1 < tokenizedLine.length; i += 2) {
                try {
                    String[] thisToken = {tokenizedLine[i], tokenizedLine[i + 1]};
                    for (ScriptConstraint constr : availableConstraints) {
                        try {
                            if (((String) (constr.getClass().getField("TOKEN").get(null))).contains(thisToken[0])) {
                                constraints.add(constr.getClass().getDeclaredConstructor().newInstance(thisToken[0] + " " + thisToken[1]));
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
        int actuallyMatchedIndex = -1;

        int i = 0;
        for(ScriptStatement sc : availableStatements) {
            if(sc.getClass() == statement) actuallyMatchedIndex = i;
            i++;
        }
        try {
            if (i > 0) {
                availableStatements.set(i, (ScriptStatement)statement.getDeclaredConstructor(statement).newInstance());
            } else {
                availableStatements.add((ScriptStatement)statement.getDeclaredConstructor(statement).newInstance());
            }
        } catch(Exception e) {}
    }
    private void registerConstraint(Class constraint) {
        int actuallyMatchedIndex = -1;

        int i = 0;
        for(ScriptConstraint sc : availableConstraints) {
            if(sc.getClass() == constraint) actuallyMatchedIndex = i;
            i++;
        }
        try {
            if (i > 0) {
                availableConstraints.set(i, (ScriptConstraint)constraint.getDeclaredConstructor(constraint).newInstance());
            } else {
                availableConstraints.add((ScriptConstraint)constraint.getDeclaredConstructor(constraint).newInstance());
            }
        } catch(Exception e) {}
    }

    public ParsedScript(ArrayList<String> scriptLines) {
        this((String[])scriptLines.toArray());
    }

    public ScriptStatement getStatement(int i) {
        return statements[i];
    }
}
package org.firstinspires.ftc.teamcode;

import android.app.Application;
import android.content.Context;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

public class ParsedScript {
    private ScriptStatement[] statements;

    private static ArrayList<ScriptStatement> availableStatements = new ArrayList<ScriptStatement>();
    private static ArrayList<ScriptConstraint> availableConstraints = new ArrayList<ScriptConstraint>();

    public ParsedScript(String script) {
        this(script.split("\n"));
    }
    public ParsedScript(String[] scriptLines) {
        this.statements = new ScriptStatement[scriptLines.length];
    
        for(int j = 0; j < scriptLines.length; j++) {
            String line = scriptLines[j];
            String[] tokenizedLine = line.split(" ");

            ArrayList<ScriptContraint> constraints = new ArrayList<ScriptContraint>();
            ScriptStatement lineStatement;

            for(Class state : availableStatements) {
                if(constr.getField("TOKEN").contains(tokenizedLine[0])) {
                    lineStatement = state.getDeclaredConstructor().newInstance(tokenizedLine[0] + " " + tokenizedLine[1]);
                    break;
                }
            }

            for(int i = 2; i + 1 < tokenizedLine.length; i += 2) {
                String[] thisToken = { tokenizedLine[i] , tokenizedLine[i + 1] };
                for(Class constr : availableConstraints) {
                    if(constr.getField("TOKEN") == thisToken[0]) {
                        constraints.add(constr.getDeclaredConstructor().newInstance(thisToken[0] + " " + thisToken[1]));
                        break;
                    }
                }
            }
            lineStatement.setConstraints(constraints.toArray());

            statements[j] = lineStatement;
        }
    }

    private void setInitialConstraintsAndStatements() {
        setInitialStatement(MovementScriptStatement.class);

        setInitialConstraint(TimeScriptConstraint.class);
    }

    private void setInitialStatement(Class statement) {
        int actuallyMatchedIndex = -1;

        int i = 0;
        for(ScriptStatement sc : availableConstraints) {
            if(sc.getClass() == statement) actuallyMatchedIndex = i;
            i++;
        }
        if(i > 0) {
            availableStatements.set(i, constraint.getDeclaredConstructor(ScriptStatement).newInstance());
        } else {
            availableStatements.add(constraint.getDeclaredConstructor(ScriptStatement).newInstance());
        }
    }
    private void setInitialConstraint(Class constraint) {
        int actuallyMatchedIndex = -1;

        int i = 0;
        for(ScriptConstraint sc : availableConstraints) {
            if(sc.getClass() == constraint) actuallyMatchedIndex = i;
            i++;
        }
            if(i > 0) {
                availableConstraints.set(i, constraint.getDeclaredConstructor(ScriptConstraint).newInstance());
            } else {
                availableConstraints.add(constraint.getDeclaredConstructor(ScriptConstraint).newInstance());
            }
    }

    private MovementOrder parseMover(String[] token) {
        if(token[0] == "MOVEDIR" || (token[1].length == 1 && !Char.isDigit(token[1].charAt(0)))) {
            return directionLetter(token[1]);
        } else if(token[0] == "MOVEOMNI" || token[1].split(",").length == 3) {
            String[] numsplit = token[1].split(",");
            return MovementOrder.HVR(Float.parseFloat(token[0]), Float.parseFloat(token[1]), Float.parseFloat(token[2]));
        } else if(token[1].split(",").length == 4) {
            //TODO: implement raw motors in MovementOrder
            return MovementOrder.NOTHING;
        } else return MovementOrder.NOTHING;
    }
    private MovementOrder directionLetter(String letter) {
        switch(letter) {
            case "F":
                //pay respects
                return new MovementOrder.HVR(0f, 1f, 0f);
            case "L":
                return new MovementOrder.HVR(-1f, 0f, 0f);
            case "R":
                return new MovementOrder.HVR(1f, 0f, 0f);
            case "B":
                return new MovementOrder.HVR(0f, -1f, 0f);
        }
        return MovementOrder.NOTHING;
    }

    public ParsedScript(ArrayList<String> scriptLines) {
        this(scriptLines.toArray());
    }

    public ScriptStatement getStatement(int i) {
        return statements[i];
    }
}
private class ScriptStatement {
    public ScriptConstraint[] constraints;

    public final static String TOKEN = ""; 

    public ScriptStatement(ScriptConstraint[] _constraints) {
        this.contstraints = _constraints;
    }
    public boolean finished(RobotState rs) {
        for(ScriptConstraint constr : this.constraints) {
            if(constr.test(rs.get(constr.operator)))
        }
    }
    public ScriptStatement() {}
}

private class MovementScriptStatement extends ScriptStatement {
    public MovementOrder order;

    public final static String[] TOKEN = {"MOVE" , "MOVEDIR", "MOVERAW"};

    public MovementScriptStatement(String[] statement, ScriptConstraint[] _constraints) {
        this.order = parseMover(statement);
        this.contstraints = _constraints;
    }
}

private enum Operation {
    LESS, LESS_EQUAL, EQUAL, MORE_EQUAL, MORE
}
private class ScriptConstraint {
    public final static String TOKEN = ""; 

    public String operator;
    public Operation operation;
    public float target;

    public static boolean process(float current, Operation operation, float target) {
        switch(operation) {
            case LESS: return current < target;
            case LESS_EQUAL: return current <= target;
            case EQUAL: return current == target;
            case MORE_EQUAL: return current >= target;
            case MORE: return current > target;
        }
    }

    public boolean test() {
        return false;
    }
    public boolean test(float target) {
        return false;
    }
    public ScriptConstraint(String arg) {
        this.operator = _operator;
        this.operation = _operation;
        this.target = _target;
    }
    public ScriptConstraint() {}
}

private class TimeScriptConstraint extends ScriptConstraint {
    public final static String TOKEN = "TIME";

    private float initialTime = -1f;

    public boolean test(float time) {
        if(initialTime == -1) initialTime = time;
        return ScriptConstraint.process(time - initialTime, this.operation, this.target);
    }

    public TimeScriptConstraint(String arg) {

        this.operator = "TIME";
        this.operation = Operation.EQUALS_GREATER;
        this.target = Float.parseFloat(arg);
    }
}
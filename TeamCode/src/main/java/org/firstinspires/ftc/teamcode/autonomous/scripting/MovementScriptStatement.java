package org.firstinspires.ftc.teamcode.autonomous.scripting;

import org.firstinspires.ftc.teamcode.data.MovementOrder;

public class MovementScriptStatement extends ScriptStatement {
    public MovementOrder order;

    public final static String TOKEN = "MOVE,MOVEDIR,MOVERAW";

    public MovementScriptStatement(String[] statement, ScriptConstraint[] _constraints) {
        this.order = MovementScriptStatement.parseMover(statement);
        this.constraints = _constraints;
    }

    public static MovementOrder parseMover(String[] token) {
        if(token[0] == "MOVEDIR" || (token[1].length() == 1 && !Character.isDigit(token[1].charAt(0)))) {
            return MovementScriptStatement.parseDirectionLetter(token[1]);

        } else if(token[0] == "MOVEOMNI" || token[1].split(",").length == 3) {
            String[] numsplit = token[1].split(",");
            return MovementOrder.HVR(Float.parseFloat(token[0]), Float.parseFloat(token[1]), Float.parseFloat(token[2]));

        } else if(token[0] == "MOVERAW" || token[1].split(",").length == 4) {
            //TODO: implement raw motors in MovementOrder
            return MovementOrder.NOTHING;

        } else {
            return MovementOrder.NOTHING;
        }
    }
    public static MovementOrder parseDirectionLetter(String letter) {
        switch(letter) {
            case "F":
                //pay respects
                return MovementOrder.HVR(0f, 1f, 0f);
            case "L":
                return MovementOrder.HVR(-1f, 0f, 0f);
            case "R":
                return MovementOrder.HVR(1f, 0f, 0f);
            case "B":
                return MovementOrder.HVR(0f, -1f, 0f);
        }
        return MovementOrder.NOTHING;
    }
}

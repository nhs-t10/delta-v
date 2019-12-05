package org.firstinspires.ftc.teamcode.autonomous.scripting;

import org.firstinspires.ftc.teamcode.data.RobotState;

public class ScriptStatement {
    public ScriptConstraint[] constraints;

    public final static String TOKEN = "";

    public ScriptStatement(ScriptConstraint[] _constraints) {
        this.constraints = _constraints;
    }
    public boolean finished(RobotState rs) {
        for(ScriptConstraint constr : this.constraints) {
            if(!constr.test(rs.get(constr.operator))) return false;
        }
        return true;
    }
    public ScriptStatement() {}

    public void setConstraints(ScriptConstraint[] _constraints) {
        constraints = _constraints;
    }
}
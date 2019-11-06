package org.firstinspires.ftc.teamcode;

public class MovementOrder {
    public float hor;
    public float ver;
    public float rot;

    public float getHor() {
        return hor;
    }

    public void setHor(float hor) {
        this.hor = hor;
    }

    public float getVer() {
        return ver;
    }

    public void setVer(float ver) {
        this.ver = ver;
    }

    public float getRot() {
        return rot;
    }

    public void setRot(float rot) {
        this.rot = rot;
    }

    private MovementOrder(float h, float v, float r) {
        this.hor = h;
        this.ver = v;
        this.rot = r;
    }

    public MovementOrder() {}

    public static MovementOrder HVR (float h, float v, float r) {
        return new MovementOrder(h, v, r);
    }

    public static MovementOrder VHR (float v, float h, float r) {
        return new MovementOrder(h, v, r);
    }

    public static MovementOrder RHV (float r, float h, float v) {
        return new MovementOrder(h, v, r);
    }

    public static MovementOrder RVH (float r, float v, float h) {
        return new MovementOrder(h, v, r);
    }
}

package org.yunghegel.gdx.utils;

import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.badlogic.gdx.utils.NumberUtils;

public class MathUtils {

    public static final float PI = (float) Math.PI;
    public static final float HALF_PI = PI * 0.5f;
    public static final float TWO_PI = PI + PI;
    public static final float ONE_THIRD = 1f / 3f;
    public static final float QUARTER_PI = PI / 4f;
    public static final float ZERO_TOLERANCE = 0.00001f;
    public static final float TRIBONACCI_CONSTANT = 1.8392868f;
    public static final float GOLDEN_RATIO = (1f + sqrt(5f)) / 2f;

    private static final Vector3 tmpVec = new Vector3();

    /**
     * Returns a vector which represents the direction a given matrix is facing.
     *
     * @param transform - in
     * @param out       - out
     */
    public static void getDirection(Matrix4 transform , Vector3 out) {
        tmpVec.set(Vector3.Z);
        out.set(tmpVec.rot(transform).nor());
    }

    /**
     * Gets the world position of modelInstance and sets it on the out vector
     *
     * @param transform modelInstance transform
     * @param out       out vector to be populated with position
     */
    public static void getPosition(Matrix4 transform , Vector3 out) {
        transform.getTranslation(out);
    }

    /**
     * Gets the world position of modelInstance and sets it on the out vector
     *
     * @param transform modelInstance transform
     * @param out       out vector to be populated with position
     */
    public static void getWorldCoordinates(Matrix4 transform , Vector3 out) {
        transform.getTranslation(out);
    }

    /**
     * Returns a random sign, either 1 or -1
     */

    public static int randomSign() {
        return Math.random() > 0.5 ? 1 : -1;
    }

    /**
     * bitwise to convert short to unsigned representation
     * @param s -the short to convert
     * @return the unsigned representation of the short
     */
    public static int unsignedShort(short s) {
        return s & 0xFFFF;
    }

    /**
     * Returns the absolute value of a float
     * @param a - the float to get the absolute value of
     * @return the absolute value of a float
     */

    public static float abs(float a) {
        return (float) Math.abs(a);
    }

    /**
     * Clamp a value between a minimum float and maximum float value
     * @param value - the value to clamp
     * @param min - the minimum value
     * @param max - the maximum value
     * @return the clamped value
     */

    public static float clamp(float value, float min, float max) {
        float clampedValue = value;
        clampedValue = Math.max(clampedValue, min);
        clampedValue = Math.min(clampedValue, max);
        return clampedValue;
    }

    /**
     * Returns the arctangent of the specified float value.
     * @param a - the value whose arc tangent is to be returned.
     * @return the arctangent of the specified float value.
     */

    public static float atan(float a) {
        return (float) Math.atan(a);
    }

    /**
     * Returns the cosine of the specified angle in radians.
     * @param a - an angle, in radians.
     * @return the cosine of the specified angle in radians.
     */

    public static float cos(float a) {
        return (float) Math.cos(a);
    }

    /**
     * Maps a value from one range to another
     * @param value - the value to map
     * @param from0 - the lower bound of the value's current range
     * @param to0 - the upper bound of the value's current range
     * @param from1 - the lower bound of the value's target range
     * @param to1 - the upper bound of the value's target range
     * @return the mapped value
     */

    public static float map(float value, float from0, float to0, float from1, float to1) {
        return from1 + (to1 - from1) * ((value - from0) / (to0 - from0));
    }

    public static int clampInt(int a, int min, int max) {
        a = a < min ? min : (Math.min(a, max));
        return a;
    }

    /**
     * Returns the value clamped between 0 and 1
     * @param a - the value to clamp
     * @return the value clamped between 0 and 1
     */

    public static float clamp01(float a) {
        return clamp(a, 0f, 1f);
    }

    /**
     * Returns the maximum value of an array of floats
     * @param values - the array of floats
     * @return the maximum value of the array
     */

    public static float max(float... values) {
        if (values.length == 0)
            return 0;
        float max = values[0];
        for (int i = 1; i < values.length; i++) {
            max = Math.max(max, values[i]);
        }
        return max;
    }

    /**
     * The minimum value of an array of floats
     * @param values - the array of floats
     * @return the minimum value of the array
     */
    public static float min(float... values) {
        if (values.length == 0)
            return 0;
        float min = values[0];
        for (int i = 1; i < values.length; i++) {
            min = Math.min(min, values[i]);
        }
        return min;
    }


    /**
     * Returns the value of the first argument raised to the power of the second argument
     */
    public static float pow(float a, float b) {
        return (float) Math.pow(a, b);
    }

    /**
     * Returns a random integer between min and max
     * @param min - the minimum value
     * @param max - the maximum value
     * @return a random integer between min and max
     */

    public static int random(int min, int max) {
        return (int) (Math.random() * (max - min)) + min;
    }

    /**
     * Returns a random float between 0 and 1
     * @return a random float between 0 and 1
     */
    public static float random(float min, float max) {
        return (float) (Math.random() * (max - min)) + min;
    }

    /**
     * Rounds a float to the nearest integer
     * @param a - the float to round
     * @return the rounded float
     */
    public static float round(float a) {
        return (float) Math.round(a);
    }

    /**
     * Rounds a float to the nearest integer
     * @param a - the float to round
     * @return the rounded integer
     */

    public static int roundToInt(float a) {
        return Math.round(a);
    }

    /**
     * Returns the sine of a float
     * @param a - the float to get the sine of
     * @return the sine of the float
     */

    public static float sin(float a) {
        return (float) Math.sin(a);
    }

    /**
     * Returns the square root of a float
     * @param a - the float to square root
     * @return the square root of the float
     */

    public static float sqrt(float a) {
        return (float) Math.sqrt(a);
    }

    /**
     * Converts a two dimensional index to a one dimensional index
     * @param rowIndex - the row index
     * @param colIndex - the column index
     *
     * @param numberOfColumns - the number of columns in the two dimensional array
     * @return the one dimensional index
     */

    public static int toOneDimensionalIndex(int rowIndex, int colIndex, int numberOfColumns) {
        return rowIndex * numberOfColumns + colIndex;
    }

    /**
     * Converts a two dimensional index to a one dimensional index
     * @param rowIndex - the row index
     * @param colIndex - the column index
     *
     * @param numberOfColumns - the number of columns in the two dimensional array
     * @return the one dimensional index
     */

    public static short toOneDimensionalIndex(short rowIndex, short colIndex, short numberOfColumns) {
        return (short) (rowIndex * numberOfColumns + colIndex);
    }

    /**
     * Converts degrees to radians
     * @param angDeg - the angle in degrees
     * @return the angle in radians
     */

    public static float toRadians(float angDeg) {
        return (float) Math.toRadians(angDeg);
    }

    private static Quaternion tmpQuat = new Quaternion();

    public static void project(Vector3 v1, Vector3 v2, Vector3 v3, Vector3 direction) {

        Vector3 centroid = new Vector3(v1.x + v2.x + v3.x, v1.y + v2.y + v3.y, v1.z + v2.z + v3.z).scl(0.333333333F);

        v1.sub(centroid);
        v2.sub(centroid);
        v3.sub(centroid);

        Vector3 surfaceNormal = getSurfaceNormal(v1, v2, v3);

        Quaternion quaternion = tmpQuat.setFromCross(surfaceNormal, direction);

        quaternion.transform(centroid);

        quaternion.transform(v1).add(centroid);
        quaternion.transform(v2).add(centroid);
        quaternion.transform(v3).add(centroid);

    }

    /**
     * Calculates the projection of vectorA into vectorB
     *
     * @param pVectorA the 3D vector to be projected
     * @param pVectorB the unit length 3D vector representing the axis
     * @return The 3D projection vector
     */
    public static Vector3 project(Vector3 pVectorA, Vector3 pVectorB){

        final float dotProduct = pVectorA.dot(pVectorB);

        return new Vector3(dotProduct * pVectorB.x, dotProduct * pVectorB.y, dotProduct * pVectorB.z);
    }

    public static Vector3 getSurfaceNormal(Vector3 v1, Vector3 v2, Vector3 v3) {
        return getSurfaceNormal(v1.x, v1.y, v1.z, v2.x, v2.y, v2.z, v3.x, v3.y, v3.z);
    }

    public static Vector3 getSurfaceNormal(float x1, float y1, float z1, float x2, float y2, float z2, float x3, float y3, float z3) {
        Vector3 u = new Vector3(x2 - x1, y2 - y1, z2 - z1);
        return u.crs(x3 - x1, y3 - y1, z3 - z1).nor();
    }

    public static Vector2 getCentroid(float x1, float y1, float x2, float y2, float x3, float y3) {
        return new Vector2(x1 + x2 + x3, y1 + y2 + y3).scl(0.333333333F);
    }

    public static float readFloatFromBytes(byte[] bytes, int offset) {
        int i = 0;
        int value = bytes[0 + offset] & 0xFF;
        value |= bytes[1 + offset]<<(8) & 0xFFFF;
        value |= bytes[2 + offset]<<(16) & 0xFFFFFF;
        value |= bytes[3 + offset]<<(24) & 0xFFFFFFFF;
        return NumberUtils.intBitsToFloat(value);
    }

    public static int writeFloatToBytes(float f, byte[] bytes, int offset) {
        int value = NumberUtils.floatToIntBits(f);
        bytes[0 + offset] = (byte) (value & 0xFF);
        bytes[1 + offset] = (byte) (value>>8 & 0xFF);
        bytes[2 + offset] = (byte) (value>>16 & 0xFF);
        bytes[3 + offset] = (byte) (value>>24 & 0xFF);
        // return next idx
        return offset + 4;
    }

    private static Matrix4 mtx = new Matrix4();
    private static Quaternion q = new Quaternion();

    public static void rotateAround(Vector3 position, Vector3 axis, float angle) {
        q.setFromAxis(axis, angle);
        mtx.set(q);
        position.prj(mtx);
    }

    public static void rotateAround(Vector3 position, Vector3 axis, float angle, Vector3 origin) {
        q.setFromAxis(axis, angle);
        mtx.set(q);
        position.sub(origin);
        position.prj(mtx);
        position.add(origin);
    }

    public static void rotateAround(Vector3 position, Vector3 axis, float angle, Vector3 origin, Vector3 out) {
        q.setFromAxis(axis, angle);
        mtx.set(q);
        out.set(position);
        out.sub(origin);
        out.prj(mtx);
        out.add(origin);
    }

    private static Vector3 crsTmp = new Vector3();
    private static Vector3 tmp = new Vector3();
    private static Vector3 tmp2 = new Vector3();
    private static Vector3 tmp3 = new Vector3();

    public static Vector3 getDownFromVector(Vector3 vec) {
        crsTmp.set(vec).nor();
        crsTmp.crs(Vector3.Y); // cross by world up to get a right handle angle vector (facing forward)
        tmp.set(vec).nor();
        return tmp.crs(crsTmp).nor(); // get the relative "down"
    }

    public static Vector3 getUpFromVector(Vector3 vec) {
        crsTmp.set(vec).nor();
        crsTmp.crs(Vector3.Y); // cross by world up to get a right handle angle vector (facing forward)
        tmp.set(vec).nor();
        return crsTmp.crs(tmp).nor(); // get the relative "down"
    }

    public static Vector3 getRightFromVector(Vector3 vec) {
        crsTmp.set(vec).nor();
        crsTmp.crs(Vector3.Y); // cross by world up to get a right handle angle vector
        //Tools.print(crsTmp, "right");
        //Tools.print(vec, "original");
        return crsTmp.nor();
    }

    public static Vector3 getLeftFromVector(Vector3 vec) {
        crsTmp.set(vec).nor();
        tmp.set(Vector3.Y).crs(crsTmp);
        return tmp.nor();
    }

    public static float getAngleFromAtoB(Vector2 a, Vector2 b) {
        float rawAngle = (float) Math.toDegrees(Math.atan2(b.y - a.y, b.x - a.x));
        return (rawAngle - 90) % 360;
    }

    public static float getAngleFromAtoB(float ax, float ay, float bx, float by) {
        return (float) Math.toDegrees(Math.atan2(by - ay, bx - ax));
    }

    private static Vector2 va = new Vector2();
    private static Vector2 vb = new Vector2();

    public static float getAngleFromAtoB(Vector3 a, Vector3 b, Vector3 axis) {
        if (axis.equals(Vector3.Y)) {
            va.set(a.x, a.z);
            vb.set(b.x, b.z);
            return getAngleFromAtoB(va, vb);
        } else {
            throw new IllegalArgumentException("bad argument for vector angle");
        }
    }

    public static void faceDirectionZ(Quaternion q, Vector3 direction) {
        Vector3 axisZ = tmp.set(direction).nor();
        Vector3	axisY = tmp2.set(tmp).crs(Vector3.Y).nor().crs(tmp).nor();
        Vector3	axisX = tmp3.set(axisY).crs(axisZ).nor();
        q.setFromAxes(false, axisX.x, axisY.x, axisZ.x,
                axisX.y, axisY.y, axisZ.y,
                axisX.z, axisY.z, axisZ.z);
    }

    public static void faceDirectionY(Quaternion q, Vector3 direction) {
        throw new GdxRuntimeException("THIS IS NOT WORKING! DONT USE IT!");
		/*Vector3 axisZ = tmp.set(direction).nor();
		Vector3	axisY = tmp2.set(tmp).crs(Vector3.X).nor().crs(tmp).nor();
		Vector3	axisX = tmp3.set(axisY).crs(axisZ).nor();
		q.setFromAxes(false, axisX.x, axisY.x, axisZ.x,
				axisX.y, axisY.y, axisZ.y,
				axisX.z, axisY.z, axisZ.z);*/
    }

    public static float constrainAngle180(float angle) {
        while (angle > 180) {
            angle = angle - 360;
        }
        while (angle < -180) {
            angle = angle + 360;
        }
        return angle;

    }

    public static Vector3 randomUnitVector() {
        float x = MathUtils.random(0f, 1f);
        float y = MathUtils.random(0f, 1f);
        float z = MathUtils.random(0f, 1f);
        return tmp.set(x, y, z).nor();
    }

    public static float barryCentric(Vector3 p1, Vector3 p2, Vector3 p3, Vector2 pos) {
        float det = (p2.z - p3.z) * (p1.x - p3.x) + (p3.x - p2.x) * (p1.z - p3.z);
        float l1 = ((p2.z - p3.z) * (pos.x - p3.x) + (p3.x - p2.x) * (pos.y - p3.z)) / det;
        float l2 = ((p3.z - p1.z) * (pos.x - p3.x) + (p1.x - p3.x) * (pos.y - p3.z)) / det;
        float l3 = 1.0f - l1 - l2;
        return l1 * p1.y + l2 * p2.y + l3 * p3.y;
    }

    /**
     * Angle between 2 points.
     *
     * @param x1
     * @param y1
     * @param x2
     * @param y2
     * @return
     */
    public static float angle(float x1, float y1, float x2, float y2) {
        return (float) Math.toDegrees(Math.atan2(x2 - x1, y2 - y1));
    }

    /**
     * Get an angle between two Vector3s
     *
     * @param from the vector to compare
     * @param to the vector to compare with
     * @return angle in degrees
     */
    public static float getAngleBetween(Vector3 from, Vector3 to) {
        float absolute = (float) Math.sqrt(from.len() * to.len());
        if (com.badlogic.gdx.math.MathUtils.isZero(absolute))
            return 0; // It is close enough to just return 0

        float angleDot = from.dot(to);
        float dot = com.badlogic.gdx.math.MathUtils.clamp(angleDot / absolute, -1f, 1f);
        return com.badlogic.gdx.math.MathUtils.acos(dot) * com.badlogic.gdx.math.MathUtils.radiansToDegrees;
    }

    /**
     * Rotate a directional vector up/down by given angle.
     *
     * @param vectorToRotate the vector to rotate
     * @param angleDegrees the angle in degrees to rotate by
     */
    public static void rotateUpDown(Vector3 vectorToRotate, float angleDegrees) {
        tmp.set(vectorToRotate);

        // Determine the axis to use
        Vector3 axis = tmp.crs(Vector3.Y);

        // If collinear, set to right
        if (axis == Vector3.Zero) axis = Vector3.X;

        vectorToRotate.rotate(axis, angleDegrees);
    }

    public static boolean isPowerOfTwo(int number) {
        return (number & (number - 1)) == 0;
    }

    /**
     * Find the nearest point on a line to a given point.
     * @param lineStart start of the line
     * @param lineEnd end of the line
     * @param point the point
     * @param out populated with the nearest point on the line
     */
    public static void findNearestPointOnLine(Vector2 lineStart, Vector2 lineEnd, Vector2 point, Vector2 out) {
        Vector2 lineDirection = Pools.vector2Pool.obtain().set(lineEnd).sub(lineStart);

        // Calculate the length of the line.
        float lineLength = lineDirection.len();
        lineDirection.nor();

        // lineStart to point
        Vector2 toPoint = Pools.vector2Pool.obtain().set(point).sub(lineStart);
        float projectedLength = lineDirection.dot(toPoint);

        // Calculate the coordinates of the projected point.
        Vector2 projectedPoint = new Vector2(lineDirection).scl(toPoint.dot(lineDirection));

        Pools.vector2Pool.free(lineDirection);
        Pools.vector2Pool.free(toPoint);

        if (projectedLength < 0) {
            out.set(lineStart);
        }
        else if (projectedLength > lineLength) {
            out.set(lineEnd);
        }
        else {
            // If the projected point lies on the line segment, return the projected point.
            out.set(lineStart).add(projectedPoint);
        }
    }

    /**
     * Calculates the midpoint between two Vector3s
     */
    public static Vector3 midpoint(Vector3 a, Vector3 b) {
        return Pools.vector3Pool.obtain().set(a).add(b).scl(0.5f);
    }







}



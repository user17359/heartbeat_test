package agh.ryszard.blazej.heartbeat.data

import com.google.gson.annotations.SerializedName
import java.util.Arrays


class IMU9DataResponse(@field:SerializedName("Body") val body: Body) {

    override fun toString(): String {
        return "ImuModel{" +
                "mBody=" + body +
                '}'
    }

    class Body {
        @SerializedName("Timestamp")
        val timestamp: Long = 0

        @SerializedName("ArrayAcc")
        val arrayAcc: Array<ArrayAcc>? = null

        @SerializedName("ArrayGyro")
        val arrayGyro: Array<ArrayGyro>? = null

        @SerializedName("ArrayMagn")
        val arrayMagn: Array<ArrayMagn>? = null

        override fun toString(): String {
            return "Body{" +
                    "timestamp=" + timestamp +
                    ", mArrayAcc=" + Arrays.toString(arrayAcc) +
                    ", mArrayGyro=" + Arrays.toString(arrayGyro) +
                    ", mArrayMagnl=" + Arrays.toString(arrayMagn) +
                    '}'
        }
    }

    inner class ArrayAcc(
        @field:SerializedName("x") val x: Double, @field:SerializedName(
            "y"
        ) val y: Double, @field:SerializedName("z") val z: Double
    ) {

        override fun toString(): String {
            return "ArrayAcc{" +
                    "x=" + x +
                    ", y=" + y +
                    ", z=" + z +
                    '}'
        }
    }

    inner class ArrayGyro(
        @field:SerializedName("x") val x: Double, @field:SerializedName(
            "y"
        ) val y: Double, @field:SerializedName("z") val z: Double
    ) {

        override fun toString(): String {
            return "ArrayGyro{" +
                    "x=" + x +
                    ", y=" + y +
                    ", z=" + z +
                    '}'
        }
    }

    inner class ArrayMagn(
        @field:SerializedName("x") val x: Double, @field:SerializedName(
            "y"
        ) val y: Double, @field:SerializedName("z") val z: Double
    ) {

        override fun toString(): String {
            return "ArrayMagn{" +
                    "x=" + x +
                    ", y=" + y +
                    ", z=" + z +
                    '}'
        }
    }
}

package agh.ryszard.blazej.heartbeat.data

import com.google.gson.annotations.SerializedName

class IMU9DataResponse(@field:SerializedName("Body") val body: Body) {
    class Body {
        @SerializedName("Timestamp")
        val timestamp: Long = 0

        @SerializedName("ArrayAcc")
        val arrayAcc: Array<ArrayAcc>? = null

        @SerializedName("ArrayGyro")
        val arrayGyro: Array<ArrayGyro>? = null

        @SerializedName("ArrayMagn")
        val arrayMagn: Array<ArrayMagn>? = null
    }

    inner class ArrayAcc(
        @field:SerializedName("x") val x: Double,
        @field:SerializedName("y") val y: Double,
        @field:SerializedName("z") val z: Double
    )

    inner class ArrayGyro(
        @field:SerializedName("x") val x: Double,
        @field:SerializedName("y") val y: Double,
        @field:SerializedName("z") val z: Double
    )

    inner class ArrayMagn(
        @field:SerializedName("x") val x: Double,
        @field:SerializedName("y") val y: Double,
        @field:SerializedName("z") val z: Double
    )
}

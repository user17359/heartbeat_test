package agh.ryszard.blazej.heartbeat.data

import com.google.gson.annotations.SerializedName

class AccDataResponse {
    @SerializedName("Body")
    var body: Body? = null

    fun AccDataResponse(body: Body?) {
        this.body = body
    }

    class Body(
        @field:SerializedName("Timestamp") val timestamp: Long,
        @field:SerializedName("ArrayAcc") val array: kotlin.Array<Array>,
        @field:SerializedName("Headers") val header: Headers
    )

    class Array(
        @field:SerializedName("x") val x: Double,
        @field:SerializedName("y") val y: Double,
        @field:SerializedName("z") val z: Double
    )

    class Headers(@field:SerializedName("Param0") val param0: Int)
}
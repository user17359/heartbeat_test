package agh.ryszard.blazej.heartbeat.data

import com.google.gson.annotations.SerializedName




class HrDataResponse {

    @SerializedName("Body")
    val body: Body? = null

    class Body(
        @field:SerializedName("average") val average: Float? = null,
        @field:SerializedName("rrData") val rrData: IntArray
    )
}
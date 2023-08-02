package agh.ryszard.blazej.heartbeat.data

import com.google.gson.annotations.SerializedName

class EcgDataResponse {

    @SerializedName("Body")
    var body: Body? = null

    class Body(
        @field:SerializedName("Samples") val data: Array<Int>? = null,
        @field:SerializedName("Timestamp") val timestamp: Long
    )
}
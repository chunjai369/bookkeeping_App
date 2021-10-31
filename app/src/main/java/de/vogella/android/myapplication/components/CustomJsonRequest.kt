package de.vogella.android.myapplication.components

import com.android.volley.NetworkResponse
import com.android.volley.ParseError
import com.android.volley.Response
import com.android.volley.toolbox.HttpHeaderParser
import com.android.volley.toolbox.JsonRequest
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.io.UnsupportedEncodingException
import java.nio.charset.Charset


//val json = String(
//    response?.data ?: ByteArray(0),
//    Charset.forName(HttpHeaderParser.parseCharset(response?.headers)))
open class CustomJsonArrayRequest
    (
    method: Int, url: String?, jsonRequest: JSONObject?,
    listener: Response.Listener<JSONArray?>?, errorListener: Response.ErrorListener?
) :
    JsonRequest<JSONArray?>(
        method, url, jsonRequest?.toString(), listener,
        errorListener
    ) {
    override fun parseNetworkResponse(response: NetworkResponse): Response<JSONArray?>? {
        return try {
            val jsonString = String(
                response.data,
                Charset.forName(HttpHeaderParser.parseCharset(response?.headers))
            )
            Response.success(
                JSONArray(jsonString),
                HttpHeaderParser.parseCacheHeaders(response)
            )
        } catch (e: UnsupportedEncodingException) {
            Response.error(ParseError(e))
        } catch (je: JSONException) {
            Response.error(ParseError(je))
        }
    }
}

package com.afollestad.silk.http;

import ch.boye.httpclientandroidlib.Header;
import ch.boye.httpclientandroidlib.HttpEntity;
import ch.boye.httpclientandroidlib.HttpResponse;
import ch.boye.httpclientandroidlib.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents the response to an HTTP request.
 *
 * @author Aidan Follestad (afollestad)
 */
public class SilkHttpResponse {

    private final List<SilkHttpHeader> mHeaders;
    private final HttpEntity mEntity;

    SilkHttpResponse(HttpResponse response) {
        mHeaders = new ArrayList<SilkHttpHeader>();
        for (Header header : response.getAllHeaders())
            mHeaders.add(new SilkHttpHeader(header));
        mEntity = response.getEntity();
    }

    /**
     * Gets all headers with a specified name from the response.
     */
    public SilkHttpHeader[] getHeaders(String name) {
        List<SilkHttpHeader> headers = new ArrayList<SilkHttpHeader>();
        for (SilkHttpHeader h : mHeaders) {
            if (h.getName().equalsIgnoreCase(name))
                headers.add(h);
        }
        return headers.toArray(new SilkHttpHeader[headers.size()]);
    }

    /**
     * Gets all response headers.
     */
    public SilkHttpHeader[] getHeaders() {
        return mHeaders.toArray(new SilkHttpHeader[mHeaders.size()]);
    }

    /**
     * Gets the response entity.
     */
    public HttpEntity getContent() {
        return mEntity;
    }

    /**
     * Gets the response content as a string.
     */
    public String getContentString() throws Exception {
        if (mEntity == null) return null;
        return EntityUtils.toString(mEntity);
    }

    /**
     * Gets the response content as a string using the specified charset.
     */
    public String getContentString(String defaultCharset) throws Exception {
        return EntityUtils.toString(mEntity, defaultCharset);
    }

    /**
     * Gets the response content as a JSONObject.
     */
    public JSONObject getContentJSON() throws Exception {
        try {
            return new JSONObject(getContentString());
        } catch (JSONException e) {
            throw new Exception("The server did not return JSON.");
        }
    }

    /**
     * Gets the response content as a JSONArray.
     */
    public JSONArray getContentJSONArray() throws Exception {
        return new JSONArray(getContentString());
    }

    /**
     * Gets the response content as a JSONObject using the specified charset.
     */
    public JSONObject getContentJSON(String defaultCharset) throws Exception {
        return new JSONObject(getContentString(defaultCharset));
    }

    /**
     * Gets the response content as a JSONArray using the specified charset.
     */
    public JSONArray getContentJSONArray(String defauktCharset) throws Exception {
        return new JSONArray(getContentString());
    }

    /**
     * Gets the response content as a raw byte array.
     */
    public byte[] getContentBytes() throws Exception {
        return EntityUtils.toByteArray(mEntity);
    }
}
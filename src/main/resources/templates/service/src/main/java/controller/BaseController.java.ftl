<#assign aDate = .now>
package ${package}.controller;

import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;
import java.util.HashSet;
import java.util.Set;
import org.apache.log4j.Logger;

/**
* Base controller.
*
* @${schema.camelCase} ${author}
* @since ${aDate?date?iso_utc}
*/

// 1xx: Informational
// It means the request has been received and the process is continuing.
// 100 Continue Only a part of the request has been received by the server, but
// as long as it has not been rejected, the client should continue with the
// request.
// 101 Switching Protocols

// 2xx: Success
// It means the action was successfully received, understood, and accepted.
// 200 OK The request is OK.
// 201 Created The request is complete, and a new resource is created .
// 202 Accepted The request is accepted for processing, but the processing is
// not complete.
// 203 Non-Authoritative Information The information in the entity header is
// from a local or third-party copy, not from the original server.
// 204 No Content A status code and a header are given in the response, but
// there is no entity-body in the reply.
// 205 Reset Content The browser should clear the form used for this transaction
// for additional input.
// 206 Partial Content The server is returning partial data of the size
// requested. Used in response to a request specifying a Range header. The
// server must specify the range included in the response with the Content-Range
// header.

// 3xx: Redirection
// It means further action must be taken in order to complete the request.
// 300 Multiple Choices A link list. The user can select a link and go to that
// location. Maximum five addresses .
// 301 Moved Permanently The requested page has moved to a new url .
// 302 Found The requested page has moved temporarily to a new url .
// 303 See Other The requested page can be found under a different url .
// 304 Not Modified This is the response code to an If-Modified-Since or
// If-None-Match header, where the URL has not been modified since the specified
// date.
// 305 Use Proxy The requested URL must be accessed through the proxy mentioned
// in the Location header.
// 306 Unused This code was used in a previous version. It is no longer used,
// but the code is reserved.
// 307 Temporary Redirect The requested page has moved temporarily to a new url.

// 4xx: Client Error
// It means the request contains incorrect syntax or cannot be fulfilled.
// 400 Bad Request The server did not understand the request.
// 401 UnAuthorized The requested page needs a username and a password.
// 402 Payment Required You can not use this code yet.
// 403 Forbidden Access is forbidden to the requested page.
// 404 Not Found The server can not find the requested page.
// 405 Method Not Allowed The method specified in the request is not allowed.
// 406 Not Acceptable The server can only generate a response that is not
// accepted by the client.
// 407 Proxy Authentication Required You must Authenticate with a proxy server
// before this request can be served.
// 408 Request Timeout The request took longer than the server was prepared to
// wait.
// 409 Conflict The request could not be completed because of a conflict.
// 410 Gone The requested page is no longer available .
// 411 Length Required The "Content-Length" is not defined. The server will not
// accept the request without it .
// 412 Precondition Failed The pre condition given in the request evaluated to
// false by the server.
// 413 Request Entity Too Large The server will not accept the request, because
// the request entity is too large.
// 414 Request-url Too Long The server will not accept the request, because the
// url is too long. Occurs when you convert a "post" request to a "get" request
// with a long query information .
// 415 Unsupported Media Type The server will not accept the request, because
// the mediaType is not supported .
// 416 Requested Range Not Satisfiable The requested byte range is not available
// and is out of bounds.
// 417 Expectation Failed The expectation given in an Expect request-header
// field could not be met by this server.

// 5xx: Server Error
// It means the server failed to fulfill an apparently valid request.
// 500 Internal Server Error The request was not completed. The server met an
// unexpected condition.
// 501 Not Implemented The request was not completed. The server did not support
// the functionality required.
// 502 Bad Gateway The request was not completed. The server received an invalid
// response from the upstream server.
// 503 Service Unavailable The request was not completed. The server is
// temporarily overloading or down.
// 504 Gateway Timeout The gateway has timed out.
// 505 HTTP Version Not Supported The server does not support the "http
// protocol" version.

public class BaseController {

private static final Logger LOGGER = Logger.getLogger("BaseController");

    /** constructor default. */
    public BaseController() {
        // LOGGER.info("init: Creating Base Controller - start");
        // todo put your code here
        // LOGGER.info("init: Creating Base Controller - completed");
    }

    public String getParam(RoutingContext context, String paramName) throws RuntimeException {
        return context.request().params().get(paramName);
    }

    public String getHeaderParam(RoutingContext context, String paramName) throws RuntimeException {
        return context.request().headers().get(paramName);
    }

    public String getBodyStringParam(RoutingContext context, String paramName)  throws RuntimeException {
        return context.getBodyAsJson().getString(paramName);
    }

    /**
    * Read request context body string parameter.
    *
    * @param context - context
    * @param paramName - parameter name
    * @param defaultValue - default value if parameter not exists
    * @return body parameter value
    * @throws RuntimeException - exception thrown if parse or read errors
    */
    public String getBodyStringParam(RoutingContext context, String paramName, String defaultValue) throws RuntimeException {
        return defaultValue != null
            ? context.getBodyAsJson().getString(paramName, defaultValue)
            : context.getBodyAsJson().getString(paramName);
    }

    public Integer getBodyIntegerParam(RoutingContext context, String paramName)    throws RuntimeException {
        return context.getBodyAsJson().getInteger(paramName);
    }

    /**
    * Read request context body integer parameter.
    *
    * @param context - context
    * @param paramName - parameter name
    * @param defaultValue - default value if parameter not exists
    * @return body parameter value
    * @throws RuntimeException - exception thrown if parse or read errors
    */
    public Integer getBodyIntegerParam(RoutingContext context, String paramName, Integer defaultValue)  throws RuntimeException {
        return defaultValue != null
            ? context.getBodyAsJson().getInteger(paramName, defaultValue)
            : context.getBodyAsJson().getInteger(paramName);
    }

    public Long getBodyLongParam(RoutingContext context, String paramName) throws RuntimeException {
        return context.getBodyAsJson().getLong(paramName);
    }

    /**
    * Read request context body long parameter.
    *
    * @param context - context
    * @param paramName - parameter name
    * @param defaultValue - default value if parameter not exists
    * @return body parameter value
    * @throws RuntimeException - exception thrown if parse or read errors
    */
    public Long getBodyLongParam(RoutingContext context, String paramName, Long defaultValue)   throws RuntimeException {
        return defaultValue != null
            ? context.getBodyAsJson().getLong(paramName, defaultValue)
            : context.getBodyAsJson().getLong(paramName);
    }

    public Boolean getBodyBooleanParam(RoutingContext context, String paramName)    throws RuntimeException {
        return context.getBodyAsJson().getBoolean(paramName);
    }

    /**
    * Read request context body boolean parameter.
    *
    * @param context - context
    * @param paramName - parameter name
    * @param defaultValue - default value if parameter not exists
    * @return body parameter value
    * @throws RuntimeException - exception thrown if parse or read errors
    */
    public Boolean getBodyBooleanParam(RoutingContext context, String paramName, Boolean defaultValue)  throws RuntimeException {
        return defaultValue != null
            ? context.getBodyAsJson().getBoolean(paramName, defaultValue)
            : context.getBodyAsJson().getBoolean(paramName);
    }

    public JsonObject getBodyJsonObjectParam(RoutingContext context, String paramName)  throws RuntimeException {
        return context.getBodyAsJson().getJsonObject(paramName);
    }

    /**
    * Read request context body Json parameter.
    *
    * @param context - context
    * @param paramName - parameter name
    * @param defaultValue - default value if parameter not exists
    * @return body parameter value
    * @throws RuntimeException - exception thrown if parse or read errors
    */
    public JsonObject getBodyJsonObjectParam(RoutingContext context, String paramName, JsonObject defaultValue) throws RuntimeException {
        return defaultValue != null
            ? context.getBodyAsJson().getJsonObject(paramName, defaultValue)
            : context.getBodyAsJson().getJsonObject(paramName);
    }

    public JsonArray getBodyJsonArrayParam(RoutingContext context, String paramName)    throws RuntimeException {
        return context.getBodyAsJson().getJsonArray(paramName);
    }

    /**
    * Read request context body JsonArray parameter.
    *
    * @param context - context
    * @param paramName - parameter name
    * @param defaultValue - default value if parameter not exists
    * @return body parameter value
    * @throws RuntimeException - exception thrown if parse or read errors
    */
    public JsonArray getBodyJsonArrayParam(RoutingContext context, String paramName, JsonArray defaultValue) throws RuntimeException {
        return defaultValue != null
            ? context.getBodyAsJson().getJsonArray(paramName, defaultValue)
            : context.getBodyAsJson().getJsonArray(paramName);
    }

    protected Long getId(RoutingContext context) {
        try {
            Long id = this.getBodyLongParam(context, "id");
            if (id > 0) {
                return id;
            }
            return -1L;
        } catch (Exception e) {
            return -1L;
        }
    }

    /**
    * put context response data and close it.
    *
    * @param context - context
    * @param statusCode - status code
    * @param id - request id
    * @param resultContent - success result JsonObject
    * @param errorContent - error result JsonObject
    */
    public void respondJsonResult(
        RoutingContext context,
        int statusCode,
        Long id,
        JsonObject resultContent,
        JsonObject errorContent) {

        final Set<String> headers = new HashSet<>();
        headers.add("content-type");
        headers.add("token");

        context
            .response()
            .setStatusCode(statusCode)
            .putHeader("content-type", "application/json; charset=utf-8")
            .putHeader("Location", context.request().absoluteURI())
            .putHeader("Access-Control-Allow-Origin", "*")
            .putHeader("Access-Control-Allow-Methods", "POST")
            .putHeader("Access-Control-Allow-Headers", headers)
            .end(new JsonObject()
                .put("id", id)
                .put("result", resultContent)
                .put("error", errorContent)
                .toString()
            );
    }
}
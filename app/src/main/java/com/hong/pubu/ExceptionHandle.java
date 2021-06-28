package com.hong.pubu;

import com.google.gson.JsonParseException;

import org.json.JSONException;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.sql.SQLException;

import retrofit2.HttpException;

/**
 * 网络请求状态代码类
 */
public class ExceptionHandle {
    /**
     * 4xx（请求错误）
     * 这些状态代码表示请求可能出错，妨碍了服务器的处理。
     */
    public static final int HTTP_400 = 400;//400 （错误请求） 服务器不理解请求的语法。
    public static final int HTTP_401 = 401;//401 （未授权） 请求要求身份验证。 对于需要登录的网页，服务器可能返回此响应。
    public static final int HTTP_403 = 403;//403 （禁止） 服务器拒绝请求。
    public static final int HTTP_404 = 404;//404 （未找到） 服务器找不到请求的网页。
    public static final int HTTP_405 = 405;//405 （方法禁用） 禁用请求中指定的方法。
    public static final int HTTP_406 = 406;//406 （不接受） 无法使用请求的内容特性响应请求的网页。
    public static final int HTTP_407 = 407;//407 （需要代理授权） 此状态代码与 401（未授权）类似，但指定请求者应当授权使用代理。
    public static final int HTTP_408 = 408;//408 （请求超时） 服务器等候请求时发生超时。
    public static final int HTTP_409 = 409;//409 （冲突） 服务器在完成请求时发生冲突。服务器必须在响应中包含有关冲突的信息。
    public static final int HTTP_410 = 410;//410 （已删除） 如果请求的资源已永久删除，服务器就会返回此响应。
    public static final int HTTP_411 = 411;//411 （需要有效长度） 服务器不接受不含有效内容长度标头字段的请求。
    public static final int HTTP_412 = 412;//412 （未满足前提条件） 服务器未满足请求者在请求中设置的其中一个前提条件。
    public static final int HTTP_413 = 413;//413 （请求实体过大） 服务器无法处理请求，因为请求实体过大，超出服务器的处理能力。
    public static final int HTTP_414 = 414;//414 （请求的 URI 过长） 请求的 URI（通常为网址）过长，服务器无法处理。
    public static final int HTTP_415 = 415;//415 （不支持的媒体类型） 请求的格式不受请求页面的支持。
    public static final int HTTP_416 = 416;//416 （请求范围不符合要求） 如果页面无法提供请求的范围，则服务器会返回此状态代码。
    public static final int HTTP_417 = 417;//417 （未满足期望值） 服务器未满足"期望"请求标头字段的要求。

    /**
     * 5xx（服务器错误）
     * 这些状态代码表示服务器在尝试处理请求时发生内部错误。 这些错误可能是服务器本身的错误，而不是请求出错。
     */
    public static final int HTTP_500 = 500;//500 （服务器内部错误） 服务器遇到错误，无法完成请求。
    public static final int HTTP_501 = 501;//501 （尚未实施） 服务器不具备完成请求的功能。例如，服务器无法识别请求方法时可能会返回此代码。
    public static final int HTTP_502 = 502;//502 （错误网关） 服务器作为网关或代理，从上游服务器收到无效响应。
    public static final int HTTP_503 = 503;//503 （服务不可用） 服务器目前无法使用（由于超载或停机维护）。通常，这只是暂时状态。
    public static final int HTTP_504 = 504;//504 （网关超时） 服务器作为网关或代理，但是没有及时从上游服务器收到请求。
    public static final int HTTP_505 = 505;//505 （HTTP 版本不受支持） 服务器不支持请求中所用的 HTTP 协议版本。

    /**
     * 自定义 约定异常
     */
    public static final int HTTP_1000 = 1000;//未知错误
    public static final int HTTP_1001 = 1001;//解析错误
    public static final int HTTP_1002 = 1002;//网络错误
    public static final int HTTP_1003 = 1003;//请求超时
    public static final int HTTP_1005 = 1005;//证书出错

    public static ResponseThrowable HandleException(Throwable e) {
        ResponseThrowable ex = new ResponseThrowable(e);
        if (e instanceof HttpException) {
            HttpException httpException = (HttpException) e;
            ex.setCode(httpException.code());
            switch (httpException.code()) {
                case HTTP_400:
                    ex.setMessage("错误请求");
                    break;
                case HTTP_401:
                    ex.setMessage("未授权");
                    break;
                case HTTP_403:
                    ex.setMessage("禁止");
                    break;
                case HTTP_404:
                    ex.setMessage("未找到");
                    break;
                case HTTP_405:
                    ex.setMessage("方法禁用");
                    break;
                case HTTP_406:
                    ex.setMessage("不接受");
                    break;
                case HTTP_407:
                    ex.setMessage("需要代理授权");
                    break;
                case HTTP_408:
                    ex.setMessage("请求超时");
                    break;
                case HTTP_409:
                    ex.setMessage("冲突");
                    break;
                case HTTP_410:
                    ex.setMessage("已删除");
                    break;
                case HTTP_411:
                    ex.setMessage("需要有效长度");
                    break;
                case HTTP_412:
                    ex.setMessage("未满足前提条件");
                    break;
                case HTTP_413:
                    ex.setMessage("请求实体过大");
                    break;
                case HTTP_414:
                    ex.setMessage("请求的URI过长");
                    break;
                case HTTP_415:
                    ex.setMessage("不支持的媒体类型");
                    break;
                case HTTP_416:
                    ex.setMessage("请求范围不符合要求");
                    break;
                case HTTP_417:
                    ex.setMessage("未满足期望值");
                    break;
                case HTTP_500:
                    ex.setMessage("服务器内部错误");
                    break;
                case HTTP_501:
                    ex.setMessage("服务器不具备完成请求的功能");
                    break;
                case HTTP_502:
                    ex.setMessage("错误网关");
                    break;
                case HTTP_503:
                    ex.setMessage("服务不可用");
                    break;
                case HTTP_504:
                    ex.setMessage("网关超时");
                    break;
                case HTTP_505:
                    ex.setMessage("HTTP版本不受支持");
                    break;
                default:
                    ex.setMessage("HttpException未知错误:" + httpException.code() + "\n" + httpException.getMessage());
                    break;
            }
            return ex;
        } else if (e instanceof JsonParseException || e instanceof JSONException) {
            ex.setCode(HTTP_1001);
            ex.setMessage("解析错误");
            return ex;
        } else if (e instanceof ConnectException) {
            ex.setCode(HTTP_1002);
            ex.setMessage("连接失败");
            return ex;
        } else if (e instanceof SocketTimeoutException) {
            ex.setCode(HTTP_1003);
            ex.setMessage("请求超时");
            return ex;
        } else if (e instanceof javax.net.ssl.SSLHandshakeException) {
            ex.setCode(HTTP_1005);
            ex.setMessage("证书验证失败");
            return ex;
        } else if (e instanceof SQLException) {
            int errorCode = ((SQLException) e).getErrorCode();
            if (errorCode == 17002) {
                ex.setCode(errorCode);
                ex.setMessage("连接失败");
            } else {
                ex.setCode(errorCode);
                ex.setMessage(e.getMessage());
            }
            return ex;
        } else {
            ex.setCode(HTTP_1000);
            if(e.getMessage().contains("No route to host"))
                ex.setMessage("无法连接到服务器");
            else{
                ex.setMessage("未知错误：\n" + e.getMessage());
            }
            return ex;
        }
    }


    public static class ResponseThrowable extends Exception {
        private int code;
        private String message;

        public ResponseThrowable(Throwable throwable) {
            super(throwable);
        }

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }

        @Override
        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }
    }

}

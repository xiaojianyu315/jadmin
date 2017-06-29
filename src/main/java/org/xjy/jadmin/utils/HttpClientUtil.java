package org.xjy.jadmin.utils;

import org.apache.http.*;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.config.ConnectionConfig;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 * 封装了一些采用HttpClient发送HTTP请求的方法
 * @see HttpClient-4.5
 * @see http://blogger.csdn.net/jadyer/article/details/7615830
 * @see http://blogger.csdn.net/jadyer/article/details/7615880
 * @see http://blogger.csdn.net/jadyer/article/details/7802139
 * @create Feb 1, 2012 3:02:27 PM
 * @update Oct 8, 2012 3:48:55 PM
 * @version v1.3
 * @history v1.0-->新建<code>sendGetRequest(String,String)</code>和<code>sendPostRequest(String,Map<String,String>,String,String)</code>方法
 * @history v1.1-->新增<code>sendPostSSLRequest(String,Map<String,String>,String,String)</code>方法,用于发送HTTPS的POST请求
 * @history v1.2-->新增<code>sendPostRequest(String,String,boolean,String,String)</code>方法,用于发送HTTP协议报文体为任意字符串的POST请求
 * @history v1.3-->新增<code>java.net.HttpURLConnection</code>实现的<code>sendPostRequestByJava()</code>方法
 *
 * @history edit by zhangyanfei at 2018.8.9 抽取
 * <pre>
 *    优化实例初始化,单例
 * </pre>
 * @history edti by zhangyanfei 2016.8.21
 * 	<pre>
 * 	   对数据异常抛出,失败数据200状态
 * 	</pre>
 */
public class HttpClientUtil {
    static final Logger logger = LoggerFactory.getLogger(HttpClientUtil.class);
    public static final int DEFAULT_TIME_OUT_MS=3000;

//    private static SSLContext DEFAULT_SSL_CTX = getDefaultSSLContext();
//    private static SSLSocketFactory DEFAULT_SSL_SocketFactory =getDefaultSSLSocketFactory();
//
//    private static SSLContext getDefaultSSLContext(){
//        if(DEFAULT_SSL_CTX==null)	{
//            try {
//                DEFAULT_SSL_CTX=SSLContext.getInstance("TLS");
//            } catch (NoSuchAlgorithmException e) {
//                throw new RuntimeException("init error",e);
//            }
//        }
//        return DEFAULT_SSL_CTX;
//    }
//
//
//    private static SSLSocketFactory getDefaultSSLSocketFactory(){
//        if(DEFAULT_SSL_SocketFactory==null){
//            X509TrustManager xtm = getDefaultX509TrustManager();
//            //这个好像是HOST验证
//            X509HostnameVerifier hostnameVerifier = new X509HostnameVerifier() {
//                public boolean verify(String arg0, SSLSession arg1) {
//                    return true;
//                }
//                public void verify(String arg0, SSLSocket arg1) throws IOException {}
//                public void verify(String arg0, String[] arg1, String[] arg2) throws SSLException {}
//                public void verify(String arg0, X509Certificate arg1) throws SSLException {}
//            };
//            //TLS1.0与SSL3.0基本上没有太大的差别，可粗略理解为TLS是SSL的继承者，但它们使用的是相同的SSLContext
//            SSLContext ctx = DEFAULT_SSL_CTX;
//            //使用TrustManager来初始化该上下文，TrustManager只是被SSL的Socket所使用
//            try {
//                ctx.init(null, new TrustManager[] { xtm }, null);
//            } catch (KeyManagementException e) {
//                throw new RuntimeException(e);
//            }
//            //创建SSLSocketFactory
//            SSLSocketFactory socketFactory = new SSLSocketFactory(ctx);
//            socketFactory.setHostnameVerifier(hostnameVerifier);
//
//            DEFAULT_SSL_SocketFactory=socketFactory;
//        }
//        return DEFAULT_SSL_SocketFactory;
//    }
//
//    private static X509TrustManager getDefaultX509TrustManager() {
//        return new X509TrustManager(){
//            public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {}
//            public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {}
//            public X509Certificate[] getAcceptedIssuers() {return null;}
//        };
//    }

    private static CloseableHttpClient buildHttpClient(int timeout){
        ConnectionConfig connectionConfig=ConnectionConfig.copy(ConnectionConfig.DEFAULT).build();
        RequestConfig requestConfig=RequestConfig.copy(RequestConfig.DEFAULT).setConnectionRequestTimeout(timeout).setSocketTimeout(timeout).setConnectTimeout(timeout).build();
        CloseableHttpClient httpClient = HttpClients.custom().setDefaultConnectionConfig(connectionConfig).setDefaultRequestConfig(requestConfig).build();
        return httpClient;
    }

    /**
     * 发送HTTP_GET请求
     * @see 该方法会自动关闭连接,释放资源
     * @param requestURL    请求地址(含参数)
     * @param decodeCharset 解码字符集,解析响应数据时用之,其为null时默认采用UTF-8解码
     * @param timeout 超时秒数（单位：ms）
     * @return 远程主机响应正文
     */
    public static String sendGetRequest(String reqURL, String decodeCharset,int timeout) throws Exception {
//		long responseLength = 0;       //响应长度
        String responseContent = null; //响应内容
        CloseableHttpClient httpClient = buildHttpClient(timeout);
        HttpGet httpGet = new HttpGet(reqURL);           //创建org.apache.http.client.methods.HttpGet
        try{
            HttpResponse response = httpClient.execute(httpGet); //执行GET请求
            if(response.getStatusLine().getStatusCode()!= HttpStatus.SC_OK){
                throw new Exception("resp status is " + response.getStatusLine().getStatusCode() + ", not "+HttpStatus.SC_OK);
            }
            HttpEntity entity = response.getEntity();            //获取响应实体
            if(null != entity){
//				responseLength = entity.getContentLength();
                responseContent = EntityUtils.toString(entity, decodeCharset==null ? "UTF-8" : decodeCharset);
                EntityUtils.consume(entity); //Consume response content
            }
        }catch(ClientProtocolException e){
            logger.error("该异常通常是协议错误导致,比如构造HttpGet对象时传入的协议不对(将'http'写成'htp')或者服务器端返回的内容不符合HTTP协议要求等,堆栈信息如下", e);
            throw e;
        }catch(ParseException e){
            logger.error(e.getMessage(), e);
            throw e;
        }catch(IOException e){
            logger.error("该异常通常是网络原因引起的,如HTTP服务器未启动等,堆栈信息如下", e);
            throw e;
        }finally{
            httpClient.close();
        }
        return responseContent;
    }


    /**
     * 发送get请求
     * @param reqURL 请求地址
     * @param timeout 超时：ms
     * @return
     */
    public static String sendGetRequest(String reqURL,int timeout) throws Exception {
        return sendGetRequest(reqURL,null, timeout);
    }

    /**
     * 发送HTTP_POST请求 字符集均为UTF-8
     * @see 该方法为<code>sendPostRequest(String,String,boolean,String,String)</code>的简化方法
     * @see 当<code>isEncoder=true</code>时,其会自动对<code>sendData</code>中的[中文][|][ ]等特殊字符进行<code>URLEncoder.encode(string,"UTF-8")</code>
     * @param isEncoder 用于指明请求数据是否需要UTF-8编码,true为需要
     */
    public static String sendPostRequest(String reqURL, String sendData, boolean isEncoder,int timeout) throws Exception {
        return sendPostRequest(reqURL, sendData, isEncoder, null, null,timeout);
    }

    /**
     * 发送HTTP_POST请求 该方法会自动关闭连接,释放资源
     * @see
     * @see 当<code>isEncoder=true</code>时,其会自动对<code>sendData</code>中的[中文][|][ ]等特殊字符进行<code>URLEncoder.encode(string,encodeCharset)</code>
     * @param reqURL        请求地址
     * @param sendData      请求参数,若有多个参数则应拼接成param11=value11¶m22=value22¶m33=value33的形式后,传入该参数中
     * @param isEncoder     请求数据是否需要encodeCharset编码,true为需要
     * @param encodeCharset 编码字符集,编码请求数据时用之,其为null时默认采用UTF-8解码
     * @param decodeCharset 解码字符集,解析响应数据时用之,其为null时默认采用UTF-8解码
     * @return 远程主机响应正文
     */
    public static String sendPostRequest(String reqURL, String sendData, boolean isEncoder, String encodeCharset, String decodeCharset,int timeout) throws Exception {
        String responseContent = null;
        CloseableHttpClient httpClient= buildHttpClient(timeout);

        HttpPost httpPost = new HttpPost(reqURL);
        //httpPost.setHeader(HTTP.CONTENT_TYPE, "application/x-www-form-urlencoded; charset=UTF-8");
        httpPost.setHeader(HTTP.CONTENT_TYPE, "application/x-www-form-urlencoded");
        try{
            if(isEncoder){
                List<NameValuePair> formParams = new ArrayList<NameValuePair>();
                for(String str : sendData.split("&")){
                    formParams.add(new BasicNameValuePair(str.substring(0,str.indexOf("=")), str.substring(str.indexOf("=")+1)));
                }
                httpPost.setEntity(new StringEntity(URLEncodedUtils.format(formParams, encodeCharset==null ? "UTF-8" : encodeCharset)));
            }else{
                httpPost.setEntity(new StringEntity(sendData));
            }

            HttpResponse response = httpClient.execute(httpPost);
            if(response.getStatusLine().getStatusCode()!= HttpStatus.SC_OK){
                throw new Exception("resp status is " + response.getStatusLine().getStatusCode() + ", not "+HttpStatus.SC_OK);
            }

            HttpEntity entity = response.getEntity();
            if (null != entity) {
                responseContent = EntityUtils.toString(entity, decodeCharset==null ? "UTF-8" : decodeCharset);
                EntityUtils.consume(entity);
            }
        }catch(Exception e){
            logger.error("与[" + reqURL + "]通信过程中发生异常,堆栈信息如下", e);
            throw e;
        }finally{
            httpClient.close();
        }
        return responseContent;
    }


    /**
     * 发送HTTP_POST请求 该方法会自动关闭连接,释放资源
     * 该方法会自动对<code>params</code>中的[中文][|][ ]等特殊字符进行<code>URLEncoder.encode(string,encodeCharset)</code>
     * @param reqURL        请求地址
     * @param params        请求参数
     * @param encodeCharset 编码字符集,编码请求数据时用之,其为null时默认采用UTF-8解码
     * @param decodeCharset 解码字符集,解析响应数据时用之,其为null时默认采用UTF-8解码
     * @return 远程主机响应正文
     */
    public static String sendPostRequest(String reqURL, Map<String, String> params, String encodeCharset, String decodeCharset, int timeout) throws Exception {
//        logger.debug("请求接口地址："+reqURL);
        String responseContent = null;
        CloseableHttpClient httpClient=buildHttpClient(timeout);
        HttpPost httpPost = new HttpPost(reqURL);
        List<NameValuePair> formParams = new ArrayList<NameValuePair>(); //创建参数队列
        for(Map.Entry<String,String> entry : params.entrySet()){
            formParams.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
        }
        try{
            httpPost.setEntity(new UrlEncodedFormEntity(formParams, encodeCharset==null ? "UTF-8" : encodeCharset));

            HttpResponse response = httpClient.execute(httpPost);
            if(response.getStatusLine().getStatusCode()!= HttpStatus.SC_OK){
                throw new Exception("resp status is " + response.getStatusLine().getStatusCode() + ", not "+HttpStatus.SC_OK);
            }
            HttpEntity entity = response.getEntity();
            if (null != entity) {
                responseContent = EntityUtils.toString(entity, decodeCharset==null ? "UTF-8" : decodeCharset);
                EntityUtils.consume(entity);
            }
//            logger.debug("接口返回数据："+responseContent);
        }catch(Exception e){
            logger.error("与[" + reqURL + "]通信过程中发生异常,堆栈信息如下", e);
            throw e;
        }finally{
            httpClient.close();
        }
        return responseContent;
    }

    public static String sendPostRequest(String reqURL,Map<String, String> params) throws Exception {
        return sendPostRequest(reqURL,params,null,null,DEFAULT_TIME_OUT_MS);
    }

    public static String sendPostRequest(String reqURL,Map<String, String> params,int timeout) throws Exception {
        return sendPostRequest(reqURL,params,null,null, timeout);
    }

    public static byte[] readStream(String url) throws IOException {
        InputStream inStream = null;

        try {
            URL urlObj = new URL(url);
            HttpURLConnection conn = (HttpURLConnection)urlObj .openConnection();
            conn.setRequestMethod("GET");
            conn.setConnectTimeout(5 * 1000);
            inStream = conn.getInputStream();
            ByteArrayOutputStream outStream = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            //每次读取的字符串长度，如果为-1，代表全部读取完毕
            int len = 0;
            while( (len=inStream.read(buffer)) != -1 ){
                outStream.write(buffer, 0, len);
            }
            //关闭输入流
            inStream.close();
            return outStream.toByteArray();
        } catch (IOException e) {
            logger.error("IOException error in catch.", e);
            throw e;
        } finally {
            try {
                inStream.close();
            } catch (IOException e) {
                logger.error("IOException error.", e);
            }
        }
    }

//    /**
//     * 发送HTTPS_POST请求
//     * @see 该方法为<code>sendPostSSLRequest(String,Map<String,String>,String,String)</code>方法的简化方法
//     * @see 该方法在对请求数据的编码和响应数据的解码时,所采用的字符集均为UTF-8
//     * @see 该方法会自动对<code>params</code>中的[中文][|][ ]等特殊字符进行<code>URLEncoder.encode(string,"UTF-8")</code>
//     */
//    public static String sendPostSSLRequest(String reqURL, Map<String, String> params,int timeout) throws Exception {
//        return sendPostSSLRequest(reqURL, params, null, null,timeout);
//    }
//
//
//    /**
//     * 发送HTTPS_POST请求
//     * @see 该方法会自动关闭连接,释放资源
//     * @see 该方法会自动对<code>params</code>中的[中文][|][ ]等特殊字符进行<code>URLEncoder.encode(string,encodeCharset)</code>
//     * @param reqURL        请求地址
//     * @param params        请求参数
//     * @param encodeCharset 编码字符集,编码请求数据时用之,其为null时默认采用UTF-8解码
//     * @param decodeCharset 解码字符集,解析响应数据时用之,其为null时默认采用UTF-8解码
//     * @param timeout  超时时间 ms
//     * @return 远程主机响应正文
//     */
//    public static String sendPostSSLRequest(String reqURL, Map<String, String> params, String encodeCharset, String decodeCharset,int timeout) throws Exception {
//        String responseContent = "";
//        HttpClient httpClient = new DefaultHttpClient();
////        httpClient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, timeout);
////        httpClient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, timeout);//数据传输时间
//        try {
//            httpClient.getConnectionManager().getSchemeRegistry().register(new Scheme("https", 443, DEFAULT_SSL_SocketFactory));
//
//            HttpPost httpPost = new HttpPost(reqURL);
//            List<NameValuePair> formParams = new ArrayList<NameValuePair>();
//            for(Map.Entry<String,String> entry : params.entrySet()){
//                formParams.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
//            }
//            httpPost.setEntity(new UrlEncodedFormEntity(formParams, encodeCharset==null ? "UTF-8" : encodeCharset));
//
//            HttpResponse response = httpClient.execute(httpPost);
//            if(response.getStatusLine().getStatusCode()!= HttpStatus.SC_OK){
//                throw new Exception("resp status is " + response.getStatusLine().getStatusCode() + ", not "+HttpStatus.SC_OK);
//            }
//            HttpEntity entity = response.getEntity();
//            if (null != entity) {
//                responseContent = EntityUtils.toString(entity, decodeCharset==null ? "UTF-8" : decodeCharset);
//                EntityUtils.consume(entity);
//            }
//        } catch (Exception e) {
//            logger.error("与[" + reqURL + "]通信过程中发生异常,堆栈信息为", e);
//            throw e;
//        } finally {
//            httpClient.getConnectionManager().shutdown();
//        }
//        return responseContent;
//    }

    /**
     * 带文件提交
     *
     * @param reqURL  请求地址
     * @param params  请求参数
     * @param file    请求文件
     * @param timeOut 超时时间
     * @return
     */
    public static String sendPostRequestWithFile(String reqURL, Map<String,String> params,
                                                Map<String, byte[]> files, int timeOut){
        String responseContent = null;
        RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(timeOut).setConnectTimeout(timeOut).build();
        HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();
        CloseableHttpClient httpClient = httpClientBuilder.build();
        try {

            MultipartEntityBuilder multipartEntityBuilder = MultipartEntityBuilder.create();
            HttpPost httpPost = new HttpPost(reqURL);
            httpPost.setConfig(requestConfig);
            // 封装普通文本类型参数
            ContentType contentType = ContentType.create("text/plain",Charset.forName("UTF-8"));
            for(Map.Entry<String,String> entry : params.entrySet()){
                multipartEntityBuilder.addTextBody(entry.getKey(), entry.getValue(), contentType);
            }

            for(Map.Entry<String,byte[]> entry : files.entrySet()) {
                multipartEntityBuilder.addBinaryBody(entry.getKey(), new ByteArrayInputStream(entry.getValue()), ContentType.DEFAULT_BINARY, entry.getKey());
            }

            httpPost.setEntity(multipartEntityBuilder.build());
            // 执行post请求
            HttpResponse response = httpClient.execute(httpPost);
            HttpEntity entity = response.getEntity();
            if (null != entity) {
                responseContent = EntityUtils.toString(entity, "UTF-8");
                EntityUtils.consume(entity);
            }
        } catch (ParseException e) {
            logger.error(e.getMessage(), e);
        } catch (IOException e) {
            logger.error("该异常通常是网络原因引起的,如HTTP服务器未启动等,堆栈信息如下", e);
        } finally {
            try {
                httpClient.close();
            } catch (IOException e) {
                logger.error("Close httpclient error.", e);
            }
        }
        return responseContent;
    }

    public static void main(String[] args) {
//        ThreadPoolTaskExecutor threadPoolTaskExecutor=new ThreadPoolTaskExecutor();
//        threadPoolTaskExecutor.setCorePoolSize(10);
//        threadPoolTaskExecutor.initialize();
//        final CountDownLatch countDownLatch=new CountDownLatch(10);
//        for(int i=0;i<10;i++){
//            threadPoolTaskExecutor.execute(new Runnable() {
//                @Override
//                public void run() {
//                    String s = null;
//                    try {
//                        s = HttpClientUtil.sendGetRequest("http://www.sina.com.cn", 1000);
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//                    countDownLatch.countDown();
//                    System.out.println(countDownLatch.getCount());
//                    try {
//                        Thread.sleep(100);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                }
//            });
//        }
//
//        try {
//            countDownLatch.await();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        System.out.println("close :"+countDownLatch.getCount());
    }
}

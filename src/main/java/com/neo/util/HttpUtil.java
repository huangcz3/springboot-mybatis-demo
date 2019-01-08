package com.neo.util;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.*;

/**
 *
 * @author Huangcz
 * @date 2017/7/28/0028
 * HTTP请求
 * GET/POST方式
 */
public class HttpUtil {

    private static Logger logger = LoggerFactory.getLogger(HttpUtil.class);

    /**
     * 发送GET请求
     *
     * @param url        目的地址
     * @param parameters 请求参数，Map类型。
     * @return 远程响应结果
     */
    public static String sendGet(String url, Map<String, String> parameters) {
        String result = "";
        BufferedReader in = null;// 读取响应输入流
        try {
            String fullUrl = "";
            if (parameters.size() == 0){
                fullUrl = url;
            } else {
                // 编码之后的参数
                String params = buildParameter(parameters);
                fullUrl = url + "?" + params;
            }
            // 创建URL对象
            URL connURL = new URL(fullUrl);
            // 打开URL连接
            HttpURLConnection httpConn = (HttpURLConnection) connURL.openConnection();
            // 设置通用属性
            httpConn.setRequestProperty("Accept", "*/*");
            httpConn.setRequestProperty("Connection", "Keep-Alive");
            httpConn.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 6.1)");
            // 建立实际的连接
            httpConn.connect();
            // 响应头部获取
            Map<String, List<String>> headers = httpConn.getHeaderFields();
            // 遍历所有的响应头字段
            /*for (String key : headers.keySet()) {
                System.out.println(key + "\t：\t" + headers.get(key));
            }*/
            // 定义BufferedReader输入流来读取URL的响应,并设置编码方式
            in = new BufferedReader(new InputStreamReader(httpConn.getInputStream(), "UTF-8"));
            String line;
            // 读取返回的内容
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }

        return result;
    }

    /**
     * 发送POST请求（application/json）
     * @param url        目的地址
     * @param parameters 请求参数，Map类型。
     * @return 响应结果
     */
    public static String sendPost(String url, Map<String, String> parameters) {
        String result = "";// 返回的结果
        BufferedReader in = null;// 读取响应输入流
        PrintWriter out = null;
        try {
            //String params = buildParameter(parameters);
            ObjectMapper objectMapper = new ObjectMapper();
            String params = objectMapper.writeValueAsString(parameters);
            // 创建URL对象
            URL connURL = new URL(url);
            // 打开URL连接
            HttpURLConnection httpConn = (HttpURLConnection) connURL.openConnection();
            // 设置通用属性
            httpConn.setRequestMethod("POST");
            httpConn.setRequestProperty("Accept", "*/*");
            httpConn.setRequestProperty("Connection", "Keep-Alive");
            httpConn.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 6.1)");
            httpConn.setRequestProperty("Content-Type", "application/json");
            httpConn.setUseCaches(false);
            httpConn.setDoInput(true);
            httpConn.setDoOutput(true);
            // 获取HttpURLConnection对象对应的输出流
            out = new PrintWriter(httpConn.getOutputStream());
            // 发送请求参数
            out.write(params);
            // flush输出流的缓冲
            out.flush();
            // 定义BufferedReader输入流来读取URL的响应，设置编码方式
            in = new BufferedReader(new InputStreamReader(httpConn.getInputStream(), "UTF-8"));
            String line;
            // 读取返回的内容
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
                if (in != null) {
                    in.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return result;
    }

    /**
     * 发送POST请求（表单格式）
     * @param url            目的地址
     * @param parametersMap  请求参数，Map类型。
     * @return 响应结果
     */
    public static String doPost(String url, Map<String, String> parametersMap)
            throws IOException {
        URL connURL = new URL(url);//先new出一个URL地址，定位网络资源
        URLConnection connection = connURL.openConnection();//打开连接
        connection.setDoOutput(true);//使能往远程写操作

        //把建立的http的连接流返回给PrintWriter
        try (PrintWriter out = new PrintWriter(connection.getOutputStream())) {

            boolean first = true;
            for (Map.Entry<String, String> pair : parametersMap.entrySet()) {
                if (first)
                    first = false;
                else
                    out.print('&');
                String name = pair.getKey().toString();
                String value = pair.getValue().toString();
                out.print(name);
                out.print('=');
                out.print(URLEncoder.encode(value, "UTF-8"));
            }

        }
        //下面的代码是去接收服务器端的反馈信号，将返回的信号全都存放在response这个对象中，
        //看一下api文档的StringBuilder类，就明白下面的代码了
        StringBuilder response = new StringBuilder();
        try (Scanner in = new Scanner(connection.getInputStream())) {
            while (in.hasNextLine()) {
                response.append(in.nextLine());
                response.append("\n");
            }
        } catch (IOException e) {
            if (!(connection instanceof HttpURLConnection)) {
                throw e;
            }
            InputStream err = ((HttpURLConnection) connection).getErrorStream();
            if (err == null) {
                throw e;
            }
            Scanner in = new Scanner(err);
            response.append(in.nextLine());
            response.append("\n");
            in.close();
        }

        return response.toString();
    }





    public static String buildParameter(Map<String, String> parameters) {
        StringBuffer sb = new StringBuffer();// 处理请求参数
        String params = "";// 编码之后的参数
        try {
            if (parameters.size() == 1) {
                for (String name : parameters.keySet()) {
                    sb.append(name).append("=").append(URLEncoder.encode(parameters.get(name), "UTF-8"));
                }
                params = sb.toString();
            } else {
                for (String name : parameters.keySet()) {
                    sb.append(name).append("=").append(URLEncoder.encode(parameters.get(name), "UTF-8")).append("&");
                }
                String temp_params = sb.toString();
                params = temp_params.substring(0, temp_params.length() - 1);
            }
            // logger.info("= = = = = http请求参数内容："+params);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return params;
    }

    /**
     * 主函数，测试请求
     *
     * @param args
     */
    public static void main(String[] args) {
        //Map<String, String> parameters = new HashMap<String, String>();
        //parameters.put("key", "cf9b038c31d14e9dbc6df0f4c786b2d7");
        //parameters.put("address", "上海市绿菊路300弄");
        //
        //
        //ObjectMapper objectMapper = new ObjectMapper();
        //try {
        //    //String param = objectMapper.writeValueAsString(parameters);
        //    String url = "https://restapi.amap.com/v3/geocode/geo";
        //    String result = sendGet(url, parameters);
        //    System.out.println(result);
        //
        //
        //} catch (Exception e) {
        //    e.printStackTrace();
        //}

        // 拼接参数
        Map<String, String> parameters = new HashMap<String, String>();
        parameters.put("key", "cf9b038c31d14e9dbc6df0f4c786b2d7");
        parameters.put("address", "上海市绿菊路300弄");

        // 请求url
        String lines = HttpUtil.sendGet("https://restapi.amap.com/v3/geocode/geo",parameters);
        if (StringUtils.isNotEmpty(lines)){
            JSONObject jsonObject = JSONObject.parseObject(lines);
            if ("OK".equals(jsonObject.getString("info"))){
                JSONArray jsonArray = jsonObject.getJSONArray("geocodes");
                JSONObject obj = jsonArray.getJSONObject(0);

                String location = obj.getString("location");
                List<String> result = Arrays.asList(location.split(","));
                System.out.println(result);
                //// 高德地图经度
                //geo.setGdLng(result.get(0));
                //// 高德地图纬度
                //geo.setGdLat(result.get(1));
            }

        }
    }
}

package com.services.dpidcalarm.utils;

/**
 * @Description：
 * @Author：zhangtao
 * @Date：2019/11/4 0004 20:07:43
 */

import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FileUtils
{
    public static String upload(MultipartFile multipartFile, String path)
    {
        File targetFile = new File(path);
        if (!targetFile.exists()) targetFile.mkdirs();
        String extName = getExtName(multipartFile.getOriginalFilename());
        targetFile = new File(targetFile.getAbsolutePath() + "/" + createTimestampFilename() + "." + extName);
        InputStream inputStream = null;
        OutputStream outputStream = null;
        try {
            if (!targetFile.exists()) targetFile.createNewFile();
            outputStream = new FileOutputStream(targetFile);
            inputStream = multipartFile.getInputStream();
            byte[] buffer = new byte[1024];
            int count = 0;
            while ((count = inputStream.read(buffer)) != -1)
                outputStream.write(buffer, 0, count);
        }
        catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                outputStream.close();
                outputStream.flush();
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return targetFile.getName();
    }

    public static void download(File file, HttpServletResponse response)
    {
        ServletOutputStream servletOutputStream = null;
        InputStream inputStream = null;
        try {
            servletOutputStream = response.getOutputStream();
            inputStream = new FileInputStream(file);
            byte[] buffer = new byte[1024];
            int count = 0;
            while ((count = inputStream.read(buffer)) != -1)
                servletOutputStream.write(buffer, 0, count);
        }
        catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                servletOutputStream.flush();
                servletOutputStream.close();
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static String getExtName(String filename)
    {
        int index = filename.lastIndexOf(".");
        return filename.substring(index + 1);
    }

    public static String getMainName(String filename) {
        int index = filename.lastIndexOf(".");
        return filename.substring(0, index);
    }

    public static String createTimestampFilename()
    {
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
        return sdf.format(date);
    }

    public static void u3dUpload(MultipartFile multipartFile, String path) {
        File targetFile = new File(path);
        if (!targetFile.exists()) targetFile.mkdirs();
        targetFile = new File(targetFile.getAbsolutePath() + "/" + multipartFile.getOriginalFilename() + ".png");
        if (targetFile.exists()) targetFile.delete();
        InputStream inputStream = null;
        OutputStream outputStream = null;
        try {
            if (!targetFile.exists()) targetFile.createNewFile();
            outputStream = new FileOutputStream(targetFile);
            inputStream = multipartFile.getInputStream();
            byte[] buffer = new byte[1024];
            int count = 0;
            while ((count = inputStream.read(buffer)) != -1)
                outputStream.write(buffer, 0, count);
        }
        catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                outputStream.close();
                outputStream.flush();
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
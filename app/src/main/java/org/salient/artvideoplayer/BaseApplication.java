package org.salient.artvideoplayer;

import android.app.Application;
import android.content.Context;

import com.google.gson.Gson;

import org.salient.artvideoplayer.bean.MovieData;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.concurrent.Executors;

/**
 * > Created by Mai on 2018/7/17
 * *
 * > Description:
 * *
 */
public class BaseApplication extends Application {

    private static MovieData mMovieData;

    public static MovieData getMovieData() {
        return mMovieData;
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        Executors.newSingleThreadExecutor().submit(new Runnable() {
            @Override
            public void run() {
                String json = readAssetsFile("video.json");
                mMovieData = new Gson().fromJson(json, MovieData.class);
            }
        });
    }

    /**
     * 读取assets中的文件
     *
     * @param path File Path
     * @return File Content String
     */
    public String readAssetsFile(String path) {
        String result = "";
        try {
            // read file content from file
            StringBuilder sb = new StringBuilder("");
            InputStreamReader reader = new InputStreamReader(getResources().getAssets().open(path));

            BufferedReader br = new BufferedReader(reader);
            String str;
            while ((str = br.readLine()) != null) {
                sb.append(str);
            }
            result = sb.toString();
            br.close();
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }
}

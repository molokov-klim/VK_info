// https://www.youtube.com/watch?v=MEDO0AbcsyE&list=PLAma_mKffTOT_bGrVruy1_JxkFLjh5sfF&index=6&ab_channel=alishev
package com.kawaii.vk_info.utils;

import android.net.Uri;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

public class NetworkUtils {
    private static final String VK_API_BASE_URL = "https://api.vk.com";
    private static final String VK_USERS_GET = "/method/users.get";
    private static final String PARAM_USER_ID = "user_ids";
    private static final String PARAM_VERSION = "v";
    private static final String ACCESS_TOKEN = "access_token";

    // формирование url запроса
    public static URL generateURL(String userID) {
        Uri builtUri = Uri.parse(VK_API_BASE_URL + VK_USERS_GET)
                .buildUpon()
                .appendQueryParameter(PARAM_USER_ID, userID)
                .appendQueryParameter(PARAM_VERSION, "5.81")
                .appendQueryParameter(ACCESS_TOKEN, "09c0367109c0367109c03671d309bb34f2009c009c0367168300e9b406bb1b3aaee0793")
                .build();

        URL url = null;
        try {
            url = new URL (builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return url;
    }

    // it's street magic
    public static String getResponseFromURL(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

        try {
            InputStream in = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            // задание разделения по переходу на новую строку
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();

            if (hasInput) {
                return scanner.next();
            } else {
                return null;
            }
        } finally {
            urlConnection.disconnect();
        }
    }
}

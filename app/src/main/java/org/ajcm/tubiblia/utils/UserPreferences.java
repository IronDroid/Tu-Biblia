package org.ajcm.tubiblia.utils;


import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class UserPreferences {

    private static SharedPreferences.Editor getEditor(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context).edit();
    }

    private static SharedPreferences getPreferences(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context);
    }

    public static void putString(Context context, String key, String value) {
        SharedPreferences.Editor editor = getEditor(context);
        editor.putString(key, value);
        editor.commit();
    }


    public static void putInt(Context context, String tag, int value) {
        SharedPreferences.Editor editor = getEditor(context);
        editor.putInt(tag, value);
        editor.commit();
    }

    public static void putBoolean(Context context, String key, boolean value) {
        SharedPreferences.Editor editor = getEditor(context);
        editor.putBoolean(key, value);
        editor.commit();
    }

    public static void putBoolean(Context context, String category, String key, boolean value) {
        SharedPreferences.Editor editor = getEditor(context);
        editor.putBoolean(key, value);
        editor.commit();
    }

    public static String getString(Context context, String key) {
        SharedPreferences sharedPreferences = getPreferences(context);
        return sharedPreferences.getString(key, null);
    }

    public static int getInt(Context context, String key) {
        SharedPreferences sharedPreferences = getPreferences(context);
        return sharedPreferences.getInt(key, 0);
    }

    public static boolean getBoolean(Context context, String key) {
        SharedPreferences sharedPreferences = getPreferences(context);
        return sharedPreferences.getBoolean(key, false);
    }

    public static void clear(Context context) {
        SharedPreferences.Editor editor = getEditor(context);
        editor.clear();
        editor.commit();
    }

}
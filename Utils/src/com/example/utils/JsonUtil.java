package com.example.utils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.constans.Constans;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

/**
 * json解析工具类
 * @author Administrator
 *
 */
public class JsonUtil implements Serializable {
	
	private static final long serialVersionUID = -7609521898676321656L;
	
	public static boolean isPrintException = Constans.DEBUG;

	/**
	 * 不可实例化
	 */
    private JsonUtil() {
        throw new Error("Do not need instantiate!");
    }
	
	/**
	 * 解析到Obj
	 * @param json
	 * @param t
	 * @return
	 */
	public static <T> T toT(String json,Class<T> t){
		Gson gson = new Gson();
		T bean = gson.fromJson(json, t);
		return bean;
	}

	/**
	 * 解析到Obj
	 * 
	 * @param typeToken
	 *            目标类型
	 * @param json
	 *            json字符串
	 * @return
	 */
	public static final <T> T toObj(TypeToken<T> typeToken, String json) {
		Gson gson = new Gson();
		return gson.fromJson(json, typeToken.getType());
	}

	/**
	 * 解析到Collection
	 * 
	 * @param json
	 * @return
	 */
	public static <T> Collection<T> toCollection(String json,
			TypeToken<Collection<T>> typeToken) {
		return JsonUtil.toObj(typeToken, json);
	}

	/**
	 * 解析到List
	 * 
	 * @param json
	 *            json字符串
	 * @return
	 */
	public static <T> List<T> toList(String json, TypeToken<List<T>> typeToken) {
		return JsonUtil.toObj(typeToken, json);
	}

	/**
	 * 解析到Set
	 * 
	 * @param json
	 *            json字符串
	 * @return
	 */
	public static <T> Set<T> toSet(String json, TypeToken<Set<T>> typeToken) {
		return JsonUtil.toObj(typeToken, json);
	}

	/**
	 * 解析到Map
	 * 
	 * @param json
	 *            json字符串
	 * @return
	 */
	public static <K, V> Map<K, V> toMap(String json,TypeToken<Map<K, V>> typeToken) {
		return JsonUtil.toObj(typeToken, json);
	}
	
	/**
	 * 从对象转换成json字符串
	 * 
	 * @param obj
	 * @return
	 */
	public static final String parseToJson(Object obj) {
		Gson gson = new Gson();
		return gson.toJson(obj);
	}
	
	 /**
     * 从的JSONObject得到Long
     *
     * @param jsonObject
     * @param key  
     * @param defaultValue 默认值
     * @return <ul>
     *         <li>如果jsonObject为null,返回defaultValue</li>
     *         <li>如果key为null或者空字符, 返回defaultValue</li>
     *         <li>if {@link JSONObject#getLong(String)} exception, return defaultValue</li>
     *         <li>return {@link JSONObject#getLong(String)}</li>
     *         </ul>
     */
    public static Long getLong(JSONObject jsonObject, String key, Long defaultValue) {
        if (jsonObject == null || StringUtils.isEmpty(key)) {
            return defaultValue;
        }

        try {
            return jsonObject.getLong(key);
        } catch (JSONException e) {
            if (isPrintException) {
                e.printStackTrace();
            }
            return defaultValue;
        }
    }

    /**
     *从jsonData得到Long
     *
     * @param jsonData
     * @param key
     * @param defaultValue
     * @return <ul>
     *         <li>if jsonObject is null, return defaultValue</li>
     *         <li>if jsonData {@link JSONObject#JSONObject(String)} exception, return defaultValue</li>
     *         <li>return {@link JSONUtils#getLong(JSONObject, String, Long)}</li>
     *         </ul>
     */
    public static Long getLong(String jsonData, String key, Long defaultValue) {
        if (StringUtils.isEmpty(jsonData)) {
            return defaultValue;
        }

        try {
            JSONObject jsonObject = new JSONObject(jsonData);
            return getLong(jsonObject, key, defaultValue);
        } catch (JSONException e) {
            if (isPrintException) {
                e.printStackTrace();
            }
            return defaultValue;
        }
    }

    /**
     * 
     * 从的JSONObject得到long
     * @param jsonObject
     * @param key
     * @param defaultValue
     * @return
     * @see JSONUtils#getLong(JSONObject, String, Long)
     */
    public static long getLong(JSONObject jsonObject, String key, long defaultValue) {
        return getLong(jsonObject, key, (Long)defaultValue);
    }

    /**
     * 
     * 从jsonData得到long
     * @param jsonData
     * @param key
     * @param defaultValue
     * @return
     * @see JSONUtils#getLong(String, String, Long)
     */
    public static long getLong(String jsonData, String key, long defaultValue) {
        return getLong(jsonData, key, (Long)defaultValue);
    }

    /**
     * 从的JSONObject得到Integer
     *
     * @param jsonObject
     * @param key
     * @param defaultValue
     * @return <ul>
     *         <li>if jsonObject is null, return defaultValue</li>
     *         <li>if key is null or empty, return defaultValue</li>
     *         <li>if {@link JSONObject#getInt(String)} exception, return defaultValue</li>
     *         <li>return {@link JSONObject#getInt(String)}</li>
     *         </ul>
     */
    public static Integer getInt(JSONObject jsonObject, String key, Integer defaultValue) {
        if (jsonObject == null || StringUtils.isEmpty(key)) {
            return defaultValue;
        }

        try {
            return jsonObject.getInt(key);
        } catch (JSONException e) {
            if (isPrintException) {
                e.printStackTrace();
            }
            return defaultValue;
        }
    }

    /**
     * 
     * 从的jsonData得到Integer
     *
     * @param jsonData
     * @param key
     * @param defaultValue
     * @return <ul>
     *         <li>if jsonObject is null, return defaultValue</li>
     *         <li>if jsonData {@link JSONObject#JSONObject(String)} exception, return defaultValue</li>
     *         <li>return {@link JSONUtils#getInt(JSONObject, String, Integer)}</li>
     *         </ul>
     */
    public static Integer getInt(String jsonData, String key, Integer defaultValue) {
        if (StringUtils.isEmpty(jsonData)) {
            return defaultValue;
        }

        try {
            JSONObject jsonObject = new JSONObject(jsonData);
            return getInt(jsonObject, key, defaultValue);
        } catch (JSONException e) {
            if (isPrintException) {
                e.printStackTrace();
            }
            return defaultValue;
        }
    }

    /**
     * 从的jsonObject得到int
     * 
     * @param jsonObject
     * @param key
     * @param defaultValue
     * @return
     * @see JSONUtils#getInt(JSONObject, String, Integer)
     */
    public static int getInt(JSONObject jsonObject, String key, int defaultValue) {
        return getInt(jsonObject, key, (Integer)defaultValue);
    }

    /**
     * 从的jsonData得到int
     * 
     * @param jsonData
     * @param key
     * @param defaultValue
     * @return
     * @see JSONUtils#getInt(String, String, Integer)
     */
    public static int getInt(String jsonData, String key, int defaultValue) {
        return getInt(jsonData, key, (Integer)defaultValue);
    }

    /**
     * 从的JSONObject得到Double
     *
     * @param jsonObject
     * @param key
     * @param defaultValue
     * @return <ul>
     *         <li>if jsonObject is null, return defaultValue</li>
     *         <li>if key is null or empty, return defaultValue</li>
     *         <li>if {@link JSONObject#getDouble(String)} exception, return defaultValue</li>
     *         <li>return {@link JSONObject#getDouble(String)}</li>
     *         </ul>
     */
    public static Double getDouble(JSONObject jsonObject, String key, Double defaultValue) {
        if (jsonObject == null || StringUtils.isEmpty(key)) {
            return defaultValue;
        }

        try {
            return jsonObject.getDouble(key);
        } catch (JSONException e) {
            if (isPrintException) {
                e.printStackTrace();
            }
            return defaultValue;
        }
    }

    /**
     * 从的jsonData得到Double
     *
     * @param jsonData
     * @param key
     * @param defaultValue
     * @return <ul>
     *         <li>if jsonObject is null, return defaultValue</li>
     *         <li>if jsonData {@link JSONObject#JSONObject(String)} exception, return defaultValue</li>
     *         <li>return {@link JSONUtils#getDouble(JSONObject, String, Double)}</li>
     *         </ul>
     */
    public static Double getDouble(String jsonData, String key, Double defaultValue) {
        if (StringUtils.isEmpty(jsonData)) {
            return defaultValue;
        }

        try {
            JSONObject jsonObject = new JSONObject(jsonData);
            return getDouble(jsonObject, key, defaultValue);
        } catch (JSONException e) {
            if (isPrintException) {
                e.printStackTrace();
            }
            return defaultValue;
        }
    }

    /**
     * 
     * 从的jsonObject得到double
     * 
     * @param jsonObject
     * @param key
     * @param defaultValue
     * @return
     * @see JSONUtils#getDouble(JSONObject, String, Double)
     */
    public static double getDouble(JSONObject jsonObject, String key, double defaultValue) {
        return getDouble(jsonObject, key, (Double)defaultValue);
    }

    /**
     * 从的jsonData得到double
     * 
     * @param jsonData
     * @param key
     * @param defaultValue
     * @return
     * @see JSONUtils#getDouble(String, String, Double)
     */
    public static double getDouble(String jsonData, String key, double defaultValue) {
        return getDouble(jsonData, key, (Double)defaultValue);
    }
    
    /**
     * 从的jsonObject得到的boolean
     *
     * @param jsonObject
     * @param key
     * @param defaultValue
     * @return <ul>
     *         <li>if jsonObject is null, return defaultValue</li>
     *         <li>if key is null or empty, return defaultValue</li>
     *         <li>return {@link JSONObject#getBoolean(String)}</li>
     *         </ul>
     */
    public static boolean getBoolean(JSONObject jsonObject, String key, Boolean defaultValue) {
        if (jsonObject == null || StringUtils.isEmpty(key)) {
            return defaultValue;
        }

        try {
            return jsonObject.getBoolean(key);
        } catch (JSONException e) {
            if (isPrintException) {
                e.printStackTrace();
            }
            return defaultValue;
        }
    }

    /**
     * 从的jsonData得到的boolean
     *
     * @param jsonData
     * @param key
     * @param defaultValue
     * @return <ul>
     *         <li>if jsonObject is null, return defaultValue</li>
     *         <li>if jsonData {@link JSONObject#JSONObject(String)} exception, return defaultValue</li>
     *         <li>return {@link JSONUtils#getBoolean(JSONObject, String, Boolean)}</li>
     *         </ul>
     */
    public static boolean getBoolean(String jsonData, String key, Boolean defaultValue) {
        if (StringUtils.isEmpty(jsonData)) {
            return defaultValue;
        }

        try {
            JSONObject jsonObject = new JSONObject(jsonData);
            return getBoolean(jsonObject, key, defaultValue);
        } catch (JSONException e) {
            if (isPrintException) {
                e.printStackTrace();
            }
            return defaultValue;
        }
    }

    /**
     * 从的jsonObject得到的map
     *
     * @param jsonObject key-value pairs json
     * @param key
     * @return <ul>
     *         <li>if jsonObject is null, return null</li>
     *         <li>return {@link JSONUtils#parseKeyAndValueToMap(String)}</li>
     *         </ul>
     */
    public static Map<String, String> getMap(JSONObject jsonObject, String key) {
        return JsonUtil.parseKeyAndValueToMap(JsonUtil.getString(jsonObject, key, null));
    }

    /**
     * 从的jsonData得到的map
     *
     * @param jsonData key-value pairs string
     * @param key
     * @return <ul>
     *         <li>if jsonData is null, return null</li>
     *         <li>if jsonData length is 0, return empty map</li>
     *         <li>if jsonData {@link JSONObject#JSONObject(String)} exception, return null</li>
     *         <li>return {@link JSONUtils#getMap(JSONObject, String)}</li>
     *         </ul>
     */
    public static Map<String, String> getMap(String jsonData, String key) {

        if (jsonData == null) {
            return null;
        }
        if (jsonData.length() == 0) {
            return new HashMap<String, String>();
        }

        try {
            JSONObject jsonObject = new JSONObject(jsonData);
            return getMap(jsonObject, key);
        } catch (JSONException e) {
            if (isPrintException) {
                e.printStackTrace();
            }
            return null;
        }
    }

    /**
     * 将JSONObject解析成键值对
     * 解析键值对映射. 忽略空的键, 如果得到的值异常, 值就设为空
     *
     * @param sourceObj key-value pairs json
     * @return <ul>
     *         <li>if sourceObj is null, return null</li>
     *         <li>else parse entry one by one</li>
     *         </ul>
     */
    @SuppressWarnings("rawtypes")
    public static Map<String, String> parseKeyAndValueToMap(JSONObject sourceObj) {
        if (sourceObj == null) {
            return null;
        }

        Map<String, String> keyAndValueMap = new HashMap<String, String>();
        for (Iterator iter = sourceObj.keys(); iter.hasNext();) {
            String key = (String)iter.next();
            keyAndValueMap.put(key, getString(sourceObj, key, ""));
        }
        return keyAndValueMap;
    }

    /**
     * 将jsonData解析成键值对
     * 解析键值对映射. 忽略空的键, 如果得到的值异常, 值就设为空
     *
     * @param source key-value pairs json
     * @return <ul>
     *         <li>if source is null or source's length is 0, return empty map</li>
     *         <li>if source {@link JSONObject#JSONObject(String)} exception, return null</li>
     *         <li>return {@link JSONUtils#parseKeyAndValueToMap(JSONObject)}</li>
     *         </ul>
     */
    public static Map<String, String> parseKeyAndValueToMap(String jsonData) {
        if (StringUtils.isEmpty(jsonData)) {
            return null;
        }

        try {
            JSONObject jsonObject = new JSONObject(jsonData);
            return parseKeyAndValueToMap(jsonObject);
        } catch (JSONException e) {
            if (isPrintException) {
                e.printStackTrace();
            }
            return null;
        }
    }

    /**
     * 从的jsonObject得到String
     *
     * @param jsonObject
     * @param key
     * @param defaultValue
     * @return <ul>
     *         <li>if jsonObject is null, return defaultValue</li>
     *         <li>if key is null or empty, return defaultValue</li>
     *         <li>if {@link JSONObject#getString(String)} exception, return defaultValue</li>
     *         <li>return {@link JSONObject#getString(String)}</li>
     *         </ul>
     */
    public static String getString(JSONObject jsonObject, String key, String defaultValue) {
        if (jsonObject == null || StringUtils.isEmpty(key)) {
            return defaultValue;
        }

        try {
            return jsonObject.getString(key);
        } catch (JSONException e) {
            if (isPrintException) {
                e.printStackTrace();
            }
            return defaultValue;
        }
    }

    /**
     * 从的jsonData得到String
     *
     * @param jsonData
     * @param key
     * @param defaultValue
     * @return <ul>
     *         <li>if jsonObject is null, return defaultValue</li>
     *         <li>if jsonData {@link JSONObject#JSONObject(String)} exception, return defaultValue</li>
     *         <li>return {@link JSONUtils#getString(JSONObject, String, String)}</li>
     *         </ul>
     */
    public static String getString(String jsonData, String key, String defaultValue) {
        if (StringUtils.isEmpty(jsonData)) {
            return defaultValue;
        }

        try {
            JSONObject jsonObject = new JSONObject(jsonData);
            return getString(jsonObject, key, defaultValue);
        } catch (JSONException e) {
            if (isPrintException) {
                e.printStackTrace();
            }
            return defaultValue;
        }
    }
    

    /**
     * 从的jsonObject得到String
     *
     * @param jsonObject
     * @param defaultValue
     * @param keyArray
     * @return <ul>
     *         <li>if jsonObject is null, return defaultValue</li>
     *         <li>if keyArray is null or empty, return defaultValue</li>
     *         <li>get {@link #getJSONObject(JSONObject, String, JSONObject)} by recursion, return it. if anyone is
     *         null, return directly</li>
     *         </ul>
     */
    public static String getStringCascade(JSONObject jsonObject, String defaultValue, String... keyArray) {
        if (jsonObject == null || keyArray == null || keyArray.length == 0) {
            return defaultValue;
        }

        String data = jsonObject.toString();
        for (String key : keyArray) {
            data = getStringCascade(data, key, defaultValue);
            if (data == null) {
                return defaultValue;
            }
        }
        return data;
    }
    
    /**
     * 从的jsonData得到String
     *
     * @param jsonData
     * @param defaultValue
     * @param keyArray
     * @return <ul>
     *         <li>if jsonData is null, return defaultValue</li>
     *         <li>if keyArray is null or empty, return defaultValue</li>
     *         <li>get {@link #getJSONObject(JSONObject, String, JSONObject)} by recursion, return it. if anyone is
     *         null, return directly</li>
     *         </ul>
     */
    public static String getStringCascade(String jsonData, String defaultValue, String... keyArray) {
        if (StringUtils.isEmpty(jsonData)) {
            return defaultValue;
        }

        String data = jsonData;
        for (String key : keyArray) {
            data = getString(data, key, defaultValue);
            if (data == null) {
                return defaultValue;
            }
        }
        return data;
    }

    /**
     * 从的jsonObject得到String数组
     *
     * @param jsonObject
     * @param key
     * @param defaultValue
     * @return <ul>
     *         <li>if jsonObject is null, return defaultValue</li>
     *         <li>if key is null or empty, return defaultValue</li>
     *         <li>if {@link JSONObject#getJSONArray(String)} exception, return defaultValue</li>
     *         <li>if {@link JSONArray#getString(int)} exception, return defaultValue</li>
     *         <li>return string array</li>
     *         </ul>
     */
    public static String[] getStringArray(JSONObject jsonObject, String key, String[] defaultValue) {
        if (jsonObject == null || StringUtils.isEmpty(key)) {
            return defaultValue;
        }

        try {
            JSONArray statusArray = jsonObject.getJSONArray(key);
            if (statusArray != null) {
                String[] value = new String[statusArray.length()];
                for (int i = 0; i < statusArray.length(); i++) {
                    value[i] = statusArray.getString(i);
                }
                return value;
            }
        } catch (JSONException e) {
            if (isPrintException) {
                e.printStackTrace();
            }
            return defaultValue;
        }
        return defaultValue;
    }

    /**
     * 从的jsonData得到String数组
     *
     * @param jsonData
     * @param key
     * @param defaultValue
     * @return <ul>
     *         <li>if jsonObject is null, return defaultValue</li>
     *         <li>if jsonData {@link JSONObject#JSONObject(String)} exception, return defaultValue</li>
     *         <li>return {@link JSONUtils#getStringArray(JSONObject, String, String[])}</li>
     *         </ul>
     */
    public static String[] getStringArray(String jsonData, String key, String[] defaultValue) {
        if (StringUtils.isEmpty(jsonData)) {
            return defaultValue;
        }

        try {
            JSONObject jsonObject = new JSONObject(jsonData);
            return getStringArray(jsonObject, key, defaultValue);
        } catch (JSONException e) {
            if (isPrintException) {
                e.printStackTrace();
            }
            return defaultValue;
        }
    }

    /**
     * 从的jsonObject得到String容器
     *
     * @param jsonObject
     * @param key
     * @param defaultValue
     * @return <ul>
     *         <li>if jsonObject is null, return defaultValue</li>
     *         <li>if key is null or empty, return defaultValue</li>
     *         <li>if {@link JSONObject#getJSONArray(String)} exception, return defaultValue</li>
     *         <li>if {@link JSONArray#getString(int)} exception, return defaultValue</li>
     *         <li>return string array</li>
     *         </ul>
     */
    public static List<String> getStringList(JSONObject jsonObject, String key, List<String> defaultValue) {
        if (jsonObject == null || StringUtils.isEmpty(key)) {
            return defaultValue;
        }

        try {
            JSONArray statusArray = jsonObject.getJSONArray(key);
            if (statusArray != null) {
                List<String> list = new ArrayList<String>();
                for (int i = 0; i < statusArray.length(); i++) {
                    list.add(statusArray.getString(i));
                }
                return list;
            }
        } catch (JSONException e) {
            if (isPrintException) {
                e.printStackTrace();
            }
            return defaultValue;
        }
        return defaultValue;
    }

    /**
     * 从的jsonData得到String容器
     *
     * @param jsonData
     * @param key
     * @param defaultValue
     * @return <ul>
     *         <li>if jsonObject is null, return defaultValue</li>
     *         <li>if jsonData {@link JSONObject#JSONObject(String)} exception, return defaultValue</li>
     *         <li>return {@link JSONUtils#getStringList(JSONObject, String, List)}</li>
     *         </ul>
     */
    public static List<String> getStringList(String jsonData, String key, List<String> defaultValue) {
        if (StringUtils.isEmpty(jsonData)) {
            return defaultValue;
        }

        try {
            JSONObject jsonObject = new JSONObject(jsonData);
            return getStringList(jsonObject, key, defaultValue);
        } catch (JSONException e) {
            if (isPrintException) {
                e.printStackTrace();
            }
            return defaultValue;
        }
    }

    /**
     * 从的jsonObject得到的JSONObject
     *
     * @param jsonObject
     * @param key
     * @param defaultValue
     * @return <ul>
     *         <li>if jsonObject is null, return defaultValue</li>
     *         <li>if key is null or empty, return defaultValue</li>
     *         <li>if {@link JSONObject#getJSONObject(String)} exception, return defaultValue</li>
     *         <li>return {@link JSONObject#getJSONObject(String)}</li>
     *         </ul>
     */
    public static JSONObject getJSONObject(JSONObject jsonObject, String key, JSONObject defaultValue) {
        if (jsonObject == null || StringUtils.isEmpty(key)) {
            return defaultValue;
        }

        try {
            return jsonObject.getJSONObject(key);
        } catch (JSONException e) {
            if (isPrintException) {
                e.printStackTrace();
            }
            return defaultValue;
        }
    }

    /**
     * 从的jsonData得到的JSONObject
     *
     * @param jsonData
     * @param key
     * @param defaultValue
     * @return <ul>
     *         <li>if jsonData is null, return defaultValue</li>
     *         <li>if jsonData {@link JSONObject#JSONObject(String)} exception, return defaultValue</li>
     *         <li>return {@link JSONUtils#getJSONObject(JSONObject, String, JSONObject)}</li>
     *         </ul>
     */
    public static JSONObject getJSONObject(String jsonData, String key, JSONObject defaultValue) {
        if (StringUtils.isEmpty(jsonData)) {
            return defaultValue;
        }

        try {
            JSONObject jsonObject = new JSONObject(jsonData);
            return getJSONObject(jsonObject, key, defaultValue);
        } catch (JSONException e) {
            if (isPrintException) {
                e.printStackTrace();
            }
            return defaultValue;
        }
    }

    /**
     * 从的jsonObject得到的JSONObject
     *
     * @param jsonObject
     * @param defaultValue
     * @param keyArray
     * @return <ul>
     *         <li>if jsonObject is null, return defaultValue</li>
     *         <li>if keyArray is null or empty, return defaultValue</li>
     *         <li>get {@link #getJSONObject(JSONObject, String, JSONObject)} by recursion, return it. if anyone is
     *         null, return directly</li>
     *         </ul>
     */
    public static JSONObject getJSONObjectCascade(JSONObject jsonObject, JSONObject defaultValue, String... keyArray) {
        if (jsonObject == null || keyArray == null || keyArray.length == 0) {
            return defaultValue;
        }

        JSONObject js = jsonObject;
        for (String key : keyArray) {
            js = getJSONObject(js, key, defaultValue);
            if (js == null) {
                return defaultValue;
            }
        }
        return js;
    }

    /**
     * 从的jsonData得到的JSONObject
     *
     * @param jsonData
     * @param defaultValue
     * @param keyArray
     * @return <ul>
     *         <li>if jsonData is null, return defaultValue</li>
     *         <li>if keyArray is null or empty, return defaultValue</li>
     *         <li>get {@link #getJSONObject(JSONObject, String, JSONObject)} by recursion, return it. if anyone is
     *         null, return directly</li>
     *         </ul>
     */
    public static JSONObject getJSONObjectCascade(String jsonData, JSONObject defaultValue, String... keyArray) {
        if (StringUtils.isEmpty(jsonData)) {
            return defaultValue;
        }

        try {
            JSONObject jsonObject = new JSONObject(jsonData);
            return getJSONObjectCascade(jsonObject, defaultValue, keyArray);
        } catch (JSONException e) {
            if (isPrintException) {
                e.printStackTrace();
            }
            return defaultValue;
        }
    }

    /**
     * 从的jsonObject得到的JSONArray
     *
     * @param jsonObject
     * @param key
     * @param defaultValue
     * @return <ul>
     *         <li>if jsonObject is null, return defaultValue</li>
     *         <li>if key is null or empty, return defaultValue</li>
     *         <li>if {@link JSONObject#getJSONArray(String)} exception, return defaultValue</li>
     *         <li>return {@link JSONObject#getJSONArray(String)}</li>
     *         </ul>
     */
    public static JSONArray getJSONArray(JSONObject jsonObject, String key, JSONArray defaultValue) {
        if (jsonObject == null || StringUtils.isEmpty(key)) {
            return defaultValue;
        }

        try {
            return jsonObject.getJSONArray(key);
        } catch (JSONException e) {
            if (isPrintException) {
                e.printStackTrace();
            }
            return defaultValue;
        }
    }

    /**
     * 从的jsonData得到的JSONArray
     *
     * @param jsonData
     * @param key
     * @param defaultValue
     * @return <ul>
     *         <li>if jsonObject is null, return defaultValue</li>
     *         <li>if jsonData {@link JSONObject#JSONObject(String)} exception, return defaultValue</li>
     *         <li>return {@link JSONUtils#getJSONArray(JSONObject, String, JSONObject)}</li>
     *         </ul>
     */
    public static JSONArray getJSONArray(String jsonData, String key, JSONArray defaultValue) {
        if (StringUtils.isEmpty(jsonData)) {
            return defaultValue;
        }

        try {
            JSONObject jsonObject = new JSONObject(jsonData);
            return getJSONArray(jsonObject, key, defaultValue);
        } catch (JSONException e) {
            if (isPrintException) {
                e.printStackTrace();
            }
            return defaultValue;
        }
    }

}

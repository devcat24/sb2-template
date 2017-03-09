package com.github.devcat24.util.json;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;


public class GsonUtil {
    public static String toJsonString(Object obj){
        String rtn = "";
        if(obj != null) {
            rtn = toJsonString("dd/MM/yyyy", obj);
        }
        return rtn;
    }
    public static String toJsonString(String dateFormat, Object obj){
        String rtn = "";
        if(obj != null) {
            //Gson gson = new GsonBuilder().setDateFormat(dateFormat).create();
            GsonBuilder gb = new GsonBuilder().setDateFormat(dateFormat);
            gb.disableHtmlEscaping();
            gb.serializeNulls();
            gb.setPrettyPrinting();
            Gson gson = gb.create();

            rtn = gson.toJson(obj);
        }
        return rtn;
    }

    public static JsonObject createJsonDatasetWithHeader(String returnCode, String returnMessage,
                                                           Long transactionId, Long errorId){
        JsonObject rtnObj = new JsonObject();
        rtnObj.addProperty("returnCode", returnCode);
        rtnObj.addProperty("returnMessage", returnMessage);
        rtnObj.addProperty("transactionId", transactionId);
        rtnObj.addProperty("errorId", errorId);

        /* Gson gson ;
        if(StringUtils.trimToNull(dateFormat) != null){
            gson = (new GsonBuilder().setPrettyPrinting().setDateFormat(dateFormat)).create();
        }   else {
            gson = (new GsonBuilder().setPrettyPrinting().setDateFormat("dd/MM/yyyy")).create();
        }
        return gson.toJson(rtnObj);*/
        return rtnObj;
    }
}

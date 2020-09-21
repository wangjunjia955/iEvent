package com.smartflow.ievent.util.Event;

import com.smartflow.ievent.util.UtilInterface.ChangeIntegerToString;
import javafx.beans.binding.IntegerBinding;

public class MyEventUtil {

    /**
     * @author haitao
     * @date 2019/9/19 10:46
     * @reasong 根据DB字段Urgency或者Impact（Integer），转化为紧急程度String
     */


    public static String changeToString(Integer urgency) {
        switch (urgency) {
            case 1:
                return "严重的";
            case 2:
                return "高";
            case 3:
                return "中";
            case 4:
                return "低";
            default:
        }
        return null;

    }

}

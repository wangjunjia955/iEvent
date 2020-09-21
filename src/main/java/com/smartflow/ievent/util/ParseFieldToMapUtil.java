package com.smartflow.ievent.util;

import org.springframework.util.StringUtils;

import java.util.*;
import java.util.stream.Collectors;

public class ParseFieldToMapUtil {

    private List<Map<String, Object>> mapList = new ArrayList<>();

//    public ParseFieldToMapUtil() {
//        mapList = new ArrayList<>();
//    }

    public List<Map<String, Object>> parseFiledToMap(Integer id, String number, String description) {
        Map<String, Object> map = new HashMap<>();
        map.put("key", id);
        map.put("label", StringUtils.isEmpty(description) ? number : number + "(" + description + ")");
        mapList.add(map);
        return mapList;
    }

    public List<Map<String, Object>> parseFiledToValueLabel(Integer id, String number, String description) {
        Map<String, Object> map = new HashMap<>();
        map.put("value", id);
        map.put("label", StringUtils.isEmpty(description) ? number : number + "(" + description + ")");
        mapList.add(map);
        return mapList;
    }

    public String parseFiledToString(String number, String description) {
        return StringUtils.isEmpty(description) ? number : number + "(" + description + ")";
    }


    public static void main(String[] args) {
        List<Map<String,Object>> list1 = new ArrayList<>();
        Map<String,Object> map1 = new HashMap<>();
//        map1.put("key","1");
        map1.put("name","张三");
        Map<String,Object> map2 = new HashMap<>();
//        map2.put("key","2");
        map2.put("name","李四");
        Map<String,Object> map3 = new HashMap<>();
//        map3.put("key","3");
        map3.put("name","王五");
        list1.add(map1);
        list1.add(map2);
        list1.add(map3);

        List<Map<String,Object>> list2 = new ArrayList<>();
        Map<String,Object> map11 = new HashMap<>();
        map11.put("name","张三");
        map11.put("value", 60);
        Map<String,Object> map22 = new HashMap<>();
        map22.put("name","李四");
        map22.put("value",70);
        list2.add(map11);
        list2.add(map22);

        List<Map<String,Object>> list3 = new ArrayList<>();
        Map<String,Object> map111 = new HashMap<>();
        map111.put("name","张三");
        map111.put("value", 60);
        Map<String,Object> map222 = new HashMap<>();
        map222.put("name","李四");
        map222.put("value",70);
        Map<String,Object> map333 = new HashMap<>();
        map333.put("name","王五");
        map333.put("value",0);
        list3.add(map111);
        list3.add(map222);
        list3.add(map333);
//        List<Map<String, Object>> mapList = merge(list1, list2);
//        for (Map<String, Object> map:mapList) {
//            System.out.println(map);
//        }

        /*listA.forEach(a -> {
            listB.forEach(b -> b.putAll(a));
        });

        listB.forEach(b -> b.entrySet().forEach(e -> System.out.println(e.getValue())));
*/
    }

    public List<Map<String, Object>> merge(List<Map<String, Object>> m1, List<Map<String, Object>> m2){

        m1.addAll(m2);

        Set<String> set = new HashSet<>();

        return m1.stream()
                .collect(Collectors.groupingBy(o->{
                    //暂存所有key
                    set.addAll(o.keySet());
                    //按a_id分组
                    return o.get("name");
                })).entrySet().stream().map(o->{

                    //合并
                    Map<String, Object> map = o.getValue().stream().flatMap(m->{
                        return m.entrySet().stream();
                    }).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (a,b)->b));

                    //为没有的key赋值0
                    set.stream().forEach(k->{
                        if(!map.containsKey(k)) map.put(k, 0);
                    });

                    return map;
                }).collect(Collectors.toList());
    }
}

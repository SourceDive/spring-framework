package mine.jdk.debug.map;

import java.util.NavigableMap;
import java.util.TreeMap;

/**
 * @author zero
 * @description NavigableMap 学习示例
 * @date 2025-12-27
 */
public class NavigableMapTest {
    
    public static void main(String[] args) {
        // NavigableMap 是一个接口，TreeMap 是它的实现类
        NavigableMap<Integer, String> map = new TreeMap<>();
        
        // 添加一些元素
        map.put(10, "十");
        map.put(20, "二十");
        map.put(30, "三十");
        map.put(40, "四十");
        map.put(50, "五十");
        
        System.out.println("原始 Map: " + map);
        System.out.println();
        
        // 1. 导航方法 - 查找小于指定键的最大键
        System.out.println("小于 25 的最大键: " + map.lowerKey(25)); // 返回 20
        System.out.println("小于等于 25 的最大键: " + map.floorKey(25)); // 返回 20
        System.out.println("大于等于 25 的最小键: " + map.ceilingKey(25)); // 返回 30
        System.out.println("大于 25 的最小键: " + map.higherKey(25)); // 返回 30
        System.out.println();
        
        // 2. 获取第一个和最后一个元素
        System.out.println("第一个键值对: " + map.firstEntry());
        System.out.println("最后一个键值对: " + map.lastEntry());
        System.out.println();
        
        // 3. 获取降序视图
        System.out.println("降序 Map: " + map.descendingMap());
        System.out.println();
        
        // 4. 获取子视图
        System.out.println("小于 30 的所有键值对: " + map.headMap(30));
        System.out.println("大于等于 30 的所有键值对: " + map.tailMap(30));
        System.out.println("20 到 40 之间的键值对: " + map.subMap(20, true, 40, true));
    }
}

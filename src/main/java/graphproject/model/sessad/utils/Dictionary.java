package graphproject.model.sessad.utils;

import java.util.HashMap;
import java.util.Map;

public class Dictionary {
    public static Map<Integer, String> mapInstance = new HashMap<>();

    static {
        mapInstance.put(1, "30Missions-2centres");
        mapInstance.put(2, "66Missions-2centres");
        mapInstance.put(3, "94Missions-2centres");
        mapInstance.put(4, "94Missions-3centres");
        mapInstance.put(5, "100Missions-2centres");
        mapInstance.put(6, "200Missions-2centres");
    }

}

package jvm;

import org.apache.lucene.util.RamUsageEstimator;

import java.util.Map;

/**
 * @author laiw
 * @date 2023/7/12 10:24
 */
public class TestJvm {

    public static void main(String[] args) {
        System.out.println("map size 100, value is " + RamUsageEstimator.sizeOf(PlatfromtActionLogModel.cache));
        System.out.println("map size 100, value is " + RamUsageEstimator.humanSizeOf(PlatfromtActionLogModel.cache));
        Map<String, PlatfromtActionLogModel> cache = PlatfromtActionLogModel.cache;
        for (int i = 0; i < 1000; i++) {
            cache.put("ces" + i, PlatfromtActionLogModel.CDN_CERT);
        }

        System.out.println("map size 100, value is " + RamUsageEstimator.sizeOf(cache));
        System.out.println("map size 100, value is " + RamUsageEstimator.humanSizeOf(cache));
    }


}

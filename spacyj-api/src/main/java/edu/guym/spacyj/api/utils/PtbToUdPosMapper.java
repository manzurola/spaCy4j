package edu.guym.spacyj.api.utils;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.stream.Collectors;

public final class PtbToUdPosMapper {
    public static final String path = "edu/guym/spacyj/api/ptb-ud-map";
    private final Map<String, String> tagMap;

    private PtbToUdPosMapper(Map<String, String> tagMap) {
        this.tagMap = tagMap;
    }

    public static PtbToUdPosMapper create() {
        Map<String, String> map = new ConcurrentSkipListMap<>();
        InputStream input = PtbToUdPosMapper.class.getClassLoader().getResourceAsStream(path);
        BufferedReader reader = new BufferedReader(new InputStreamReader(input));
        List<String> lines = reader.lines().collect(Collectors.toList());
        for (String line : lines) {
            String[] split = line.split("\t");
            String ptb = split[0].trim();
            String ud = split[1].trim();
            map.put(ptb, ud);
        }

        return new PtbToUdPosMapper(map);
    }

    public String getUdPos(String tag) {
        return tagMap.get(tag);
    }
}
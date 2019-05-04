package com.safecode.cyberzone.base.utils;

import java.util.UUID;

public class UUIDUtil {

    public static String getUUID() {
        String uuid = UUID.randomUUID().toString();
        StringBuilder builder = new StringBuilder();
        builder.append(uuid.substring(0, 8))
                .append(uuid.substring(9, 13))
                .append(uuid.substring(14, 18))
                .append(uuid.substring(19, 23))
                .append(uuid.substring(24));
        return builder.toString();
    }

}

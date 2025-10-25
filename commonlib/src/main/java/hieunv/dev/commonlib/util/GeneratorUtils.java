package hieunv.dev.commonlib.util;

import lombok.experimental.UtilityClass;

@UtilityClass
public class GeneratorUtils {

    public long generateAccountNumber() {
        return 1000000000L + (long) (Math.random() * 9000000000L);
    }
}

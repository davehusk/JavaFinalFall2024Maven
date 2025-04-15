package util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Log {
    private static final Logger logger = LoggerFactory.getLogger(Log.class);

    public static void info(String msg) {
        logger.info(msg);
    }

    public static void success(String msg) {
        logger.info("✅ " + msg);
    }

    public static void warn(String msg) {
        logger.warn("⚠️ " + msg);
    }

    public static void error(String msg) {
        logger.error("❌ " + msg);
    }

    public static void error(String msg, Throwable t) {
        logger.error("❌ " + msg, t);
    }

    public static void debug(String msg) {
        logger.debug("🐛 " + msg);
    }
}

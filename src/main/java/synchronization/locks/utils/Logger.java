package synchronization.locks.utils;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Logger implements Closeable {
    private static volatile Logger instance;
    private static final Object LOCK = new Object();

    private final BufferedWriter writer;

    private final String pattern = "d.MM.yy HH:mm";
    private final SimpleDateFormat formatter = new SimpleDateFormat(pattern);

    private Logger(OutputStream outputStream) {
        writer = new BufferedWriter(
                new OutputStreamWriter(
                        outputStream != null
                                ? outputStream
                                : System.out));
    }

    public static Logger getInstance(OutputStream outputStream) {
        if (instance == null) {
            synchronized (LOCK) {
                if (instance == null) instance = new Logger(outputStream);
            }
        }
        return instance;
    }

    public static Logger getInstance() {
        return getInstance(null);
    }

    public void write(String message) {
        StringBuilder sb = new StringBuilder();
        sb.append(formatter.format(new Date())).append(": ").append(message);
        synchronized (this) {
            try {
                writer.write(sb.toString());
                writer.newLine();
                writer.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void close() {
        try {
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

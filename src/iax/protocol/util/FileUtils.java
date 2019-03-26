package iax.protocol.util;

import java.io.*;

/**
 * Created by C on 2018/3/30.
 */
public class FileUtils {

    public static byte[] readFileToByteArray(File file) throws IOException {
        FileInputStream in = null;

        byte[] var2;
        try {
            in = openInputStream(file);
            var2 = toByteArray(in);
        } finally {
            close((Closeable) in);
        }

        return var2;
    }

    private static void close(Closeable in) {
        try {
            if(in != null) {
                in.close();
            }
        } catch (IOException var2) {
            ;
        }
    }

    private static byte[] toByteArray(InputStream input) throws IOException {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        copy((InputStream)input, (OutputStream)output);
        return output.toByteArray();
    }

    private static int copy(InputStream input, OutputStream output) throws IOException {
        long count = copyLarge(input, output);
        return count > 2147483647L ? -1 : (int)count;
    }

    private static long copyLarge(InputStream input, OutputStream output) throws IOException {
        return copy(input, output, new byte[4096]);
    }

    private static long copy(InputStream input, OutputStream output, byte[] buffer) throws IOException {
        long count;
        int n;
        for (count = 0L; -1 != (n = input.read(buffer)); count += (long)n) {
            output.write(buffer, 0, n);
        }
        return count;
    }

    private static FileInputStream openInputStream(File file) throws IOException {
        if (file.exists()) {
            if (file.isDirectory()) {
                throw new IOException("File '" + file + "' exists but is a directory");
            } else if (!file.canRead()) {
                throw new IOException("File '" + file + "' cannot be read");
            } else {
                return new FileInputStream(file);
            }
        } else {
            throw new FileNotFoundException("File '" + file + "' does not exist");
        }
    }

}

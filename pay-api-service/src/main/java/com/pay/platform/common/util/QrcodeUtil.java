package com.pay.platform.common.util;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.EnumMap;
import java.util.Map;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * DateTime: 2019/5/23 21:16
 */
public class QrcodeUtil {

    private static final Logger log = LoggerFactory.getLogger(QrcodeUtil.class);

    public static void drawQrcodeImg(String content, HttpServletResponse response) throws IOException {
        ByteArrayOutputStream out = drawQrcodeImg(content);
        response.setContentType("image/png");
        response.setContentLength(out.size());
        response.setHeader("Cache-Control", "public");
        out.writeTo(response.getOutputStream());
    }

    public static ByteArrayOutputStream drawQrcodeImg(String content) throws IOException {
        BitMatrix matrix;
        int width = 800;
        int height = 800;

        Map<EncodeHintType, Object> hints = new EnumMap<EncodeHintType, Object>(EncodeHintType.class);
        hints.put(EncodeHintType.MARGIN, Integer.valueOf(0));
        hints.put(EncodeHintType.CHARACTER_SET, StandardCharsets.UTF_8.name());
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.M);

        try {
            matrix = (new QRCodeWriter()).encode(content, BarcodeFormat.QR_CODE, width, height, hints);
        } catch (WriterException we) {
            log.error(we.getMessage());
            throw new IOException(we);
        }

        ByteArrayOutputStream pngOut = new ByteArrayOutputStream();
        MatrixToImageWriter.writeToStream(matrix, "PNG", pngOut);
        return pngOut;
    }
}

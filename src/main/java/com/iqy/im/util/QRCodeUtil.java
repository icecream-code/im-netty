package com.iqy.im.util;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

import com.google.zxing.*;
import org.springframework.stereotype.Component;

import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

@Component
public class QRCodeUtil {

    private static final String CHARACTER_SET = "utf-8";

    private static final int WIDTH = 300;

    private static final int HEIGHT = 300;

    private static final String FORMAT = "png";

    public static void createQRCode(String filePath, String content) {
        try {
            BitMatrix bitMatrix = getBitMatrix(content);
            Path file = new File(filePath).toPath();
            MatrixToImageWriter.writeToPath(bitMatrix, FORMAT, file);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static byte[] createQRCode(String content) throws Exception {
        BitMatrix bitMatrix = getBitMatrix(content);
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        MatrixToImageWriter.writeToStream(bitMatrix, FORMAT, os);
        return os.toByteArray();
    }

    public static BufferedImage createBufferedImage(String content) throws Exception {
        BitMatrix bitMatrix = getBitMatrix(content);
        int width = bitMatrix.getWidth();
        int height = bitMatrix.getHeight();
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                image.setRGB(x, y, bitMatrix.get(x, y) ? 0xFF000000 : 0xFFFFFFFF);
            }
        }
        return image;
    }

    public String getContentFromQRCode(String filePath) {
        MultiFormatReader formatReader = new MultiFormatReader();
        File file = new File(filePath);
        BufferedImage image;
        try {
            image = ImageIO.read(file);
            BinaryBitmap binaryBitmap = new BinaryBitmap(new HybridBinarizer(new BufferedImageLuminanceSource(image)));
            Map<DecodeHintType, Object> hintMap = new HashMap<>();
            hintMap.put(DecodeHintType.CHARACTER_SET, CHARACTER_SET);
            Result result = formatReader.decode(binaryBitmap, hintMap);
            return result.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private static BitMatrix getBitMatrix(String content) throws WriterException {
        Map<EncodeHintType, Object> hintMap = new HashMap<>();
        // 字符编码
        hintMap.put(EncodeHintType.CHARACTER_SET, CHARACTER_SET);
        // 二维码的纠错等级
        hintMap.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.M);
        // 图片边距
        hintMap.put(EncodeHintType.MARGIN, 2);
        return new MultiFormatWriter().encode(content, BarcodeFormat.QR_CODE, WIDTH, HEIGHT, hintMap);
    }

}

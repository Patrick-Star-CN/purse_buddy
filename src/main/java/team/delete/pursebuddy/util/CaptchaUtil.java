package team.delete.pursebuddy.util;

import com.wf.captcha.SpecCaptcha;
import lombok.extern.slf4j.Slf4j;
import team.delete.pursebuddy.constant.ErrorCode;
import team.delete.pursebuddy.exception.AppException;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Base64;

/**
 * @author patrick_star
 * @Desc 验证码生成工具类
 * @version 1.0
 */
@Slf4j
public class CaptchaUtil extends SpecCaptcha {

    private Color textColor;

    public CaptchaUtil() {
    }

    public CaptchaUtil(int width, int height) {
        this();
        setWidth(width);
        setHeight(height);
    }

    public CaptchaUtil(int width, int height, int len) {
        this(width, height);
        setLen(len);
    }

    public CaptchaUtil(int width, int height, int len, Font font) {
        this(width, height, len);
        setFont(font);
    }

    public CaptchaUtil(int width, int height, int len, int fontIndex, String textColor) {
        this(width, height, len);
        try {
            setFont(fontIndex);
        } catch (Exception e) {
            setFont(new Font("Arial", Font.BOLD, 32));
        }
        setTextColor(textColor);
    }

    /**
     * @Desc 设置验证码字符颜色
     * @param textColor 格式#aabbcc
     */
    public void setTextColor(String textColor) {
        // #aabbcc
        int r = Integer.parseInt(textColor.substring(1, 3), 16);
        int g = Integer.parseInt(textColor.substring(3, 5), 16);
        int b = Integer.parseInt(textColor.substring(5, 7), 16);
        setTextColor(r, g, b);
    }

    public void setTextColor(int r, int g, int b) {
        textColor = new Color(r, g, b);
    }

    /**
     * @Desc 生成验证码
     * @param out 输出流
     * @return 是否成功
     */
    @Override
    public boolean out(OutputStream out) {
        return generateImage(textChar(), out);
    }

    @Override
    public String toBase64() {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        if (out(outputStream)) {
            return "data:image/png;base64," + Base64.getEncoder().encodeToString(outputStream.toByteArray());
        } else {
            throw new AppException(ErrorCode.GENERATE_FAILED);
        }
    }

    /**
     * @Desc 生成验证码图形
     * @param chars 验证码
     * @param out   输出流
     * @return boolean
     */
    private boolean generateImage(char[] chars, OutputStream out) {
        try (OutputStream o = out) {
            BufferedImage bi = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            Graphics2D g2d = (Graphics2D) bi.getGraphics();
            // 填充背景
            g2d.setColor(Color.WHITE);
            g2d.fillRect(0, 0, width, height);
            // 抗锯齿
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            // 画干扰圆
            drawOval(2, textColor, g2d);
            // 画干扰线
            g2d.setStroke(new BasicStroke(2f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL));
            drawBesselLine(2, textColor, g2d);
            // 画字符串
            g2d.setFont(getFont());
            FontMetrics fontMetrics = g2d.getFontMetrics();
            // 每一个字符所占的宽度
            int fW = width / chars.length;
            // 字符的左右边距
            int fSp = (fW - (int) fontMetrics.getStringBounds("W", g2d).getWidth()) / 2;
            for (int i = 0; i < chars.length; i++) {
                g2d.setColor(textColor == null ? color() : textColor);
                // 文字的纵坐标
                int fY = height - ((height - (int) fontMetrics.getStringBounds(String.valueOf(chars[i]), g2d).getHeight()) >> 1);
                g2d.drawString(String.valueOf(chars[i]), i * fW + fSp + 3, fY - 3);
            }
            g2d.dispose();
            ImageIO.write(bi, "png", o);
            o.flush();
            return true;
        } catch (IOException e) {
            log.error("生成图形验证码{}失败！", new String(chars), e);
        }
        return false;
    }

}

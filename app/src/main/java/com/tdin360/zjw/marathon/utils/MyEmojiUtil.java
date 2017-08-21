package com.tdin360.zjw.marathon.utils;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ImageSpan;
import android.widget.TextView;

import com.sqk.emojirelease.Emoji;
import com.sqk.emojirelease.EmojiContentList;
import com.sqk.emojirelease.EmojiUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 表情工具类
 * Created by admin on 17/7/28.
 */

public class MyEmojiUtil {


    private static ArrayList<Emoji> emojiList;

    public static ArrayList<Emoji> getEmojiList(Context context) {
        if (emojiList == null) {
            synchronized (EmojiUtil.class) {
                if (emojiList == null) {
                    emojiList = generateEmojis(context);
                }
            }
        }
        return emojiList;
    }

    private static ArrayList<Emoji> generateEmojis(Context context) {
        ArrayList<Emoji> list = new ArrayList<>();
        for (int i = 0; i < EmojiResArray.length; i++) {
            Emoji emoji = new Emoji();
            emoji.setImageUri(EmojiResArray[i]);
            emoji.setContent(context.getString(EmojiContentResArray[i]));
            list.add(emoji);
        }
        return list;
    }


    public static final int[] EmojiResArray = {
            com.sqk.emojirelease.R.drawable.d_aini,
            com.sqk.emojirelease.R.drawable.d_aoteman,
            com.sqk.emojirelease.R.drawable.d_baibai,
            com.sqk.emojirelease.R.drawable.d_beishang,
            com.sqk.emojirelease.R.drawable.d_bishi,
            com.sqk.emojirelease.R.drawable.d_bizui,
            com.sqk.emojirelease.R.drawable.d_chanzui,
            com.sqk.emojirelease.R.drawable.d_chijing,
            com.sqk.emojirelease.R.drawable.d_dahaqi,
            com.sqk.emojirelease.R.drawable.d_dalian,
            com.sqk.emojirelease.R.drawable.d_ding,
            com.sqk.emojirelease.R.drawable.d_doge,
            com.sqk.emojirelease.R.drawable.d_feizao,
            com.sqk.emojirelease.R.drawable.d_ganmao,
            com.sqk.emojirelease.R.drawable.d_guzhang,
            com.sqk.emojirelease.R.drawable.d_haha,
            com.sqk.emojirelease.R.drawable.d_haixiu,
            com.sqk.emojirelease.R.drawable.d_han,
            com.sqk.emojirelease.R.drawable.d_hehe,
            com.sqk.emojirelease.R.drawable.d_heixian,
            com.sqk.emojirelease.R.drawable.d_heng,
            com.sqk.emojirelease.R.drawable.d_huaxin,
            com.sqk.emojirelease.R.drawable.d_jiyan,
            com.sqk.emojirelease.R.drawable.d_keai,
            com.sqk.emojirelease.R.drawable.d_kelian,
            com.sqk.emojirelease.R.drawable.d_ku,
            com.sqk.emojirelease.R.drawable.d_kun,
            com.sqk.emojirelease.R.drawable.d_landelini,
            com.sqk.emojirelease.R.drawable.d_lei,
            com.sqk.emojirelease.R.drawable.d_miao,
            com.sqk.emojirelease.R.drawable.d_nanhaier,
            com.sqk.emojirelease.R.drawable.d_nu,
            com.sqk.emojirelease.R.drawable.d_numa,
            com.sqk.emojirelease.R.drawable.d_numa,
            com.sqk.emojirelease.R.drawable.d_qian,
            com.sqk.emojirelease.R.drawable.d_qinqin,
            com.sqk.emojirelease.R.drawable.d_shayan,
            com.sqk.emojirelease.R.drawable.d_shengbing,
            com.sqk.emojirelease.R.drawable.d_shenshou,
            com.sqk.emojirelease.R.drawable.d_shiwang,
            com.sqk.emojirelease.R.drawable.d_shuai,
            com.sqk.emojirelease.R.drawable.d_shuijiao,
            com.sqk.emojirelease.R.drawable.d_sikao,
            com.sqk.emojirelease.R.drawable.d_taikaixin,
            com.sqk.emojirelease.R.drawable.d_touxiao,
            com.sqk.emojirelease.R.drawable.d_tu,
            com.sqk.emojirelease.R.drawable.d_tuzi,
            com.sqk.emojirelease.R.drawable.d_wabishi,
            com.sqk.emojirelease.R.drawable.d_weiqu,
            com.sqk.emojirelease.R.drawable.d_xiaoku,
            com.sqk.emojirelease.R.drawable.d_xiongmao,
            com.sqk.emojirelease.R.drawable.d_xixi,
            com.sqk.emojirelease.R.drawable.d_xu,
            com.sqk.emojirelease.R.drawable.d_yinxian,
            com.sqk.emojirelease.R.drawable.d_yiwen,
            com.sqk.emojirelease.R.drawable.d_youhengheng,
            com.sqk.emojirelease.R.drawable.d_yun,
            com.sqk.emojirelease.R.drawable.d_zhuakuang,
            com.sqk.emojirelease.R.drawable.d_zhutou,
            com.sqk.emojirelease.R.drawable.d_zuiyou,
            com.sqk.emojirelease.R.drawable.d_zuohengheng,
            com.sqk.emojirelease.R.drawable.f_geili,
            com.sqk.emojirelease.R.drawable.f_hufen,
            com.sqk.emojirelease.R.drawable.f_jiong,
            com.sqk.emojirelease.R.drawable.f_meng,
            com.sqk.emojirelease.R.drawable.f_shenma,
            com.sqk.emojirelease.R.drawable.f_v5,
            com.sqk.emojirelease.R.drawable.f_xi,
            com.sqk.emojirelease.R.drawable.f_zhi,
            com.sqk.emojirelease.R.drawable.h_buyao,
            com.sqk.emojirelease.R.drawable.h_good,
            com.sqk.emojirelease.R.drawable.h_haha,
            com.sqk.emojirelease.R.drawable.h_lai,
            com.sqk.emojirelease.R.drawable.h_ok,
            com.sqk.emojirelease.R.drawable.h_quantou,
            com.sqk.emojirelease.R.drawable.h_ruo,
            com.sqk.emojirelease.R.drawable.h_woshou,
            com.sqk.emojirelease.R.drawable.h_ye,
            com.sqk.emojirelease.R.drawable.h_zan,
            com.sqk.emojirelease.R.drawable.h_zuoyi,
            com.sqk.emojirelease.R.drawable.l_shangxin,
            com.sqk.emojirelease.R.drawable.l_xin,
            com.sqk.emojirelease.R.drawable.o_dangao,
            com.sqk.emojirelease.R.drawable.o_feiji,
            com.sqk.emojirelease.R.drawable.o_ganbei,
            com.sqk.emojirelease.R.drawable.o_huatong,
            com.sqk.emojirelease.R.drawable.o_lazhu,
            com.sqk.emojirelease.R.drawable.o_liwu,
            com.sqk.emojirelease.R.drawable.o_lvsidai,
            com.sqk.emojirelease.R.drawable.o_weibo,
            com.sqk.emojirelease.R.drawable.o_weiguan,
            com.sqk.emojirelease.R.drawable.o_yinyue,
            com.sqk.emojirelease.R.drawable.o_zhaoxiangji,
            com.sqk.emojirelease.R.drawable.o_zhong,
            com.sqk.emojirelease.R.drawable.w_fuyun,
            com.sqk.emojirelease.R.drawable.w_shachenbao,
            com.sqk.emojirelease.R.drawable.w_taiyang,
            com.sqk.emojirelease.R.drawable.w_weifeng,
            com.sqk.emojirelease.R.drawable.w_xianhua,
            com.sqk.emojirelease.R.drawable.w_xiayu,
            com.sqk.emojirelease.R.drawable.w_yueliang,
    };

    public static final int[] EmojiContentResArray = {
            com.sqk.emojirelease.R.string.emoji_aini,
            com.sqk.emojirelease.R.string.emoji_aoteman,
            com.sqk.emojirelease.R.string.emoji_baibai,
            com.sqk.emojirelease.R.string.emoji_beishang,
            com.sqk.emojirelease.R.string.emoji_bishi,
            com.sqk.emojirelease.R.string.emoji_bizui,
            com.sqk.emojirelease.R.string.emoji_chanzui,
            com.sqk.emojirelease.R.string.emoji_chijing,
            com.sqk.emojirelease.R.string.emoji_haqian,
            com.sqk.emojirelease.R.string.emoji_dalian,
            com.sqk.emojirelease.R.string.emoji_ding,
            com.sqk.emojirelease.R.string.emoji_doge,
            com.sqk.emojirelease.R.string.emoji_feizao,
            com.sqk.emojirelease.R.string.emoji_ganmao,
            com.sqk.emojirelease.R.string.emoji_guzhang,
            com.sqk.emojirelease.R.string.emoji_haha_zh,
            com.sqk.emojirelease.R.string.emoji_haixiu,
            com.sqk.emojirelease.R.string.emoji_han,
            com.sqk.emojirelease.R.string.emoji_weixiao,
            com.sqk.emojirelease.R.string.emoji_heixian,
            com.sqk.emojirelease.R.string.emoji_heng,
            com.sqk.emojirelease.R.string.emoji_se,
            com.sqk.emojirelease.R.string.emoji_jiyan,
            com.sqk.emojirelease.R.string.emoji_keai,
            com.sqk.emojirelease.R.string.emoji_kelian,
            com.sqk.emojirelease.R.string.emoji_ku,
            com.sqk.emojirelease.R.string.emoji_kun,
            com.sqk.emojirelease.R.string.emoji_baiyan,
            com.sqk.emojirelease.R.string.emoji_lei,
            com.sqk.emojirelease.R.string.emoji_miaomiao,
            com.sqk.emojirelease.R.string.emoji_boy,
            com.sqk.emojirelease.R.string.emoji_nu,
            com.sqk.emojirelease.R.string.emoji_numa,
            com.sqk.emojirelease.R.string.emoji_girl,
            com.sqk.emojirelease.R.string.emoji_qian,
            com.sqk.emojirelease.R.string.emoji_qinqin,
            com.sqk.emojirelease.R.string.emoji_shayan,
            com.sqk.emojirelease.R.string.emoji_sick,
            com.sqk.emojirelease.R.string.emoji_caonima,
            com.sqk.emojirelease.R.string.emoji_shiwang,
            com.sqk.emojirelease.R.string.emoji_shuai,
            com.sqk.emojirelease.R.string.emoji_shui,
            com.sqk.emojirelease.R.string.emoji_sikao,
            com.sqk.emojirelease.R.string.emoji_taikaixin,
            com.sqk.emojirelease.R.string.emoji_touxiao,
            com.sqk.emojirelease.R.string.emoji_tu,
            com.sqk.emojirelease.R.string.emoji_tuzi,
            com.sqk.emojirelease.R.string.emoji_wabi,
            com.sqk.emojirelease.R.string.emoji_weiqu,
            com.sqk.emojirelease.R.string.emoji_xiaocry,
            com.sqk.emojirelease.R.string.emoji_xiongmao,
            com.sqk.emojirelease.R.string.emoji_xixi,
            com.sqk.emojirelease.R.string.emoji_xu,
            com.sqk.emojirelease.R.string.emoji_yinxian,
            com.sqk.emojirelease.R.string.emoji_yiwen,
            com.sqk.emojirelease.R.string.emoji_youhengheng,
            com.sqk.emojirelease.R.string.emoji_yun,
            com.sqk.emojirelease.R.string.emoji_zhuakuang,
            com.sqk.emojirelease.R.string.emoji_zhutou,
            com.sqk.emojirelease.R.string.emoji_zuiyou,
            com.sqk.emojirelease.R.string.emoji_zuohengheng,
            com.sqk.emojirelease.R.string.emoji_geili,
            com.sqk.emojirelease.R.string.emoji_hufen,
            com.sqk.emojirelease.R.string.emoji_jiong,
            com.sqk.emojirelease.R.string.emoji_meng,
            com.sqk.emojirelease.R.string.emoji_shenma,
            com.sqk.emojirelease.R.string.emoji_weiwu,
            com.sqk.emojirelease.R.string.emoji_xi,
            com.sqk.emojirelease.R.string.emoji_zhi,
            com.sqk.emojirelease.R.string.emoji_no,
            com.sqk.emojirelease.R.string.emoji_good,
            com.sqk.emojirelease.R.string.emoji_haha,
            com.sqk.emojirelease.R.string.emoji_lai,
            com.sqk.emojirelease.R.string.emoji_ok,
            com.sqk.emojirelease.R.string.emoji_quantou,
            com.sqk.emojirelease.R.string.emoji_ruo,
            com.sqk.emojirelease.R.string.emoji_woshou,
            com.sqk.emojirelease.R.string.emoji_ye,
            com.sqk.emojirelease.R.string.emoji_zan,
            com.sqk.emojirelease.R.string.emoji_zuoyi,
            com.sqk.emojirelease.R.string.emoji_shangxin,
            com.sqk.emojirelease.R.string.emoji_xin,
            com.sqk.emojirelease.R.string.emoji_dangao,
            com.sqk.emojirelease.R.string.emoji_feiji,
            com.sqk.emojirelease.R.string.emoji_ganbei,
            com.sqk.emojirelease.R.string.emoji_huatong,
            com.sqk.emojirelease.R.string.emoji_lazhu,
            com.sqk.emojirelease.R.string.emoji_liwu,
            com.sqk.emojirelease.R.string.emoji_lvsidai,
            com.sqk.emojirelease.R.string.emoji_weibo,
            com.sqk.emojirelease.R.string.emoji_weiguan,
            com.sqk.emojirelease.R.string.emoji_yinyue,
            com.sqk.emojirelease.R.string.emoji_zhaoxiangji,
            com.sqk.emojirelease.R.string.emoji_zhong,
            com.sqk.emojirelease.R.string.emoji_fuyun,
            com.sqk.emojirelease.R.string.emoji_shachenbao,
            com.sqk.emojirelease.R.string.emoji_taiyang,
            com.sqk.emojirelease.R.string.emoji_weifeng,
            com.sqk.emojirelease.R.string.emoji_xianhua,
            com.sqk.emojirelease.R.string.emoji_xiayu,
            com.sqk.emojirelease.R.string.emoji_yueliang,
    };

    public static int calculateInSampleSize(BitmapFactory.Options options,
                                            int reqWidth, int reqHeight) {
        // 源图片的高度和宽度
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;
        if (height > reqHeight || width > reqWidth) {
            // 计算出实际宽高和目标宽高的比率
            final int heightRatio = Math.round((float) height / (float) reqHeight);
            final int widthRatio = Math.round((float) width / (float) reqWidth);
            // 选择宽和高中最小的比率作为inSampleSize的值，这样可以保证最终图片的宽和高
            // 一定都会大于等于目标的宽和高。
            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
        }
        return inSampleSize;
    }

    public static Bitmap decodeSampledBitmapFromResource(Resources res, int resId,
                                                         int reqWidth, int reqHeight) {
        // 第一次解析将inJustDecodeBounds设置为true，来获取图片大小
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(res, resId, options);
        // 调用上面定义的方法计算inSampleSize值
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);
        // 使用获取到的inSampleSize值再次解析图片
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeResource(res, resId, options);
    }


    public static int dip2px(Context context, float dipValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

    public static void handlerEmojiText(TextView comment, String content, Context context) throws IOException {
        comment.setText(handlerTextToEmojiSpannable(content, context));
    }

    public static SpannableStringBuilder handlerTextToEmojiSpannable(String content, Context context) throws IOException {
        if (content == null) {
            throw new IOException("content should not be null");
        }
        if (emojiList == null) {
            emojiList = getEmojiList(context);
        }
        SpannableStringBuilder sb = new SpannableStringBuilder(content);
        String regex = "\\[(\\S+?)\\]";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(content);
        while (m.find()) {
            String tempText = m.group();
            for (int i = 0; i < EmojiContentList.EmojiTextArray.length; i ++) {
                if (tempText.equals(EmojiContentList.EmojiTextArray[i][0]) || tempText.equals(EmojiContentList.EmojiTextArray[i][1])) {
                    Emoji emoji = emojiList.get(i);
                    //转换为Span并设置Span的大小
                    sb.setSpan(new ImageSpan(context, decodeSampledBitmapFromResource(context.getResources(), emoji.getImageUri()
                            , dip2px(context, 20), dip2px(context, 20))),
                            m.start(), m.end(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    break;
                }
            }
        }
        return sb;
    }
}

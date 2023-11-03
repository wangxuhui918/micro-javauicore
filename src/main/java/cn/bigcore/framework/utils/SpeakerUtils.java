package cn.bigcore.framework.utils;


import cn.bigcore.framework.utils.log.LogUtils;
import com.jacob.activeX.ActiveXComponent;
import com.jacob.com.Dispatch;
import com.jacob.com.Variant;

public class SpeakerUtils {
    /**
     * 是否说话
     */
    public static boolean speak = true;

    /**
     * 阅读一段文本
     *
     * @param content
     */
    public static void voicing(String content) {
        if (speak) {
            ActiveXComponent sap = new ActiveXComponent("Sapi.SpVoice");
            Dispatch dispatch = sap.getObject();
            try {
                // 音量 0-100
                sap.setProperty("Volume", new Variant(100));
                // 语音朗读速度 -10 到 +10
                sap.setProperty("Rate", new Variant(-2));
                // 执行朗读
                Dispatch.call(dispatch, "Speak", new Variant(content));

            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    dispatch.safeRelease();
                    sap.safeRelease();
                } catch (Exception e) {
                    LogUtils.err("声音驱动安装失败");
                }
            }
        }
    }

    public static void voicing2WavFile(String content, String fileFullPath) {
        ActiveXComponent ax = null;
        try {
            ax = new ActiveXComponent("Sapi.SpVoice");
            Dispatch spVoice = ax.getObject();

            ax = new ActiveXComponent("Sapi.SpFileStream");
            Dispatch spFileStream = ax.getObject();

            ax = new ActiveXComponent("Sapi.SpAudioFormat");
            Dispatch spAudioFormat = ax.getObject();

            //设置音频流格式
            Dispatch.put(spAudioFormat, "Type", new Variant(22));

            //设置文件输出流格式
            Dispatch.putRef(spFileStream, "Format", spAudioFormat);
            //调用输出 文件流打开方法，创建一个.wav文件
            Dispatch.call(spFileStream, "Open", new Variant(fileFullPath), new Variant(3), new Variant(true));
            //设置声音对象的音频输出流为输出文件对象
            Dispatch.putRef(spVoice, "AudioOutputStream", spFileStream);
            //设置音量 0到100
            Dispatch.put(spVoice, "Volume", new Variant(100));
            //设置朗读速度
            Dispatch.put(spVoice, "Rate", new Variant(-2));
            //设置语音库
//            Dispatch.put(spVoice, "Voice", 1);
            //开始朗读
            Dispatch.call(spVoice, "Speak", new Variant(content));
            //关闭输出文件
            Dispatch.call(spFileStream, "Close");
            Dispatch.putRef(spVoice, "AudioOutputStream", null);
            spAudioFormat.safeRelease();
            spFileStream.safeRelease();
            spVoice.safeRelease();
            ax.safeRelease();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

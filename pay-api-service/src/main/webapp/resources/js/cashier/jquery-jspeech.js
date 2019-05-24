/*--
    User: WildGrass
    Date: 2017/02/22
    QQ: 1318465213
    WX: pts_8599
--*/
/**
 * 将秒转换为  00:00格式
 * @param time 时间 单位秒
 * @returns {string}  返回00:00格式的时间
 */
function convertTime(time) {
    //分钟
    var minute = time / 60;
    var minutes = parseInt(minute);
    if (minutes < 10) {
        minutes = "0" + minutes;
    }
    //秒
    var second = time % 60;
    var seconds = parseInt(second);
    if (seconds < 10) {
        seconds = "0" + seconds;
    }
    var allTime = "" + minutes + "" + ":" + "" + seconds + "";
    return allTime;
}

/**
 * 设置播放时间
 * @param currentTime 当前时间 单位秒
 * @param maxTime 最大时间 单位秒
 * @param timePlace 选择器  例如传入h1   那么$("h1").text("00:00/04:00");
 */
function timeChange(currentTime, maxTime, timePlace) {//默认获取的时间是时间戳改成我们常见的时间格式
    $(timePlace).text(convertTime(currentTime)+"/"+convertTime(maxTime));
}

(function($){
    /**
     * 初始化
     * @param tiemSelector  不需要显示时间可以不传  参数值如果为h1，那么$("h1").text("00:00/04:00")
     * @param endFun js函数  播放结束
     * @returns true 正常  false  出错   出错一般是不支持audio
     */
    $.fn.jspeechInit = function(tiemSelector,endFun){
        try {
            var content = $(this).text();
            var src = "http://tts.baidu.com/text2audio?lan=zh&ie=UTF-8&text=" + content;
            var audioId = $(this).attr("audio-id");
            var html = "<audio id='" + audioId + "' src='" + src + "'/>";
            $(this).append(html);
            var audio = document.getElementById(audioId);
            // 其它事件 /loadeddata初始化/pause暂停/play播放/ended结束/timeupdate/

            if (tiemSelector != undefined) {
                audio.addEventListener("timeupdate", function () {    //监听播放结束事件
                    timeChange(audio.currentTime, audio.duration, tiemSelector);
                }, false);
            }
            if (endFun != undefined) {
                audio.addEventListener("ended", endFun, false);
            }
            return true;
        }catch(ex){
            return false;
        }
    };
    /**
     * 暂停方法
     * @returns true 正常  false  出错   出错一般是不支持audio
     */
    $.fn.jspeechPause = function(){
        try{
            var audioId = $(this).attr("audio-id");
            var audio = document.getElementById(audioId);
            audio.pause();
            return true;
        }catch(ex){
            return false;
        }
    };
    /**
     * 播放方法
     * @returns true 正常  false  出错   出错一般是不支持audio
     */
    $.fn.jspeechPlay = function(){
        try {
            var audioId = $(this).attr("audio-id");
            var audio = document.getElementById(audioId);
            audio.play();
            return true;
        }catch(ex){
            return false;
        }
    };
    /**
     * 停止
     * @returns true 正常  false  出错   出错一般是不支持audio
     */
    $.fn.jspeechStop = function(){
        try {
            var audioId = $(this).attr("audio-id");
            var audio = document.getElementById(audioId);
            audio.pause();
            audio.currentTime = 0;
            return true;
        }catch(ex){
            return false;
        }
    };
    /**
     * 重播
     * @returns true 正常  false  出错   出错一般是不支持audio
     */
    $.fn.jspeechReplay = function(){
        try {
            var audioId = $(this).attr("audio-id");
            var audio = document.getElementById(audioId);
            audio.currentTime = 0;
            audio.play();
            return true;
        }catch(ex){
            return false;
        }
    };
})(jQuery);
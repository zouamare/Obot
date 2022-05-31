package Obot;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ObotIO {

    public String printNoAttendanceCheckRecord(){
        return "출석체크 정보가 없습니다. 출석체크를 하셨는지 다시 확인해주세요.";
    }

    public String printTodayStudyTime(long[] times){
        return String.format("%d시간 %d분 %d초 동안 공부하셨습니다.",times[0],times[1],times[2]);
    }

    public String printAttendanceCheckMessage(LocalDateTime now){
        return "["+now.format(DateTimeFormatter.ofPattern("yyyy년 MM월 dd일 HH시 mm분 ss초"))+"] 출석체크 되었습니다.";
    }

    public String printLeavingCheckMessage(LocalDateTime now){
        return "["+now.format(DateTimeFormatter.ofPattern("yyyy년 MM월 dd일 HH시 mm분 ss초"))+"] 퇴실체크 되었습니다.";
    }
}
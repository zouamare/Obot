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

    public String printLifeQuote(String lifeQuoteRandom) {
        return "[오늘의 명언]\n" + lifeQuoteRandom;
    }

    public String printPreAttendanceCheckMessage(){
        return "이미 출석체크를 하셨습니다.";
    }

    public String printRank(int rank,Member member){
        return String.format("%-17d위/%-17s/%-17d시간/Lv.%-17d",rank+1,member.getUsername(),member.getExp()/4,member.getLv());
    }

    public String printRankName(){
        return String.format("**%-17s/%-17s/%-17s/%-17s**","순위","이름","공부시간","레벨");
    }

    public String printNoRank(){
        return "랭크된 인원이 없습니다.";
    }
}

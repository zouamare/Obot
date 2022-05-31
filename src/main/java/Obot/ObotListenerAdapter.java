package Obot;

import net.dv8tion.jda.api.entities.ChannelType;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.HashMap;

public class ObotListenerAdapter extends ListenerAdapter {
    HashMap<String,LocalDateTime> timeTable = new HashMap<>();  //사용자의 출석체크 정보를 저장하는 hash map
    ObotIO IO = new ObotIO();
    @Override
    public void onMessageReceived(MessageReceivedEvent event){  // message가 생성될 때마다 호출되는 메소드
        if(event.getAuthor().isBot())   // 봇의 대화는 무시
            return;

        if(event.getMessage().getContentRaw().startsWith("0ㅊㅊ")){   // 출석체크
            LocalDateTime now = getTodayDateTime();
            timeTable.put(event.getMember().getId(),now);
            event.getChannel().sendMessage(IO.printAttendanceCheckMessage(now)).queue();
        }

        if(event.getMessage().getContentRaw().startsWith("0ㅌㅊ")){   // 퇴실체크
            if(timeTable.containsKey(event.getMember().getId())){
                LocalDateTime before = timeTable.get(event.getMember().getId());
                LocalDateTime now = getTodayDateTime();
                event.getChannel().sendMessage(IO.printLeavingCheckMessage(now)).queue();
                sendMessage(event.getAuthor(), IO.printTodayStudyTime(calculateTime(before, now)));
                timeTable.remove(event.getMember().getEffectiveName());
            }
            else{
                event.getChannel().sendMessage(IO.printNoAttendanceCheckRecord()).queue();
            }
        }

        // ********필요 없는 내용**********
        if(event.isFromType(ChannelType.PRIVATE)){
            System.out.printf("[PM] %s: %s\n", event.getAuthor().getName(),
                    event.getMessage().getContentDisplay());
        }
        else{
            System.out.printf("[%s][%s] %s: %s\n", event.getGuild().getName(),  //서버 이름
                    event.getTextChannel().getName(),   //채널 이름
                    event.getMember().getEffectiveName(),   //메세지를 보낸 멤버명
                    event.getMessage().getContentDisplay());    //메세지 내용
        }
        // ********필요 없는 내용**********
    }

    private LocalDateTime getTodayDateTime(){
        // 오늘 날짜와 시간을 반환하는 메소드
        return LocalDateTime.now();
    }

    private long getSeconds(LocalDateTime before, LocalDateTime now){
        //Duration을 이용하여 시간 차이를 반환하는 메소드
        Duration d = Duration.between(before,now);
        return d.getSeconds();
    }

    private long[] calculateTime(LocalDateTime before, LocalDateTime now){
        // 시간 차이를 계산하여 시, 분, 초로 반환하는 메소드
        long[] times = new long[3];

        long time = getSeconds(before,now);
        if(time>3600){
            times[0] = time/3600;
            time %= 3600;
        }
        if(time>60){
            times[1] = time/60;
            time %= 60;
        }

        times[2] = time;
        return times;
    }

    public void sendMessage(User user, String content){
        // 유저에게 개인 DM을 보내는 메소드
        user.openPrivateChannel()
                .flatMap(channel -> channel.sendMessage(content))
                .queue();
    }
}

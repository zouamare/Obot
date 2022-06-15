package Obot;

import net.dv8tion.jda.api.entities.ChannelType;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.HashMap;

public class ObotListenerAdapter extends ListenerAdapter {
    HashMap<String,LocalDateTime> timeTable;  //사용자의 출석체크 정보를 저장하는 hash map
    ObotIO IO;
    LifeQuote lifeQuote;

    public ObotListenerAdapter() throws IOException {
        this.timeTable = new HashMap<>();
        this.IO = new ObotIO();
        this.lifeQuote = new LifeQuote();
    }

    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
        String userid;
        String serverid;
        if (event.getName().equals("출첵")) {
            userid = event.getMember().getId();
            serverid = event.getGuild().getId();
            LocalDateTime now = getTodayDateTime();
            EntityManager em = Main.emf.createEntityManager();
            String selectOne = "select m from Member m where m.serverid ='" + serverid + "'and m.userid='" + userid + "'";
            EntityTransaction tx = em.getTransaction();
            tx.begin();
            Member member;
            try {
                member = em.createQuery(selectOne, Member.class).getSingleResult();
            } catch (Exception e) {
                member = null;
            }
            if (member == null) {
                member = new Member(null, userid, serverid, null, null, 0L, 0L);
                em.persist(member);
            }
            if (member.getIndate() == null) {
                member.setIndate(now);
                tx.commit();
                event.getChannel().sendMessage(IO.printAttendanceCheckMessage(now)).queue();
            } else {
                event.getChannel().sendMessage(IO.printPreAttendanceCheckMessage()).queue();
            }
//            timeTable.put(event.getMember().getId(),now);
            em.close();
        } else if (event.getName().equals("퇴첵")) {
            if (timeTable.containsKey(event.getMember().getId())) {
                userid = event.getMember().getId();
                serverid = event.getGuild().getId();
                EntityManager em = Main.emf.createEntityManager();
                String selectOne = "select m from Member m where m.serverid ='" + serverid + "'and m.userid='" + userid + "'";
                EntityTransaction tx = em.getTransaction();
                tx.begin();
                Member member;
                try {
                    member = em.createQuery(selectOne, Member.class).getSingleResult();
                } catch (Exception e) {
                    member = null;
                }
                if (member != null && member.getIndate() != null) {
                    LocalDateTime before = member.getIndate();
//                LocalDateTime before = timeTable.get(event.getMember().getId());
                    LocalDateTime now = getTodayDateTime();
                    event.getChannel().sendMessage(IO.printLeavingCheckMessage(now)).queue();
                    sendMessage(event.getUser(), IO.printTodayStudyTime(calculateTime(before, now)));
//                timeTable.remove(event.getMember().getId());
                    member.setIndate(null);
                    member.setExp((Long) (member.getExp()) + (getSeconds(before, now) / 900));
                    tx.commit();
                } else {
                    event.getChannel().sendMessage(IO.printNoAttendanceCheckRecord()).queue();
                }
                em.close();
                }
            }
        else if (event.getName().equals("명언")) {
            event.getChannel().sendMessage(IO.printLifeQuote(lifeQuote.getLifeQuoteRandom())).queue();
        }
    }

    private LocalDateTime getTodayDateTime(){
        // 오늘 날짜와 시간을 반환하는 메소드
        return LocalDateTime.now(ZoneId.of("Asia/Seoul"));
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

package Obot;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

public class AttendanceController {
    ObotIO IO = new ObotIO();

    public String startAttendanceCheck(ID id,String name){
        LocalDateTime now = getTodayDateTime();
        EntityManager em = ObotController.emf.createEntityManager();
        String selectOne = "select m from Member m where m.serverid ='" + id.getServerID() + "'and m.userid='" + id.getUserID() + "'";
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        Member member;
        try {
            member = em.createQuery(selectOne, Member.class).getSingleResult();
        } catch (Exception e) {
            member = null;
        }
        if (member == null) {
            member = new Member(null, id.getUserID(), id.getServerID(), null, null, 0L, 0L,name);
            em.persist(member);
        }
        em.close();
        if (member.getIndate() == null) {
            member.setIndate(now);
            tx.commit();
            return IO.printAttendanceCheckMessage(now);
        }
        return IO.printPreAttendanceCheckMessage();
    }

    public List<String> endAttendanceCheck(ID id){
        EntityManager em = ObotController.emf.createEntityManager();
        String selectOne = "select m from Member m where m.serverid ='" + id.getServerID() + "'and m.userid='" + id.getUserID() + "'";
        EntityTransaction tx = em.getTransaction();
        List<String> list = new ArrayList<>();
        tx.begin();
        Member member;
        try {
            member = em.createQuery(selectOne, Member.class).getSingleResult();
        } catch (Exception e) {
            member = null;
        }
        if (member != null && member.getIndate() != null) {
            LocalDateTime before = member.getIndate();
            LocalDateTime now = getTodayDateTime();
            member.setIndate(null);
            long exp =(member.getExp()) + (getSeconds(before, now) / 900);
            member.setExp((Long) exp);
            member.setLv(setLv(exp));
            list.add(IO.printLeavingCheckMessage(now));
            //하단에 경험치 증가량, 레벨표시 적용해야함 -> 아직 적용안됨!!
            list.add(IO.printTodayStudyTime(calculateTime(before, now)));
            tx.commit();
        }else{
            list.add(IO.printNoAttendanceCheckRecord());
        }
        em.close();
        return list;
    }

    public List<String> rankExp(ID id){
        EntityManager em = ObotController.emf.createEntityManager();
        String selectQuery = "select m from Member m where m.serverid ='" + id.getServerID()+"'order by m.lv desc,m.exp desc";
        EntityTransaction tx = em.getTransaction();
        List<String> list = new ArrayList<>();
        tx.begin();
        List<Member> memberList;
        try {
            memberList = em.createQuery(selectQuery,Member.class).getResultList();
        }catch (Exception e){
            memberList = null;
        }
        if(memberList !=null){
            list.add(IO.printRankName());
            if(memberList.size()>5){
                for(int i = 0; i<5; i++){
                    list.add(IO.printRank(i,memberList.get(i)));
                }
            }else {
                for(int i = 0; i<memberList.size(); i++){
                    list.add(IO.printRank(i,memberList.get(i)));
                }
            }
        }else{
            list.add(IO.printNoRank());
        }
        return list;
    }

    private LocalDateTime getTodayDateTime() {
        // 오늘 날짜와 시간을 반환하는 메소드
        return LocalDateTime.now(ZoneId.of("Asia/Seoul"));
    }

    private long getSeconds(LocalDateTime before, LocalDateTime now) {
        //Duration을 이용하여 시간 차이를 반환하는 메소드
        Duration d = Duration.between(before, now);
        return d.getSeconds();
    }
    private long[] calculateTime(LocalDateTime before, LocalDateTime now) {
        // 시간 차이를 계산하여 시, 분, 초로 반환하는 메소드
        long[] times = new long[3];

        long time = getSeconds(before, now);
        if (time > 3600) {
            times[0] = time / 3600;
            time %= 3600;
        }
        if (time > 60) {
            times[1] = time / 60;
            time %= 60;
        }

        times[2] = time;
        return times;
    }

    private long setLv(long exp){
        if(exp<16) return 1;
        if(exp<41) return 2;
        if(exp<73) return 3;
        if(exp<120) return 4;
        if(exp<172) return 5;
        if(exp<264) return 6;
        if(exp<370) return 7;
        if(exp<541) return 8;
        if(exp<1001) return 9;
        return 10;
    }
}

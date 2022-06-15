package Obot;

import javax.persistence.*;
import java.time.LocalDateTime;


@Entity
public class Member {

    public Member() {
    }

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long memberid;
    private String userid;
    private String serverid;
    private LocalDateTime indate;
    private LocalDateTime outdate;
    private Long lv;
    private Long exp;

    public Member(Long memberid, String userid, String serverid, LocalDateTime indate, LocalDateTime outdate, Long lv, Long exp) {
        this.memberid = memberid;
        this.userid = userid;
        this.serverid = serverid;
        this.indate = indate;
        this.outdate = outdate;
        this.lv = lv;
        this.exp = exp;
    }

    public Long getMemberid() {
        return memberid;
    }

    public void setMemberid(Long memberid) {
        this.memberid = memberid;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getServerid() {
        return serverid;
    }

    public void setServerid(String serverid) {
        this.serverid = serverid;
    }

    public LocalDateTime getIndate() {
        return indate;
    }

    public void setIndate(LocalDateTime indate) {
        this.indate = indate;
    }

    public LocalDateTime getOutdate() {
        return outdate;
    }

    public void setOutdate(LocalDateTime outdate) {
        this.outdate = outdate;
    }

    public Long getLv() {
        return lv;
    }

    public void setLv(Long lv) {
        this.lv = lv;
    }

    public Long getExp() {
        return exp;
    }

    public void setExp(Long exp) {
        this.exp = exp;
    }
}


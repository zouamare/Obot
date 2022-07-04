package Obot;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.security.auth.login.LoginException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ObotController {
    static EntityManagerFactory emf;
    public void run() throws LoginException, IOException {
        initJdbc();
        initJda();
    }

    private void initJdbc(){
        Map<String,String> map= new HashMap<>();
        map.put("javax.persistence.jdbc.user",System.getenv("dbid"));
        map.put("javax.persistence.jdbc.password",System.getenv("dbpassword"));
        map.put("javax.persistence.jdbc.url",System.getenv("dburl"));
        emf = Persistence.createEntityManagerFactory("Obot",map);
    }

    private void initJda() throws LoginException, IOException {
        JDA jda = JDABuilder.createDefault(System.getenv("token"))
                .setActivity(Activity.playing("공부"))    // 공부 하는 중 표시
                .build();
        jda.upsertCommand("랭킹", "랭킹보기").queue();
        jda.getPresence().setStatus(OnlineStatus.ONLINE);
        jda.addEventListener(new ObotListenerAdapter());
    }
}

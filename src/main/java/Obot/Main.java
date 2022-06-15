package Obot;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.MessageBuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.entities.ChannelType;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.build.Commands;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.security.auth.login.LoginException;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class Main{
    static EntityManagerFactory emf;
    public static void main(String[] args) throws LoginException, IOException {
        Map<String,String> map= new HashMap<>();
        map.put("javax.persistence.jdbc.user",System.getenv("dbid"));
        map.put("javax.persistence.jdbc.password",System.getenv("dbpassword"));
        map.put("javax.persistence.jdbc.url",System.getenv("dburl"));
        emf = Persistence.createEntityManagerFactory("Obot",map);

        JDA jda = JDABuilder.createDefault(System.getenv("token"))
                .setActivity(Activity.playing("공부"))    // 공부 하는 중 표시
                .build();
        jda.getPresence().setStatus(OnlineStatus.ONLINE);
        jda.addEventListener(new ObotListenerAdapter());
    }
}
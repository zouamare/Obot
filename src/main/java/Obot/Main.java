package Obot;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.MessageBuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.entities.ChannelType;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

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
//        JDA jda = JDABuilder.createDefault(System.getenv("token")).build();
//        jda.getPresence().setStatus(OnlineStatus.ONLINE);
//        jda.addEventListener(new ObotListenerAdapter());
        emf = Persistence.createEntityManagerFactory("Obot",map);
        JDA jda = JDABuilder.createLight(System.getenv("token"), Collections.emptyList())
                .addEventListeners(new ObotListenerAdapter())
                .setActivity(Activity.playing("Type /출첵"))
                .build();
        jda.upsertCommand("출첵", "출석체크");
    }
}
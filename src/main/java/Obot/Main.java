package Obot;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.MessageBuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.ChannelType;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.security.auth.login.LoginException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Main{
    static EntityManagerFactory emf;
    public static void main(String[] args) throws LoginException, IOException {
        Map<String,String> map= new HashMap<>();
        map.put("dbid",System.getenv("dbid"));
        map.put("dbpassword",System.getenv("dbpassword"));
        map.put("dburl",System.getenv("dburl"));

        emf = Persistence.createEntityManagerFactory("Obot",map);
        JDA jda = JDABuilder.createDefault(System.getenv("token")).build();
        jda.getPresence().setStatus(OnlineStatus.ONLINE);
        jda.addEventListener(new ObotListenerAdapter());

    }
}
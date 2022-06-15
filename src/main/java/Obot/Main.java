package Obot;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.MessageBuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.entities.ChannelType;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import javax.security.auth.login.LoginException;
import java.io.IOException;
import java.util.Collections;

public class Main{
    public static void main(String[] args) throws LoginException, IOException {

//        JDA jda = JDABuilder.createDefault(System.getenv("token")).build();
//        jda.getPresence().setStatus(OnlineStatus.ONLINE);
//        jda.addEventListener(new ObotListenerAdapter());

        JDA jda = JDABuilder.createLight(System.getenv("token"), Collections.emptyList())
                .addEventListeners(new ObotListenerAdapter())
                .setActivity(Activity.playing("Type /출첵"))
                .build();
        jda.upsertCommand("출첵", "출석체크");
    }
}
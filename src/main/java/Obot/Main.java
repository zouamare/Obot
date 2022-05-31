package Obot;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.MessageBuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.ChannelType;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import javax.security.auth.login.LoginException;

public class Main{
    public static void main(String[] args) throws LoginException {

        JDA jda = JDABuilder.createDefault(System.getenv("token")).build();
        jda.getPresence().setStatus(OnlineStatus.ONLINE);
        jda.addEventListener(new ObotListenerAdapter());

    }
}
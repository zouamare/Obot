package Obot;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.MessageBuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.ChannelType;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import javax.security.auth.login.LoginException;

public class Main extends ListenerAdapter {
    public static void main(String[] args) throws LoginException {

        JDA jda = JDABuilder.createDefault("OTgwODQwNTE2MDMzODQzMjAw.GSIS--.4a8JTtQWfjn5KMTBfArjvH3auxM1WhmGwcXjQY").build();
        jda.getPresence().setStatus(OnlineStatus.ONLINE);
        jda.addEventListener(new Main());

    }

    @Override
    public void onMessageReceived(MessageReceivedEvent event){
        if(event.getAuthor().isBot())
            return;

        if(event.getMessage().getContentRaw().startsWith("!인사")){
            System.out.println("event :"+event.getMessage().getContentRaw());
            event.getChannel().sendMessage("안녕하세요!").queue();
        }

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
    }

}
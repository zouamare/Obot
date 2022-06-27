package Obot;

import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.io.IOException;
import java.util.List;

public class ObotListenerAdapter extends ListenerAdapter {
    // 정말 수신 - 송신 역할만 할 수 있도록 변경
    LifeQuoteController lifeQuote;
    AttendanceController attendance;

    public ObotListenerAdapter() throws IOException {
        this.lifeQuote = new LifeQuoteController();
        this.attendance = new AttendanceController();
    }

    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
        if (event.getName().equals("출첵")) {
             String response = attendance.startAttendanceCheck(new ID(event.getMember().getId(),event.getGuild().getId()));
             event.reply(response).queue();
        }
        if (event.getName().equals("퇴첵")) {
            List<String> response = attendance.endAttendanceCheck(new ID(event.getMember().getId(),event.getGuild().getId()));
            if(response.size() > 1)
                sendMessage(event.getUser(),response.get(1));
            event.reply(response.get(0)).queue();
        }
        if (event.getName().equals("명언")) {
            String response = lifeQuote.getLifeQuoteRandom();
            event.reply(response).queue();
        }
    }

    public void sendMessage(User user, String content) {
        // 유저에게 개인 DM을 보내는 메소드
        user.openPrivateChannel()
                .flatMap(channel -> channel.sendMessage(content))
                .queue();
    }
}

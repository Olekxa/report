package com.greedobank.reports.service;

import com.greedobank.reports.client.UserClient;
import com.greedobank.reports.model.Role;
import com.greedobank.reports.model.RoleTitle;
import com.greedobank.reports.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.io.IOException;

@Service
@EnableScheduling
public class ScheduledSending {
    private final ReportServiceBase reportService;
    private final MailService mailService;
    private final UserClient userClient;


    @Autowired
    public ScheduledSending(ReportServiceDaily reportService, MailService mailService, UserClient userClient) {
        this.reportService = reportService;
        this.mailService = mailService;
        this.userClient = userClient;
    }

    // @Scheduled(cron = "0 0 9 * * *")
    @Scheduled(cron = "1 * * * * *")
    private void scheduledSend() throws IOException, MessagingException, UsernameNotFoundException {
        User[] users = userClient.getMailsOfUsers();

        User user = new User(1, "", "", "okukurik@griddynamics.com", new Role(1, RoleTitle.ROLE_ADMIN));

        mailService.sendDailyNewsReportToUsers(reportService.getExpectedNews(), new User[]{user});
    }
}

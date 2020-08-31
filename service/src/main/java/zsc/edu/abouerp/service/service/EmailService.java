package zsc.edu.abouerp.service.service;


import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import zsc.edu.abouerp.entity.domain.PersonnelStatus;
import zsc.edu.abouerp.service.config.EmailProperties;

/**
 * @author Abouerp
 */
@Service
public class EmailService {

    private final JavaMailSender javaMailSender;
    private static String sender;
    private final static String PROBATION_SUBJECT = "Offer通知";
    private final static String PROBATION_TEXT = "通过公司的面试，您已通过招聘考核。我司现以书面方式通知您已被公司正式录用，于报到之日起进入试用期阶段。";
    private final static String IN_OFFICE_SUBJECT = "员工转正通知";
    private final static String IN_OFFICE_TEXT = "您好，祝贺您被我公司录用，正式成为秋临公司的一员，同时也很高兴你对秋临公司的信任。";
    private final static String KEEP_PROBATION_SUBJECT = "试用期延期通知";
    private final static String KEEP_PROBATION_TEXT = "根据您与公司签订的劳动合同，有关您的试用期将于X年X月X日结束。经用人事部门考察后，您的考察结果未能胜任岗位工作。决定由X年X月X日开始，将您的试用期延长X个月至X年X月X日止以作深入考察。";
    private final static String OFF_OFFICE_SUBJECT = "离职申请成功通知";
    private final static String OFF_OFFICE_TEXT = "根据你提出的离职申请，经公司审核你的工作移交手续，同意正式离职,开始时间为提出申请后一个月内正式起效，双方自即日起终止劳动关系。离职后，如有交接不清楚事项，请用心配合公司协助处理。感谢你对本公司的贡献，并祝你事业顺利。";
    private final static String ROLE_CHANGE = "请您在接到本通知起3日内办完原部门的工作交接共和相关物品移交手续，并前往新部门报到。";

    public EmailService(JavaMailSender javaMailSender, EmailProperties emailProperties) {
        this.javaMailSender = javaMailSender;
        this.sender = emailProperties.getUsername();
    }

    @Async
    public void sendEmail(String receiver, String name, PersonnelStatus personnelStatus, String roleName) {
        String text1 = null;
        String subject1 = null;
        if (personnelStatus.equals(PersonnelStatus.PROBATION)) {
            //取得offer
            text1 = String.format("恭喜你" + name + ", " + PROBATION_TEXT);
            subject1 = PROBATION_SUBJECT;
        }
        if (personnelStatus.equals(PersonnelStatus.IN_OFFICE)) {
            //正式员工
            text1 = String.format(name + ", " + IN_OFFICE_TEXT);
            subject1 = IN_OFFICE_SUBJECT;
        }
        if (personnelStatus.equals(personnelStatus.equals(PersonnelStatus.OFF_OFFICE))) {
            //离职
            text1 = String.format(name + ", " + OFF_OFFICE_TEXT);
            subject1 = OFF_OFFICE_SUBJECT;
        }
        if (roleName != null) {
            text1 = String.format("您好！公司通过对您的长期观察和近来的工作表现，并考虑到公司的实际需要和您的个人工作能力，经讨论，将你调入新岗位:" + roleName + ", " + ROLE_CHANGE);
            subject1 = String.format("岗位调动通知");
        }
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom(sender);
        simpleMailMessage.setTo(receiver);
        simpleMailMessage.setSubject(subject1);
        simpleMailMessage.setText(text1);
        javaMailSender.send(simpleMailMessage);
    }
}

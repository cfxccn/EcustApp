using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Mvc;
using System.Web.Security;
using System.Net.Mail;

namespace WebJSON.Controllers
{
    public class UserRegisterController : Controller
    {
        UserInfo storedb = new UserInfo();

        //
        // GET: /UserRegister/

        public string Index(String userName, String userEmail, String userPwd, String userMobile, String userDepart, String userNum, String userRealName)
        {
            accountuser user = new accountuser();
            if (storedb.user.Where(u => u.useremail == userEmail).Count()!=0)
                return "-1";
            if (storedb.user.Where(u => u.username == userName).Count() != 0)
                return "-1";

            System.Security.Cryptography.MD5 userkeymd5 = new System.Security.Cryptography.MD5CryptoServiceProvider();
            byte[] userkeybytResult = userkeymd5.ComputeHash(System.Text.Encoding.Default.GetBytes(userEmail + userPwd));
            string userKey = BitConverter.ToString(userkeybytResult, 4, 8);
            userKey = userKey.Replace("-", "");

            System.Security.Cryptography.MD5  captchamd5 = new System.Security.Cryptography.MD5CryptoServiceProvider();
            byte[] captchabytResult = captchamd5.ComputeHash(System.Text.Encoding.Default.GetBytes(userEmail));
            string captcha = BitConverter.ToString(captchabytResult, 0,4);
            captcha = captcha.Replace("-", "");
            try
            {
                user.username = userName;
                user.useremail = userEmail;
                user.userpwd = userPwd;
                user.usermobile = userMobile;
                user.userdepart = userDepart;
                user.usernum = userNum;
                user.userrealname = userRealName;
                user.userkey = userKey;
                user.captcha = captcha;
                user.isverify = 0;
                if (!sendCaptcha(userEmail, captcha)) {
                    return "-1";
                }
                storedb.user.Add(user);
                storedb.SaveChanges();
                return userKey;
            }
            catch {
                return "-1";
            }
        }
        private Boolean sendCaptcha(string userEmail, string captcha)
        {

            try {
                MailMessage mailMessage = new MailMessage();

                mailMessage.From = new MailAddress("10111939@mail.ecust.edu.cn");
                mailMessage.To.Add(userEmail);

                mailMessage.Subject = "USTA团队-华东理工校园应用注册验证";
                mailMessage.Body = "请点击链接完成注册 http://59.78.93.208:9092/UserVerify?useremail=" + userEmail + "&captcha=" + captcha + "";
                mailMessage.IsBodyHtml = false;
                mailMessage.Priority = MailPriority.Normal;


                SmtpClient smtpClient = new SmtpClient();
                smtpClient.Host = "202.120.111.18";
                smtpClient.Port = 25;
                smtpClient.UseDefaultCredentials = true;
                smtpClient.Credentials = new System.Net.NetworkCredential("10111939", "qwer1234");//如果是匿名发送则不需要这一句
                smtpClient.Send(mailMessage);
                return true;
            }
            catch {
                return false;
            }
        }
    }
}

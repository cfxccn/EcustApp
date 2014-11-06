using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Mvc;

namespace WebJSON.Controllers
{
    public class UserVerifyController : Controller
    {
        UserInfo userstoredb = new UserInfo();
        //
        // GET: /UserVerify/
        public string  Index(string userEmail,string captcha)
        {
            accountuser user =userstoredb.user.Where(u => u.useremail == userEmail).First();
            if (user.captcha.Equals(captcha))
            {
                user.isverify = 1;
                userstoredb.SaveChanges();
                return "验证成功";
            }
            else{
                return "验证失败";
            }
        }

    }
}

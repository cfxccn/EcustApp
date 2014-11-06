using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Mvc;

namespace WebJSON.Controllers
{
    public class UserLoginController : Controller
    {
        //
        // GET: /UserLogin/
        UserInfo storedb = new UserInfo();
        public string Index(string userEmail, string userPwd)
        {
            accountuser user = new accountuser();
                try
                {
                    user = storedb.user.Where(u => u.useremail == userEmail).First();
                    if (user.userpwd == userPwd)
                    {
                        return user.username+","+user.userkey;
                    }
                    else
                    {
                        return "-1";
                    }
                }
                catch {
                    return "-1";
                }
              

        }


    
    
    
    }
}

using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Mvc;

namespace WebJSON.Controllers
{
    public class UserRenRenQQLoginController : Controller
    {
        //
        // GET: /UserRenRenLogin/
        UserInfo storedb = new UserInfo();
        public int Index(string userName ,string userEmail)
        {

            if (storedb.user.Where(u => u.useremail == userEmail).Count() == 0)
            {
                try
                {

                    accountuser user = new accountuser();
                    user.username = userName;
                    user.useremail = userEmail;
                    user.userkey = userEmail;
                    user.userpwd = DateTime.Now.ToString();
                    user.isverify = 1;
                    storedb.user.Add(user);
                    storedb.SaveChanges();
                    return 1;

                }
                catch
                {
                    return 0;

                }
            }
            return 0;

        }

    }
}

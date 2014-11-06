using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Mvc;

namespace WebJSON.Controllers
{
    public class UserEditInfoController : Controller
    {
        //
        // GET: /UserLogin/
        UserInfo storedb = new UserInfo();

        public string Index(string userEmail, string userOldPwd,string userNewPwd)
        {

           // user user = new user();
            try
            {
                accountuser user = storedb.user.Where(u => u.useremail == userEmail).First();

     //           user = storedb.user.Where(u => u.useremail == userEmail).First();

                if (user.userpwd == userOldPwd)
                {
                    //storedb.user.Remove(user);
                    //storedb.SaveChanges();
                    user.userpwd = userNewPwd;
                    System.Security.Cryptography.MD5 md5 = new System.Security.Cryptography.MD5CryptoServiceProvider();
                    byte[] bytResult = md5.ComputeHash(System.Text.Encoding.Default.GetBytes(userEmail + userNewPwd));
                    string userKey = BitConverter.ToString(bytResult, 4, 8);
                    userKey = userKey.Replace("-", "");
                    user.userkey = userKey;
               //     storedb.user.Add(user);
                    storedb.SaveChanges();
                    return user.userkey;
                }
                else
                {
                    return "0";
                }
            }
            catch
            {
                return "-1";
            }


        }

    }
}

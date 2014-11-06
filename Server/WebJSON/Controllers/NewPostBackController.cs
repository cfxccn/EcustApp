using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Mvc;

namespace WebJSON.Controllers
{
    public class NewPostBackController : Controller
    {
        //
        // GET: /NewPostBack/

        DataInfo storedb = new DataInfo();
        UserInfo userstoredb = new UserInfo();

        public int Index(int postid, string text, string useremail, string userkey,string anony)
        {
            accountuser user = new accountuser();
            user = userstoredb.user.Where(u => u.useremail == useremail).First();
            if (!userkey.Equals(user.userkey))
            {
                return -1;
            }
            try
            {
                postback postback = new postback();
                postback.userid = user.userid;
                postback.postid = postid;
                postback.text = text;
                postback.anonymity = int.Parse(anony);
                postback.date = DateTime.Now;
                storedb.postback.Add(postback);
                storedb.SaveChanges();
                return 1;
            }
            catch
            {
                return -1;

            }




        }
    }
}

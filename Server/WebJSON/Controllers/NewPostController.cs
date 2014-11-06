using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Mvc;

namespace WebJSON.Controllers
{
    public class NewPostController : Controller
    {
        DataInfo storedb = new DataInfo();
        UserInfo userstoredb = new UserInfo();

        public int Index(string posttitle, string text, string useremail, string userkey,string anony)
        {
            accountuser user = new accountuser();
            user = userstoredb.user.Where(u => u.useremail == useremail).First();
            if (!userkey.Equals(user.userkey)) {
                return -1;
            }
            try
            {
                post post = new post();
                post.userid = user.userid;
                post.posttitle = posttitle;
                post.text = text;
                post.date = DateTime.Now;
                post.anonymity = int.Parse(anony);
                storedb.post.Add(post);
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

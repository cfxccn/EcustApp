using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Mvc;
using Newtonsoft.Json;
using Newtonsoft.Json.Linq;

namespace WebJSON.Controllers
{
    public class PostsTitlesController : Controller
    {
        DataInfo storedb = new DataInfo();

        //
        // GET: /AdviseInsert/

        public JArray Index(int postid=int.MaxValue)
        {
                  

            IEnumerable<post> posts;
            posts = storedb.post.Where(i=>i.postid<postid).OrderByDescending(u=>u.date).Take(15);
            string json = JsonConvert.SerializeObject(posts);
            return JArray.Parse(json);

        }
    }
}

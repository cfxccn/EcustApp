using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Mvc;
using Newtonsoft.Json;
using Newtonsoft.Json.Linq;

namespace WebJSON.Controllers
{

    public class PostDetailsController : Controller
    {
        //
        // GET: /PostDetails/
        DataInfo storedb = new DataInfo();
        public JArray Index(int postid)
        {
            IEnumerable<postback> postback;
            postback = storedb.postback.Where(u => u.postid == postid);
            string postbackjson = JsonConvert.SerializeObject(postback);
            IEnumerable<post> post;
            post = storedb.post.Where(u => u.postid == postid).Take(1);
            string postjson = JsonConvert.SerializeObject(post);
            JArray post_postbackJArray = JArray.Parse(postjson);
            JArray postbackJArray = JArray.Parse(postbackjson);
            post_postbackJArray.Add(postbackJArray);

            return post_postbackJArray;
        }
    }
}

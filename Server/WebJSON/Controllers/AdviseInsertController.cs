using Newtonsoft.Json;
using Newtonsoft.Json.Linq;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Mvc;

namespace WebJSON.Controllers
{
    public class AdviseInsertController : Controller
    {
        DataInfo storedb = new DataInfo();

        
        public int Index(string sex, string grade, string text, string w, string h, string android_version, string mobile_model, string density)
        {
            try
            {
                advise ad = new advise();
                ad.sex = sex;
                ad.grade = grade;
                ad.text = text;
                ad.width = w;
                ad.height = h;
                ad.mobile_model = mobile_model;
                ad.android_version = android_version;
                ad.density = density;
                storedb.advise.Add(ad);
                storedb.SaveChanges();
                return 1;
            }
            catch {
                return -1;

            }
        }
    }
}

using Newtonsoft.Json.Linq;
using Newtonsoft.Json;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Mvc;

namespace WebJSON.Controllers
{
    public class NearbyTitlesController : Controller
    {
        DataInfo storedb = new DataInfo();
        //
        // GET: /Neighbourhood/
        public JArray Index(int id = int.MaxValue)
        {
            string eatJson = "[]";
            string playJson = "[]";
            string liveJson = "[]";
            string goJson = "[]";
            string otherJson = "[]";


            IEnumerable<nearbyeating> nearbyeatings;
            nearbyeatings = storedb.nearbyeating.Where(u => u.id < id).Take(9);
            eatJson = JsonConvert.SerializeObject(nearbyeatings);

            IEnumerable<nearbyplaying> nearbyplayings;
            nearbyplayings = storedb.nearbyplaying.Where(u => u.id < id).Take(9);
            playJson = JsonConvert.SerializeObject(nearbyplayings);

            IEnumerable<nearbyliving> nearbylivings;
            nearbylivings = storedb.nearbyliving.Where(u => u.id < id).Take(9);
            liveJson = JsonConvert.SerializeObject(nearbylivings);

            IEnumerable<nearbygoing> nearbygoings;
            nearbygoings = storedb.nearbygoing.Where(u => u.id < id).Take(9);
            goJson = JsonConvert.SerializeObject(nearbygoings);

            IEnumerable<nearbyother> nearbyothers;
            nearbyothers = storedb.nearbyother.Where(u => u.id < id).Take(9);
            otherJson = JsonConvert.SerializeObject(nearbyothers);
            JToken jObject1= JToken.Parse(eatJson);
            JToken jObject2 =JToken.Parse(playJson);
            JToken jObject3= JToken.Parse(liveJson);
            JToken jObject4 =JToken.Parse(goJson);
            JToken jObject5 =JToken.Parse(otherJson);

            JArray jArray = JArray.Parse("[]");
            jArray.Add(jObject1);
            jArray.Add(jObject2);
            jArray.Add(jObject3);
            jArray.Add(jObject4);
            jArray.Add(jObject5);
            return jArray;


        }

    }
}

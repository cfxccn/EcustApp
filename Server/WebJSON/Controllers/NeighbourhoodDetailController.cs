using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Mvc;
using Newtonsoft.Json.Linq;
using Newtonsoft.Json;

namespace WebJSON.Controllers
{
    public class NeighbourhoodDetailController : Controller
    {
        //
        // GET: /NeighbourhoodDetail/

        DataInfo storedb = new DataInfo();

        public JArray Index(int type, int id)
        {
            string json = "[]";
            string _type = "吃";
            switch (type)
            {
                case 1: _type = "吃";
                    IEnumerable<nearbyeating> nearbyeatings;
                    nearbyeatings = storedb.nearbyeating.Where(u => u.id == id).Where(u => u.type == _type).Take(1);
                    json = JsonConvert.SerializeObject(nearbyeatings);
                    return JArray.Parse(json);
                    break;
                case 2: _type = "住";
                    IEnumerable<nearbyliving> nearbylivings;
                    nearbylivings = storedb.nearbyliving.Where(u => u.id == id).Where(u => u.type == _type).Take(1);
                    json = JsonConvert.SerializeObject(nearbylivings);
                    return JArray.Parse(json);
                    break;

                case 3: _type = "行";
                    IEnumerable<nearbygoing> nearbygoings;
                    nearbygoings = storedb.nearbygoing.Where(u => u.id == id).Where(u => u.type == _type).Take(1);
                    json = JsonConvert.SerializeObject(nearbygoings);
                    return JArray.Parse(json);
                    break;
                case 4: _type = "玩";
                    IEnumerable<nearbyplaying> nearbyplayings;
                    nearbyplayings = storedb.nearbyplaying.Where(u => u.id == id).Where(u => u.type == _type).Take(1);
                    json = JsonConvert.SerializeObject(nearbyplayings);
                    return JArray.Parse(json);
                    break;
                case 5: _type = "其他";
                    IEnumerable<nearbyother> nearbyothers;
                    nearbyothers = storedb.nearbyother.Where(u => u.id == id).Where(u => u.type == _type).Take(1);
                    json = JsonConvert.SerializeObject(nearbyothers);
                    return JArray.Parse(json);
                    break;
            }
            return JArray.Parse(json);

        }

    }
}

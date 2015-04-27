using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Windows.Forms;
using System.Web;
using System.Xml;
using System.Collections;
using System.Net;
using System.IO;
using System.Xml.Serialization;
using Admin.Weather;
using Newtonsoft.Json;
using Newtonsoft.Json.Linq;

namespace Admin
{
    public partial class Form1 : Form
    {
        int i=0;
        ecustj111Entities storedb = new ecustj111Entities();
        Weather.WeatherWSSoapClient soapClient = new WeatherWSSoapClient("WeatherWSSoap");
        string[] result;
        string type;
        public Form1()
        {
            InitializeComponent();
            button2_Click(null,null);
             textBox1.Text = storedb.version.Max(i => i.build).ToString();
        }
        private void button1_Click(object sender, EventArgs e)
        {
            getWeatherOnce();
        }
        private void button2_Click(object sender, EventArgs e)
        {
            label1.Visible = true;
            button2.Visible = false;
            button3.Visible = true;
          //  timer1.Enabled = true;
            timer1.Start();


        }



        private void button3_Click(object sender, EventArgs e)
        {
            label1.Visible = false;
            button2.Visible = true;
            button3.Visible = false;
      //      timer1.Enabled = false;
            timer1.Stop();
        }

        private  void getWeatherOnce() {

                result = soapClient.getWeather("奉贤", null);

                if (result.Length < 6) {
                    MessageBox.Show("暂无");
                    return;
                }
            weather weatheronce = new Admin.weather();
            weatheronce.datetime = System.DateTime.Now;

            weatheronce.h12 = result[7].Substring(result[7].IndexOf("日") + 2);
            weatheronce.h12temp = result[8];
            weatheronce.h12img1 = result[10].Substring(0, result[10].LastIndexOf("."));
            weatheronce.h12img2 = result[11].Substring(0, result[11].LastIndexOf("."));
            weatheronce.h24 = result[12].Substring(result[12].IndexOf("日") + 2);

            weatheronce.h24temp = result[13];
            //weather.h24img1 = result[15];
            //weather.h24img2 = result[16];
            weatheronce.h24img1 = result[15].Substring(0, result[15].LastIndexOf("."));
            weatheronce.h24img2 = result[16].Substring(0, result[16].LastIndexOf("."));

            HttpWebRequest request = (HttpWebRequest)HttpWebRequest.Create("http://www.pm25.in/api/querys/pm2_5.json?city=shanghai&token=jp1p2yuzcb4FRTwpotEb&stations=no");
            request.Timeout = 5000;
            request.Method = "GET";
            HttpWebResponse response = (HttpWebResponse)request.GetResponse();
            StreamReader sr = new StreamReader(response.GetResponseStream());
            string jsonstr = sr.ReadLine();
            JArray airAqis = JArray.Parse(jsonstr);
            JObject airA = (JObject)airAqis.First();

            weatheronce.aqi = airA.Property("quality").Value.ToString();
            weatheronce.pm25 = int.Parse(airA.Property("pm2_5").Value.ToString());

            storedb.weather.Add(weatheronce);
            storedb.SaveChanges();

            //    MessageBox.Show(airA.Property("pm2_5").Value.ToString());

            MessageBox.Show(weatheronce.datetime + " " + weatheronce.h12 + " " + weatheronce.pm25);
            
        }

        private void timer1_Tick(object sender, EventArgs e)
        {
            i++;
            label2.Text = i.ToString();
            getWeatherOnce();
       
        }

        private void button4_Click(object sender, EventArgs e)
        {
            version version = new version();
            version.build = int.Parse(textBox1.Text);
            storedb.version.Add(version);
            storedb.SaveChanges();
            MessageBox.Show("插入 Build:" + version.build + "成功");
        }

        private void Form1_Load(object sender, EventArgs e)
        {
            // TODO:  这行代码将数据加载到表“ecustj111DataSet.nearbyplaying”中。您可以根据需要移动或删除它。
          }

        private void button5_Click(object sender, EventArgs e)
        {
            

        }
        private void getData(object sender, EventArgs e)
        {
            type = comboBox1.Text;
            if (type.Equals("吃"))
            {
                this.nearbyeatingTableAdapter.Fill(this.ecustj111DataSet.nearbyeating);
                this.dataGridView1.DataSource = this.nearbyeatingBindingSource;
            }
            else if (type.Equals("住"))
            {
                this.nearbylivingTableAdapter.Fill(this.ecustj111DataSet.nearbyliving);
                this.dataGridView1.DataSource = this.nearbylivingBindingSource;
            }
            else if (type.Equals("行"))
            {
                this.nearbygoingTableAdapter.Fill(this.ecustj111DataSet.nearbygoing);
                this.dataGridView1.DataSource = this.nearbygoingBindingSource;
            }
            else if (type.Equals("玩"))
            {
                this.nearbyplayingTableAdapter.Fill(this.ecustj111DataSet.nearbyplaying);
                this.dataGridView1.DataSource = this.nearbyplayingBindingSource;

            }
            else if (type.Equals("其他"))
            {
                this.nearbyotherTableAdapter.Fill(this.ecustj111DataSet.nearbyother);
                this.dataGridView1.DataSource = this.nearbyotherBindingSource;

            }
        }

        private void button6_Click(object sender, EventArgs e)
        {
            type = comboBox1.Text;
            if (type.Equals("吃"))
            {
                this.nearbyeatingTableAdapter.Update(this.ecustj111DataSet.nearbyeating);
            //    this.dataGridView1.DataSource = this.nearbyeatingBindingSource;
            }
            else if (type.Equals("住"))
            {
                this.nearbylivingTableAdapter.Update(this.ecustj111DataSet.nearbyliving);
            //    this.dataGridView1.DataSource = this.nearbylivingBindingSource;
            }
            else if (type.Equals("行"))
            {
                this.nearbygoingTableAdapter.Update(this.ecustj111DataSet.nearbygoing);
           //     this.dataGridView1.DataSource = this.nearbygoingBindingSource;
            }
            else if (type.Equals("玩"))
            {
                this.nearbyplayingTableAdapter.Update(this.ecustj111DataSet.nearbyplaying);
            //    this.dataGridView1.DataSource = this.nearbyplayingBindingSource;

            }
            else if (type.Equals("其他"))
            {
                this.nearbyotherTableAdapter.Update(this.ecustj111DataSet.nearbyother);
            //    this.dataGridView1.DataSource = this.nearbyotherBindingSource;

            }
        }

        private void getData()
        {
        
        }


    }
}

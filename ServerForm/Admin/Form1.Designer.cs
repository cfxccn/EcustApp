namespace Admin
{
    partial class Form1
    {
        /// <summary>
        /// 必需的设计器变量。
        /// </summary>
        private System.ComponentModel.IContainer components = null;

        /// <summary>
        /// 清理所有正在使用的资源。
        /// </summary>
        /// <param name="disposing">如果应释放托管资源，为 true；否则为 false。</param>
        protected override void Dispose(bool disposing)
        {
            if (disposing && (components != null))
            {
                components.Dispose();
            }
            base.Dispose(disposing);
        }

        #region Windows 窗体设计器生成的代码

        /// <summary>
        /// 设计器支持所需的方法 - 不要
        /// 使用代码编辑器修改此方法的内容。
        /// </summary>
        private void InitializeComponent()
        {
            this.components = new System.ComponentModel.Container();
            this.button1 = new System.Windows.Forms.Button();
            this.button2 = new System.Windows.Forms.Button();
            this.button3 = new System.Windows.Forms.Button();
            this.label1 = new System.Windows.Forms.Label();
            this.timer1 = new System.Windows.Forms.Timer(this.components);
            this.label2 = new System.Windows.Forms.Label();
            this.button4 = new System.Windows.Forms.Button();
            this.textBox1 = new System.Windows.Forms.TextBox();
            this.comboBox1 = new System.Windows.Forms.ComboBox();
            this.dataGridView1 = new System.Windows.Forms.DataGridView();
            this.dataGridViewTextBoxColumn1 = new System.Windows.Forms.DataGridViewTextBoxColumn();
            this.dataGridViewTextBoxColumn2 = new System.Windows.Forms.DataGridViewTextBoxColumn();
            this.dataGridViewTextBoxColumn3 = new System.Windows.Forms.DataGridViewTextBoxColumn();
            this.dataGridViewTextBoxColumn4 = new System.Windows.Forms.DataGridViewTextBoxColumn();
            this.dataGridViewTextBoxColumn5 = new System.Windows.Forms.DataGridViewTextBoxColumn();
            this.dataGridViewTextBoxColumn6 = new System.Windows.Forms.DataGridViewTextBoxColumn();
            this.dataGridViewTextBoxColumn7 = new System.Windows.Forms.DataGridViewTextBoxColumn();
            this.dataGridViewTextBoxColumn8 = new System.Windows.Forms.DataGridViewTextBoxColumn();
            this.dataGridViewTextBoxColumn9 = new System.Windows.Forms.DataGridViewTextBoxColumn();
            this.dataGridViewTextBoxColumn10 = new System.Windows.Forms.DataGridViewTextBoxColumn();
            this.nearbyplayingBindingSource = new System.Windows.Forms.BindingSource(this.components);
            this.ecustj111DataSet = new Admin.ecustj111DataSet();
            this.nearbyeatingBindingSource = new System.Windows.Forms.BindingSource(this.components);
            this.nearbyeatingTableAdapter = new Admin.ecustj111DataSetTableAdapters.nearbyeatingTableAdapter();
            this.nearbygoingBindingSource = new System.Windows.Forms.BindingSource(this.components);
            this.nearbygoingTableAdapter = new Admin.ecustj111DataSetTableAdapters.nearbygoingTableAdapter();
            this.nearbylivingBindingSource = new System.Windows.Forms.BindingSource(this.components);
            this.nearbylivingTableAdapter = new Admin.ecustj111DataSetTableAdapters.nearbylivingTableAdapter();
            this.nearbyotherBindingSource = new System.Windows.Forms.BindingSource(this.components);
            this.nearbyotherTableAdapter = new Admin.ecustj111DataSetTableAdapters.nearbyotherTableAdapter();
            this.nearbyplayingTableAdapter = new Admin.ecustj111DataSetTableAdapters.nearbyplayingTableAdapter();
            this.button6 = new System.Windows.Forms.Button();
            ((System.ComponentModel.ISupportInitialize)(this.dataGridView1)).BeginInit();
            ((System.ComponentModel.ISupportInitialize)(this.nearbyplayingBindingSource)).BeginInit();
            ((System.ComponentModel.ISupportInitialize)(this.ecustj111DataSet)).BeginInit();
            ((System.ComponentModel.ISupportInitialize)(this.nearbyeatingBindingSource)).BeginInit();
            ((System.ComponentModel.ISupportInitialize)(this.nearbygoingBindingSource)).BeginInit();
            ((System.ComponentModel.ISupportInitialize)(this.nearbylivingBindingSource)).BeginInit();
            ((System.ComponentModel.ISupportInitialize)(this.nearbyotherBindingSource)).BeginInit();
            this.SuspendLayout();
            // 
            // button1
            // 
            this.button1.Location = new System.Drawing.Point(11, 11);
            this.button1.Name = "button1";
            this.button1.Size = new System.Drawing.Size(146, 37);
            this.button1.TabIndex = 0;
            this.button1.Text = "立即获取一次天气并插入数据库";
            this.button1.UseVisualStyleBackColor = true;
            this.button1.Click += new System.EventHandler(this.button1_Click);
            // 
            // button2
            // 
            this.button2.Location = new System.Drawing.Point(11, 65);
            this.button2.Name = "button2";
            this.button2.Size = new System.Drawing.Size(146, 33);
            this.button2.TabIndex = 1;
            this.button2.Text = "自每小时自动获取天气并插入数据库";
            this.button2.UseVisualStyleBackColor = true;
            this.button2.Click += new System.EventHandler(this.button2_Click);
            // 
            // button3
            // 
            this.button3.Location = new System.Drawing.Point(11, 153);
            this.button3.Name = "button3";
            this.button3.Size = new System.Drawing.Size(146, 33);
            this.button3.TabIndex = 2;
            this.button3.Text = "取消每小时自动抓取天气";
            this.button3.UseVisualStyleBackColor = true;
            this.button3.Visible = false;
            this.button3.Click += new System.EventHandler(this.button3_Click);
            // 
            // label1
            // 
            this.label1.AutoSize = true;
            this.label1.Location = new System.Drawing.Point(9, 122);
            this.label1.Name = "label1";
            this.label1.Size = new System.Drawing.Size(131, 12);
            this.label1.TabIndex = 3;
            this.label1.Text = "每小时自动抓取中 次数";
            this.label1.Visible = false;
            // 
            // timer1
            // 
            this.timer1.Interval = 3600000;
            this.timer1.Tick += new System.EventHandler(this.timer1_Tick);
            // 
            // label2
            // 
            this.label2.AutoSize = true;
            this.label2.Location = new System.Drawing.Point(146, 122);
            this.label2.Name = "label2";
            this.label2.Size = new System.Drawing.Size(11, 12);
            this.label2.TabIndex = 4;
            this.label2.Text = "0";
            // 
            // button4
            // 
            this.button4.Location = new System.Drawing.Point(11, 247);
            this.button4.Name = "button4";
            this.button4.Size = new System.Drawing.Size(146, 35);
            this.button4.TabIndex = 5;
            this.button4.Text = "插入最新版本号到数据库";
            this.button4.UseVisualStyleBackColor = true;
            this.button4.Click += new System.EventHandler(this.button4_Click);
            // 
            // textBox1
            // 
            this.textBox1.Location = new System.Drawing.Point(11, 209);
            this.textBox1.Name = "textBox1";
            this.textBox1.Size = new System.Drawing.Size(146, 21);
            this.textBox1.TabIndex = 6;
            // 
            // comboBox1
            // 
            this.comboBox1.DropDownStyle = System.Windows.Forms.ComboBoxStyle.DropDownList;
            this.comboBox1.FormattingEnabled = true;
            this.comboBox1.Items.AddRange(new object[] {
            "吃",
            "住",
            "行",
            "玩",
            "其他"});
            this.comboBox1.Location = new System.Drawing.Point(174, 11);
            this.comboBox1.Name = "comboBox1";
            this.comboBox1.Size = new System.Drawing.Size(121, 20);
            this.comboBox1.TabIndex = 7;
            this.comboBox1.SelectedValueChanged += new System.EventHandler(this.getData);
            // 
            // dataGridView1
            // 
            this.dataGridView1.AutoGenerateColumns = false;
            this.dataGridView1.ColumnHeadersHeightSizeMode = System.Windows.Forms.DataGridViewColumnHeadersHeightSizeMode.AutoSize;
            this.dataGridView1.Columns.AddRange(new System.Windows.Forms.DataGridViewColumn[] {
            this.dataGridViewTextBoxColumn1,
            this.dataGridViewTextBoxColumn2,
            this.dataGridViewTextBoxColumn3,
            this.dataGridViewTextBoxColumn4,
            this.dataGridViewTextBoxColumn5,
            this.dataGridViewTextBoxColumn6,
            this.dataGridViewTextBoxColumn7,
            this.dataGridViewTextBoxColumn8,
            this.dataGridViewTextBoxColumn9,
            this.dataGridViewTextBoxColumn10});
            this.dataGridView1.DataSource = this.nearbyplayingBindingSource;
            this.dataGridView1.Location = new System.Drawing.Point(174, 48);
            this.dataGridView1.Name = "dataGridView1";
            this.dataGridView1.RowTemplate.Height = 23;
            this.dataGridView1.Size = new System.Drawing.Size(1080, 449);
            this.dataGridView1.TabIndex = 9;
            // 
            // dataGridViewTextBoxColumn1
            // 
            this.dataGridViewTextBoxColumn1.DataPropertyName = "id";
            this.dataGridViewTextBoxColumn1.HeaderText = "id";
            this.dataGridViewTextBoxColumn1.Name = "dataGridViewTextBoxColumn1";
            this.dataGridViewTextBoxColumn1.ReadOnly = true;
            // 
            // dataGridViewTextBoxColumn2
            // 
            this.dataGridViewTextBoxColumn2.DataPropertyName = "name";
            this.dataGridViewTextBoxColumn2.HeaderText = "name";
            this.dataGridViewTextBoxColumn2.Name = "dataGridViewTextBoxColumn2";
            // 
            // dataGridViewTextBoxColumn3
            // 
            this.dataGridViewTextBoxColumn3.DataPropertyName = "type";
            this.dataGridViewTextBoxColumn3.HeaderText = "type";
            this.dataGridViewTextBoxColumn3.Name = "dataGridViewTextBoxColumn3";
            // 
            // dataGridViewTextBoxColumn4
            // 
            this.dataGridViewTextBoxColumn4.DataPropertyName = "introduction";
            this.dataGridViewTextBoxColumn4.HeaderText = "introduction";
            this.dataGridViewTextBoxColumn4.Name = "dataGridViewTextBoxColumn4";
            // 
            // dataGridViewTextBoxColumn5
            // 
            this.dataGridViewTextBoxColumn5.DataPropertyName = "detail";
            this.dataGridViewTextBoxColumn5.HeaderText = "detail";
            this.dataGridViewTextBoxColumn5.Name = "dataGridViewTextBoxColumn5";
            // 
            // dataGridViewTextBoxColumn6
            // 
            this.dataGridViewTextBoxColumn6.DataPropertyName = "location";
            this.dataGridViewTextBoxColumn6.HeaderText = "location";
            this.dataGridViewTextBoxColumn6.Name = "dataGridViewTextBoxColumn6";
            // 
            // dataGridViewTextBoxColumn7
            // 
            this.dataGridViewTextBoxColumn7.DataPropertyName = "longtitude";
            this.dataGridViewTextBoxColumn7.HeaderText = "longtitude";
            this.dataGridViewTextBoxColumn7.Name = "dataGridViewTextBoxColumn7";
            // 
            // dataGridViewTextBoxColumn8
            // 
            this.dataGridViewTextBoxColumn8.DataPropertyName = "latitude";
            this.dataGridViewTextBoxColumn8.HeaderText = "latitude";
            this.dataGridViewTextBoxColumn8.Name = "dataGridViewTextBoxColumn8";
            // 
            // dataGridViewTextBoxColumn9
            // 
            this.dataGridViewTextBoxColumn9.DataPropertyName = "phone";
            this.dataGridViewTextBoxColumn9.HeaderText = "phone";
            this.dataGridViewTextBoxColumn9.Name = "dataGridViewTextBoxColumn9";
            // 
            // dataGridViewTextBoxColumn10
            // 
            this.dataGridViewTextBoxColumn10.DataPropertyName = "ll";
            this.dataGridViewTextBoxColumn10.HeaderText = "ll";
            this.dataGridViewTextBoxColumn10.Name = "dataGridViewTextBoxColumn10";
            // 
            // nearbyplayingBindingSource
            // 
            this.nearbyplayingBindingSource.DataMember = "nearbyplaying";
            this.nearbyplayingBindingSource.DataSource = this.ecustj111DataSet;
            // 
            // ecustj111DataSet
            // 
            this.ecustj111DataSet.DataSetName = "ecustj111DataSet";
            this.ecustj111DataSet.SchemaSerializationMode = System.Data.SchemaSerializationMode.IncludeSchema;
            // 
            // nearbyeatingBindingSource
            // 
            this.nearbyeatingBindingSource.DataMember = "nearbyeating";
            this.nearbyeatingBindingSource.DataSource = this.ecustj111DataSet;
            // 
            // nearbyeatingTableAdapter
            // 
            this.nearbyeatingTableAdapter.ClearBeforeFill = true;
            // 
            // nearbygoingBindingSource
            // 
            this.nearbygoingBindingSource.DataMember = "nearbygoing";
            this.nearbygoingBindingSource.DataSource = this.ecustj111DataSet;
            // 
            // nearbygoingTableAdapter
            // 
            this.nearbygoingTableAdapter.ClearBeforeFill = true;
            // 
            // nearbylivingBindingSource
            // 
            this.nearbylivingBindingSource.DataMember = "nearbyliving";
            this.nearbylivingBindingSource.DataSource = this.ecustj111DataSet;
            // 
            // nearbylivingTableAdapter
            // 
            this.nearbylivingTableAdapter.ClearBeforeFill = true;
            // 
            // nearbyotherBindingSource
            // 
            this.nearbyotherBindingSource.DataMember = "nearbyother";
            this.nearbyotherBindingSource.DataSource = this.ecustj111DataSet;
            // 
            // nearbyotherTableAdapter
            // 
            this.nearbyotherTableAdapter.ClearBeforeFill = true;
            // 
            // nearbyplayingTableAdapter
            // 
            this.nearbyplayingTableAdapter.ClearBeforeFill = true;
            // 
            // button6
            // 
            this.button6.Location = new System.Drawing.Point(316, 11);
            this.button6.Name = "button6";
            this.button6.Size = new System.Drawing.Size(75, 23);
            this.button6.TabIndex = 10;
            this.button6.Text = "保存";
            this.button6.UseVisualStyleBackColor = true;
            this.button6.Click += new System.EventHandler(this.button6_Click);
            // 
            // Form1
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 12F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.ClientSize = new System.Drawing.Size(1266, 533);
            this.Controls.Add(this.button6);
            this.Controls.Add(this.dataGridView1);
            this.Controls.Add(this.comboBox1);
            this.Controls.Add(this.textBox1);
            this.Controls.Add(this.button4);
            this.Controls.Add(this.label2);
            this.Controls.Add(this.label1);
            this.Controls.Add(this.button3);
            this.Controls.Add(this.button2);
            this.Controls.Add(this.button1);
            this.Name = "Form1";
            this.Text = "后台管理";
            this.Load += new System.EventHandler(this.Form1_Load);
            ((System.ComponentModel.ISupportInitialize)(this.dataGridView1)).EndInit();
            ((System.ComponentModel.ISupportInitialize)(this.nearbyplayingBindingSource)).EndInit();
            ((System.ComponentModel.ISupportInitialize)(this.ecustj111DataSet)).EndInit();
            ((System.ComponentModel.ISupportInitialize)(this.nearbyeatingBindingSource)).EndInit();
            ((System.ComponentModel.ISupportInitialize)(this.nearbygoingBindingSource)).EndInit();
            ((System.ComponentModel.ISupportInitialize)(this.nearbylivingBindingSource)).EndInit();
            ((System.ComponentModel.ISupportInitialize)(this.nearbyotherBindingSource)).EndInit();
            this.ResumeLayout(false);
            this.PerformLayout();

        }

        #endregion

        private System.Windows.Forms.Button button1;
        private System.Windows.Forms.Button button2;
        private System.Windows.Forms.Button button3;
        private System.Windows.Forms.Label label1;
        private System.Windows.Forms.Timer timer1;
        private System.Windows.Forms.Label label2;
        private System.Windows.Forms.Button button4;
        private System.Windows.Forms.TextBox textBox1;
        private System.Windows.Forms.ComboBox comboBox1;
        private System.Windows.Forms.DataGridView dataGridView1;
        private ecustj111DataSet ecustj111DataSet;

        private System.Windows.Forms.BindingSource nearbyeatingBindingSource;
        private ecustj111DataSetTableAdapters.nearbyeatingTableAdapter nearbyeatingTableAdapter;

        private System.Windows.Forms.DataGridViewTextBoxColumn dataGridViewTextBoxColumn1;
        private System.Windows.Forms.DataGridViewTextBoxColumn dataGridViewTextBoxColumn2;
        private System.Windows.Forms.DataGridViewTextBoxColumn dataGridViewTextBoxColumn3;
        private System.Windows.Forms.DataGridViewTextBoxColumn dataGridViewTextBoxColumn4;
        private System.Windows.Forms.DataGridViewTextBoxColumn dataGridViewTextBoxColumn5;
        private System.Windows.Forms.DataGridViewTextBoxColumn dataGridViewTextBoxColumn6;
        private System.Windows.Forms.DataGridViewTextBoxColumn dataGridViewTextBoxColumn7;
        private System.Windows.Forms.DataGridViewTextBoxColumn dataGridViewTextBoxColumn8;
        private System.Windows.Forms.DataGridViewTextBoxColumn dataGridViewTextBoxColumn9;
        private System.Windows.Forms.DataGridViewTextBoxColumn dataGridViewTextBoxColumn10;
        private System.Windows.Forms.BindingSource nearbygoingBindingSource;
        private ecustj111DataSetTableAdapters.nearbygoingTableAdapter nearbygoingTableAdapter;
        private System.Windows.Forms.BindingSource nearbylivingBindingSource;
        private ecustj111DataSetTableAdapters.nearbylivingTableAdapter nearbylivingTableAdapter;
        private System.Windows.Forms.BindingSource nearbyotherBindingSource;
        private ecustj111DataSetTableAdapters.nearbyotherTableAdapter nearbyotherTableAdapter;
        private System.Windows.Forms.BindingSource nearbyplayingBindingSource;
        private ecustj111DataSetTableAdapters.nearbyplayingTableAdapter nearbyplayingTableAdapter;
        private System.Windows.Forms.Button button6;
    }
}


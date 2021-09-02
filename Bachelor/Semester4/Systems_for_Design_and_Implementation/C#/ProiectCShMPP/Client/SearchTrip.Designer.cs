using System.ComponentModel;
using System.Windows.Forms;

namespace GUI
{
    partial class SearchTrip
    {
        /// <summary>
        /// Required designer variable.
        /// </summary>
        private IContainer components = null;

        /// <summary>
        /// Clean up any resources being used.
        /// </summary>
        /// <param name="disposing">true if managed resources should be disposed; otherwise, false.</param>
        protected override void Dispose(bool disposing)
        {
            if (disposing && (components != null))
            {
                components.Dispose();
            }

            base.Dispose(disposing);
        }

        #region Windows Form Designer generated code

        /// <summary>
        /// Required method for Designer support - do not modify
        /// the contents of this method with the code editor.
        /// </summary>
        private void InitializeComponent()
        {
            this.panel1 = new System.Windows.Forms.Panel();
            this.labelGreeting = new System.Windows.Forms.Label();
            this.dataGridViewTrips = new System.Windows.Forms.DataGridView();
            this.panel1.SuspendLayout();
            ((System.ComponentModel.ISupportInitialize) (this.dataGridViewTrips)).BeginInit();
            this.SuspendLayout();
            // 
            // panel1
            // 
            this.panel1.Controls.Add(this.labelGreeting);
            this.panel1.Dock = System.Windows.Forms.DockStyle.Top;
            this.panel1.Location = new System.Drawing.Point(0, 0);
            this.panel1.Name = "panel1";
            this.panel1.Size = new System.Drawing.Size(800, 100);
            this.panel1.TabIndex = 0;
            // 
            // labelGreeting
            // 
            this.labelGreeting.Font = new System.Drawing.Font("Microsoft Sans Serif", 15.75F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte) (0)));
            this.labelGreeting.Location = new System.Drawing.Point(12, 35);
            this.labelGreeting.Name = "labelGreeting";
            this.labelGreeting.Size = new System.Drawing.Size(681, 23);
            this.labelGreeting.TabIndex = 0;
            this.labelGreeting.Text = "label1";
            // 
            // dataGridViewTrips
            // 
            this.dataGridViewTrips.AutoSizeColumnsMode = System.Windows.Forms.DataGridViewAutoSizeColumnsMode.Fill;
            this.dataGridViewTrips.ColumnHeadersHeightSizeMode = System.Windows.Forms.DataGridViewColumnHeadersHeightSizeMode.AutoSize;
            this.dataGridViewTrips.Dock = System.Windows.Forms.DockStyle.Fill;
            this.dataGridViewTrips.Location = new System.Drawing.Point(0, 100);
            this.dataGridViewTrips.Name = "dataGridViewTrips";
            this.dataGridViewTrips.Size = new System.Drawing.Size(800, 350);
            this.dataGridViewTrips.TabIndex = 1;
            this.dataGridViewTrips.SelectionMode = DataGridViewSelectionMode.FullRowSelect;
            // 
            // SearchTrip
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.ClientSize = new System.Drawing.Size(800, 450);
            this.Controls.Add(this.dataGridViewTrips);
            this.Controls.Add(this.panel1);
            this.Name = "SearchTrip";
            this.Text = "SearchTrip";
            this.Load += new System.EventHandler(this.SearchTrip_Load);
            this.panel1.ResumeLayout(false);
            ((System.ComponentModel.ISupportInitialize) (this.dataGridViewTrips)).EndInit();
            this.ResumeLayout(false);
        }

        private System.Windows.Forms.Label labelGreeting;

        private System.Windows.Forms.DataGridView dataGridViewTrips;

        private System.Windows.Forms.Panel panel1;

        #endregion
    }
}
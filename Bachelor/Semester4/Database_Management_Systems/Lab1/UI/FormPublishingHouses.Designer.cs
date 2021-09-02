using System.Windows.Forms;

namespace Lab1.UI
{
    partial class FormPublishingHouses
    {
        /// <summary>
        /// Required designer variable.
        /// </summary>
        private System.ComponentModel.IContainer components = null;

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
            this.labelEdituri = new System.Windows.Forms.Label();
            this.dataGridViewPublishingHouses = new System.Windows.Forms.DataGridView();
            this.buttonAddBook = new System.Windows.Forms.Button();
            this.panel3 = new System.Windows.Forms.Panel();
            ((System.ComponentModel.ISupportInitialize) (this.dataGridViewPublishingHouses)).BeginInit();
            this.panel3.SuspendLayout();
            this.SuspendLayout();
            // 
            // labelEdituri
            // 
            this.labelEdituri.BackColor = System.Drawing.Color.FromArgb(((int) (((byte) (63)))), ((int) (((byte) (114)))), ((int) (((byte) (175)))));
            this.labelEdituri.Dock = System.Windows.Forms.DockStyle.Top;
            this.labelEdituri.Font = new System.Drawing.Font("Microsoft Sans Serif", 18F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte) (0)));
            this.labelEdituri.ForeColor = System.Drawing.Color.FromArgb(((int) (((byte) (249)))), ((int) (((byte) (247)))), ((int) (((byte) (247)))));
            this.labelEdituri.ImageAlign = System.Drawing.ContentAlignment.TopCenter;
            this.labelEdituri.Location = new System.Drawing.Point(0, 0);
            this.labelEdituri.Name = "labelEdituri";
            this.labelEdituri.Size = new System.Drawing.Size(800, 61);
            this.labelEdituri.TabIndex = 0;
            this.labelEdituri.Text = "Edituri";
            this.labelEdituri.TextAlign = System.Drawing.ContentAlignment.MiddleCenter;
            // 
            // dataGridViewPublishingHouses
            // 
            this.dataGridViewPublishingHouses.AutoSizeColumnsMode = System.Windows.Forms.DataGridViewAutoSizeColumnsMode.Fill;
            this.dataGridViewPublishingHouses.ColumnHeadersHeightSizeMode = System.Windows.Forms.DataGridViewColumnHeadersHeightSizeMode.AutoSize;
            this.dataGridViewPublishingHouses.GridColor = System.Drawing.Color.FromArgb(((int) (((byte) (219)))), ((int) (((byte) (226)))), ((int) (((byte) (239)))));
            this.dataGridViewPublishingHouses.Location = new System.Drawing.Point(0, 61);
            this.dataGridViewPublishingHouses.Name = "dataGridViewPublishingHouses";
            this.dataGridViewPublishingHouses.SelectionMode = System.Windows.Forms.DataGridViewSelectionMode.FullRowSelect;
            this.dataGridViewPublishingHouses.Size = new System.Drawing.Size(800, 389);
            this.dataGridViewPublishingHouses.TabIndex = 1;
            this.dataGridViewPublishingHouses.DoubleClick += new System.EventHandler(this.FormPublishingHouses_DoubleClick);
            // 
            // buttonAddBook
            // 
            this.buttonAddBook.Anchor = System.Windows.Forms.AnchorStyles.None;
            this.buttonAddBook.BackColor = System.Drawing.Color.FromArgb(((int) (((byte) (17)))), ((int) (((byte) (45)))), ((int) (((byte) (78)))));
            this.buttonAddBook.Font = new System.Drawing.Font("Microsoft Sans Serif", 12F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte) (0)));
            this.buttonAddBook.ForeColor = System.Drawing.Color.FromArgb(((int) (((byte) (219)))), ((int) (((byte) (226)))), ((int) (((byte) (239)))));
            this.buttonAddBook.Location = new System.Drawing.Point(333, 9);
            this.buttonAddBook.Name = "buttonAddBook";
            this.buttonAddBook.Size = new System.Drawing.Size(139, 39);
            this.buttonAddBook.TabIndex = 2;
            this.buttonAddBook.Text = "Adauga Carte";
            this.buttonAddBook.UseVisualStyleBackColor = false;
            this.buttonAddBook.Click += new System.EventHandler(this.buttonAddBook_Click);
            // 
            // panel3
            // 
            this.panel3.Controls.Add(this.buttonAddBook);
            this.panel3.Dock = System.Windows.Forms.DockStyle.Bottom;
            this.panel3.Location = new System.Drawing.Point(0, 390);
            this.panel3.Name = "panel3";
            this.panel3.Size = new System.Drawing.Size(800, 60);
            this.panel3.TabIndex = 5;
            // 
            // FormPublishingHouses
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.BackColor = System.Drawing.Color.FromArgb(((int) (((byte) (63)))), ((int) (((byte) (114)))), ((int) (((byte) (175)))));
            this.ClientSize = new System.Drawing.Size(800, 450);
            this.Controls.Add(this.panel3);
            this.Controls.Add(this.dataGridViewPublishingHouses);
            this.Controls.Add(this.labelEdituri);
            this.Name = "FormPublishingHouses";
            this.Text = "Edituri";
            this.Load += new System.EventHandler(this.Form1_Load);
            ((System.ComponentModel.ISupportInitialize) (this.dataGridViewPublishingHouses)).EndInit();
            this.panel3.ResumeLayout(false);
            this.ResumeLayout(false);
        }

        private System.Windows.Forms.Panel panel3;

        private System.Windows.Forms.Button buttonAddBook;

        private System.Windows.Forms.DataGridView dataGridViewPublishingHouses;

        private System.Windows.Forms.Label labelEdituri;

        #endregion
    }
}
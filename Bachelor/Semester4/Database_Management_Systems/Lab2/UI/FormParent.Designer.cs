using System.Windows.Forms;

namespace Lab1.UI
{
    partial class FormParent
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
            this.labelParent = new System.Windows.Forms.Label();
            this.dataGridViewParents = new System.Windows.Forms.DataGridView();
            this.buttonAddChild = new System.Windows.Forms.Button();
            this.panel3 = new System.Windows.Forms.Panel();
            ((System.ComponentModel.ISupportInitialize) (this.dataGridViewParents)).BeginInit();
            this.panel3.SuspendLayout();
            this.SuspendLayout();
            // 
            // labelParent
            // 
            this.labelParent.BackColor = System.Drawing.Color.FromArgb(((int) (((byte) (63)))), ((int) (((byte) (114)))), ((int) (((byte) (175)))));
            this.labelParent.Dock = System.Windows.Forms.DockStyle.Top;
            this.labelParent.Font = new System.Drawing.Font("Microsoft Sans Serif", 18F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte) (0)));
            this.labelParent.ForeColor = System.Drawing.Color.FromArgb(((int) (((byte) (249)))), ((int) (((byte) (247)))), ((int) (((byte) (247)))));
            this.labelParent.ImageAlign = System.Drawing.ContentAlignment.TopCenter;
            this.labelParent.Location = new System.Drawing.Point(0, 0);
            this.labelParent.Name = "labelParent";
            this.labelParent.Size = new System.Drawing.Size(800, 61);
            this.labelParent.TabIndex = 0;
            this.labelParent.Text = "Parent";
            this.labelParent.TextAlign = System.Drawing.ContentAlignment.MiddleCenter;
            // 
            // dataGridViewPublishingHouses
            // 
            this.dataGridViewParents.AutoSizeColumnsMode = System.Windows.Forms.DataGridViewAutoSizeColumnsMode.Fill;
            this.dataGridViewParents.ColumnHeadersHeightSizeMode = System.Windows.Forms.DataGridViewColumnHeadersHeightSizeMode.AutoSize;
            this.dataGridViewParents.GridColor = System.Drawing.Color.FromArgb(((int) (((byte) (219)))), ((int) (((byte) (226)))), ((int) (((byte) (239)))));
            this.dataGridViewParents.Location = new System.Drawing.Point(0, 61);
            this.dataGridViewParents.Name = "dataGridViewParents";
            this.dataGridViewParents.SelectionMode = System.Windows.Forms.DataGridViewSelectionMode.FullRowSelect;
            this.dataGridViewParents.Size = new System.Drawing.Size(800, 389);
            this.dataGridViewParents.TabIndex = 1;
            this.dataGridViewParents.DoubleClick += new System.EventHandler(this.FormParent_DoubleClick);
            // 
            // buttonAddChild
            // 
            this.buttonAddChild.Anchor = System.Windows.Forms.AnchorStyles.None;
            this.buttonAddChild.BackColor = System.Drawing.Color.FromArgb(((int) (((byte) (17)))), ((int) (((byte) (45)))), ((int) (((byte) (78)))));
            this.buttonAddChild.Font = new System.Drawing.Font("Microsoft Sans Serif", 12F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte) (0)));
            this.buttonAddChild.ForeColor = System.Drawing.Color.FromArgb(((int) (((byte) (219)))), ((int) (((byte) (226)))), ((int) (((byte) (239)))));
            this.buttonAddChild.Location = new System.Drawing.Point(333, 9);
            this.buttonAddChild.Name = "buttonAddChild";
            this.buttonAddChild.Size = new System.Drawing.Size(139, 39);
            this.buttonAddChild.TabIndex = 2;
            this.buttonAddChild.Text = "Adauga Carte";
            this.buttonAddChild.UseVisualStyleBackColor = false;
            this.buttonAddChild.Click += new System.EventHandler(this.buttonAddBook_Click);
            // 
            // panel3
            // 
            this.panel3.Controls.Add(this.buttonAddChild);
            this.panel3.Dock = System.Windows.Forms.DockStyle.Bottom;
            this.panel3.Location = new System.Drawing.Point(0, 390);
            this.panel3.Name = "panel3";
            this.panel3.Size = new System.Drawing.Size(800, 60);
            this.panel3.TabIndex = 5;
            // 
            // FormParent
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.BackColor = System.Drawing.Color.FromArgb(((int) (((byte) (63)))), ((int) (((byte) (114)))), ((int) (((byte) (175)))));
            this.ClientSize = new System.Drawing.Size(800, 450);
            this.Controls.Add(this.panel3);
            this.Controls.Add(this.dataGridViewParents);
            this.Controls.Add(this.labelParent);
            this.Name = "FormParent";
            this.Text = "Parent";
            this.Load += new System.EventHandler(this.FormParent_Load);
            ((System.ComponentModel.ISupportInitialize) (this.dataGridViewParents)).EndInit();
            this.panel3.ResumeLayout(false);
            this.ResumeLayout(false);
        }

        private System.Windows.Forms.Panel panel3;

        private System.Windows.Forms.Button buttonAddChild;

        private System.Windows.Forms.DataGridView dataGridViewParents;

        private System.Windows.Forms.Label labelParent;

        #endregion
    }
}
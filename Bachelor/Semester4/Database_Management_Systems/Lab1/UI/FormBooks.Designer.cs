using System.ComponentModel;
using System.Windows.Forms;

namespace Lab1.UI
{
    partial class FormBooks
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
            this.labelCarti = new System.Windows.Forms.Label();
            this.panel1 = new System.Windows.Forms.Panel();
            this.panel2 = new System.Windows.Forms.Panel();
            this.dataGridViewBooks = new System.Windows.Forms.DataGridView();
            this.panel3 = new System.Windows.Forms.Panel();
            this.tableLayoutPanel1 = new System.Windows.Forms.TableLayoutPanel();
            this.labelAn = new System.Windows.Forms.Label();
            this.labelEid = new System.Windows.Forms.Label();
            this.labelTitlu = new System.Windows.Forms.Label();
            this.textBoxTitlu = new System.Windows.Forms.TextBox();
            this.textBoxEid = new System.Windows.Forms.TextBox();
            this.textBoxAn = new System.Windows.Forms.TextBox();
            this.buttonUpdate = new System.Windows.Forms.Button();
            this.buttonDelete = new System.Windows.Forms.Button();
            this.panel2.SuspendLayout();
            ((System.ComponentModel.ISupportInitialize) (this.dataGridViewBooks)).BeginInit();
            this.panel3.SuspendLayout();
            this.tableLayoutPanel1.SuspendLayout();
            this.SuspendLayout();
            // 
            // labelCarti
            // 
            this.labelCarti.Dock = System.Windows.Forms.DockStyle.Top;
            this.labelCarti.Font = new System.Drawing.Font("Microsoft Sans Serif", 18F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte) (0)));
            this.labelCarti.ForeColor = System.Drawing.Color.FromArgb(((int) (((byte) (249)))), ((int) (((byte) (247)))), ((int) (((byte) (247)))));
            this.labelCarti.Location = new System.Drawing.Point(0, 0);
            this.labelCarti.Name = "labelCarti";
            this.labelCarti.Size = new System.Drawing.Size(800, 61);
            this.labelCarti.TabIndex = 0;
            this.labelCarti.Text = "Carti";
            this.labelCarti.TextAlign = System.Drawing.ContentAlignment.MiddleCenter;
            // 
            // panel1
            // 
            this.panel1.Dock = System.Windows.Forms.DockStyle.Bottom;
            this.panel1.Location = new System.Drawing.Point(0, 376);
            this.panel1.Name = "panel1";
            this.panel1.Size = new System.Drawing.Size(800, 74);
            this.panel1.TabIndex = 1;
            // 
            // panel2
            // 
            this.panel2.Controls.Add(this.dataGridViewBooks);
            this.panel2.Dock = System.Windows.Forms.DockStyle.Fill;
            this.panel2.Location = new System.Drawing.Point(0, 61);
            this.panel2.Name = "panel2";
            this.panel2.Size = new System.Drawing.Size(800, 315);
            this.panel2.TabIndex = 2;
            // 
            // dataGridViewBooks
            // 
            this.dataGridViewBooks.AutoSizeColumnsMode = System.Windows.Forms.DataGridViewAutoSizeColumnsMode.DisplayedCells;
            this.dataGridViewBooks.Dock = System.Windows.Forms.DockStyle.Fill;
            this.dataGridViewBooks.Location = new System.Drawing.Point(0, 0);
            this.dataGridViewBooks.Name = "dataGridViewBooks";
            this.dataGridViewBooks.SelectionMode = System.Windows.Forms.DataGridViewSelectionMode.FullRowSelect;
            this.dataGridViewBooks.Size = new System.Drawing.Size(800, 315);
            this.dataGridViewBooks.TabIndex = 0;
            this.dataGridViewBooks.SelectionChanged += new System.EventHandler(this.dataGridViewBooks_SelectionChanged);
            // 
            // panel3
            // 
            this.panel3.Controls.Add(this.tableLayoutPanel1);
            this.panel3.Dock = System.Windows.Forms.DockStyle.Right;
            this.panel3.Location = new System.Drawing.Point(452, 61);
            this.panel3.Name = "panel3";
            this.panel3.Size = new System.Drawing.Size(348, 315);
            this.panel3.TabIndex = 3;
            // 
            // tableLayoutPanel1
            // 
            this.tableLayoutPanel1.ColumnCount = 2;
            this.tableLayoutPanel1.ColumnStyles.Add(new System.Windows.Forms.ColumnStyle(System.Windows.Forms.SizeType.Percent, 50F));
            this.tableLayoutPanel1.ColumnStyles.Add(new System.Windows.Forms.ColumnStyle(System.Windows.Forms.SizeType.Percent, 50F));
            this.tableLayoutPanel1.Controls.Add(this.labelAn, 0, 1);
            this.tableLayoutPanel1.Controls.Add(this.labelEid, 0, 2);
            this.tableLayoutPanel1.Controls.Add(this.labelTitlu, 0, 0);
            this.tableLayoutPanel1.Controls.Add(this.textBoxTitlu, 1, 0);
            this.tableLayoutPanel1.Controls.Add(this.textBoxEid, 1, 2);
            this.tableLayoutPanel1.Controls.Add(this.textBoxAn, 1, 1);
            this.tableLayoutPanel1.Controls.Add(this.buttonUpdate, 0, 3);
            this.tableLayoutPanel1.Controls.Add(this.buttonDelete, 1, 3);
            this.tableLayoutPanel1.Dock = System.Windows.Forms.DockStyle.Fill;
            this.tableLayoutPanel1.Location = new System.Drawing.Point(0, 0);
            this.tableLayoutPanel1.Name = "tableLayoutPanel1";
            this.tableLayoutPanel1.RowCount = 4;
            this.tableLayoutPanel1.RowStyles.Add(new System.Windows.Forms.RowStyle(System.Windows.Forms.SizeType.Percent, 25F));
            this.tableLayoutPanel1.RowStyles.Add(new System.Windows.Forms.RowStyle(System.Windows.Forms.SizeType.Percent, 25F));
            this.tableLayoutPanel1.RowStyles.Add(new System.Windows.Forms.RowStyle(System.Windows.Forms.SizeType.Percent, 25F));
            this.tableLayoutPanel1.RowStyles.Add(new System.Windows.Forms.RowStyle(System.Windows.Forms.SizeType.Percent, 25F));
            this.tableLayoutPanel1.Size = new System.Drawing.Size(348, 315);
            this.tableLayoutPanel1.TabIndex = 0;
            // 
            // labelAn
            // 
            this.labelAn.Anchor = System.Windows.Forms.AnchorStyles.Left;
            this.labelAn.Font = new System.Drawing.Font("Microsoft Sans Serif", 9.75F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte) (0)));
            this.labelAn.ForeColor = System.Drawing.Color.FromArgb(((int) (((byte) (249)))), ((int) (((byte) (247)))), ((int) (((byte) (247)))));
            this.labelAn.Location = new System.Drawing.Point(3, 105);
            this.labelAn.Name = "labelAn";
            this.labelAn.Size = new System.Drawing.Size(168, 23);
            this.labelAn.TabIndex = 1;
            this.labelAn.Text = "An:";
            this.labelAn.TextAlign = System.Drawing.ContentAlignment.MiddleCenter;
            // 
            // labelEid
            // 
            this.labelEid.Anchor = System.Windows.Forms.AnchorStyles.Left;
            this.labelEid.Font = new System.Drawing.Font("Microsoft Sans Serif", 9.75F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte) (0)));
            this.labelEid.ForeColor = System.Drawing.Color.FromArgb(((int) (((byte) (249)))), ((int) (((byte) (247)))), ((int) (((byte) (247)))));
            this.labelEid.Location = new System.Drawing.Point(3, 183);
            this.labelEid.Name = "labelEid";
            this.labelEid.Size = new System.Drawing.Size(168, 23);
            this.labelEid.TabIndex = 2;
            this.labelEid.Text = "eid:";
            this.labelEid.TextAlign = System.Drawing.ContentAlignment.MiddleCenter;
            // 
            // labelTitlu
            // 
            this.labelTitlu.Anchor = System.Windows.Forms.AnchorStyles.Left;
            this.labelTitlu.Font = new System.Drawing.Font("Microsoft Sans Serif", 9.75F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte) (0)));
            this.labelTitlu.ForeColor = System.Drawing.Color.FromArgb(((int) (((byte) (249)))), ((int) (((byte) (247)))), ((int) (((byte) (247)))));
            this.labelTitlu.Location = new System.Drawing.Point(3, 0);
            this.labelTitlu.Name = "labelTitlu";
            this.labelTitlu.Size = new System.Drawing.Size(168, 78);
            this.labelTitlu.TabIndex = 0;
            this.labelTitlu.Text = "Titlu:";
            this.labelTitlu.TextAlign = System.Drawing.ContentAlignment.MiddleCenter;
            // 
            // textBoxTitlu
            // 
            this.textBoxTitlu.Anchor = System.Windows.Forms.AnchorStyles.Left;
            this.textBoxTitlu.Location = new System.Drawing.Point(177, 29);
            this.textBoxTitlu.Name = "textBoxTitlu";
            this.textBoxTitlu.Size = new System.Drawing.Size(168, 20);
            this.textBoxTitlu.TabIndex = 3;
            // 
            // textBoxEid
            // 
            this.textBoxEid.Anchor = System.Windows.Forms.AnchorStyles.Left;
            this.textBoxEid.Location = new System.Drawing.Point(177, 185);
            this.textBoxEid.Name = "textBoxEid";
            this.textBoxEid.Size = new System.Drawing.Size(168, 20);
            this.textBoxEid.TabIndex = 5;
            // 
            // textBoxAn
            // 
            this.textBoxAn.Anchor = System.Windows.Forms.AnchorStyles.Left;
            this.textBoxAn.Location = new System.Drawing.Point(177, 107);
            this.textBoxAn.Name = "textBoxAn";
            this.textBoxAn.Size = new System.Drawing.Size(168, 20);
            this.textBoxAn.TabIndex = 6;
            // 
            // buttonUpdate
            // 
            this.buttonUpdate.Anchor = ((System.Windows.Forms.AnchorStyles) ((System.Windows.Forms.AnchorStyles.Left | System.Windows.Forms.AnchorStyles.Right)));
            this.buttonUpdate.BackColor = System.Drawing.Color.FromArgb(((int) (((byte) (17)))), ((int) (((byte) (45)))), ((int) (((byte) (78)))));
            this.buttonUpdate.Font = new System.Drawing.Font("Microsoft Sans Serif", 8.25F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte) (0)));
            this.buttonUpdate.ForeColor = System.Drawing.Color.FromArgb(((int) (((byte) (219)))), ((int) (((byte) (226)))), ((int) (((byte) (239)))));
            this.buttonUpdate.Location = new System.Drawing.Point(3, 258);
            this.buttonUpdate.Name = "buttonUpdate";
            this.buttonUpdate.Size = new System.Drawing.Size(168, 33);
            this.buttonUpdate.TabIndex = 7;
            this.buttonUpdate.Text = "Update";
            this.buttonUpdate.UseVisualStyleBackColor = false;
            this.buttonUpdate.Click += new System.EventHandler(this.buttonUpdate_Click);
            // 
            // buttonDelete
            // 
            this.buttonDelete.Anchor = ((System.Windows.Forms.AnchorStyles) ((System.Windows.Forms.AnchorStyles.Left | System.Windows.Forms.AnchorStyles.Right)));
            this.buttonDelete.BackColor = System.Drawing.Color.FromArgb(((int) (((byte) (17)))), ((int) (((byte) (45)))), ((int) (((byte) (78)))));
            this.buttonDelete.Font = new System.Drawing.Font("Microsoft Sans Serif", 8.25F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte) (0)));
            this.buttonDelete.ForeColor = System.Drawing.Color.FromArgb(((int) (((byte) (219)))), ((int) (((byte) (226)))), ((int) (((byte) (239)))));
            this.buttonDelete.Location = new System.Drawing.Point(177, 258);
            this.buttonDelete.Name = "buttonDelete";
            this.buttonDelete.Size = new System.Drawing.Size(168, 33);
            this.buttonDelete.TabIndex = 8;
            this.buttonDelete.Text = "Delete";
            this.buttonDelete.UseVisualStyleBackColor = false;
            this.buttonDelete.Click += new System.EventHandler(this.buttonDelete_Click);
            // 
            // FormBooks
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.BackColor = System.Drawing.Color.FromArgb(((int) (((byte) (63)))), ((int) (((byte) (114)))), ((int) (((byte) (175)))));
            this.ClientSize = new System.Drawing.Size(800, 450);
            this.Controls.Add(this.panel3);
            this.Controls.Add(this.panel2);
            this.Controls.Add(this.panel1);
            this.Controls.Add(this.labelCarti);
            this.Name = "FormBooks";
            this.Text = "FormBooks";
            this.Load += new System.EventHandler(this.FormBooks_Load);
            this.panel2.ResumeLayout(false);
            ((System.ComponentModel.ISupportInitialize) (this.dataGridViewBooks)).EndInit();
            this.panel3.ResumeLayout(false);
            this.tableLayoutPanel1.ResumeLayout(false);
            this.tableLayoutPanel1.PerformLayout();
            this.ResumeLayout(false);
        }

        private System.Windows.Forms.Button buttonUpdate;
        private System.Windows.Forms.Button buttonDelete;

        private System.Windows.Forms.TextBox textBoxTitlu;
        private System.Windows.Forms.TextBox textBoxAn;
        private System.Windows.Forms.TextBox textBoxEid;

        private System.Windows.Forms.Label labelEid;
        private System.Windows.Forms.Label labelAn;

        private System.Windows.Forms.Label labelTitlu;

        private System.Windows.Forms.TableLayoutPanel tableLayoutPanel1;

        private System.Windows.Forms.DataGridView dataGridViewBooks;

        private System.Windows.Forms.Panel panel3;

        private System.Windows.Forms.Panel panel2;

        private System.Windows.Forms.Panel panel1;

        private System.Windows.Forms.Label labelCarti;

        #endregion
    }
}
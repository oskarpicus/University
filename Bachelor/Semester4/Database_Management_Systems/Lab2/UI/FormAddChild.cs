using System;
using System.Collections.Generic;
using System.Drawing;
using System.Linq;
using System.Windows.Forms;
using Lab1.MyAdapter;

namespace Lab1.UI
{
    public partial class FormAddChild : Form
    {
        private readonly Adapter _adapter;
        private readonly int _parentId;
        private readonly Dictionary<string, TextBox> _textBoxes = new Dictionary<string, TextBox>();
        
        public FormAddChild(int id,Adapter adapter)
        {
            InitializeComponent();
            _adapter = adapter;
            _parentId = id;
        }

        private void buttonSave_Click(object sender, EventArgs e)
        {
            try
            {
                IList<string> parameters = new List<string>();
                string idChild = Utils.GetChildIdColumn();
                foreach (var column in Utils.GetResource(Resources.ChildColumns).Split(','))
                {
                    if (column!=idChild)
                    {
                        parameters.Add(_textBoxes[column].Text);
                    }
                }
                int result = _adapter.AddChild(parameters.ToArray());
                if (result!=0)
                {
                    MessageBox.Show(@"Successfully added");
                    Close();
                }
                else
                {
                    MessageBox.Show(@"Failed to add");
                }
            }
            catch (Exception exception)
            {
                MessageBox.Show(exception.Message);
            }
        }

        private void FormAddChild_Load(object sender, EventArgs e)
        {
            Text = @"Add" + Utils.GetResource(Resources.TableChild);
            var columns = Utils.GetResource(Resources.ChildColumns).Split(',');
            string childIdColumn = Utils.GetChildIdColumn(), parentIdColumn = Utils.GetParentIdColumn();
            int rowCount = 0;
            tableLayoutPanel1.RowCount = columns.Length - 1;
            var style = AnchorStyles.Left | AnchorStyles.Right;
            foreach (var column in columns)
            {
                if(column==childIdColumn)
                    continue;
                Label label = new Label
                {
                    Text = column + @":",
                    Font = new Font("Microsoft Sans Serif", 8F, FontStyle.Bold, GraphicsUnit.Point, 0),
                    ForeColor = Color.FromArgb(249, 247, 247),
                    Anchor = style,
                    TextAlign = ContentAlignment.MiddleCenter
                };
                TextBox textBox = new TextBox {Anchor = style};
                if (column==parentIdColumn)
                {
                    textBox.Text = _parentId.ToString();
                    textBox.ReadOnly = true;
                }
                tableLayoutPanel1.Controls.Add(label, 0, rowCount);
                tableLayoutPanel1.Controls.Add(textBox, 1, rowCount);
                _textBoxes.Add(column, textBox);
                rowCount++;
            }
        }
    }
}